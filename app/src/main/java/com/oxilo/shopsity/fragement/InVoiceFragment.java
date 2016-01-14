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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oxilo.shopsity.ApplicationController;
import com.oxilo.shopsity.MODAL.UserCampaign;
import com.oxilo.shopsity.MyAnimator;
import com.oxilo.shopsity.POJO.InVoiceObject;
import com.oxilo.shopsity.POJO.ModalAddCampign;
import com.oxilo.shopsity.POJO.ModalLogin;
import com.oxilo.shopsity.R;
import com.oxilo.shopsity.adapter.InVoiceAdapter;
import com.oxilo.shopsity.holder.GroupItem;
import com.oxilo.shopsity.logger.Log;
import com.oxilo.shopsity.utility.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InVoiceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InVoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InVoiceFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private UserCampaign userCampaign;
    private ModalAddCampign modalAddCampign;
    private ModalLogin modalLogin;


    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    Toolbar toolbar;
    private GroupItem groupItem;

    private View mProgressView;
    private View mLoginFormView;

    public InVoiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userCampaign Parameter 1.
     * @param modalAddCampign Parameter 2.
     * @param modalLogin Parameter 3.
     * @return A new instance of fragment InVoiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InVoiceFragment newInstance(UserCampaign userCampaign,ModalAddCampign modalAddCampign, ModalLogin modalLogin) {
        InVoiceFragment fragment = new InVoiceFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, userCampaign);
        args.putParcelable(ARG_PARAM2, modalAddCampign);
        args.putParcelable(ARG_PARAM3, modalLogin);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userCampaign = getArguments().getParcelable(ARG_PARAM1);
            modalAddCampign = getArguments().getParcelable(ARG_PARAM2);
            modalLogin = getArguments().getParcelable(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_in_voice, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUiWidget(view);
        if (groupItem == null)
            groupItem = new GroupItem();
        getInVoice();
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
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
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
        toolbar.setTitle("Invoice");
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
        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update the main content by replacing fragments
                getFragmentManager().popBackStack();
            }
        });

        mLoginFormView = view.findViewById(R.id.login_form);
        mProgressView = view.findViewById(R.id.login_progress);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyle_2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        MyAnimator itemAnimator = new MyAnimator();
        recyclerView.setItemAnimator(itemAnimator);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                InVoiceAdapter inVoiceAdapter = new InVoiceAdapter(groupItem.items);
//                recyclerView.setAdapter(inVoiceAdapter);
//            }
//        }, 3000);


    }

    /**
     * NETWORK SIGNUP CALL
     *
     */
    public void getInVoice() {
        showProgress(true);
        String URL = getResources().getString(R.string.mobikyte_campaign_invoice_url) + "data=" + Uri.encode(makeJsonBody().toString());
        StringRequest req = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showProgress(false);
                VolleyLog.v("Response:%n %s", response);
                Gson gson = new GsonBuilder().create();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals(getResources().getString(R.string.response_success))){
                        JSONArray jsonArray= jsonObject.getJSONArray("invoice_list");
                        if (jsonArray.get(1).equals(false) || jsonArray.get(1).equals("false")){
                            return;
                        }else{
                            JSONArray jObject = jsonArray.getJSONArray(1);
                            Log.e("RESPONSE STR", "" + jObject);

                            for (int k = 0; k<jObject.length();k++){
                                JSONObject jObject1 = jObject.getJSONObject(k);
                                InVoiceObject inVoiceObject = gson.fromJson(jObject1.toString(),InVoiceObject.class);
                                Log.e("MY STR", "" + inVoiceObject.getCurrency());
                                groupItem.items.add(inVoiceObject);
                            }
                            InVoiceAdapter inVoiceAdapter = new InVoiceAdapter(groupItem.items);
                            recyclerView.setAdapter(inVoiceAdapter);
                        }
//                        JSONArray jObject = jsonArray.getJSONArray(1);
//                        Log.e("RESPONSE STR", "" + jObject);
//
//                        for (int k = 0; k<jObject.length();k++){
//                            JSONObject jObject1 = jObject.getJSONObject(k);
//                            InVoiceObject inVoiceObject = gson.fromJson(jObject1.toString(),InVoiceObject.class);
//                            Log.e("MY STR", "" + inVoiceObject.getCurrency());
//                            groupItem.items.add(inVoiceObject);
//                        }
//                        InVoiceAdapter inVoiceAdapter = new InVoiceAdapter(groupItem.items);
//                        recyclerView.setAdapter(inVoiceAdapter);

                    }
                    else if (jsonObject.getString("status").equals(getResources().getString(R.string.response_error))){
                        FailFragment failFragment = FailFragment.newInstance(userCampaign,modalAddCampign,modalLogin);
                        ActivityUtils.launchFragementWithAnimation(failFragment,getActivity());
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
                Toast.makeText(getActivity(), "Something went wrong. please try it again", Toast.LENGTH_SHORT).show();
            }
        });

        // add the request object to the queue to be executed
        ApplicationController.getInstance().addToRequestQueue(req,ApplicationController.REGISTRATION_TAG);
    }



    private JSONObject makeJsonBody(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("clientid", "" + modalLogin.getClientid());
            jsonObject.put("api","fetch_invoice_request");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("JSON STRING", "" + jsonObject.toString());
        return jsonObject;
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
