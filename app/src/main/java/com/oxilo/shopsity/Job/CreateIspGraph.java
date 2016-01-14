package com.oxilo.shopsity.Job;

import android.content.Context;

import com.oxilo.shopsity.POJO.IspDatum;
import com.oxilo.shopsity.event.IspGraphFinishedEvent;
import com.oxilo.shopsity.fragement.graph.HorizentalGraph;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by ericbasendra on 28/11/15.
 */
public class CreateIspGraph extends Job{
    List<IspDatum> ispDatumList;
    Context mContext;
    public CreateIspGraph(List<IspDatum> ispDatumList,Context mConetx) {
        super(new Params(Priority.MID).requireNetwork().groupBy("fetch-isp"));
        this.mContext = mConetx;
        this.ispDatumList = ispDatumList;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        HorizentalGraph horizentalGraph = new HorizentalGraph(mContext,ispDatumList);
        EventBus.getDefault().post(new IspGraphFinishedEvent(horizentalGraph.getDataSet(),horizentalGraph.getXAxisValues()));
    }

    @Override
    protected void onCancel() {

    }
}
