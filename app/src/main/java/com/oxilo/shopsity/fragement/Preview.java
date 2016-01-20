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

import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;

import com.oxilo.shopsity.MODAL.MobiKytePlaceCampaignInfo;
import com.oxilo.shopsity.MODAL.UserCampaign;
import com.oxilo.shopsity.POJO.ModalAddCampign;
import com.oxilo.shopsity.POJO.ModalLogin;
import com.oxilo.shopsity.R;
import com.oxilo.shopsity.activity.MapsActivity;
import com.oxilo.shopsity.utility.ActivityUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Preview.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Preview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Preview extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    // TODO: Rename and change types of parameters
    private UserCampaign userCampaign;
    ModalAddCampign modalAddCampign;
    ModalLogin modalLogin;
//    Toolbar toolbar;

    /**UI WIDGET
     *
     */
    private TextView camapignView,startDateView,locationView,actionView,campaignTitleView,campaignMessageView;


    private OnFragmentInteractionListener mListener;
//    private DataPullingInterface mHostInterface;

    public MobiKytePlaceCampaignInfo place = null;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userCampaign Parameter 1.
     * @param modalAddCampign Parameter 2.
     * @return A new instance of fragment Preview.
     */
    // TODO: Rename and change types and number of parameters
    public static Preview newInstance(MobiKytePlaceCampaignInfo place,UserCampaign userCampaign,ModalAddCampign modalAddCampign,ModalLogin modalLogin) {
        Preview fragment = new Preview();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, userCampaign);
        args.putParcelable(ARG_PARAM2, modalAddCampign);
        args.putParcelable(ARG_PARAM3, modalLogin);
        args.putParcelable(ARG_PARAM4, place);
        fragment.setArguments(args);
        return fragment;
    }

    public Preview() {
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
        return inflater.inflate(R.layout.fragment_preview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUIWidget(view);
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
//        toolbar.setTitle("Preview");
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView toolbar_title = (TextView) getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("Preview");
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

    private void initUIWidget(View v){
//         toolbar = (Toolbar)v.findViewById(R.id.toolbar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // update the main content by replacing fragments
//                Intent i = new Intent(getActivity(),MapsActivity.class);
//                i.putExtra(getResources().getString(R.string.praceable_modal_regsitration), modalLogin);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(i);
//            }
//        });
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);

        camapignView = (TextView)v.findViewById(R.id.action_campaign_id);
        startDateView = (TextView)v.findViewById(R.id.action_start_date);
        locationView = (TextView)v.findViewById(R.id.action_location);
        actionView = (TextView)v.findViewById(R.id.action_action);
        campaignTitleView = (TextView)v.findViewById(R.id.campaign_title);
        campaignMessageView = (TextView)v.findViewById(R.id.campaign_message);

        String orderId = null;
        double lat = 20.0;
        double lng = 34.0;
        if (modalAddCampign != null){
            orderId = "" + modalAddCampign.getOrderId();
        }else {
            orderId = "" + 456;
        }

        campaignTitleView.setText(userCampaign.getCampaignTitle());
        campaignMessageView.setText(userCampaign.getPromotionMessage());
        actionView.setText(userCampaign.getWebRequestUrl());
        try {
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                locationView.setText("Mumbai");
            }else{
                if (place != null ){
                    locationView.setText(place.getAddress().toString());
                }
            }
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }


        camapignView.setText(orderId);
        startDateView.setText(userCampaign.getStartDate());

//        locationView.setText(String.format("%.2f", place.getLat()) + "/" + String.format("%.2f", place.getLng()));

        AppCompatButton btn_next = (AppCompatButton)v.findViewById(R.id.email_sign_in_button);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Pay checkout = Pay.newInstance(place,userCampaign,modalAddCampign,modalLogin);
                ActivityUtils.launchFragementWithAnimation(checkout,getActivity());
            }
        });

    }

}
