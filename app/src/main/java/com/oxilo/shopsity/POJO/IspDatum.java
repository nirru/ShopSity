package com.oxilo.shopsity.POJO;

/**
 * Created by ericbasendra on 24/11/15.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IspDatum implements Parcelable{

    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("device")
    @Expose
    private String device;

    protected IspDatum(Parcel in) {
        count = in.readString();
        device = in.readString();
    }

    public static final Creator<IspDatum> CREATOR = new Creator<IspDatum>() {
        @Override
        public IspDatum createFromParcel(Parcel in) {
            return new IspDatum(in);
        }

        @Override
        public IspDatum[] newArray(int size) {
            return new IspDatum[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(count);
        parcel.writeString(device);
    }
}
