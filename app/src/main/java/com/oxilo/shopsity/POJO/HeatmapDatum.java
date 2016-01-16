package com.oxilo.shopsity.POJO;
/*
 All Copyright, Audianz Network Pvt ltd.
CIN:
All intellectual property, code ownership belongs un-conditionally
to Audianz Network Pvt Ltd. No unauthorised code copying,
redistribution and editing is permitted.
Author: Audianz Network Pvt Ltd
CIN:
*/

/**
 * Created by ericbasendra on 24/11/15.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeatmapDatum implements Parcelable{

    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("camp_id")
    @Expose
    private String campId;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("log")
    @Expose
    private String log;

    protected HeatmapDatum(Parcel in) {
        count = in.readString();
        campId = in.readString();
        lat = in.readString();
        log = in.readString();
    }

    public static final Creator<HeatmapDatum> CREATOR = new Creator<HeatmapDatum>() {
        @Override
        public HeatmapDatum createFromParcel(Parcel in) {
            return new HeatmapDatum(in);
        }

        @Override
        public HeatmapDatum[] newArray(int size) {
            return new HeatmapDatum[size];
        }
    };

    /**
     *
     * @return
     * The count
     */
    public String getCount() {
        return count;
    }

    /**
     *
     * @param count
     * The count
     */
    public void setCount(String count) {
        this.count = count;
    }

    /**
     *
     * @return
     * The campId
     */
    public String getCampId() {
        return campId;
    }

    /**
     *
     * @param campId
     * The camp_id
     */
    public void setCampId(String campId) {
        this.campId = campId;
    }

    /**
     *
     * @return
     * The lat
     */
    public String getLat() {
        return lat;
    }

    /**
     *
     * @param lat
     * The lat
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     *
     * @return
     * The log
     */
    public String getLog() {
        return log;
    }

    /**
     *
     * @param log
     * The log
     */
    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(count);
        parcel.writeString(campId);
        parcel.writeString(lat);
        parcel.writeString(log);
    }
}
