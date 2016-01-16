package com.oxilo.shopsity.activity;

/*
 All Copyright, Audianz Network Pvt ltd.
CIN:
All intellectual property, code ownership belongs un-conditionally
to Audianz Network Pvt Ltd. No unauthorised code copying,
redistribution and editing is permitted.
Author: Audianz Network Pvt Ltd
CIN:
*/

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.oxilo.shopsity.ApplicationController;
import com.oxilo.shopsity.POJO.ModalAlreadyRegistred;
import com.oxilo.shopsity.POJO.ModalLogin;
import com.oxilo.shopsity.R;
import com.oxilo.shopsity.logger.Log;
import com.sithagi.countrycodepicker.CountryPicker;
import com.sithagi.countrycodepicker.CountryPickerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SignUp extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView,mOrginizationView,mMobileView,mCountryView;
    private TextView mAlreadyLoginView;
    private Button mEmailSignInButton;
    private View mProgressView;
    private View mLoginFormView;

    //Country picker refrences and variable
    CountryPicker picker;
    String dialerCode;

    //Activity Transition Instance
    ExitActivityTransition exitTransition;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Set up the ToolBar.
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("SignUp");
        ((AppCompatActivity) SignUp.this).setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        exitTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.sign_card)).start(savedInstanceState);
        initUiwidget();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (exitTransition!=null){
            exitTransition.exit(SignUp.this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_UP) {
            final View view = getCurrentFocus();

            if(view != null) {
                final boolean consumed = super.dispatchTouchEvent(ev);

                final View viewTmp = getCurrentFocus();
                final View viewNew = viewTmp != null ? viewTmp : view;

                if(viewNew.equals(view)) {
                    final Rect rect = new Rect();
                    final int[] coordinates = new int[2];

                    view.getLocationOnScreen(coordinates);

                    rect.set(coordinates[0], coordinates[1], coordinates[0] + view.getWidth(), coordinates[1] + view.getHeight());

                    final int x = (int) ev.getX();
                    final int y = (int) ev.getY();

                    if(rect.contains(x, y)) {
                        return consumed;
                    }
                }
                else if(viewNew instanceof EditText) {
                    return consumed;
                }

                final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                inputMethodManager.hideSoftInputFromWindow(viewNew.getWindowToken(), 0);

                viewNew.clearFocus();

                return consumed;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * All Ui Widget initilazation goes here
     */
    private void initUiwidget(){
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mOrginizationView = (EditText)findViewById(R.id.input_org_name);
        mMobileView = (EditText)findViewById(R.id.input_mobile_number);
        mCountryView = (EditText)findViewById(R.id.input_country_name);
        mAlreadyLoginView = (TextView)findViewById(R.id.already_member);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        picker = CountryPicker.newInstance("SelectCountry");

        mAlreadyLoginView.setOnClickListener(l);
        mEmailSignInButton.setOnClickListener(l);
        mCountryView.setOnClickListener(l);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */

    private void attemptLogin() {
        // Reset errors.
        mOrginizationView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mMobileView.setError(null);
        mCountryView.setError(null);

        // Store values at the time of the login attempt.
        String orginization = mOrginizationView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String mobile = mMobileView.getText().toString();
        String country = mCountryView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid Orginiazation name.
        if (TextUtils.isEmpty(orginization)) {
            mOrginizationView.setError(getString(R.string.error_field_required));
            focusView = mOrginizationView;
            cancel = true;
        }

        // Check for a valid email address.
        else if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
//        else if(!isPasswordValid(password)){
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
//        }

        // Check for a valid Country, if the user select one.
        else if (TextUtils.isEmpty(country)) {
            mCountryView.setError(getString(R.string.error_invalid_country));
            focusView = mCountryView;
            cancel = true;
        }
        // Check for a valid mobile, if the user entered one.
        else if (TextUtils.isEmpty(mobile) || (mobile.length()==9)) {
            mMobileView.setError(getString(R.string.error_invalid_mobile));
            focusView = mMobileView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

           // Authicate user
            doSomething();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        } else {
           return true;
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        if (password.length()>4)
            return true;
        else
        return false;
    }



    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(SignUp.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Open Country Picker Dialog.
     */

    private void countryPicker(){
        picker.show(getSupportFragmentManager(), "COUNTRY_CODE_PICKER");
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode) {
                mCountryView.setError(null);
                dialerCode = code;
                mCountryView.setText(name);
                picker.dismiss();
            }
        });
    }

    private void switchToLoginActivity(){
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
            // Do something for lollipop and above versions
                    final Intent intent = new Intent(SignUp.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    return;
        } else {
            // do something for phones running an SDK before lollipop
            exitTransition.exit(SignUp.this);
            return;
        }

    }

    /**
     * NETWORK SIGNUP CALL
     *
     */
    public void doSomething() {
        String URL = getResources().getString(R.string.mobikyte_base_url) + "data=" + makeJsonBody();
        StringRequest req = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showProgress(false);
                VolleyLog.v("Response:%n %s", response);
                Gson gson = new GsonBuilder().create();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals(getResources().getString(R.string.response_success))){
                        ModalLogin modalRegistration = gson.fromJson(response, ModalLogin.class);
                        Intent i = new Intent(SignUp.this,WelcomeActivity.class);
                        i.putExtra(getResources().getString(R.string.praceable_modal_regsitration), modalRegistration);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        return;
                    }
                    else if (jsonObject.getString("status").equals(getResources().getString(R.string.response_error))){
                        ModalAlreadyRegistred modalAlreadyRegistred = gson.fromJson(response, ModalAlreadyRegistred.class);
                        Toast.makeText(SignUp.this,modalAlreadyRegistred.getErrorString(),Toast.LENGTH_LONG).show();
                        mEmailView.setError(getString(R.string.error_invalid_email));
                        mEmailView.requestFocus();
                        return;
                    }
                } catch (JSONException e) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false);
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

       // add the request object to the queue to be executed
        ApplicationController.getInstance().addToRequestQueue(req,ApplicationController.REGISTRATION_TAG);
    }



    private JSONObject makeJsonBody(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("country",mCountryView.getText().toString().trim());
            jsonObject.put("name",mOrginizationView.getText().toString().trim());
            jsonObject.put("emailid",mEmailView.getText().toString().trim());
            jsonObject.put("password",mPasswordView.getText().toString().trim());
            jsonObject.put("mobile",mMobileView.getText().toString().trim());
            jsonObject.put("api","register_request");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("JSON STRING", "" + jsonObject.toString());
        return jsonObject;
    }
    /**
     * Widget Click Listener.
     */

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.input_country_name:
                    countryPicker();
                    break;
                case R.id.email_sign_in_button:
                    attemptLogin();
                    break;
                case R.id.already_member:
                    switchToLoginActivity();
                default:
                    break;
            }
        }
    };
}
