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
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.oxilo.shopsity.POJO.ModalLogin;
import com.oxilo.shopsity.R;
import com.oxilo.shopsity.activity.MapsActivity;
import com.oxilo.shopsity.logger.Log;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ObjectiveFragement.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ObjectiveFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObjectiveFragement extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ModalLogin modalLogin;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    TextView objHeader1,objHeader2,objPromote,objCapture,objReach_Sell,objReach_Book;
    CheckBox objCheckBox,objCheckBox1,objCheckBox2,objCheckBox3;
    AppCompatButton objButtonNext;

//    Toolbar toolbar;

    public ObjectiveFragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param modalLogin Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ObjectiveFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static ObjectiveFragement newInstance(ModalLogin modalLogin, String param2) {
        ObjectiveFragement fragment = new ObjectiveFragement();
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
        return inflater.inflate(R.layout.fragment_objective_fragement, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView toolbar_title = (TextView) getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("Objective");
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
        Log.e("odal", "" + modalLogin.getClientid());
//        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
//        toolbar.setTitle("Location");
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);

        objHeader1=(TextView)view.findViewById(R.id.objectiveHeader);
        objHeader2=(TextView)view.findViewById(R.id.objectiveHeader2);
        objPromote=(TextView)view.findViewById(R.id.objPromote);
        objCapture=(TextView)view.findViewById(R.id.objectiveCapture);
        objReach_Sell=(TextView)view.findViewById(R.id.objectiveReach_Selling);
        objReach_Book=(TextView)view.findViewById(R.id.objectiveReach_Booking);

        objCheckBox=(CheckBox)view.findViewById(R.id.objectiveCheckBox);
        objCheckBox1=(CheckBox)view.findViewById(R.id.objectiveCheckBox1);
        objCheckBox2=(CheckBox)view.findViewById(R.id.objectiveCheckBox2);
        objCheckBox3=(CheckBox)view.findViewById(R.id.objectiveCheckBox3);

        objButtonNext=(AppCompatButton)view. findViewById(R.id.email_sign_in_button);

        objButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),MapsActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(getResources().getString(R.string.praceable_modal_regsitration), modalLogin);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
                return;
            }
        });


        objCheckBox.setChecked(true);
        objCheckBox.setOnClickListener(l);
        objCheckBox1.setOnClickListener(l);
        objCheckBox2.setOnClickListener(l);
        objCheckBox3.setOnClickListener(l);
    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.objectiveCheckBox:
                    objCheckBox.setChecked(true);
                    objCheckBox1.setChecked(false);
                    objCheckBox2.setChecked(false);
                    objCheckBox3.setChecked(false);
                    break;
                case R.id.objectiveCheckBox1:
                    objCheckBox.setChecked(false);
                    objCheckBox1.setChecked(true);
                    objCheckBox2.setChecked(false);
                    objCheckBox3.setChecked(false);
                    break;
                case R.id.objectiveCheckBox2:
                    objCheckBox.setChecked(false);
                    objCheckBox1.setChecked(false);
                    objCheckBox2.setChecked(true);
                    objCheckBox3.setChecked(false);
                    break;
                case R.id.objectiveCheckBox3:
                    objCheckBox.setChecked(false);
                    objCheckBox1.setChecked(false);
                    objCheckBox2.setChecked(false);
                    objCheckBox3.setChecked(true);
                    break;
            }
        }
    };
}
