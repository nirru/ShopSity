package com.oxilo.shopsity.POJO;

/**
 * Created by ericbasendra on 02/01/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClickDatum {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("hour")
    @Expose
    private String hour;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("creative_id")
    @Expose
    private String creativeId;
    @SerializedName("bannerid")
    @Expose
    private String bannerid;
    @SerializedName("campaignid")
    @Expose
    private String campaignid;

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

    /**
     *
     * @return
     * The hour
     */
    public String getHour() {
        return hour;
    }

    /**
     *
     * @param hour
     * The hour
     */
    public void setHour(String hour) {
        this.hour = hour;
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

    /**
     *
     * @return
     * The creativeId
     */
    public String getCreativeId() {
        return creativeId;
    }

    /**
     *
     * @param creativeId
     * The creative_id
     */
    public void setCreativeId(String creativeId) {
        this.creativeId = creativeId;
    }

    /**
     *
     * @return
     * The bannerid
     */
    public String getBannerid() {
        return bannerid;
    }

    /**
     *
     * @param bannerid
     * The bannerid
     */
    public void setBannerid(String bannerid) {
        this.bannerid = bannerid;
    }

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

}
