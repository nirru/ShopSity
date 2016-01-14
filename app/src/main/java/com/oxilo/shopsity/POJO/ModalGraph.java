package com.oxilo.shopsity.POJO;

/**
 * Created by ericbasendra on 24/11/15.
 */
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModalGraph implements Parcelable{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("impr_data")
    @Expose
    private List<ImprDatum> imprData = new ArrayList<ImprDatum>();
    @SerializedName("click_data")
    @Expose
    private List<ClickDatum> clickData = new ArrayList<ClickDatum>();
    @SerializedName("device_data")
    @Expose
    private List<DeviceDatum> deviceData = new ArrayList<DeviceDatum>();
    @SerializedName("isp_data")
    @Expose
    private List<IspDatum> ispData = new ArrayList<IspDatum>();
    @SerializedName("heatmap_data")
    @Expose
    private List<HeatmapDatum> heatmapData = new ArrayList<HeatmapDatum>();

    protected ModalGraph(Parcel in) {
        status = in.readString();
        in.readTypedList(imprData,ImprDatum.CREATOR);
        in.readTypedList(deviceData,DeviceDatum.CREATOR);
        in.readTypedList(ispData,IspDatum.CREATOR);
        in.readTypedList(heatmapData,HeatmapDatum.CREATOR);
    }

    public static final Creator<ModalGraph> CREATOR = new Creator<ModalGraph>() {
        @Override
        public ModalGraph createFromParcel(Parcel in) {
            return new ModalGraph(in);
        }

        @Override
        public ModalGraph[] newArray(int size) {
            return new ModalGraph[size];
        }
    };

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The imprData
     */
    public List<ImprDatum> getImprData() {
        return imprData;
    }

    /**
     *
     * @param imprData
     * The impr_data
     */
    public void setImprData(List<ImprDatum> imprData) {
        this.imprData = imprData;
    }

    /**
     *
     * @return
     * The clickData
     */
    public List<ClickDatum> getClickData() {
        return clickData;
    }

    /**
     *
     * @param clickData
     * The click_data
     */
    public void setClickData(List<ClickDatum> clickData) {
        this.clickData = clickData;
    }

    /**
     *
     * @return
     * The deviceData
     */
    public List<DeviceDatum> getDeviceData() {
        return deviceData;
    }

    /**
     *
     * @param deviceData
     * The device_data
     */
    public void setDeviceData(List<DeviceDatum> deviceData) {
        this.deviceData = deviceData;
    }

    /**
     *
     * @return
     * The ispData
     */
    public List<IspDatum> getIspData() {
        return ispData;
    }

    /**
     *
     * @param ispData
     * The isp_data
     */
    public void setIspData(List<IspDatum> ispData) {
        this.ispData = ispData;
    }

    /**
     *
     * @return
     * The heatmapData
     */
    public List<HeatmapDatum> getHeatmapData() {
        return heatmapData;
    }

    /**
     *
     * @param heatmapData
     * The heatmap_data
     */
    public void setHeatmapData(List<HeatmapDatum> heatmapData) {
        this.heatmapData = heatmapData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeTypedList(imprData);
        parcel.writeTypedList(deviceData);
        parcel.writeTypedList(ispData);
        parcel.writeTypedList(heatmapData);
    }
}
