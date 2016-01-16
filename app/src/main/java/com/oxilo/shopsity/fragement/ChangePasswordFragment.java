package com.oxilo.shopsity.fragement;

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
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oxilo.shopsity.ApplicationController;
import com.oxilo.shopsity.POJO.ModalAlreadyRegistred;
import com.oxilo.shopsity.POJO.ModalChangePassword;
import com.oxilo.shopsity.POJO.ModalLogin;
import com.oxilo.shopsity.R;
import com.oxilo.shopsity.logger.Log;
import com.oxilo.shopsity.utility.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChangePasswordFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    public static final int TOAST_LENGHT_SHORT_DURATION = 2500;
    private ModalLogin modalLogin;
    private String mParam2;

    private EditText mCurrentPasswordView,mNewPasswordView,mConfirmPasswordView;

    private OnFragmentInteractionListener mListener;

    private View mProgressView;
    private View mLoginFormView;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param modalLogin Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePasswordFragment newInstance(ModalLogin modalLogin, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, modalLogin);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            modalLogin = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        ActivityUtils.setupUI(view.findViewById(R.id.main_root),getActivity());

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUiWidget(view);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void initUiWidget(View view){
        mLoginFormView = view.findViewById(R.id.login_form);
        mProgressView = view.findViewById(R.id.login_progress);
        mCurrentPasswordView = (EditText)view.findViewById(R.id.old_password);
        mNewPasswordView = (EditText)view.findViewById(R.id.new_password);
        mConfirmPasswordView = (EditText)view.findViewById(R.id.confirm_password);

        AppCompatButton submit_button = (AppCompatButton)view.findViewById(R.id.email_sign_in_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptToChangePassword();
            }
        });
    }

    private void attemptToChangePassword(){
        // Reset errors.
        mCurrentPasswordView.setError(null);
        mNewPasswordView.setError(null);
        mConfirmPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String old_password = mCurrentPasswordView.getText().toString();
        String new_password = mNewPasswordView.getText().toString();
        String confirm_password = mConfirmPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a empty Old Password.
        if (TextUtils.isEmpty(old_password)) {
            mCurrentPasswordView.setError(getString(R.string.error_field_required));
            focusView = mCurrentPasswordView;
            cancel = true;
        }

//        if(!old_password.equals(modalLogin.getPassword())){
//            mCurrentPasswordView.setError(getString(R.string.error_current_password_wrong));
//            focusView = mCurrentPasswordView;
//            cancel = true;
//        }

        // Check for a empty new password.
        else if (TextUtils.isEmpty(new_password)) {
            mNewPasswordView.setError(getString(R.string.error_field_required));
            focusView = mNewPasswordView;
            cancel = true;
        }
        // Check for empty confirm password.
        else if (TextUtils.isEmpty(confirm_password)) {
            mConfirmPasswordView.setError(getString(R.string.error_field_required));
            focusView = mConfirmPasswordView;
            cancel = true;
        }



        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            doSomething();
        }
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
     * NETWORK SIGNUP CALL
     *
     */
    public void doSomething() {
        String URL = getResources().getString(R.string.mobikyte_base_url) + "data=" + Uri.encode(makeJsonBody().toString());
        StringRequest req = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showProgress(false);
                String dfgde = response.toString();
                VolleyLog.v("Response:%n %s", response);
                Gson gson = new GsonBuilder().create();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals(getResources().getString(R.string.response_success))){
                        ModalChangePassword modalChangePassword = gson.fromJson(response, ModalChangePassword.class);
                        modalLogin.setPassword(mCurrentPasswordView.getText().toString());
                        Toast.makeText(getActivity(),"your Password has been changed successfully",Toast.LENGTH_SHORT).show();
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                getFragmentManager().popBackStack();
//                            }
//                        }, TOAST_LENGHT_SHORT_DURATION);

                    }
                    else if (jsonObject.getString("status").equals(getResources().getString(R.string.response_error))){
                        ModalAlreadyRegistred modalAlreadyRegistred = gson.fromJson(response, ModalAlreadyRegistred.class);
                        Toast.makeText(getActivity(), modalAlreadyRegistred.getErrorString(), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Activity activity = getActivity();
                if(activity != null && isAdded())
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
            jsonObject.put("clientid",modalLogin.getClientid());
            jsonObject.put("email", modalLogin.getEmailid());
            jsonObject.put("password", modalLogin.getPassword());
            jsonObject.put("mobileno",modalLogin.getMobile() != null ? modalLogin.getMobile() : "12121223232");
            jsonObject.put("name",modalLogin.getName());
            jsonObject.put("business_name",modalLogin.getBusinessName());
            jsonObject.put("address",modalLogin.getAddress() != null ? modalLogin.getAddress() : "India");
            jsonObject.put("city",modalLogin.getCity() !=null ? modalLogin.getCity() : "Jaipur");
            jsonObject.put("state",modalLogin.getState() != null ? modalLogin.getState() : "Rajasthan");
            jsonObject.put("zip",modalLogin.getZip() != null ? modalLogin.getZip() : "302020");
            jsonObject.put("web_url",modalLogin.getWebsite() != null ? modalLogin.getWebsite() : "http://www.audianz.com");
            jsonObject.put("api","update_register_request");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("JSON STRING", "" + jsonObject.toString());
        return jsonObject;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView toolbar_title = (TextView) getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("Change Password");
    }

}
