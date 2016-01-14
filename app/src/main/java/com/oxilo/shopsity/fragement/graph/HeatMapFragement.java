package com.oxilo.shopsity.fragement.graph;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.oxilo.shopsity.ApplicationController;
import com.oxilo.shopsity.Job.CreateHeatMapGraph;
import com.oxilo.shopsity.POJO.CampList;
import com.oxilo.shopsity.POJO.ModalAlreadyRegistred;
import com.oxilo.shopsity.POJO.ModalGraph;
import com.oxilo.shopsity.R;
import com.oxilo.shopsity.activity.MapsActivity;
import com.oxilo.shopsity.event.HeatMapFinishedEvent;
import com.oxilo.shopsity.holder.GroupItem;
import com.oxilo.shopsity.logger.Log;
import com.oxilo.shopsity.utility.PermissionUtils;
import com.oxilo.shopsity.volley.VolleyErrorHelper;
import com.path.android.jobqueue.JobManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HeatMapFragement.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HeatMapFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeatMapFragement extends Fragment implements GoogleMap.OnMyLocationButtonClickListener,ActivityCompat.OnRequestPermissionsResultCallback{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //HAndling Map
    MapView mapView;
    private GoogleMap mMap;
    private boolean mapsSupported = true;
    private GroupItem groupItem;
    private View mProgressView;
    private View mLoginFormView;

    private CampList camInfo;

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;
    Toolbar toolbar;

    /**
     * HEatMAp Component
     */

    /**
     * Alternative radius for convolution
     */
    private static final int ALT_HEATMAP_RADIUS = 10;

    /**
     * Alternative opacity of heatmap overlay
     */
    private static final double ALT_HEATMAP_OPACITY = 0.4;

    /**
     * Alternative heatmap gradient (blue -> red)
     * Copied from Javascript version
     */
    private static final int[] ALT_HEATMAP_GRADIENT_COLORS = {
            Color.argb(0, 0, 255, 255),// transparent
            Color.argb(255 / 3 * 2, 0, 255, 255),
            Color.rgb(0, 191, 255),
            Color.rgb(0, 0, 127),
            Color.rgb(255, 0, 0)
    };

    public static final float[] ALT_HEATMAP_GRADIENT_START_POINTS = {
            0.0f, 0.10f, 0.20f, 0.60f, 1.0f
    };

    public static final Gradient ALT_HEATMAP_GRADIENT = new Gradient(ALT_HEATMAP_GRADIENT_COLORS,
            ALT_HEATMAP_GRADIENT_START_POINTS);

    private HeatmapTileProvider mProvider;
    private TileOverlay mOverlay;

    private boolean mDefaultGradient = true;
    private boolean mDefaultRadius = true;
    private boolean mDefaultOpacity = true;
    JobManager jobManager;

    /**
     * Maps name of data set to data (list of LatLngs)
     * Also maps to the URL of the data set for attribution
     */
    private HashMap<String, DataSet> mLists = new HashMap<String, DataSet>();

    public HeatMapFragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param camInfo Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HeatMapFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static HeatMapFragement newInstance(CampList camInfo, String param2) {
        HeatMapFragement fragment = new HeatMapFragement();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, camInfo);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            camInfo = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_heat_map_fragement, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) view.findViewById(R.id.map);
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
    public boolean onMyLocationButtonClick() {
        return false;
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission((MapsActivity) getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
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
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            mapsSupported = false;
        }

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
        initializeMap();
    }

    private void initializeMap() {
        if (mMap == null && mapsSupported) {
            mapView = (MapView) getActivity().findViewById(R.id.map);
           mapView.getMapAsync(new OnMapReadyCallback() {
               @Override
               public void onMapReady(GoogleMap googleMap) {
                   mMap = googleMap;
                   //setup markers etc...
                   // Add a marker in Sydney and move the camera
                   LatLng sydney = new LatLng(-34, 151);
                   mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,14));
               }
           });

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle("HeatMap");
        mapView.onResume();
        initializeMap();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @SuppressWarnings("UnusedDeclaration")
    public void onEventMainThread(HeatMapFinishedEvent event) {
        // Update the UI in the main thread
        // Create a heat map tile provider, passing it the latlngs of the police stations.
        showProgress(false);
        if (event.getLatLngArrayList().size() > 0) {
            mapView.setVisibility(View.VISIBLE);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(event.getLatLngArrayList().get(1), 10));
            mProvider = new HeatmapTileProvider.Builder()
                    .data(event.getLatLngArrayList())
                    .build();
            // Add a tile overlay to the map, using the heat map tile provider.
            mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
        }
        else{
            mapView.setVisibility(View.GONE);
            Toast.makeText(getActivity(),"No Heatmap Found",Toast.LENGTH_LONG).show();
        }
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
        jobManager = ApplicationController.getInstance().getJobManager();
        groupItem = new GroupItem();
        mLoginFormView = view.findViewById(R.id.login_form);
        mProgressView = view.findViewById(R.id.login_progress);

        Button change_radius = (Button)view.findViewById(R.id.change_radius);
        final Button change_gradient = (Button)view.findViewById(R.id.change_gradient);
        Button change_opacity = (Button)view.findViewById(R.id.change_opacity);

        change_radius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeRadius();
            }
        });

        change_gradient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeGradient();
            }
        });

        change_opacity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeOpacity();
            }
        });
    }


    //handler with map
    public void changeRadius() {
        if (mDefaultRadius) {
            mProvider.setRadius(ALT_HEATMAP_RADIUS);
        } else {
            mProvider.setRadius(HeatmapTileProvider.DEFAULT_RADIUS);
        }
        mOverlay.clearTileCache();
        mDefaultRadius = !mDefaultRadius;
    }

    public void changeGradient() {
        if (mDefaultGradient) {
            mProvider.setGradient(ALT_HEATMAP_GRADIENT);
        } else {
            mProvider.setGradient(HeatmapTileProvider.DEFAULT_GRADIENT);
        }
        mOverlay.clearTileCache();
        mDefaultGradient = !mDefaultGradient;
    }

    public void changeOpacity() {
        if (mDefaultOpacity) {
            mProvider.setOpacity(ALT_HEATMAP_OPACITY);
        } else {
            mProvider.setOpacity(HeatmapTileProvider.DEFAULT_OPACITY);
        }
        mOverlay.clearTileCache();
        mDefaultOpacity = !mDefaultOpacity;
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
                        groupItem.heatmapDatumList = modalGraph.getHeatmapData();
                        jobManager.addJobInBackground(new CreateHeatMapGraph(getActivity(),groupItem.heatmapDatumList));
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
