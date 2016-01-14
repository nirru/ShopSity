package com.oxilo.shopsity.fragement;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oxilo.shopsity.MODAL.MobiKytePlaceCampaignInfo;
import com.oxilo.shopsity.POJO.ModalLogin;
import com.oxilo.shopsity.R;
import com.oxilo.shopsity.ui.CustomAutoCompleteTextView;
import com.oxilo.shopsity.utility.ActivityUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Map.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Map#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Map extends Fragment implements GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMapClickListener,GoogleMap.OnMarkerDragListener
{
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


    //HAndling Map
    MapView mapView;
    private GoogleMap mMap;
    private boolean mapsSupported = true;
    Toolbar toolbar;

    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private MobiKytePlaceCampaignInfo mobiKytePlaceCampaignInfo;
    CustomAutoCompleteTextView searchBox;
    private View mProgressView;
    private Context mContext;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Map.
     */
    // TODO: Rename and change types and number of parameters
    public static Map newInstance(String param1, String param2,ModalLogin modalLogin) {
        Map fragment = new Map();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putParcelable(ARG_PARAM3,modalLogin);
        fragment.setArguments(args);
        return fragment;
    }

    public Map() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            modalLogin = getArguments().getParcelable(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        ActivityUtils.setupUI(v.findViewById(R.id.drawerLayout),getActivity());

        mobiKytePlaceCampaignInfo = new MobiKytePlaceCampaignInfo();

        // First we need to check availability of play services
        mContext = getActivity();
        mapView = (MapView) v.findViewById(R.id.map);
        initUiWidget(v);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @SuppressWarnings("deprecation")
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
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
//        mMap.addMarker(new MarkerOptions()
//                .position(latLng)
//                .draggable(true));
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        mListener.onDragerEnd(marker);
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 12));
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

        void OnResultSelected(GoogleMap map);

        void mapSearchPlacce(CustomAutoCompleteTextView searchView,View mProgressView);

        void onDragerEnd(Marker marker);

        MobiKytePlaceCampaignInfo getMobikytePlaceInfo();
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
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    mMap.setOnMarkerDragListener(Map.this);
                    mMap.setOnMapClickListener(Map.this);
                    LatLng sydney = new LatLng(-34, 151);
                    mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").draggable(true));

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));

                    mListener.OnResultSelected(mMap);

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
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        toolbar.setTitle("Location");
        searchBox.setText("");
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void initUiWidget(final View v){
        toolbar = (Toolbar)v.findViewById(R.id.toolbar);
        toolbar.setTitle("Location");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);
        AppCompatButton action_next = (AppCompatButton)v.findViewById(R.id.action_next_btn);
        action_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mobiKytePlaceCampaignInfo =  mListener.getMobikytePlaceInfo();
                        String naaa= "" + mobiKytePlaceCampaignInfo.getLat();
                        String cou = mobiKytePlaceCampaignInfo.getCountry();
                        Message mapFragment = Message.newInstance(mobiKytePlaceCampaignInfo, "", modalLogin);
                        ActivityUtils.launchFragementWithAnimation(mapFragment,getActivity());
                    }
                }, 600);

            }
        });
        searchBox = (CustomAutoCompleteTextView) v
                .findViewById(R.id.search_box);

        searchBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (searchBox.getRight() - searchBox.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        searchBox.setText("");
                        return true;
                    }
                }
                return false;
            }
        });


        mProgressView = v.findViewById(R.id.login_progress);


        mListener.mapSearchPlacce(searchBox, mProgressView);
    }
}
