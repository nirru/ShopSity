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
import android.graphics.Color;
import android.net.Uri;
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
import com.oxilo.shopsity.utility.ActivityUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Pay.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Pay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pay extends Fragment {
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
//    private Toolbar toolbar;

    private OnFragmentInteractionListener mListener;
    MobiKytePlaceCampaignInfo place;

    TextView freeTrailAction;
    TextView actionA ;
    TextView actionB ;
    TextView actionC;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userCampaign Parameter 1.
     * @param modalAddCampign Parameter 2.
     * @param modalLogin Parameter 3.
     * @return A new instance of fragment Pay.
     */
    // TODO: Rename and change types and number of parameters
    public static Pay newInstance(MobiKytePlaceCampaignInfo place,UserCampaign userCampaign, ModalAddCampign modalAddCampign, ModalLogin modalLogin) {
        Pay fragment = new Pay();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, userCampaign);
        args.putParcelable(ARG_PARAM2,place);
        args.putParcelable(ARG_PARAM3,modalAddCampign);
        args.putParcelable(ARG_PARAM4,modalLogin);
        fragment.setArguments(args);
        return fragment;
    }

    public Pay() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userCampaign = getArguments().getParcelable(ARG_PARAM1);
            place = getArguments().getParcelable(ARG_PARAM2);
            modalAddCampign = getArguments().getParcelable(ARG_PARAM3);
            modalLogin = getArguments().getParcelable(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pay, container, false);
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
//        toolbar.setTitle("Pay");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView toolbar_title = (TextView) getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("Pay");
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
//        toolbar = (Toolbar)v.findViewById(R.id.toolbar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // update the main content by replacing fragments
//               getFragmentManager().popBackStack();
//            }
//        });
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);

        AppCompatButton check_out_btn = (AppCompatButton)v.findViewById(R.id.check_out_btn);
        check_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checkout preview = Checkout.newInstance(place,userCampaign,modalAddCampign,modalLogin);
                ActivityUtils.launchFragementWithAnimation(preview,getActivity());
            }
        });

         freeTrailAction = (TextView)v.findViewById(R.id.action_free_trial);
         actionA = (TextView)v.findViewById(R.id.action_plan_a);
         actionB = (TextView)v.findViewById(R.id.action_plan_b);
         actionC = (TextView)v.findViewById(R.id.action_plan_c);

        freeTrailAction.setBackgroundColor(Color.BLUE);
        actionB.setBackgroundResource(R.drawable.rounded_map_search);
        actionB.setBackgroundResource(R.drawable.rounded_map_search);
        actionC.setBackgroundResource(R.drawable.rounded_map_search);

        freeTrailAction.setOnClickListener(cli);
        actionA.setOnClickListener(cli);
        actionB.setOnClickListener(cli);
        actionC.setOnClickListener(cli);
    }

    View.OnClickListener cli = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.action_free_trial:
                    freeTrailAction.setBackgroundColor(Color.BLUE);
                    actionA.setBackgroundResource(R.drawable.rounded_map_search);
                    actionB.setBackgroundResource(R.drawable.rounded_map_search);
                    actionC.setBackgroundResource(R.drawable.rounded_map_search);
                    break;

                case R.id.action_plan_a:
                    actionA.setBackgroundColor(Color.BLUE);
                    freeTrailAction.setBackgroundResource(R.drawable.rounded_map_search);
                    actionB.setBackgroundResource(R.drawable.rounded_map_search);
                    actionC.setBackgroundResource(R.drawable.rounded_map_search);
                    break;

                case R.id.action_plan_b:
                    actionB.setBackgroundColor(Color.BLUE);
                    freeTrailAction.setBackgroundResource(R.drawable.rounded_map_search);
                    actionA.setBackgroundResource(R.drawable.rounded_map_search);
                    actionC.setBackgroundResource(R.drawable.rounded_map_search);
                    break;

                case R.id.action_plan_c:
                    actionC.setBackgroundColor(Color.BLUE);
                    freeTrailAction.setBackgroundResource(R.drawable.rounded_map_search);
                    actionB.setBackgroundResource(R.drawable.rounded_map_search);
                    actionA.setBackgroundResource(R.drawable.rounded_map_search);
                    break;
            }
        }
    };

}
