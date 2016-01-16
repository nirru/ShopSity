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
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModalFetchCampaign {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("camp_list")
    @Expose
    private List<CampList> campList = new ArrayList<CampList>();

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

}
