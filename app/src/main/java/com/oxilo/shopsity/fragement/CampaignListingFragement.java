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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oxilo.shopsity.ApplicationController;
import com.oxilo.shopsity.Constants;
import com.oxilo.shopsity.POJO.CampList;
import com.oxilo.shopsity.POJO.ModalAlreadyRegistred;
import com.oxilo.shopsity.POJO.ModalFetchCampaign;
import com.oxilo.shopsity.POJO.ModalLogin;
import com.oxilo.shopsity.R;
import com.oxilo.shopsity.expandcollapse.VerticalChild;
import com.oxilo.shopsity.expandcollapse.VerticalExpandableAdapter;
import com.oxilo.shopsity.expandcollapse.VerticalParent;
import com.oxilo.shopsity.holder.GroupItem;
import com.oxilo.shopsity.logger.Log;
import com.oxilo.shopsity.utility.ActivityUtils;
import com.oxilo.shopsity.utility.DateUtils;
import com.oxilo.shopsity.volley.VolleyErrorHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CampaignListingFragement.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CampaignListingFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CampaignListingFragement extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ModalLogin modalLogin;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View mProgressView;
    private View mLoginFormView;
    private RecyclerView mRecyleView;
    private GroupItem groupItem;
    private VerticalExpandableAdapter mExpandableAdapter;
    private Toolbar toolbar;

    int myPosition = 0;

    public CampaignListingFragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param modalLogin Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CampaignListingFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static CampaignListingFragement newInstance(ModalLogin modalLogin, String param2) {
        CampaignListingFragement fragment = new CampaignListingFragement();
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
        return inflater.inflate(R.layout.fragment_campaign_listing_fragement, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiWidget(view);
        showProgress(true);
        fetchcampaign();

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
//        ((CampaignListAdapter)campaignListAdapter).setOnItemClickListener(new CampaignListAdapter.MyClickListener() {
//            @Override
//            public void onItemClick(int position, View v) {
//               Fragment report = ReportFragement.newInstance(modalLogin,groupItem.campLists.get(position));
//                ActivityUtils.launchFragementWithAnimation(report,getActivity());
//            }
//        });
//        if (mExpandableAdapter!=null){
//            ((VerticalExpandableAdapter)mExpandableAdapter).setOnItemClickListener(new VerticalExpandableAdapter.DashBoard() {
//                @Override
//                public void onItemClick(int position, View v) {
//                    Fragment report = ReportFragement.newInstance(modalLogin,groupItem.campLists.get(position));
//                    ActivityUtils.launchFragementWithAnimation(report,getActivity());
//                }
//            });
//        }
        toolbar.setTitle("Reports");
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
        toolbar.setTitle("Reports");

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

        mProgressView = view.findViewById(R.id.login_progress);
        mLoginFormView = view.findViewById(R.id.login_form);
        mRecyleView = (RecyclerView)view.findViewById(R.id.recyle);
//        mRecyleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        groupItem = new GroupItem();
//        campaignListAdapter = new CampaignListAdapter(groupItem.campLists,getActivity());
//        mRecyleView.setAdapter(campaignListAdapter);
    }

    private void fetchcampaign(){
        showProgress(true);
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
                        ModalFetchCampaign modalFetchCampign = gson.fromJson(response, ModalFetchCampaign.class);
                        groupItem.campLists = modalFetchCampign.getCampList();
                        // Create a new adapter with  data items
                        mExpandableAdapter = new VerticalExpandableAdapter(getActivity(), setUpTestData(groupItem.campLists), new VerticalExpandableAdapter.DashBoard() {
                            @Override
                            public void onItemClick(int position, View v) {
                                try {
                                    Log.e("fefe","" + position);
                                    Fragment report;
                                    if (position == -1 || myPosition == -1)
                                    {
                                        myPosition = 0;
                                    }
                                    if (myPosition == 0){
                                        report = ReportFragement.newInstance(modalLogin,groupItem.campLists.get(myPosition));
                                    }else {
                                        if (position - 2 > 0)
                                            report = ReportFragement.newInstance(modalLogin,groupItem.campLists.get(position - 2));
                                        else
                                            report = ReportFragement.newInstance(modalLogin,groupItem.campLists.get(0));
                                    }

                                    ActivityUtils.launchFragementWithAnimation(report,getActivity());
                                }
                                catch (Exception ex){
                                    ex.printStackTrace();
                                }
                            }
                        });

                        // Attach this activity to the Adapter as the ExpandCollapseListener
                        mExpandableAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
                            @Override
                            public void onListItemExpanded(int position) {
                                Log.e("CHEEE","" + position);
                                myPosition = position;
                            }

                            @Override
                            public void onListItemCollapsed(int position) {

                            }
                        });

                        // Set the RecyclerView's adapter to the ExpandableAdapter we just created
                        mRecyleView.setAdapter(mExpandableAdapter);
                        // Set the layout manager to a LinearLayout manager for vertical list
                        mRecyleView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                Toast.makeText(getActivity(), VolleyErrorHelper.getErrorType(error, getActivity()),Toast.LENGTH_SHORT).show();

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

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("clientid",modalLogin.getClientid());
            jsonObject.put("api","fetch_campaign_request");
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

    /**
     * Method to set up test data used in the RecyclerView.
     *
     * Each child list item contains a string.
     * Each parent list item contains a number corresponding to the number of the parent and a string
     * that contains a message.
     * Each parent also contains a list of children which is generated in this. Every odd numbered
     * parent gets one child and every even numbered parent gets two children.
     *
     * @return A List of Objects that contains all parent items. Expansion of children are handled in the adapter
     */
    private List<VerticalParent> setUpTestData(List<CampList> campLists) {
        DateUtils dateUtils = new DateUtils();
        Collections.sort(campLists,dateUtils);
        List<VerticalParent> verticalParentList = new ArrayList<>();

        for (int i = 0; i < campLists.size(); i++) {
            List<VerticalChild> childItemList = new ArrayList<>();

            CampList campList = campLists.get(i);

            VerticalChild verticalChild = new VerticalChild();
            verticalChild.setCampList(campList);
            childItemList.add(verticalChild);

            VerticalParent verticalParent = new VerticalParent();
            verticalParent.setChildItemList(childItemList);
            verticalParent.setParentNumber(i);
            verticalParent.setParentText("" + campList.getCampName());
//            verticalParent.setParentText("" + ActivityUtils.GetDateTime(Long.valueOf(campList.getStartDate())));
            if (i == 0) {
                verticalParent.setInitiallyExpanded(true);
            }
            verticalParentList.add(verticalParent);
        }

        return verticalParentList;
    }
}
