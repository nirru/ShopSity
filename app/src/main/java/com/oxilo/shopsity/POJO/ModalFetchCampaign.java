package com.oxilo.shopsity.POJO;

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
