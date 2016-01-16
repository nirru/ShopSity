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
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oxilo.shopsity.MODAL.UserCampaign;
import com.oxilo.shopsity.POJO.CampList;
import com.oxilo.shopsity.POJO.ModalAddCampign;
import com.oxilo.shopsity.POJO.ModalCheckOut;
import com.oxilo.shopsity.POJO.ModalLogin;
import com.oxilo.shopsity.R;
import com.oxilo.shopsity.activity.WelcomeActivity;
import com.oxilo.shopsity.ui.CustomTextView;
import com.oxilo.shopsity.utility.ActivityUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ThankYou.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ThankYou#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThankYou extends Fragment {

    //UI Widget
    private CustomTextView compaignView,orderView,adBudgetView,
            startFromView,statusView,inrView,
            compaignTitleView,promotionMessageView;
    AppCompatButton seeInvoiceButton, goToHomeButton;
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
    private ModalCheckOut modalCheckOut;

    private OnFragmentInteractionListener mListener;
//    Toolbar toolbar;

    public ThankYou() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userCampaign Parameter 1.
     * @param modalAddCampign Parameter 2.
     * @param modalLogin Parameter 3.
     * @return A new instance of fragment ThankYou.
     */
    // TODO: Rename and change types and number of parameters
    public static ThankYou newInstance(UserCampaign userCampaign, ModalAddCampign modalAddCampign, ModalLogin modalLogin, ModalCheckOut modalCheckOut) {
        ThankYou fragment = new ThankYou();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, userCampaign);
        args.putParcelable(ARG_PARAM2, modalAddCampign);
        args.putParcelable(ARG_PARAM3, modalLogin);
        args.putParcelable(ARG_PARAM4, modalCheckOut);
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
            modalCheckOut = getArguments().getParcelable(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thankyou, container, false);
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
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        toolbar.setTitle("Thankyou");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView toolbar_title = (TextView) getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("Thankyou");
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

        compaignView =(CustomTextView)view.findViewById(R.id.campaign_action_view);
        orderView=(CustomTextView)view.findViewById(R.id.action_order_view);
        adBudgetView=(CustomTextView)view.findViewById(R.id.action_ad_view);
        startFromView=(CustomTextView)view.findViewById(R.id.campaign_start_from);
        statusView=(CustomTextView)view.findViewById(R.id.action_compilance);
        inrView=(CustomTextView)view.findViewById(R.id.action_inr);
        compaignTitleView=(CustomTextView)view.findViewById(R.id.action_campaign_tittle);
        promotionMessageView=(CustomTextView)view.findViewById(R.id.action_promotion_message);

        seeInvoiceButton=(AppCompatButton)getActivity().findViewById(R.id.email_sign_in_button);
        goToHomeButton = (AppCompatButton)getActivity().findViewById(R.id.home_button);

        CampList listCamp=null;
        try {
            for (int i = 0 ; i < modalCheckOut.getCampList().size() ; i ++){
                CampList campList = modalCheckOut.getCampList().get(i);
                if (campList.getOrderId() != null){
                    if (Integer.parseInt(campList.getOrderId()) == modalAddCampign.getOrderId()){
                        listCamp = campList;
                        break;
                    }
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        if (listCamp != null){
            try {
                compaignView.setText("" + listCamp.getCampId());
                orderView.setText("" + listCamp.getOrderId());
                adBudgetView.setText("" +listCamp.getTotalImp());
                startFromView.setText(userCampaign.getStartDate());
                statusView.setText(getResources().getString(R.string.compilance));
                inrView.setText("" + 0);
                compaignTitleView.setText(listCamp.getCampName());
                promotionMessageView.setText(listCamp.getPromoMsg());
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        seeInvoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InVoiceFragment inVoiceFragment = InVoiceFragment.newInstance(userCampaign,modalAddCampign,modalLogin);
                ActivityUtils.launchFragementWithAnimation(inVoiceFragment,getActivity());
            }
        });

        goToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent mintent = new Intent(getActivity(), WelcomeActivity.class);
                mintent.putExtra(getResources().getString(R.string.praceable_modal_regsitration), modalLogin);
                mintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mintent);
                getActivity().overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
                return;
            }
        });
    }
}
