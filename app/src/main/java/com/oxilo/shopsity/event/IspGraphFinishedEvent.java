package com.oxilo.shopsity.event;

import com.github.mikephil.charting.data.BarDataSet;

import java.util.ArrayList;

/**
 * Created by ericbasendra on 28/11/15.
 */
public class IspGraphFinishedEvent {

    ArrayList<BarDataSet> dataSets;
    ArrayList<String> xAxis;

    public IspGraphFinishedEvent(){

    }

    public IspGraphFinishedEvent(ArrayList<BarDataSet>dataSets,ArrayList<String> xAxis){
        this.dataSets =dataSets;
        this.xAxis = xAxis;
    }

    public ArrayList<BarDataSet> getDataSets() {
        return dataSets;
    }

    public void setDataSets(ArrayList<BarDataSet> dataSets) {
        this.dataSets = dataSets;
    }

    public ArrayList<String> getxAxis() {
        return xAxis;
    }

    public void setxAxis(ArrayList<String> xAxis) {
        this.xAxis = xAxis;
    }

}
