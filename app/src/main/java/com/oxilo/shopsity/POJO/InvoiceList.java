package com.oxilo.shopsity.POJO;

/**
 * Created by ericbasendra on 21/11/15.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoiceList {

    @SerializedName("campaignid")
    @Expose
    private String campaignid;


    /**
     *
     * @return
     * The campaignid
     */
    public String getCampaignid() {
        return campaignid;
    }

    /**
     *
     * @param campaignid
     * The campaignid
     */
    public void setCampaignid(String campaignid) {
        this.campaignid = campaignid;
    }

//
}
