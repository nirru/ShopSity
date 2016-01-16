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
 * Created by ericbasendra on 14/01/16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModalCheckOut implements Parcelable{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("camp_list")
    @Expose
    private List<CampList> campList = new ArrayList<CampList>();

    protected ModalCheckOut(Parcel in) {
        status = in.readString();
        campList = in.createTypedArrayList(CampList.CREATOR);
    }

    public static final Creator<ModalCheckOut> CREATOR = new Creator<ModalCheckOut>() {
        @Override
        public ModalCheckOut createFromParcel(Parcel in) {
            return new ModalCheckOut(in);
        }

        @Override
        public ModalCheckOut[] newArray(int size) {
            return new ModalCheckOut[size];
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
     * The campList
     */
    public List<CampList> getCampList() {
        return campList;
    }

    /**
     *
     * @param campList
     * The camp_list
     */
    public void setCampList(List<CampList> campList) {
        this.campList = campList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeTypedList(campList);
    }
}
