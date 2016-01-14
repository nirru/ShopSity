package com.oxilo.shopsity.Job;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.oxilo.shopsity.POJO.HeatmapDatum;
import com.oxilo.shopsity.event.HeatMapFinishedEvent;
import com.oxilo.shopsity.logger.Log;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by ericbasendra on 29/11/15.
 */
public class CreateHeatMapGraph extends Job{
    Context mContext;
    List<HeatmapDatum> heatmapDatumList;

    public CreateHeatMapGraph(Context mContext, List<HeatmapDatum> heatmapDatumList) {
        super(new Params(Priority.HIGH).requireNetwork().groupBy("fetch-heatmap"));
        this.mContext = mContext;
        this.heatmapDatumList = heatmapDatumList;
        Log.e("HETA MAP DATA SIZE","" + heatmapDatumList.size());
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        EventBus.getDefault().post(new HeatMapFinishedEvent(readItems()));
    }

    @Override
    protected void onCancel() {

    }

    private ArrayList<LatLng> readItems() throws JSONException {
        ArrayList<LatLng> list = new ArrayList<LatLng>();
        double lat = 0.0;
        double lng = 0.0;
        for (int i = 0; i < heatmapDatumList.size(); i++) {
            if (!heatmapDatumList.get(i).getLat().toString().equals("")){
                lat = Double.parseDouble(heatmapDatumList.get(i).getLat().toString());
            }
            if (!heatmapDatumList.get(i).getLog().toString().equals("")){
                lng = Double.parseDouble(heatmapDatumList.get(i).getLog().toString());
            }
            list.add(new LatLng(lat, lng));
        }
        return list;
    }
}
