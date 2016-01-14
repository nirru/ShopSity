package com.oxilo.shopsity.POJO;

/**
 * Created by ericbasendra on 24/11/15.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImprDatum implements Parcelable{

    @SerializedName("date")
    @Expose
    private String date;
//    @SerializedName("hour")
//    @Expose
//    private String hour;
    @SerializedName("count")
    @Expose
    private String count;

    protected ImprDatum(Parcel in) {
        date = in.readString();
//        hour = in.readString();
        count = in.readString();
    }

    public static final Creator<ImprDatum> CREATOR = new Creator<ImprDatum>() {
        @Override
        public ImprDatum createFromParcel(Parcel in) {
            return new ImprDatum(in);
        }

        @Override
        public ImprDatum[] newArray(int size) {
            return new ImprDatum[size];
        }
    };

    /**
     *
     * @return
     * The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(String date) {
        this.date = date;
    }

//    /**
//     *
//     * @return
//     * The hour
//     */
//    public String getHour() {
//        return hour;
//    }
//
//    /**
//     *
//     * @param hour
//     * The hour
//     */
//    public void setHour(String hour) {
//        this.hour = hour;
//    }

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
        parcel.writeString(date);
//        parcel.writeString(hour);
        parcel.writeString(count);
    }
}
