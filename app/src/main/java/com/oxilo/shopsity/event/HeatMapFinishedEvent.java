package com.oxilo.shopsity.event;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by ericbasendra on 29/11/15.
 */
public class HeatMapFinishedEvent {
    ArrayList<LatLng> latLngArrayList;

    public HeatMapFinishedEvent(){

    }
    public HeatMapFinishedEvent(ArrayList<LatLng> latLngArrayList){
        this.latLngArrayList = latLngArrayList;
    }
    public ArrayList<LatLng> getLatLngArrayList() {
        return latLngArrayList;
    }

    public void setLatLngArrayList(ArrayList<LatLng> latLngArrayList) {
        this.latLngArrayList = latLngArrayList;
    }


}
