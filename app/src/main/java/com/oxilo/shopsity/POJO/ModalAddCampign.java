package com.oxilo.shopsity.POJO;

/**
 * Created by ericbasendra on 15/11/15.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModalAddCampign implements Parcelable{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("amount")
    @Expose
    private String amount;

    protected ModalAddCampign(Parcel in) {
        status = in.readString();
        amount = in.readString();
    }

    public static final Creator<ModalAddCampign> CREATOR = new Creator<ModalAddCampign>() {
        @Override
        public ModalAddCampign createFromParcel(Parcel in) {
            return new ModalAddCampign(in);
        }

        @Override
        public ModalAddCampign[] newArray(int size) {
            return new ModalAddCampign[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(amount);
    }

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
     * The orderId
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     *
     * @param orderId
     * The order_id
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     *
     * @return
     * The amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     * The amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }


}