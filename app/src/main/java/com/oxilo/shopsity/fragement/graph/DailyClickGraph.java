package com.oxilo.shopsity.fragement.graph;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.oxilo.shopsity.POJO.ClickDatum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericbasendra on 01/01/16.
 */
public class DailyClickGraph {

    List<ClickDatum> imprDatumList;
    Context mContext;

    public DailyClickGraph(){

    }

    public DailyClickGraph(Context mContext, List<ClickDatum> imprDatumList){
        this.imprDatumList = imprDatumList;
        this.mContext = mContext;
    }


    public ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;
            ArrayList<BarEntry> valueSet1 = new ArrayList<>();
            for (int i =0;i<imprDatumList.size();i++){
                if (i < 10){
                    ClickDatum ispDatum = imprDatumList.get((imprDatumList.size()-1) - i);
                    BarEntry barEntry = new BarEntry(Integer.parseInt(ispDatum.getCount()),i);
                    valueSet1.add(barEntry);
                }
            }

            BarDataSet barDataSet1 = new BarDataSet(valueSet1, "");
            barDataSet1.setBarSpacePercent(40f);
            barDataSet1.setColor(Color.rgb(0, 155, 0));
            barDataSet1.setDrawValues(false);

            dataSets = new ArrayList<>();
            dataSets.add(barDataSet1);


        return dataSets;
    }

    public ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
            for (int i = 0;i<imprDatumList.size();i++){
                String xAxis1 = imprDatumList.get(i).getDate() + "/n" + imprDatumList.get(i).getHour();
//                String xAxis1 = ActivityUtils.GetDayFromDate(imprDatumList.get(10 - i).getDate()) + "-" + ActivityUtils.GetMonth(imprDatumList.get(10 - i).getDate()) + "-" + ActivityUtils.GetYear(imprDatumList.get(10 - i).getDate());
                xAxis.add(xAxis1);
            }


        return xAxis;
    }
}
