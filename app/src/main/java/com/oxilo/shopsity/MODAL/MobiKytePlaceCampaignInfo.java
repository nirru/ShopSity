package com.oxilo.shopsity.MODAL;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ericbasendra on 25/11/15.
 */
public class MobiKytePlaceCampaignInfo implements Parcelable{

    double lat=20.90;
    double lng=34.90;
    String address;
    String mobileNumber;
    String country;

    public MobiKytePlaceCampaignInfo(){

    }

    public MobiKytePlaceCampaignInfo(Parcel in) {
        lat = in.readDouble();
        lng = in.readDouble();
        address = in.readString();
        mobileNumber = in.readString();
        country = in.readString();
    }

    public static final Creator<MobiKytePlaceCampaignInfo> CREATOR = new Creator<MobiKytePlaceCampaignInfo>() {
        @Override
        public MobiKytePlaceCampaignInfo createFromParcel(Parcel in) {
            return new MobiKytePlaceCampaignInfo(in);
        }

        @Override
        public MobiKytePlaceCampaignInfo[] newArray(int size) {
            return new MobiKytePlaceCampaignInfo[size];
        }
    };

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
        parcel.writeString(address);
        parcel.writeString(mobileNumber);
        parcel.writeString(country);
    }
}
