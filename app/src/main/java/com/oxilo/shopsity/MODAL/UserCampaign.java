package com.oxilo.shopsity.MODAL;

/*
 All Copyright, Audianz Network Pvt ltd.
CIN:
All intellectual property, code ownership belongs un-conditionally
to Audianz Network Pvt Ltd. No unauthorised code copying,
redistribution and editing is permitted.
Author: Audianz Network Pvt Ltd
CIN:
*/

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ericbasendra on 15/11/15.
 */
public class UserCampaign implements Parcelable{

    String campaignTitle;

    String promotionMessage;

    String startDate;

    String startTime;

    String dateTime;

    String webRequestUrl;

    String call;

    public UserCampaign(){

    }

    protected UserCampaign(Parcel in) {
        campaignTitle = in.readString();
        promotionMessage = in.readString();
        startDate = in.readString();
        startTime = in.readString();
        dateTime = in.readString();
        webRequestUrl = in.readString();
        call = in.readString();
    }

    public static final Creator<UserCampaign> CREATOR = new Creator<UserCampaign>() {
        @Override
        public UserCampaign createFromParcel(Parcel in) {
            return new UserCampaign(in);
        }

        @Override
        public UserCampaign[] newArray(int size) {
            return new UserCampaign[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(campaignTitle);
        parcel.writeString(promotionMessage);
        parcel.writeString(startDate);
        parcel.writeString(startTime);
        parcel.writeString(dateTime);
        parcel.writeString(webRequestUrl);
        parcel.writeString(call);
    }

    public String getCampaignTitle() {
        return campaignTitle;
    }

    public void setCampaignTitle(String campaignTitle) {
        this.campaignTitle = campaignTitle;
    }

    public String getPromotionMessage() {
        return promotionMessage;
    }

    public void setPromotionMessage(String promotionMessage) {
        this.promotionMessage = promotionMessage;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getWebRequestUrl() {
        return webRequestUrl;
    }

    public void setWebRequestUrl(String webRequestUrl) {
        this.webRequestUrl = webRequestUrl;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }
}
