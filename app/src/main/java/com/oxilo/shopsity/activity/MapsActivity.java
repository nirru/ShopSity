package com.oxilo.shopsity.activity;

/*
 All Copyright, Audianz Network Pvt ltd.
CIN:
All intellectual property, code ownership belongs un-conditionally
to Audianz Network Pvt Ltd. No unauthorised code copying,
redistribution and editing is permitted.
Author: Audianz Network Pvt Ltd
CIN:
*/

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.oxilo.shopsity.Constants;
import com.oxilo.shopsity.FetchAddressIntentService;
import com.oxilo.shopsity.MODAL.MobiKytePlaceCampaignInfo;
import com.oxilo.shopsity.POJO.ModalLogin;
import com.oxilo.shopsity.R;
import com.oxilo.shopsity.adapter.MobikytePlaceAutoAdapter;
import com.oxilo.shopsity.fragement.AboutFragement;
import com.oxilo.shopsity.fragement.Action;
import com.oxilo.shopsity.fragement.CampaignListingFragement;
import com.oxilo.shopsity.fragement.ChangePasswordFragment;
import com.oxilo.shopsity.fragement.Checkout;
import com.oxilo.shopsity.fragement.FailFragment;
import com.oxilo.shopsity.fragement.HelpFragement;
import com.oxilo.shopsity.fragement.InVoiceFragment;
import com.oxilo.shopsity.fragement.Map;
import com.oxilo.shopsity.fragement.Message;
import com.oxilo.shopsity.fragement.MyMap;
import com.oxilo.shopsity.fragement.Pay;
import com.oxilo.shopsity.fragement.Preview;
import com.oxilo.shopsity.fragement.ReportFragement;
import com.oxilo.shopsity.fragement.Settings;
import com.oxilo.shopsity.fragement.ThankYou;
import com.oxilo.shopsity.fragement.graph.HeatMapFragement;
import com.oxilo.shopsity.fragement.graph.PieFragment;
import com.oxilo.shopsity.logger.Log;
import com.oxilo.shopsity.ui.CustomAutoCompleteTextView;
import com.oxilo.shopsity.utility.ActivityUtils;
import com.oxilo.shopsity.utility.PermissionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapsActivity extends SampleActivityBase implements
        Map.OnFragmentInteractionListener,
        Message.OnFragmentInteractionListener, Action.OnFragmentInteractionListener,
        Preview.OnFragmentInteractionListener, Checkout.OnFragmentInteractionListener,
        Pay.OnFragmentInteractionListener, ThankYou.OnFragmentInteractionListener,
        FailFragment.OnFragmentInteractionListener, InVoiceFragment.OnFragmentInteractionListener,
        Settings.OnFragmentInteractionListener, PieFragment.OnFragmentInteractionListener,
        CampaignListingFragement.OnFragmentInteractionListener,
        HeatMapFragement.OnFragmentInteractionListener,
        ReportFragement.OnFragmentInteractionListener, HelpFragement.OnFragmentInteractionListener,
        ChangePasswordFragment.OnFragmentInteractionListener,AboutFragement.OnFragmentInteractionListener,
        MyMap.OnFragmentInteractionListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    static final String SEARCH_OPENED = "search_Opened";

    ExitActivityTransition exitTransition;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    FloatingActionButton fabBtn;
    CoordinatorLayout rootLayout;
    Toolbar toolbar;
    NavigationView navigation;
    public static Stack<android.app.Fragment> fragmentStack;
    private FragmentManager fragmentManager;

    //Handling Search View
    private MenuItem mSearchAction;
//    private PlaceAutocompleteAdapter mAdapter;

    //icon that shows when the search bar is closed (magnifier)
    private Drawable mIconOpenSearch;

    //icon that shows when the search bar is opened (x sign)
    private Drawable mIconCloseSearch;

    //keeps track if the search bar is opened
    private boolean mSearchOpened;

    //HAndling Map
    private GoogleMap mMap;
    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));
    public Place place1 = null;


    MarkerOptions markerOptions;
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

    private ModalLogin modalLogin;
    private TextView userNameView;

    private MobiKytePlaceCampaignInfo mobiKytePlaceCampaignInfo;


    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;

    /**
     * Represents a geographical location.
     */
    protected Location mCurrentLocation;

    /**
     * Represents a Circle on Map.
     */
    Circle mapCircle;

    /**
     * Receiver registered with this activity to get the response from FetchAddressIntentService.
     */
    private AddressResultReceiver mResultReceiver;


    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;

    private MobikytePlaceAutoAdapter mAdapter;

    private View mProgressView;

    CustomAutoCompleteTextView searchBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mobiKytePlaceCampaignInfo = new MobiKytePlaceCampaignInfo();
        mResultReceiver = new AddressResultReceiver(new Handler());
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }
        setContentView(R.layout.activity_maps);

        if (savedInstanceState == null) {
            mSearchOpened = false;
        } else {
            mSearchOpened = savedInstanceState.getBoolean(SEARCH_OPENED);
        }


        //Setiing the toolbar
//        toolbar = (Toolbar)findViewById(R.id.toolbar);
//        toolbar.setTitle("Map");
//        ((AppCompatActivity) MapsActivity.this).setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        exitTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.drawerLayout)).start(savedInstanceState);

        // Getting the icons.
        mIconOpenSearch = getResources()
                .getDrawable(R.drawable.ic_search_white_24dp);
        mIconCloseSearch = getResources()
                .getDrawable(R.drawable.ic_close);


//        if (mSearchOpened) {
//            openSearchBar();
//        }

        if (getIntent() != null) {
            modalLogin = getIntent().getParcelableExtra(getResources().getString(R.string.praceable_modal_regsitration));
            Log.e("CLIENT ID", "" + modalLogin.getClientid());
        }

        initDrawerItem();

    }


    /**
     * Initialize all the drawer item here
     * and handling the click event of drawer item
     */
    private void initDrawerItem() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(MapsActivity.this, drawerLayout, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }


            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerLayout.setDrawerListener(drawerToggle);
        // mDrawerToggle.setHomeAsUpIndicator(R.drawable.menu_icon);

        toolbar.setNavigationIcon(R.mipmap.mobikyte_logo);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        navigation = (NavigationView) findViewById(R.id.navigation);
        View header = navigation.getHeaderView(0);
        TextView userNameView = (TextView) header.findViewById(R.id.username_id);
        userNameView.setText("" + modalLogin.getName());
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navItem1:
                        Intent i = new Intent(MapsActivity.this, ObjectiveScreen.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.putExtra(getResources().getString(R.string.praceable_modal_regsitration), modalLogin);
                        startActivity(i);
                        break;
                    case R.id.navItem2:
                        refreshFragement(2);
                        break;
                    case R.id.navItem3:
                        refreshFragement(3);
                        break;
                    case R.id.navItem4:
                        refreshFragement(4);
                        break;
                    case R.id.navItem5:
                        final Intent intent = new Intent(MapsActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        ActivityTransitionLauncher.with(MapsActivity.this).from(getWindow().getDecorView().getRootView()).launch(intent);
                        break;
                    case R.id.navItem6:
                        final Intent mintent = new Intent(MapsActivity.this, WelcomeActivity.class);
                        mintent.putExtra(getResources().getString(R.string.praceable_modal_regsitration), modalLogin);
                        mintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mintent);
                        break;
                    case R.id.navItem7:
                        refreshFragement(7);
                        break;
                    case R.id.navItem8:
                        refreshFragement(8);
                        break;
                }
                return false;
            }
        });

        refreshFragement(1);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }


    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean(SEARCH_OPENED, mSearchOpened);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
            Intent i = new Intent(MapsActivity.this,ObjectiveScreen.class);
            i.putExtra(getResources().getString(R.string.praceable_modal_regsitration), modalLogin);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
        } else if (getFragmentManager().getBackStackEntryCount() == 1) {
            Intent i = new Intent(MapsActivity.this, MapsActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.putExtra(getResources().getString(R.string.praceable_modal_regsitration), modalLogin);
            startActivity(i);
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        mSearchAction = menu.findItem(R.id.action_search);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Fragment f = getFragmentManager().findFragmentById(R.id.main_content);
        if (f instanceof HelpFragement) {
            // do something with f
            menu.findItem(R.id.action_about).setVisible(false);
            return true;
        } else {
            menu.findItem(R.id.action_about).setVisible(true);
            return true;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (drawerToggle.onOptionsItemSelected(item))
//            return true;


        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 1) {
                    if (drawerToggle.onOptionsItemSelected(item))
                        return true;
                } else if (fm.getBackStackEntryCount() == 1) {
                    if (drawerToggle.onOptionsItemSelected(item))
                        return true;
                } else if (fm.getBackStackEntryCount() == 0) {
                    if (drawerToggle.onOptionsItemSelected(item))
                        return true;
                }
                return true;
            case R.id.action_about:
                Fragment fragment = HelpFragement.newInstance("", "");
                ActivityUtils.launchFragementWithAnimation(fragment, MapsActivity.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

//        if (id == R.id.action_search) {
//            if (mSearchOpened) {
//                closeSearchBar();
//            } else {
//                openSearchBar();
//            }
//            return true;
//        }

//        return super.onOptionsItemSelected(item);
    }

    private void refreshFragement(int item) {
        Fragment mapFragment = null;
        fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        switch (item) {
            case 1:
                mapFragment = Map.newInstance("", "", modalLogin);
                ft.add(R.id.main_content, mapFragment);
                ft.commit();
                break;
            case 2:
                mapFragment = CampaignListingFragement.newInstance(modalLogin, "");
                ActivityUtils.launchFragementWithAnimation(mapFragment, MapsActivity.this);
                break;
            case 3:
                Settings settings = Settings.newInstance(modalLogin, "");
                ActivityUtils.launchFragementWithAnimation(settings, MapsActivity.this);
                break;
            case 4:
                mapFragment = HelpFragement.newInstance("","");
                ActivityUtils.launchFragementWithAnimation(mapFragment, MapsActivity.this);
                break;
            case 7:
                mapFragment = ChangePasswordFragment.newInstance(modalLogin, "");
                ActivityUtils.launchFragementWithAnimation(mapFragment, MapsActivity.this);
                break;
            case 8:
                mapFragment = AboutFragement.newInstance("", "");
                ActivityUtils.launchFragementWithAnimation(mapFragment, MapsActivity.this);
                break;
            default:
                mapFragment = Map.newInstance("", "", modalLogin);
                ft.add(R.id.main_content, mapFragment);
                ft.commit();
                break;
        }

    }

    /**

     private void openSearchBar() {

     // Set custom view on action bar.
     ActionBar actionBar = getSupportActionBar();
     actionBar.setDisplayShowCustomEnabled(true);
     actionBar.setCustomView(R.layout.actionbar_search);

     // Search edit text field setup.
     searchBox = (AutoCompleteTextView) actionBar.getCustomView()
     .findViewById(R.id.search_box);

     // Register a listener that receives callbacks when a suggestion has been selected
     searchBox.setOnItemClickListener(mAutocompleteClickListener);

     // Set up the adapter that will retrieve suggestions from the Places Geo Data API that cover
     // the entire world.
     mAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, BOUNDS_GREATER_SYDNEY,
     null);
     searchBox.setAdapter(mAdapter);

     searchBox.requestFocus();
     // show the keyboard
     InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
     imm.showSoftInput(searchBox, InputMethodManager.SHOW_IMPLICIT);

     // Change search icon accordingly.
     mSearchAction.setIcon(mIconCloseSearch);
     mSearchOpened = true;

     }

     private void closeSearchBar() {

     searchBox.setText("");
     // Remove custom view.
     getSupportActionBar().setDisplayShowCustomEnabled(false);

     // hide the keyboard
     InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
     imm.hideSoftInputFromWindow(searchBox.getWindowToken(), 0);

     // Change search icon accordingly.
     mSearchAction.setIcon(mIconOpenSearch);
     mSearchOpened = false;

     }
     */

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void OnResultSelected(final GoogleMap map) {
        if (map != null) {
            mMap = map;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    ActivityCompat.requestPermissions(MapsActivity.this,new String[]{ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION},LOCATION_PERMISSION_REQUEST_CODE);
                    return;
                }
            }else{
                map.setMyLocationEnabled(true);
                map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
                        displayLocationOnDragerEnd(map.getMyLocation().getLatitude(),map.getMyLocation().getLongitude());
                        return true;
                    }
                });
            }
            markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).draggable(true);
        }

    }

    @Override
    public void mapSearchPlacce(CustomAutoCompleteTextView searchView, View mProgressView) {
        this.mProgressView = mProgressView;
        searchBox = searchView;
        mAdapter = new MobikytePlaceAutoAdapter(MapsActivity.this, mGoogleApiClient, BOUNDS_GREATER_SYDNEY,
                null);
        searchView.setOnItemClickListener(mAutocompleteClickListener);
        searchView.setAdapter(mAdapter);
    }

    @Override
    public void onDragerEnd(Marker marker) {
        stopLocationUpdates();
        if (marker!=null){
            displayLocationOnDragerEnd(marker.getPosition().latitude,marker.getPosition().longitude);
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                searchBox.setText(address + "," + city + "," + state + "," + country);
                searchBox.dismissDropDown();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (NullPointerException ex){
                ex.printStackTrace();
            }
        }

    }

    @Override
    public MobiKytePlaceCampaignInfo getMobikytePlaceInfo() {
        return this.mobiKytePlaceCampaignInfo;
    }


    /**
     * Method to verify google play services on the device
     * */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(MapsActivity.this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, MapsActivity.this,
                        Map.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(MapsActivity.this,
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                ((AppCompatActivity) MapsActivity.this).finish();
            }
            return false;
        }
        return true;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_RESOLVE_ERROR) {
            mResolvingError = false;
            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!mGoogleApiClient.isConnecting() &&
                        !mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(MapsActivity.this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mGoogleApiClient != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    displayLocationOnDragerEnd(mMap.getMyLocation().getLatitude(),mMap.getMyLocation().getLongitude());
                    return true;
                }
            });
            mGoogleApiClient.connect();
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            getMobiAddressInfo();
            displayLocation();
        }
    }


    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(MapsActivity.this)
                .enableAutoManage(this, 0 /* clientId */, MapsActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(LocationServices.API)
                .build();

        createLocationRequest();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                ActivityCompat.requestPermissions(MapsActivity.this,new String[]{ACCESS_FINE_LOCATION},LOCATION_PERMISSION_REQUEST_CODE);
                return;
            }
        }else{
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            displayLocation();
            startLocationUpdates();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        displayLocation();
        getMobiAddressInfo();

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (result.hasResolution()) {
            try {
                mResolvingError = true;
                result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                mGoogleApiClient.connect();
            }
        } else {
            // Show dialog using GoogleApiAvailability.getErrorDialog()
            showErrorDialog(result.getErrorCode());
            mResolvingError = true;
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.

        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }


    /**
     * Requests location updates from the FusedLocationApi.
     */
    protected void startLocationUpdates() {
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                ActivityCompat.requestPermissions(MapsActivity.this,new String[]{ACCESS_FINE_LOCATION},LOCATION_PERMISSION_REQUEST_CODE);
                return;
            }
        }else{
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    private void displayLocation() {
        try {
            if (mCurrentLocation!=null) {
                double latitude = mCurrentLocation.getLatitude();
                double longitude = mCurrentLocation.getLongitude();
                if (mapCircle != null)
                    mapCircle.remove();
                if (mMap!=null)
                    mMap.clear();
                mapCircle = mMap.addCircle(new CircleOptions()
                        .center(new LatLng(latitude, longitude))
                        .radius(7500)
                        .strokeColor(Color.parseColor("#3a89e6"))
                        .strokeWidth(3)
                        .fillColor(Color.TRANSPARENT));
                mMap.addMarker(markerOptions.position(new LatLng(latitude, longitude)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12));
            }
            else {
                Toast.makeText(MapsActivity.this,"Couldn't get the location. Make sure location is enabled on the device",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void displayLocationOnDragerEnd(double latitude,double longitude) {
        try {
            if (mapCircle != null)
                mapCircle.remove();
            if (mMap!=null)
                mMap.clear();
            mapCircle = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(latitude, longitude))
                    .radius(7500)
                    .strokeColor(Color.parseColor("#3a89e6"))
                    .strokeWidth(3)
                    .fillColor(Color.TRANSPARENT));
            mMap.addMarker(markerOptions.position(new LatLng(latitude, longitude)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12));
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void getMobiAddressInfo(){
        if (mCurrentLocation != null){
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            // Address found using the Geocoder.
            List<Address> addresses = null;

            String errorMessage = "";


            try {
                // Using getFromLocation() returns an array of Addresses for the area immediately
                // surrounding the given latitude and longitude. The results are a best guess and are
                // not guaranteed to be accurate.
                addresses = geocoder.getFromLocation(
                        mCurrentLocation.getLatitude(),
                        mCurrentLocation.getLongitude(),
                        // In this sample, we get just a single address.
                        1);
            } catch (IOException ioException) {
                // Catch network or other I/O problems.
                errorMessage = getString(R.string.service_not_available);
                android.util.Log.e(TAG, errorMessage, ioException);
            } catch (IllegalArgumentException illegalArgumentException) {
                // Catch invalid latitude or longitude values.
                errorMessage = getString(R.string.invalid_lat_long_used);
                android.util.Log.e(TAG, errorMessage + ". " +
                        "Latitude = " + mCurrentLocation.getLatitude() +
                        ", Longitude = " + mCurrentLocation.getLongitude(), illegalArgumentException);
            }

            // Handle case where no address was found.
            if (addresses == null || addresses.size()  == 0) {
                if (errorMessage.isEmpty()) {
                    errorMessage = getString(R.string.no_address_found);
                    android.util.Log.e(TAG, errorMessage);
                }
            } else {
                Address address = addresses.get(0);
                ArrayList<String> addressFragments = new ArrayList<String>();

                // Fetch the address lines using getAddressLine,
                // join them, and send them to the thread.
                for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    addressFragments.add(address.getAddressLine(i));
                }
                android.util.Log.i(TAG, getString(R.string.address_found));
            }

            // Handle case where no address was found.
            if (addresses == null || addresses.size()  == 0) {
                if (errorMessage.isEmpty()) {
                    errorMessage = getString(R.string.no_address_found);
                    android.util.Log.e(TAG, errorMessage);
                }
            } else {
                StringBuilder sb = new StringBuilder();
                Address address = addresses.get(0);
                ArrayList<String> addressFragments = new ArrayList<String>();


                // Fetch the address lines using {@code getAddressLine},
                // join them, and send them to the thread. The {@link android.location.address}
                // class provides other options for fetching address details that you may prefer
                // to use. Here are some examples:
                // getLocality() ("Mountain View", for example)
                // getAdminArea() ("CA", for example)
                // getPostalCode() ("94043", for example)
                // getCountryCode() ("US", for example)
                // getCountryName() ("United States", for example)

                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append(" ");
                }
//                sb.append(address.getLocality()).append(",");
                sb.append(address.getPostalCode()).append(",");
                sb.append(address.getCountryName());
                String addressLine = sb.toString();
                addressLine.toString().replaceAll("^,", "").replaceAll(",,", ",").replaceAll(",$", "");

                String countryName = address.getCountryName();
                String psotalCode = address.getPostalCode();

                mobiKytePlaceCampaignInfo.setAddress(addressLine != null ? addressLine.toString() : "Jaipur");
                mobiKytePlaceCampaignInfo.setLat(address.getLatitude() != 0 ? address.getLatitude() : 27.00);
                mobiKytePlaceCampaignInfo.setLng(address.getLongitude() != 0 ? address.getLongitude() : 27.00);
                mobiKytePlaceCampaignInfo.setMobileNumber("34454545");
                mobiKytePlaceCampaignInfo.setCountry(countryName.toString() != null ? countryName.toString() : "India");

                android.util.Log.i(TAG, getString(R.string.address_found));
            }
        }

    }

    /**
     * Creates an intent, adds location data to it as an extra, and starts the intent service for
     * fetching an address.
     */
//    protected void startIntentService() {
//        if (!Geocoder.isPresent()) {
//            Toast.makeText(MapsActivity.this, R.string.no_geocoder_available, Toast.LENGTH_LONG).show();
//            return;
//        }
//        // It is possible that the user presses the button to get the address before the
//        // GoogleApiClient object successfully connects. In such a case, mAddressRequested
//        // is set to true, but no attempt is made to fetch the address (see
//        // fetchAddressButtonHandler()) . Instead, we start the intent service here if the
//        // user has requested an address, since we now have a connection to GoogleApiClient.
//
//        // Create an intent for passing to the intent service responsible for fetching the address.
//        Intent intent = new Intent(MapsActivity.this, FetchAddressIntentService.class);
//
//        // Pass the result receiver as an extra to the service.
//        intent.putExtra(Constants.RECEIVER, mResultReceiver);
//
//        // Pass the location data as an extra to the service.
//        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mCurrentLocation);
//
//        // Start the service. If the service isn't already running, it is instantiated and started
//        // (creating a process for it if needed); if it is running then it remains running. The
//        // service kills itself automatically once all intents are processed.
//        startService(intent);
//    }
    /**
     * Listener that handles selections from suggestions from the AutoCompleteTextView that
     * displays Place suggestions.
     * Gets the place id of the selected item and issues a request to the Places Geo Data API
     * to retrieve more details about the place.
     *
     * @see com.google.android.gms.location.places.GeoDataApi#getPlaceById(com.google.android.gms.common.api.GoogleApiClient,
     * String...)
     */
    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            Log.i("MAP", "Autocomplete item selected: " + primaryText);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
             details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

            Toast.makeText(MapsActivity.this, "Clicked: " + primaryText,
                    Toast.LENGTH_SHORT).show();
            Log.i("MAP", "Called getPlaceById to get Place details for " + placeId);
        }
    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e("MAP", "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }

            stopLocationUpdates();

            // Get the Place object from the buffer.
            final Place place = places.get(0);

            mobiKytePlaceCampaignInfo.setAddress(place.getAddress().toString());
            mobiKytePlaceCampaignInfo.setLat(place.getLatLng().latitude);
            mobiKytePlaceCampaignInfo.setLng(place.getLatLng().longitude);
            mobiKytePlaceCampaignInfo.setMobileNumber(place.getPhoneNumber().toString());
            mobiKytePlaceCampaignInfo.setCountry(place.getLocale().getCountry().toString());

            mMap.clear();
            if (mapCircle!=null)
                mapCircle.remove();
            mapCircle = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(place.getLatLng().latitude, place.getLatLng().longitude))
                    .radius(3500)
                    .strokeColor(Color.parseColor("#3a89e6"))
                    .strokeWidth(3)
                    .fillColor(Color.TRANSPARENT));
            mMap.addMarker(markerOptions.position(place.getLatLng()).title(place.getName().toString()));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(),12));

            // Format details of the place for display and show it in a TextView.
//            mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
//                    place.getId(), place.getAddress(), place.getPhoneNumber(),
//                    place.getWebsiteUri()));



            Log.i("MAP", "Place details received: " + place.getName());

            places.release();
        }
    };


    /**
     * Receiver for data sent from FetchAddressIntentService.
     */
    @SuppressLint("ParcelCreator")
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        /**
         *  Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string or an error message sent from the intent service.
            mobiKytePlaceCampaignInfo = resultData.getParcelable(Constants.RESULT_DATA_KEY);

            String phone = mobiKytePlaceCampaignInfo.getAddress();
            String country = mobiKytePlaceCampaignInfo.getCountry();
//            displayAddressOutput();

            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
//                showToast(getString(R.string.address_found));
            }

            // Reset. Enable the Fetch Address button and stop showing the progress bar.
//            mAddressRequested = false;
//            updateUIWidgets();
//            showProgress(false);
        }
    }
    // The rest of this code is all about building the error dialog

    /* Creates a dialog for an error message */
    private void showErrorDialog(int errorCode) {
        // Create a fragment for the error dialog
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        // Pass the error that should be displayed
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "errordialog");
    }

    /* Called from ErrorDialogFragment when the dialog is dismissed. */
    public void onDialogDismissed() {
        mResolvingError = false;
    }

//    /* A fragment to display an error dialog */
    public static class ErrorDialogFragment extends DialogFragment {
        public ErrorDialogFragment() { }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the error code and retrieve the appropriate dialog
            int errorCode = this.getArguments().getInt(DIALOG_ERROR);
            return GoogleApiAvailability.getInstance().getErrorDialog(
                    this.getActivity(), errorCode, REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            ((MapsActivity) getActivity()).onDialogDismissed();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (checkPlayServices()) {
        if (!PermissionUtils.isLocationEnabled(MapsActivity.this)) {
            ActivityUtils.showSettingsAlert(MapsActivity.this);
        } else {
            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
//                    mGoogleApiClient.connect();
                startLocationUpdates();
            }
        }
      }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
}
