package com.oxilo.shopsity.fragement.graph;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.dacer.androidcharts.PieHelper;
import com.dacer.androidcharts.PieView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oxilo.shopsity.ApplicationController;
import com.oxilo.shopsity.Job.CreateClickGraph;
import com.oxilo.shopsity.Job.CreateImpressionGraph;
import com.oxilo.shopsity.Job.CreateIspGraph;
import com.oxilo.shopsity.POJO.CampList;
import com.oxilo.shopsity.POJO.DeviceDatum;
import com.oxilo.shopsity.POJO.ModalAlreadyRegistred;
import com.oxilo.shopsity.POJO.ModalGraph;
import com.oxilo.shopsity.POJO.ModalLogin;
import com.oxilo.shopsity.R;
import com.oxilo.shopsity.custom.MyYAxisValueFormatter;
import com.oxilo.shopsity.event.DailyClickGraphFinishedEvent;
import com.oxilo.shopsity.event.ImpressionGraphFinishedEvent;
import com.oxilo.shopsity.event.IspGraphFinishedEvent;
import com.oxilo.shopsity.holder.GroupItem;
import com.oxilo.shopsity.logger.Log;
import com.oxilo.shopsity.ui.CustomTextView;
import com.oxilo.shopsity.volley.VolleyErrorHelper;
import com.path.android.jobqueue.JobManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PieFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PieFragment extends Fragment {
    // TODO: DEVICE NAME
    public static final String DEVICE_ANDROID="android";
    public static final String DEVICE_IOS="ios";
    public static final String DEVICE_WINDOW="windows";
    public static final String DEVICE_OTHERS="";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ModalLogin modalLogin;
    private CampList camInfo;

    private OnFragmentInteractionListener mListener;
    private PieView pieView;
    Toolbar toolbar;
    private TextView androidUserView,iOsUserView,windowUserView,other;
    private View mProgressView;
    private View mLoginFormView;
    private GroupItem groupItem;

    /**Horizental Bar Chart VIEW COMPONENET
     *
     */

    private HorizontalBarChart horizontalBarChart;

    /**LINE CHART VIEW COMPONENET
     *
     */
    private LineChartView chart;
    private LineChartData data;
    private int numberOfLines = 1;
    private int maxNumberOfLines = 4;
    private int numberOfPoints = 12;

    float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = true;
    private boolean hasLabels = false;
    private boolean isCubic = true;
    private boolean hasLabelForSelected = false;
    private boolean pointsHaveDifferentColor;


    /** BAr Chart
     *
     */
    protected BarChart mChart,dalilyClickChart;

    JobManager jobManager;

    public PieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param modalLogin Parameter 1.
     * @param camInfo Parameter 2.
     * @return A new instance of fragment PieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PieFragment newInstance(ModalLogin modalLogin, CampList camInfo) {
        PieFragment fragment = new PieFragment();
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
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_pie, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        jobManager = ApplicationController.getInstance().getJobManager();
        initUiWidget(view);
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
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        Activity activity = getActivity();
        if (!isAdded())
            return;
        if (toolbar!=null)
            toolbar.setTitle("Impression");
    }

    /** handle the response
     * back from background thread
     * to main thread by using EVENTBUS
     */
    @SuppressWarnings("UnusedDeclaration")
    public void onEventMainThread(ImpressionGraphFinishedEvent event) {
        // Update the UI in the main thread
//        chart.setLineChartData(event.getData());
        if (event.getxAxis().size() > 0){
            mChart.setDrawValueAboveBar(false);
            mChart.setDrawBarShadow(false);
            mChart.setDrawGridBackground(false);

            XAxis xAxis = mChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawAxisLine(false);
            xAxis.setDrawGridLines(false);
            xAxis.setSpaceBetweenLabels(1);

            YAxisValueFormatter custom = new MyYAxisValueFormatter();

            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.setDrawAxisLine(false);
            leftAxis.setDrawGridLines(false);
            leftAxis.setLabelCount(8, false);
            leftAxis.setValueFormatter(custom);
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            leftAxis.setSpaceTop(15f);

            YAxis rightAxis = mChart.getAxisRight();
            rightAxis.setDrawAxisLine(false);
            rightAxis.setDrawGridLines(false);
            rightAxis.setLabelCount(8, false);
            rightAxis.setValueFormatter(custom);
            rightAxis.setSpaceTop(15f);

            Legend l = mChart.getLegend();
            l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
            l.setForm(Legend.LegendForm.SQUARE);
            l.setFormSize(9f);
            l.setTextSize(11f);
            l.setXEntrySpace(2f);

            BarData data = new BarData(event.getxAxis(), event.getDataSets());
            mChart.setData(data);
            mChart.setDescription("My Chart");
            mChart.animateXY(2000, 2000);
            mChart.invalidate();
        }


    }

    @SuppressWarnings("UnusedDeclaration")
    public void onEventMainThread(DailyClickGraphFinishedEvent event) {
        // Update the UI in the main thread
//        chart.setLineChartData(event.getData());
        if (event.getxAxis().size() > 0){
            dalilyClickChart.setDrawValueAboveBar(false);
            dalilyClickChart.setDrawBarShadow(false);
            dalilyClickChart.setDrawGridBackground(false);

            XAxis xAxis = dalilyClickChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawAxisLine(false);
            xAxis.setDrawGridLines(false);
            xAxis.setSpaceBetweenLabels(1);

            YAxisValueFormatter custom = new MyYAxisValueFormatter();

            YAxis leftAxis = dalilyClickChart.getAxisLeft();
            leftAxis.setDrawAxisLine(false);
            leftAxis.setDrawGridLines(false);
            leftAxis.setLabelCount(8, false);
            leftAxis.setValueFormatter(custom);
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            leftAxis.setSpaceTop(15f);

            YAxis rightAxis = dalilyClickChart.getAxisRight();
            rightAxis.setDrawAxisLine(false);
            rightAxis.setDrawGridLines(false);
            rightAxis.setLabelCount(8, false);
            rightAxis.setValueFormatter(custom);
            rightAxis.setSpaceTop(15f);

            Legend l = dalilyClickChart.getLegend();
            l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
            l.setForm(Legend.LegendForm.SQUARE);
            l.setFormSize(9f);
            l.setTextSize(11f);
            l.setXEntrySpace(2f);

            BarData data = new BarData(event.getxAxis(), event.getDataSets());
            dalilyClickChart.setData(data);
            dalilyClickChart.setDescription("My Chart");
            dalilyClickChart.animateXY(2000, 2000);
            dalilyClickChart.invalidate();
        }


    }

    @SuppressWarnings("UnusedDeclaration")
    public void onEventMainThread(IspGraphFinishedEvent event) {
        // Update the UI in the main thread
        showProgress(false);
        if (event.getxAxis().size() > 0){
            horizontalBarChart.setDrawValueAboveBar(false);
            horizontalBarChart.setDrawBarShadow(false);
            horizontalBarChart.setDrawGridBackground(false);
            horizontalBarChart.setMaxVisibleValueCount(11);

            XAxis xl = horizontalBarChart.getXAxis();
            xl.setPosition(XAxis.XAxisPosition.BOTTOM);
            xl.setDrawAxisLine(true);
            xl.setDrawGridLines(false);
            xl.setGridLineWidth(0.3f);

            YAxis yl = horizontalBarChart.getAxisLeft();
            yl.setDrawAxisLine(false);
            yl.setDrawGridLines(false);
            yl.setGridLineWidth(0.3f);
            yl.setDrawLabels(false);
//        yl.setInverted(true);

            YAxis yr = horizontalBarChart.getAxisRight();
            yr.setDrawAxisLine(true);
            yr.setDrawGridLines(false);
            yr.setDrawLabels(false);
//        yr.setInverted(true);
            BarData data = new BarData(event.getxAxis(), event.getDataSets());
            horizontalBarChart.setData(data);
            horizontalBarChart.setDescription("My Chart");
            horizontalBarChart.animateXY(2000, 2000);
            horizontalBarChart.invalidate();
        }

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
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
          groupItem = new GroupItem();
          mLoginFormView = view.findViewById(R.id.login_form);
          mProgressView = view.findViewById(R.id.login_progress);

          pieView = (PieView)view.findViewById(R.id.pie_view);
          horizontalBarChart = (HorizontalBarChart)view.findViewById(R.id.horizentalGraph);
           mChart = (BarChart) view.findViewById(R.id.chart1);
         dalilyClickChart = (BarChart) view.findViewById(R.id.chart_daily_clcik);
//          chart = (LineChartView) view.findViewById(R.id.chart1);
          androidUserView = (CustomTextView)view.findViewById(R.id.android_user);
          iOsUserView = (CustomTextView)view.findViewById(R.id.ios_user);
          windowUserView = (CustomTextView)view.findViewById(R.id.window_user);
          other = (CustomTextView)view.findViewById(R.id.other_user);
//        pieView = (PieView)view.findViewById(R.id.pie_view);
    }


    private void set(PieView pieView){
        float totalCount = 0;
        float previousValue=0;
        ArrayList<Integer> colorslists = new ArrayList<>();
        colorslists.add(R.color.green);
        colorslists.add(R.color.yellow);
        colorslists.add(R.color.blue);
        colorslists.add(R.color.red);
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<PieHelper>();
        for (int i = 0;i < groupItem.deviceDatumsList.size(); i++){
            DeviceDatum deviceDatum = groupItem.deviceDatumsList.get(i);
            previousValue = Float.parseFloat(deviceDatum.getCount());
            totalCount = totalCount + previousValue;
        }
        for (int i = 0;i < groupItem.deviceDatumsList.size(); i++){
            if (groupItem.deviceDatumsList.size()>0){
//                pieHelperArrayList.add(new PieHelper(((float)Float.parseFloat(groupItem.deviceDatumsList.get(i).getCount())/totalCount) * 100,groupItem.deviceDatumsList.get(i).getDevice(),colorslists.get(i)));
//                pieHelperArrayList.add(new PieHelper(((float)Float.parseFloat(groupItem.deviceDatumsList.get(1).getCount())/totalCount) * 100,DEVICE_ANDROID,Color.BLUE));
//                pieHelperArrayList.add(new PieHelper(((float)Float.parseFloat(groupItem.deviceDatumsList.get(2).getCount())/totalCount) * 100,DEVICE_IOS,Color.RED));
//                pieHelperArrayList.add(new PieHelper(((float) Float.parseFloat(groupItem.deviceDatumsList.get(3).getCount()) / totalCount) * 100, DEVICE_WINDOW, Color.YELLOW));
//                pieView.setDate(pieHelperArrayList);
                Log.e("Toatal count", "" + totalCount);
//                pieView.selectedPie(4);

                if (groupItem.deviceDatumsList.get(i).getDevice().equals(DEVICE_ANDROID)){
                    pieHelperArrayList.add(new PieHelper(((float)Float.parseFloat(groupItem.deviceDatumsList.get(i).getCount())/totalCount) * 100,groupItem.deviceDatumsList.get(i).getDevice(),Color.BLUE));
                    androidUserView.setText(String.format("%.2f", ((float) Float.parseFloat(groupItem.deviceDatumsList.get(i).getCount()) / totalCount) * 100) + "% " + "Android User" + "\n" + groupItem.deviceDatumsList.get(i).getCount());
                    androidUserView.setTextColor(Color.BLUE);
                }
                else if(groupItem.deviceDatumsList.get(i).getDevice().equals(DEVICE_IOS)){
                    pieHelperArrayList.add(new PieHelper(((float)Float.parseFloat(groupItem.deviceDatumsList.get(i).getCount())/totalCount) * 100,groupItem.deviceDatumsList.get(i).getDevice(),Color.RED));
                    iOsUserView.setText(String.format("%.2f", ((float) Float.parseFloat(groupItem.deviceDatumsList.get(i).getCount()) / totalCount) * 100) + "% " + "iOS User" + "\n" + groupItem.deviceDatumsList.get(i).getCount());
                    iOsUserView.setTextColor(Color.RED);
                }
                else if(groupItem.deviceDatumsList.get(i).getDevice().equals(DEVICE_WINDOW)){
                    pieHelperArrayList.add(new PieHelper(((float) Float.parseFloat(groupItem.deviceDatumsList.get(i).getCount()) / totalCount) * 100, groupItem.deviceDatumsList.get(i).getDevice(), Color.YELLOW));
                    windowUserView.setText(String.format("%.2f", ((float) Float.parseFloat(groupItem.deviceDatumsList.get(i).getCount()) / totalCount) * 100) + "% " + "Window User" + "\n" + groupItem.deviceDatumsList.get(i).getCount());
                    windowUserView.setTextColor(Color.YELLOW);
                }else if(groupItem.deviceDatumsList.get(i).getDevice().equals(DEVICE_OTHERS)){
                    pieHelperArrayList.add(new PieHelper(((float) Float.parseFloat(groupItem.deviceDatumsList.get(i).getCount()) / totalCount) * 100, groupItem.deviceDatumsList.get(i).getDevice(), Color.GREEN));
                    other.setText(String.format("%.2f", ((float) Float.parseFloat(groupItem.deviceDatumsList.get(i).getCount()) / totalCount) * 100) + "% " + "Other User" + "\n" + groupItem.deviceDatumsList.get(i).getCount());
                    other.setTextColor(Color.GREEN);
                }

                pieView.setDate(pieHelperArrayList);
                Log.e("Toatal count", "" + totalCount);
                pieView.selectedPie(4);

//                other.setText(String.format("%.2f", ((float) Float.parseFloat(groupItem.deviceDatumsList.get(0).getCount()) / totalCount) * 100) + "% " + "Other User" + "\n" + groupItem.deviceDatumsList.get(0).getCount());
//                androidUserView.setText(String.format("%.2f", ((float) Float.parseFloat(groupItem.deviceDatumsList.get(1).getCount()) / totalCount) * 100) + "% " + "Android User" + "\n" + groupItem.deviceDatumsList.get(1).getCount());
//                iOsUserView.setText(String.format("%.2f", ((float) Float.parseFloat(groupItem.deviceDatumsList.get(2).getCount()) / totalCount) * 100) + "% " + "iOS User" + "\n" + groupItem.deviceDatumsList.get(2).getCount());
//                windowUserView.setText(String.format("%.2f", ((float) Float.parseFloat(groupItem.deviceDatumsList.get(3).getCount()) / totalCount) * 100) + "% " + "Windows User" + "\n" + groupItem.deviceDatumsList.get(3).getCount());

            }
        }


//        other.setTextColor(Color.GREEN);
//        androidUserView.setTextColor(Color.BLUE);
//        iOsUserView.setTextColor(Color.RED);
//        windowUserView.setTextColor(Color.YELLOW);
    }


    private void fetchcampaign(){
        showProgress(true);
        String URL = getResources().getString(R.string.mobikyte_graph_url) + "data=" + Uri.encode(makeJsonBody().toString());
        StringRequest req = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                VolleyLog.v("Response:%n %s", response);
                Gson gson = new GsonBuilder().create();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals(getResources().getString(R.string.response_success))){
                        ModalGraph modalGraph = gson.fromJson(response, ModalGraph.class);
                        groupItem.deviceDatumsList = modalGraph.getDeviceData();
                        groupItem.clickDataList = modalGraph.getClickData();
                        groupItem.imprDatumsList = modalGraph.getImprData();
                        groupItem.ispDatumList = modalGraph.getIspData();
                        groupItem.heatmapDatumList = modalGraph.getHeatmapData();
                        set(pieView);

                        jobManager.addJobInBackground(new CreateClickGraph(groupItem.clickDataList,getActivity()));
                        jobManager.addJobInBackground(new CreateImpressionGraph(groupItem.imprDatumsList,getActivity()));
                        jobManager.addJobInBackground(new CreateIspGraph(groupItem.ispDatumList,getActivity()));
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

        // add the request object to the queue to be executed
        ApplicationController.getInstance().addToRequestQueue(req, ApplicationController.REGISTRATION_TAG);
    }

    private JSONObject makeJsonBody(){
        JSONObject jsonObject = new JSONObject();
        try {
            Log.e("CAMPINFO","" + camInfo.getCampId());
            jsonObject.put("campaign_id",camInfo.getCampId());
            jsonObject.put("api","test_detail_screen_request");
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
