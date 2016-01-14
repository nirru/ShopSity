package com.oxilo.shopsity.fragement;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oxilo.shopsity.ApplicationController;
import com.oxilo.shopsity.Constants;
import com.oxilo.shopsity.MODAL.MobiKytePlaceCampaignInfo;
import com.oxilo.shopsity.MODAL.UserCampaign;
import com.oxilo.shopsity.POJO.ModalAddCampign;
import com.oxilo.shopsity.POJO.ModalAlreadyRegistred;
import com.oxilo.shopsity.POJO.ModalLogin;
import com.oxilo.shopsity.R;
import com.oxilo.shopsity.logger.Log;
import com.oxilo.shopsity.utility.ActivityUtils;
import com.oxilo.shopsity.volley.VolleyErrorHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Action.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Action#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Action extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    // TODO: Rename and change types of parameters
    private UserCampaign userCampaign;
    private ModalAddCampign modalAddCampign;
    private ModalLogin modalLogin;

    private OnFragmentInteractionListener mListener;
//    private DataPullingInterface mHostInterface;

    /**---UI WIDGET Declaration
     *
     */
    private EditText webSiteView,callView;
    private AppCompatButton btn_next;
    Toolbar toolbar;
    public MobiKytePlaceCampaignInfo place = null;

    private View mLoginFormView,mProgressView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userCampaign Parameter 1.
     * @return A new instance of fragment Action.
     */
    // TODO: Rename and change types and number of parameters
    public static Action newInstance(MobiKytePlaceCampaignInfo place,UserCampaign userCampaign,ModalLogin modalLogin) {
        Action fragment = new Action();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, userCampaign);
        args.putParcelable(ARG_PARAM3,modalLogin);
        args.putParcelable(ARG_PARAM4,place);
        fragment.setArguments(args);
        return fragment;
    }

    public Action() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userCampaign = getArguments().getParcelable(ARG_PARAM1);
            modalLogin = getArguments().getParcelable(ARG_PARAM3);
            place = getArguments().getParcelable(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_action, container, false);
        ActivityUtils.setupUI(v.findViewById(R.id.main_root), getActivity());
        return v;
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle("Action");
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
        public void onFragmentInteraction(Uri uri);
    }

    private void initUiWidget(View view){
        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("Action");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update the main content by replacing fragments
                getFragmentManager().popBackStack();
            }
        });
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);

        mLoginFormView = view.findViewById(R.id.login_form);
        mProgressView = view.findViewById(R.id.login_progress);

        webSiteView = (EditText)view.findViewById(R.id.website);
        callView = (EditText)view.findViewById(R.id.call);

        AppCompatButton btn_next = (AppCompatButton)view.findViewById(R.id.email_sign_in_button);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        // Reset errors.
        webSiteView.setError(null);
        callView.setError(null);

        // Store values at the time of the login attempt.
        String web = webSiteView.getText().toString();
        String call = callView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for  URL, if the user entered one.
        if (TextUtils.isEmpty(web)) {
            webSiteView.setError(getString(R.string.error_field_required));
            focusView = webSiteView;
            cancel = true;
        }

        // Check for a valid call address.
       else if (TextUtils.isEmpty(call)) {
            callView.setError(getString(R.string.error_field_required));
            focusView = callView;
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
           doSomething();
        }
    }

    /**
     * NETWORK SIGNUP CALL
     *
     */
    public void doSomething() {
        String URL = getResources().getString(R.string.mobikyte_campaign_base_url) + "data=" + Uri.encode(makeJsonBody().toString());
        StringRequest req = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showProgress(false);
                VolleyLog.v("Response:%n %s", response);
                Gson gson = new GsonBuilder().create();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals(getResources().getString(R.string.response_success))){
                        ModalAddCampign modalAddCampign = gson.fromJson(response, ModalAddCampign.class);
                        Preview preview = Preview.newInstance(place, userCampaign, modalAddCampign, modalLogin);
                        ActivityUtils.launchFragementWithAnimation(preview,getActivity());
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
                Toast.makeText(getActivity(), VolleyErrorHelper.getErrorType(error, getActivity()), Toast.LENGTH_SHORT).show();
            }
        });

        req.setRetryPolicy(new DefaultRetryPolicy(
                Constants.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // add the request object to the queue to be executed
        ApplicationController.getInstance().addToRequestQueue(req,ApplicationController.REGISTRATION_TAG);
    }

    private JSONObject makeJsonBody(){
        double lat = 0.0,lng = 0.0;
        String address = "India", web, action;

        if (webSiteView.getText().toString().equals("") || webSiteView.getText().toString().equals("null"))
        {
            web = "http://wwww.mobikyte.com";
        }else{
            web = webSiteView.getText().toString();
        }

        if (callView.getText().toString().equals("") || callView.getText().toString().equals("null"))
        {
            action = "123456789";
        }else{
            action = callView.getText().toString();
        }

        try {
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                address = "India";
                lat = 27.00;
                lng = 27.00;
            }else{
                if (place.getAddress()!=null){
                    address = place.getAddress().toString().trim();
                    lat = place.getLat();
                    lng = place.getLng();
                }
                else
                {
                    address = "India";
                    lat = 27.00;
                    lng = 27.00;
                }
            }

        }catch (NullPointerException ex){
            ex.printStackTrace();
        }

        userCampaign.setCall(action);
        userCampaign.setWebRequestUrl(web);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("accbal","25");
            jsonObject.put("camp_name",userCampaign.getCampaignTitle());
            jsonObject.put("click_to_action",action);
            jsonObject.put("clientid",modalLogin.getClientid());
            jsonObject.put("lat", lat);
            jsonObject.put("lon", lng);
            jsonObject.put("price_in_usd","25.0");
            jsonObject.put("promote_msg",userCampaign.getPromotionMessage());
            jsonObject.put("startTime",milliseconds(userCampaign.getDateTime()));
            jsonObject.put("store_address",address);
            jsonObject.put("web_url",web);
            jsonObject.put("api","add_campaign_request");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("JSON STRING", "" + jsonObject.toString());
        return jsonObject;
    }

    public long milliseconds(String date) {
        String date_ = date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(date);
            long timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
            return timeInMilliseconds;
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
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
}
