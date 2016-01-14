package com.oxilo.shopsity.POJO;

/**
 * Created by ericbasendra on 24/11/15.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceDatum implements Parcelable{

    @SerializedName("device")
    @Expose
    private String device;
    @SerializedName("count")
    @Expose
    private String count;

    protected DeviceDatum(Parcel in) {
        device = in.readString();
        count = in.readString();
    }

    public static final Creator<DeviceDatum> CREATOR = new Creator<DeviceDatum>() {
        @Override
        public DeviceDatum createFromParcel(Parcel in) {
            return new DeviceDatum(in);
        }

        @Override
        public DeviceDatum[] newArray(int size) {
            return new DeviceDatum[size];
        }
    };

    /**
     *
     * @return
     * The device
     */
    public String getDevice() {
        return device;
    }

    /**
     *
     * @param device
     * The device
     */
    public void setDevice(String device) {
        this.device = device;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(device);
        parcel.writeString(count);
    }
}
