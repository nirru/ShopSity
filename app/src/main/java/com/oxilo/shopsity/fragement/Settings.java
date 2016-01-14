package com.oxilo.shopsity.fragement;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oxilo.shopsity.POJO.ModalLogin;
import com.oxilo.shopsity.R;
import com.oxilo.shopsity.ui.CustomTextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Settings.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Settings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Settings extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ModalLogin modalLogin;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    Toolbar toolbar;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param modalLogin Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settings.
     */
    // TODO: Rename and change types and number of parameters
    public static Settings newInstance(ModalLogin modalLogin, String param2) {
        Settings fragment = new Settings();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, modalLogin);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Settings() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_settings, container, false);
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
    public void onResume() {
        super.onResume();
        if (toolbar!=null)
            toolbar.setTitle("Settings");
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
        public void onFragmentInteraction(Uri uri);
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
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);

        CustomTextView nameView = (CustomTextView)view.findViewById(R.id.action_view_name);
        CustomTextView businessNameView = (CustomTextView)view.findViewById(R.id.action_bussiness_name);
        CustomTextView userNameView = (CustomTextView)view.findViewById(R.id.action_user_name);
        CustomTextView businessAddressView = (CustomTextView)view.findViewById(R.id.action_bussiness_address);
        CustomTextView cityNameView = (CustomTextView)view.findViewById(R.id.action_user_city);
        CustomTextView stateNameView = (CustomTextView)view.findViewById(R.id.action_user_state);
        CustomTextView zipCodeView = (CustomTextView)view.findViewById(R.id.action_zip_code);
        CustomTextView emailView = (CustomTextView)view.findViewById(R.id.action_email);
        CustomTextView webUrlView = (CustomTextView)view.findViewById(R.id.action_web_url);
        CustomTextView mobileNumberView = (CustomTextView)view.findViewById(R.id.action_mobile_no);
        CustomTextView aboutView = (CustomTextView)view.findViewById(R.id.action_about);

        nameView.setText(modalLogin.getName()!=null ? modalLogin.getName() : "name");
        businessNameView.setText(modalLogin.getBusinessName()!=null ? modalLogin.getBusinessName() : "business name");
        userNameView.setText(modalLogin.getName()!=null ? modalLogin.getName() : "user name");
        businessAddressView.setText(modalLogin.getAddress()!=null ? modalLogin.getAddress() : "address");
        cityNameView.setText(modalLogin.getCity()!=null ? modalLogin.getCity() : "city");
        stateNameView.setText(modalLogin.getState()!=null ? modalLogin.getState() : "state");
        zipCodeView.setText(modalLogin.getZip()!=null ? modalLogin.getZip() : "zip");
        emailView.setText(modalLogin.getMobile()!=null ? modalLogin.getEmailid() : "mobile");
        webUrlView.setText(modalLogin.getWebsite()!=null ? modalLogin.getWebsite() : "webUrl");
        mobileNumberView.setText(modalLogin.getMobile()!=null ? modalLogin.getMobile() : "website");
        aboutView.setText("about");
    }

}
