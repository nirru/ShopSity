package com.oxilo.shopsity.Job;

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

import com.oxilo.shopsity.POJO.ImprDatum;
import com.oxilo.shopsity.event.ImpressionGraphFinishedEvent;
import com.oxilo.shopsity.fragement.graph.BarGraph;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import de.greenrobot.event.EventBus;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by ericbasendra on 28/11/15.
 */
public class CreateImpressionGraph extends Job {
    private static final AtomicInteger jobCounter = new AtomicInteger(0);
    private final int id;
    List<ImprDatum> imprDatumList;

    /**LINE CHART VIEW COMPONENET
     *
     */
    private LineChartView chart;
    private LineChartData data;
    private int numberOfLines = 1;

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
    private Context mContext;

    public CreateImpressionGraph(List<ImprDatum> imprDatumList,Context mContext) {
        super(new Params(Priority.MID).requireNetwork().groupBy("fetch-impression"));
        id = jobCounter.incrementAndGet();
        this.mContext = mContext;
        this.imprDatumList = imprDatumList;
    }

    @Override
    public void onAdded() {
    }

    @Override
    public void onRun() throws Throwable {
//        if(id != jobCounter.get()) {
//            //looks like other fetch jobs has been added after me. no reason to keep fetching
//            //many times, cancel me, let the other one to do task.
//            return;
//        }
//        createLineGraph();
        BarGraph horizentalGraph = new BarGraph(mContext,imprDatumList);
        EventBus.getDefault().post(new ImpressionGraphFinishedEvent(horizentalGraph.getDataSet(),horizentalGraph.getXAxisValues()));
//        EventBus.getDefault().post(new ImpressionGraphFinishedEvent(data));

    }

    @Override
    protected void onCancel() {

    }

    @SuppressWarnings("deprecation")
    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return super.shouldReRunOnThrowable(throwable);
    }
/**
    void createLineGraph(){
        if (imprDatumList.size() > 0){
            List<Line> lines = new ArrayList<Line>();
            List<PointValue> values = new ArrayList<PointValue>();
            for (int i = 0;i< imprDatumList.size();i++){
                ImprDatum imprDatum = imprDatumList.get(i);
                values.add(new PointValue(Integer.parseInt(imprDatum.getHour()), Integer.parseInt(imprDatum.getCount())));
            }

            Line line = new Line(values);

            line.setColor(ChartUtils.COLORS[0]);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            if (pointsHaveDifferentColor){
                line.setPointColor(ChartUtils.COLORS[(0 + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);

            data = new LineChartData(lines);

            if (hasAxes) {
                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);
                if (hasAxesNames) {
                    String xAxis = ActivityUtils.GetDayFromDate(imprDatumList.get(0).getDate()) + "-" + ActivityUtils.GetMonth(imprDatumList.get(0).getDate()) + "-" + ActivityUtils.GetYear(imprDatumList.get(0).getDate());
                    axisX.setName(xAxis + "/" + "Hour");
                    axisY.setName("Count");
                }
                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);
            } else {
                data.setAxisXBottom(null);
                data.setAxisYLeft(null);
            }
        }



//        data.setBaseValue(Float.NEGATIVE_INFINITY);
    }
/**
    private void reset() {
        numberOfLines = 1;

        hasAxes = true;
        hasAxesNames = true;
        hasLines = true;
        hasPoints = true;
//        shape = ValueShape.CIRCLE;
        isFilled = true;
        hasLabels = false;
        isCubic = true;
        hasLabelForSelected = false;
        pointsHaveDifferentColor = false;

        chart.setValueSelectionEnabled(hasLabelForSelected);
        resetViewport();
    }

    private void resetViewport() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(chart.getMaximumViewport());
        v.bottom = 0;
        v.top = 100;
        v.left = 0;
        v.right = numberOfPoints - 1;
        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);
    }
*/



}
