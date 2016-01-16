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
import android.app.Fragment;
import android.content.Intent;
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
import android.widget.TextView;

import com.oxilo.shopsity.DataPullingInterface;
import com.oxilo.shopsity.MODAL.MobiKytePlaceCampaignInfo;
import com.oxilo.shopsity.MODAL.UserCampaign;
import com.oxilo.shopsity.POJO.ModalLogin;
import com.oxilo.shopsity.R;
import com.oxilo.shopsity.activity.MapsActivity;
import com.oxilo.shopsity.logger.Log;
import com.oxilo.shopsity.utility.ActivityUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Message.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Message#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Message extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ModalLogin modalLogin;

    private OnFragmentInteractionListener mListener;
    private DataPullingInterface mHostInterface;

    private AppCompatButton btn_next;
    private EditText mCampaignView,mPromotionMessageView,mStartDateView,mStartTimeView;

    private View mLoginFormView,mProgressView;

    private int mHourOfDay, mMinute, mSecond,mYear,  mMonthOfYear,  mDayOfMonth;
//    Toolbar toolbar;
    public MobiKytePlaceCampaignInfo place = null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param place Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Message.
     */
    // TODO: Rename and change types and number of parameters
    public static Message newInstance(MobiKytePlaceCampaignInfo place, String param2,ModalLogin modalLogin) {
        Message fragment = new Message();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, place);
        args.putString(ARG_PARAM2, param2);
        args.putParcelable(ARG_PARAM3,modalLogin);
        fragment.setArguments(args);
        return fragment;
    }

    public Message() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            place = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            modalLogin = getArguments().getParcelable(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ActivityUtils.setupUI(view.findViewById(R.id.main_root),getActivity());
        Log.e("CLIENT ID121222","" + modalLogin.getClientid());

        initUiWidget(view);
        return view;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem menuItem) {
//        if (menuItem.getItemId() == android.R.id.home) {
//            Intent i = new Intent(getActivity(),MapsActivity.class);
//            i.putExtra(getResources().getString(R.string.praceable_modal_regsitration), modalLogin);
//            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(i);
//        }
//        return super.onOptionsItemSelected(menuItem);
//    }

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
//        toolbar.setTitle("Message");
        showProgress(false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView toolbar_title = (TextView) getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("Message");
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

        mLoginFormView = v.findViewById(R.id.login_form);
        mProgressView = v.findViewById(R.id.login_progress);
        mCampaignView = (EditText)v.findViewById(R.id.campaign_title);
        mPromotionMessageView = (EditText)v.findViewById(R.id.promotion_message);
        mStartDateView = (EditText)v.findViewById(R.id.start_date);
        mStartTimeView = (EditText)v.findViewById(R.id.start_time);
        btn_next = (AppCompatButton)v.findViewById(R.id.email_sign_in_button);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              attemptCampaignCreation();
            }
        });

        mStartDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                  @Override
                  public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                      mYear = year;
                      mMonthOfYear = monthOfYear;
                      mDayOfMonth = dayOfMonth;
                      String date = dayOfMonth+"/"+(++monthOfYear)+"/"+year;
                      mStartDateView.setText(date);
                      mStartDateView.setError(null);
                  }
              }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

               dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        mStartTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        mHourOfDay = hourOfDay;
                        mMinute = minute;
                        mSecond = second;
                        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
                        String minuteString = minute < 10 ? "0"+minute : ""+minute;
                        String secondString = second < 10 ? "0"+second : ""+second;
                        String time = hourString+"h"+minuteString+"m"+secondString+"s";
//                        mStartTimeView.setText(time);
                        mStartTimeView.setText("" + ActivityUtils.Convert24to12(hourOfDay + ":" + minute + ":" + second));
                        mStartTimeView.setError(null);
                    }
                }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);

                timePickerDialog.show(getFragmentManager(),"Timepickerdialog");
            }
        });
    }

    private void attemptCampaignCreation(){
        // Reset errors.
        mCampaignView.setError(null);
        mPromotionMessageView.setError(null);
        mStartDateView.setError(null);
        mStartTimeView.setError(null);

        // Store values at the time of the login attempt.
        String campaign_title = mCampaignView.getText().toString();
        String promotion_message = mPromotionMessageView.getText().toString();
        String start_date = mStartDateView.getText().toString();
        String start_time = mStartTimeView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid Campaign Title.
        if (TextUtils.isEmpty(campaign_title)) {
            mCampaignView.setError(getString(R.string.error_field_required));
            focusView = mCampaignView;
            cancel = true;
        }

        // Check for a valid Promotion Message.
        else if (TextUtils.isEmpty(promotion_message)) {
            mPromotionMessageView.setError(getString(R.string.error_field_required));
            focusView = mPromotionMessageView;
            cancel = true;
        }
        // Check for a valid Start Date.
        else if (TextUtils.isEmpty(start_date)) {
            mStartDateView.setError(getString(R.string.error_field_required));
            focusView = mStartDateView;
            cancel = true;
        }
        // Check for a valid Start Time
        else if (TextUtils.isEmpty(start_time)) {
            mStartTimeView.setError(getString(R.string.error_invalid_country));
            focusView = mStartTimeView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            Action actionFragment = Action.newInstance(place,getUserCampaign(), modalLogin);
            ActivityUtils.launchFragementWithAnimation(actionFragment, getActivity());
        }
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




    public long getTimeInMilli(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        System.out.println("Calender - Time in milliseconds : " + calendar.getTimeInMillis());

        return calendar.getTimeInMillis();
    }


    private UserCampaign getUserCampaign(){
        UserCampaign userCampaign = new UserCampaign();
        userCampaign.setCampaignTitle(mCampaignView.getText().toString());
        userCampaign.setPromotionMessage(mPromotionMessageView.getText().toString());
        userCampaign.setStartDate(mStartDateView.getText().toString());
        userCampaign.setStartTime(mStartTimeView.getText().toString());
        userCampaign.setDateTime(createDateTimeString());
        userCampaign.setWebRequestUrl("http://www.mobikyte.com");
        userCampaign.setCall("123456789");

        return userCampaign;

    }

    private String createDateTimeString(){
        String dateseperator = "-";
        String timeSeperator = ":";
        String dateTime = mYear + dateseperator + ++mMonthOfYear + dateseperator + mDayOfMonth + " " + mHourOfDay + timeSeperator + mMinute + timeSeperator + mSecond;

        return dateTime;
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

}
