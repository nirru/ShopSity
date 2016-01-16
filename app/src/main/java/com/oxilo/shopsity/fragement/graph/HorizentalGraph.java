package com.oxilo.shopsity.fragement.graph;

/*
 All Copyright, Audianz Network Pvt ltd.
CIN:
All intellectual property, code ownership belongs un-conditionally
to Audianz Network Pvt Ltd. No unauthorised code copying,
redistribution and editing is permitted.
Author: Audianz Network Pvt Ltd
CIN:
*/

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.oxilo.shopsity.POJO.IspDatum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericbasendra on 25/11/15.
 */
public class HorizentalGraph {

    Context mContext;
    List<IspDatum>ispDatumList;

    public HorizentalGraph(){

    }

    public HorizentalGraph(Context mContext,List<IspDatum>ispDatumList){
        this.ispDatumList = ispDatumList;
        this.mContext = mContext;
    }

//    public void setHorizontalBarChart(){
//        BarData data = new BarData(getXAxisValues(), getDataSet());
//        chart.setData(data);
//        chart.setDescription("My Chart");
//        chart.animateXY(2000, 2000);
//        chart.invalidate();
//    }

    public ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
//        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
//        valueSet1.add(v1e1);
//        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
//        valueSet1.add(v1e2);
//        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
//        valueSet1.add(v1e3);
//        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
//        valueSet1.add(v1e4);
//        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
//        valueSet1.add(v1e5);
//        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
//        valueSet1.add(v1e6);


        for (int i =0;i<ispDatumList.size();i++){
            if (i < 10){
                IspDatum ispDatum = ispDatumList.get(10 - i);
                BarEntry barEntry = new BarEntry(Integer.parseInt(ispDatum.getCount()),i);
                valueSet1.add(barEntry);
            }
        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setDrawValues(true);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }

    public ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int i = 0;i<ispDatumList.size();i++){
            if (i < 10){
                xAxis.add(ispDatumList.get(10 - i).getDevice());
            }

        }
//        xAxis.add("JAN");
//        xAxis.add("FEB");
//        xAxis.add("MAR");
//        xAxis.add("APR");
//        xAxis.add("MAY");
//        xAxis.add("JUN");
        return xAxis;
    }

}
