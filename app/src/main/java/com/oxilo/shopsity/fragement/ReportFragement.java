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
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.oxilo.shopsity.POJO.CampList;
import com.oxilo.shopsity.POJO.ModalLogin;
import com.oxilo.shopsity.R;
import com.oxilo.shopsity.SocialAuthListener;
import com.oxilo.shopsity.fragement.graph.HeatMapFragement;
import com.oxilo.shopsity.fragement.graph.PieFragment;
import com.oxilo.shopsity.ui.CustomTextView;
import com.oxilo.shopsity.utility.ActivityUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReportFragement.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReportFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragement extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ModalLogin modalLogin;
    private CampList camInfo;

    private OnFragmentInteractionListener mListener;
    SocialAuthListener socialAuthListener;

    private View mProgressView;
    private View mLoginFormView;

//    Toolbar toolbar;

    public ReportFragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param modalLogin Parameter 1.
     * @param camInfo Parameter 2.
     * @return A new instance of fragment ReportFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportFragement newInstance(ModalLogin modalLogin, CampList camInfo) {
        ReportFragement fragment = new ReportFragement();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, modalLogin);
        args.putParcelable(ARG_PARAM2, camInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            modalLogin = getArguments().getParcelable(ARG_PARAM1);
            camInfo = getArguments().getParcelable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_fragement, container, false);
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
//        toolbar.setTitle("Dashboard");
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView toolbar_title = (TextView) getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("Dashboard");
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
//        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // update the main content by replacing fragments
//                getFragmentManager().popBackStack();
//            }
//        });
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);

        socialAuthListener = new SocialAuthListener(getActivity());
        mLoginFormView = view.findViewById(R.id.login_form);
        mProgressView = view.findViewById(R.id.login_progress);

        TextView campaignStatus = (CustomTextView)view.findViewById(R.id.sataus);
        TextView campaignTitleView = (CustomTextView)view.findViewById(R.id.camp_title);
        TextView campaignMessageView = (CustomTextView)view.findViewById(R.id.campaign_message);
        TextView campaignIdView = (CustomTextView)view.findViewById(R.id.campaign_action_view);
        TextView reportIdView = (CustomTextView)view.findViewById(R.id.action_report_id);
        TextView planView = (CustomTextView)view.findViewById(R.id.action_plan);
        TextView createDateView = (CustomTextView)view.findViewById(R.id.action_create_date);
        TextView actionTotalAds = (CustomTextView)view.findViewById(R.id.action_total_ads);
        TextView actionTotalView = (CustomTextView)view.findViewById(R.id.action_total_view);
        TextView actionClicView = (CustomTextView)view.findViewById(R.id.action_click);

        campaignStatus.setText((camInfo.getCampStatus()!=null) ? getStatus(camInfo.getCampStatus())+"" : "compliance");
        campaignTitleView.setText((camInfo.getCampName()!=null) ? camInfo.getCampName()+"" : "Name is blank");
        campaignMessageView.setText((camInfo.getPromoMsg()!=null) ? camInfo.getPromoMsg()+"" : "Message is blank");
        campaignIdView.setText((camInfo.getCampId()!=null) ? camInfo.getCampId()+"" : "8267");
        reportIdView.setText((camInfo.getOrderId()!=null) ? camInfo.getOrderId()+"" : "8237");
        planView.setText((camInfo.getTotalImp()!=null) ? camInfo.getTotalImp()+"" : "5500");
        createDateView.setText((camInfo.getStartDate()>=0) ? ActivityUtils.GetDateTime(Long.valueOf(camInfo.getStartDate())) + "" : "23-Nov-2015 20:30:00");
        actionTotalAds.setText((camInfo.getTotalImp()!=null) ? camInfo.getTotalImp()+"" : "5500");
        actionTotalView.setText((camInfo.getShownImp()!=null) ? camInfo.getShownImp()+"" : "5500");
//        actionTotalView.setText((camInfo.getTotalImp() !=null) ? Integer.parseInt(camInfo.getTotalImp()) - camInfo.getShownImp().intValue() +"" : "7585");
        actionClicView.setText((camInfo.getClicks() >= 0) ? camInfo.getAction() + "" : "7585");


        Button action_imression_btn = (AppCompatButton)view.findViewById(R.id.action_Impression);
        Button action_heatmap_btn = (AppCompatButton)view.findViewById(R.id.action_Location);
        ImageView facebook_sharing_btn = (ImageView)view.findViewById(R.id.facebook_btn);
        action_imression_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Fragment pieFragement = PieFragment.newInstance(modalLogin,camInfo);
               ActivityUtils.launchFragementWithAnimation(pieFragement,getActivity());
            }
        });

        action_heatmap_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Fragment heatMapFragemet = HeatMapFragement.newInstance(camInfo,"");
             ActivityUtils.launchFragementWithAnimation(heatMapFragemet,getActivity());
            }
        });

        facebook_sharing_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                socialAuthListener.faceBookAuthentication();
            }
        });
    }

    public String getStatus(String status){
        if (status.toString().trim().equals(getActivity().getResources().getString(R.string.status_paused))){
            return "paused";
        }
        else if (status.toString().trim().equals(getActivity().getResources().getString(R.string.status_awaiting))){
            return "awaiting";
        }
        else if(status.toString().trim().equals(getActivity().getResources().getString(R.string.status_completed))){
            return "completed";
        }
        else if(status.toString().trim().equals(getActivity().getResources().getString(R.string.status_running))){
            return "running";
        }
        else if(status.toString().trim().equals(getActivity().getResources().getString(R.string.status_unpaid))){
            return "unpaid";
        }
        else if(status.toString().trim().equals(getActivity().getResources().getString(R.string.status_compliance))){
            return "compliance";
        }
        else{
            return "compliance";
        }
    }
}
