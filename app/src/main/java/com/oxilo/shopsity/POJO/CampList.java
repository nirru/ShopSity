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
 * Created by ericbasendra on 26/11/15.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CampList implements Parcelable{

    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("camp_id")
    @Expose
    private String campId;
    @SerializedName("clientid")
    @Expose
    private String clientid;
    @SerializedName("web_url")
    @Expose
    private String webUrl;
    @SerializedName("camp_name")
    @Expose
    private String campName;
    @SerializedName("promo_msg")
    @Expose
    private String promoMsg;
    @SerializedName("start_date")
    @Expose
    private Long startDate;
    @SerializedName("end_date")
    @Expose
    private Long endDate;
    @SerializedName("total_imp")
    @Expose
    private String totalImp;
    @SerializedName("shown_imp")
    @Expose
    private Integer shownImp;
    @SerializedName("clicks")
    @Expose
    private Integer clicks;
    @SerializedName("call")
    @Expose
    private Integer call;
    @SerializedName("web")
    @Expose
    private Integer web;
    @SerializedName("map")
    @Expose
    private Integer map;
    @SerializedName("camp_status")
    @Expose
    private String campStatus;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("order_amount")
    @Expose
    private Integer orderAmount;

    protected CampList(Parcel in) {
        action = in.readString();
        campId = in.readString();
        clientid = in.readString();
        webUrl = in.readString();
        campName = in.readString();
        promoMsg = in.readString();
        startDate = in.readLong();
        endDate = in.readLong();
        totalImp = in.readString();
        shownImp = in.readInt();
        clicks = in.readInt();
        call = in.readInt();
        web = in.readInt();
        map = in.readInt();
        campStatus = in.readString();
        orderId = in.readString();
        lat = in.readString();
        lon = in.readString();
    }

    public static final Creator<CampList> CREATOR = new Creator<CampList>() {
        @Override
        public CampList createFromParcel(Parcel in) {
            return new CampList(in);
        }

        @Override
        public CampList[] newArray(int size) {
            return new CampList[size];
        }
    };

    /**
     *
     * @return
     * The action
     */
    public String getAction() {
        return action;
    }

    /**
     *
     * @param action
     * The action
     */
    public void setAction(String action) {
        this.action = action;
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
     * The clientid
     */
    public String getClientid() {
        return clientid;
    }

    /**
     *
     * @param clientid
     * The clientid
     */
    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    /**
     *
     * @return
     * The webUrl
     */
    public String getWebUrl() {
        return webUrl;
    }

    /**
     *
     * @param webUrl
     * The web_url
     */
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    /**
     *
     * @return
     * The campName
     */
    public String getCampName() {
        return campName;
    }

    /**
     *
     * @param campName
     * The camp_name
     */
    public void setCampName(String campName) {
        this.campName = campName;
    }

    /**
     *
     * @return
     * The promoMsg
     */
    public String getPromoMsg() {
        return promoMsg;
    }

    /**
     *
     * @param promoMsg
     * The promo_msg
     */
    public void setPromoMsg(String promoMsg) {
        this.promoMsg = promoMsg;
    }

    /**
     *
     * @return
     * The startDate
     */
    public Long getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate
     * The start_date
     */
    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return
     * The endDate
     */
    public Long getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate
     * The end_date
     */
    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    /**
     *
     * @return
     * The totalImp
     */
    public String getTotalImp() {
        return totalImp;
    }

    /**
     *
     * @param totalImp
     * The total_imp
     */
    public void setTotalImp(String totalImp) {
        this.totalImp = totalImp;
    }

    /**
     *
     * @return
     * The shownImp
     */
    public Integer getShownImp() {
        return shownImp;
    }

    /**
     *
     * @param shownImp
     * The shown_imp
     */
    public void setShownImp(Integer shownImp) {
        this.shownImp = shownImp;
    }

    /**
     *
     * @return
     * The clicks
     */
    public Integer getClicks() {
        return clicks;
    }

    /**
     *
     * @param clicks
     * The clicks
     */
    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }

    /**
     *
     * @return
     * The call
     */
    public Integer getCall() {
        return call;
    }

    /**
     *
     * @param call
     * The call
     */
    public void setCall(Integer call) {
        this.call = call;
    }

    /**
     *
     * @return
     * The web
     */
    public Integer getWeb() {
        return web;
    }

    /**
     *
     * @param web
     * The web
     */
    public void setWeb(Integer web) {
        this.web = web;
    }

    /**
     *
     * @return
     * The map
     */
    public Integer getMap() {
        return map;
    }

    /**
     *
     * @param map
     * The map
     */
    public void setMap(Integer map) {
        this.map = map;
    }

    /**
     *
     * @return
     * The campStatus
     */
    public String getCampStatus() {
        return campStatus;
    }

    /**
     *
     * @param campStatus
     * The camp_status
     */
    public void setCampStatus(String campStatus) {
        this.campStatus = campStatus;
    }

    /**
     *
     * @return
     * The orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     *
     * @param orderId
     * The order_id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
     * The lon
     */
    public String getLon() {
        return lon;
    }

    /**
     *
     * @param lon
     * The lon
     */
    public void setLon(String lon) {
        this.lon = lon;
    }

    /**
     *
     * @return
     * The orderAmount
     */
    public Integer getOrderAmount() {
        return orderAmount;
    }

    /**
     *
     * @param orderAmount
     * The order_amount
     */
    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(action);
        parcel.writeString(campId);
        parcel.writeString(clientid);
        parcel.writeString(webUrl);
        parcel.writeString(campName);
        parcel.writeString(promoMsg);
        parcel.writeLong(startDate);
        parcel.writeLong(endDate);
        parcel.writeString(totalImp);
        parcel.writeInt(shownImp);
        parcel.writeInt(clicks);
        parcel.writeInt(call);
        parcel.writeInt(web);
        parcel.writeInt(map);
        parcel.writeString(campStatus);
        parcel.writeString(orderId);
        parcel.writeString(lat);
        parcel.writeString(lon);
    }
}
