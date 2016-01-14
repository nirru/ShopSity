package com.oxilo.shopsity.POJO;

/**
 * Created by ericbasendra on 15/11/15.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModalRegistration implements Parcelable{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("clientid")
    @Expose
    private Integer clientid;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("emailid")
    @Expose
    private String emailid;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("zip")
    @Expose
    private Object zip;

    protected ModalRegistration(Parcel in) {
        status = in.readString();
        businessName = in.readString();
        username = in.readString();
        emailid = in.readString();
        password = in.readString();
        mobile = in.readString();
    }

    public static final Creator<ModalRegistration> CREATOR = new Creator<ModalRegistration>() {
        @Override
        public ModalRegistration createFromParcel(Parcel in) {
            return new ModalRegistration(in);
        }

        @Override
        public ModalRegistration[] newArray(int size) {
            return new ModalRegistration[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(businessName);
        parcel.writeString(username);
        parcel.writeString(emailid);
        parcel.writeString(password);
        parcel.writeString(mobile);
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
     * The clientid
     */
    public Integer getClientid() {
        return clientid;
    }

    /**
     *
     * @param clientid
     * The clientid
     */
    public void setClientid(Integer clientid) {
        this.clientid = clientid;
    }

    /**
     *
     * @return
     * The businessName
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     *
     * @param businessName
     * The business_name
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The emailid
     */
    public String getEmailid() {
        return emailid;
    }

    /**
     *
     * @param emailid
     * The emailid
     */
    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    /**
     *
     * @return
     * The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     * The mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     *
     * @param mobile
     * The mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     *
     * @return
     * The address
     */
    public Object getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(Object address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The state
     */
    public Object getState() {
        return state;
    }

    /**
     *
     * @param state
     * The state
     */
    public void setState(Object state) {
        this.state = state;
    }

    /**
     *
     * @return
     * The city
     */
    public Object getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(Object city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The zip
     */
    public Object getZip() {
        return zip;
    }

    /**
     *
     * @param zip
     * The zip
     */
    public void setZip(Object zip) {
        this.zip = zip;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}