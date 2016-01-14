package com.oxilo.shopsity.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
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
import com.oxilo.shopsity.R;
import com.oxilo.shopsity.logger.Log;
import com.oxilo.shopsity.utility.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotActivity extends AppCompatActivity {

    Toolbar toolbar;
    //Activity Transition Instance
    ExitActivityTransition exitTransition;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private AppCompatButton mForgotPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Forgot Password");
        ((AppCompatActivity) ForgotActivity.this).setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        exitTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.ht_main)).start(savedInstanceState);

        initUiwidget();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (exitTransition!=null){
            exitTransition.exit(ForgotActivity.this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == android.R.id.home) {
            if (exitTransition!= null) {
                exitTransition.exit(ForgotActivity.this);
            }
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
     * Init UI widget
     */

    private void initUiwidget(){
        mForgotPasswordButton = (AppCompatButton)findViewById(R.id.email_sign_in_button);
        mEmailView = (AutoCompleteTextView)findViewById(R.id.email);

        mForgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSomething();
            }
        });
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */

    public void doSomething() {
        String URL = getResources().getString(R.string.mobikyte_base_url) + "data=" + makeJsonBody();
        StringRequest req = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                VolleyLog.v("Response:%n %s", response);
                Gson gson = new GsonBuilder().create();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals(getResources().getString(R.string.response_success))){
                        ActivityUtils.finishMyCurrentActivityAfterShowingToast(ForgotActivity.this,"Your password has been set to your mail.");
                    }
                    else if (jsonObject.getString("status").equals(getResources().getString(R.string.response_error))){
                        ModalAlreadyRegistred modalAlreadyRegistred = gson.fromJson(response, ModalAlreadyRegistred.class);
                        Toast.makeText(ForgotActivity.this, modalAlreadyRegistred.getErrorString(), Toast.LENGTH_LONG).show();
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
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        // add the request object to the queue to be executed
        ApplicationController.getInstance().addToRequestQueue(req, ApplicationController.LOGIN_TAG);

    }



    private JSONObject makeJsonBody(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("emailid",mEmailView.getText().toString().trim());
            jsonObject.put("api","forget_pass_request");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("JSON STRING", "" + jsonObject.toString());
        return jsonObject;
    }
}
