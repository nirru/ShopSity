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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.oxilo.shopsity.DataPullingInterface;
import com.oxilo.shopsity.MODAL.MobiKytePlaceCampaignInfo;
import com.oxilo.shopsity.MODAL.UserCampaign;
import com.oxilo.shopsity.POJO.ModalAddCampign;
import com.oxilo.shopsity.POJO.ModalCheckOut;
import com.oxilo.shopsity.POJO.ModalLogin;
import com.oxilo.shopsity.R;
import com.oxilo.shopsity.logger.Log;
import com.oxilo.shopsity.utility.ActivityUtils;
import com.oxilo.shopsity.volley.VolleyErrorHelper;
import com.paytm.pgsdk.PaytmMerchant;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.*;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Checkout.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Checkout#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Checkout extends Fragment {
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

    private View mLoginFormView,mProgressView;

    private OnFragmentInteractionListener mListener;
    private DataPullingInterface mHostInterface;
//    Toolbar toolbar;
    public MobiKytePlaceCampaignInfo place = null;

    /**Paytm Detail
     *
     */
    private int randomInt = 0;
    private PaytmPGService Service = null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userCampaign Parameter 1.
     * @param modalAddCampign Parameter 2.
     * @param modalLogin Parameter 3.
     * @return A new instance of fragment Checkout.
     */
    // TODO: Rename and change types and number of parameters
    public static Checkout newInstance(MobiKytePlaceCampaignInfo place,UserCampaign userCampaign,ModalAddCampign modalAddCampign, ModalLogin modalLogin) {
        Checkout fragment = new Checkout();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, userCampaign);
        args.putParcelable(ARG_PARAM2, modalAddCampign);
        args.putParcelable(ARG_PARAM3, modalLogin);
        args.putParcelable(ARG_PARAM4,place);
        fragment.setArguments(args);
        return fragment;
    }

    public Checkout() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userCampaign = getArguments().getParcelable(ARG_PARAM1);
            modalAddCampign = getArguments().getParcelable(ARG_PARAM2);
            modalLogin = getArguments().getParcelable(ARG_PARAM3);
            place = getArguments().getParcelable(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false);
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
        showProgress(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView toolbar_title = (TextView) getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("Checkout");
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

    private void initUiWidget(View v){
//        toolbar = (Toolbar)v.findViewById(R.id.toolbar);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // update the main content by replacing fragments
//                getFragmentManager().popBackStack();
//            }
//        });
//
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);

        mLoginFormView = v.findViewById(R.id.login_form);
        mProgressView = v.findViewById(R.id.login_progress);
        TextView invoice_id = (TextView) v.findViewById(R.id.invioce_id);
        TextView order = (TextView)v.findViewById(R.id.order_id);
        TextView campaign = (TextView)v.findViewById(R.id.campaign);
        TextView start_date = (TextView)v.findViewById(R.id.start_date);
        TextView plan = (TextView)v.findViewById(R.id.plan);
        TextView inr = (TextView)v.findViewById(R.id.inr);

        invoice_id.setText("" + modalAddCampign.getOrderId());
        order.setText("" + modalAddCampign.getOrderId());
        campaign.setText("" + modalAddCampign.getOrderId());
        start_date.setText(userCampaign.getStartDate());

        AppCompatButton btn_next = (AppCompatButton)v.findViewById(R.id.email_sign_in_button);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(true);
                doCheckOut();
            }
        });

        ImageView paytm = (ImageView)v.findViewById(R.id.paytm);
        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPaytm();
            }
        });
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
    public void doCheckOut() {
        String URL = getResources().getString(R.string.mobikyte_campaign_base_url) + "data=" + Uri.encode(makeJsonBody().toString());
        Log.e("KDFE", " 0 " + URL);
        StringRequest req = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showProgress(false);
                String ss = response.toString();
                Log.e("RESPONSE TO STRING" , "" + response.toString());
                VolleyLog.v("Response:%n %s", response);
                Gson gson = new GsonBuilder().create();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals(getResources().getString(R.string.response_success))){
                        ModalCheckOut modalCheckOut = gson.fromJson(response, ModalCheckOut.class);
                        ThankYou thankYou = ThankYou.newInstance(userCampaign,modalAddCampign,modalLogin,modalCheckOut);
                        ActivityUtils.launchFragementWithAnimation(thankYou,getActivity());
                    }
                    else if (jsonObject.getString("status").equals(getResources().getString(R.string.response_error))){
                        FailFragment failFragment = FailFragment.newInstance(userCampaign,modalAddCampign,modalLogin);
                        ActivityUtils.launchFragementWithAnimation(failFragment,getActivity());
                    }
                } catch (JSONException e) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                    e.printStackTrace();
                }
                catch (NumberFormatException ex){
                    ex.printStackTrace();
                }
                catch (NullPointerException ex){
                    ex.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Activity activity = getActivity();
                if(activity != null && isAdded())
                    showProgress(false);
                Toast.makeText(getActivity(), "Something went wrong. please try it again", Toast.LENGTH_SHORT).show();
                if (error.networkResponse != null){
                    if(error.networkResponse.statusCode==500) {
                        Log.e("error", "" + new String(error.networkResponse.data));
                    }
                }
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
        String country;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            country = "India";
        }else{
            if (place.getCountry()!=null){
                country = place.getCountry().toString().trim();
            }
            else
            {
                country = "India";
            }
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("currency","INR");
            jsonObject.put("cust_country",country);
            jsonObject.put("cust_email",modalLogin.getEmailid() != null ? modalLogin.getEmailid() : "nur@gmail.com");
            jsonObject.put("cust_id", modalLogin.getClientid() != null ? modalLogin.getClientid() : "253");
            jsonObject.put("cust_mobile", modalLogin.getMobile()!= null ? modalLogin.getMobile() : "77375454");
            jsonObject.put("cust_name",modalLogin.getName() != null ? modalLogin.getName() : "Shopsity");
            jsonObject.put("invoice_id",modalAddCampign.getOrderId());
            jsonObject.put("isFreePlan","1");
            jsonObject.put("order_amount",modalAddCampign.getAmount() != null ? modalAddCampign.getAmount() : "232");
            jsonObject.put("order_date",userCampaign.getDateTime());
            jsonObject.put("order_id",modalAddCampign.getOrderId());
            jsonObject.put("order_status",1);
            jsonObject.put("product_sku",modalAddCampign.getOrderId());
            jsonObject.put("sub_order_id",modalAddCampign.getOrderId());
            jsonObject.put("api","add_order_detail_request");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("JSON STRING", "" + jsonObject.toString());
        return jsonObject;
    }


    private String createJson(){
        String country;
        if (place.getCountry()!=null){
            country = place.getCountry().toString().trim();
        }
        else
        {
            country = "India";
        }
//        String hh = "{\"order_date\":\"" + userCampaign.getDateTime() + "\",\"cust_email\":\"" + modalLogin.getEmailid() + "\",\"cust_id\":\"" + modalLogin.getClientid() + "\",\"order_id\":\""+ modalAddCampign.getOrderId() + "\",\"cust_country\":\"" + country + "\",\"currency\":\"INR\",\"cust_name\":\"" + modalLogin.getName() + "\",\"isFreePlan\":\"1\",\"sub_order_id\":\"" + modalAddCampign.getOrderId() + "\",\"product_sku\":\""+ modalAddCampign.getOrderId() + "\",\"cust_mobile\":\"" + modalLogin.getMobile() + "\",\"order_status\":\"1\",\"order_amount\":\"" + modalAddCampign.getAmount() + "\",\"api\":\"add_order_detail_request\",\"invoice_id\" :\"" + modalAddCampign.getOrderId() + "\"}";
        String hh = "{\"order_date\":\"" + userCampaign.getDateTime() + "\",\"cust_email\":\"" + "us@us.com" + "\",\"cust_id\":\"" + "253" + "\",\"order_id\":\""+ "10410" + "\",\"cust_country\":\"" + "India" + "\",\"currency\":\"INR\",\"cust_name\":\"" + "BUSINESSNAME" + "\",\"isFreePlan\":\"1\",\"sub_order_id\":\"" + "10410" + "\",\"product_sku\":\""+ "10410" + "\",\"cust_mobile\":\"" + "7737502781" + "\",\"order_status\":\"1\",\"order_amount\":\"" + "0.0" + "\",\"api\":\"add_order_detail_request\",\"invoice_id\" :\"" + "10410" + "\"}";

        return hh;
    }

    private void openPaytm(){
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(1000);

//        Service = PaytmPGService.getStagingService(); //for testing environment
		Service = PaytmPGService.getStagingService(); //for production environment
//

		/*PaytmMerchant constructor takes two parameters
		1) Checksum generation url
		2) Checksum verification url
		Merchant should replace the below values with his values*/

        PaytmMerchant Merchant = new PaytmMerchant("https://pguat.paytm.com/merchant-chksum/ChecksumGenerator","https://pguat.paytm.com/merchant-chksum/ValidateChksum");

        //below parameter map is required to construct PaytmOrder object, Merchant should replace below map values with his own values

        HashMap<String, String> paramMap = new HashMap<>();

        //these are mandatory parameters
        paramMap.put("REQUEST_TYPE", "DEFAULT");
        paramMap.put("ORDER_ID", "" + modalAddCampign.getOrderId());
        paramMap.put("MID", "klbGlV59135347348753");
        paramMap.put("CUST_ID", modalLogin.getClientid());
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("INDUSTRY_TYPE_ID", "Retail");
        paramMap.put("WEBSITE", "paytm");
        paramMap.put("TXN_AMOUNT", modalAddCampign.getAmount());
        paramMap.put("THEME", "merchant");



        PaytmOrder Order = new PaytmOrder(paramMap);


        Service.initialize(Order, Merchant,null);
        Service.startPaymentTransaction(getActivity(), false, false, new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionSuccess(Bundle bundle) {
                Log.i("Error", "someErrorOccurred :" + "PAyment SUCCESS");
            }

            @Override
            public void onTransactionFailure(String s, Bundle bundle) {
                Log.i("Error","someErrorOccurred :"+s);
            }

            @Override
            public void networkNotAvailable() {

            }

            @Override
            public void clientAuthenticationFailed(String s) {
                Log.i("Error","clientAuthenticationFailed :"+s);

            }

            @Override
            public void someUIErrorOccurred(String s) {

            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {
                Log.i("Error","onErrorLoadingWebPage :"+s1);
            }
        });
    }

}
