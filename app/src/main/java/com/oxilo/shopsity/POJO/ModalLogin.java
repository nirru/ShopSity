package com.oxilo.shopsity.POJO;

/**
 * Created by ericbasendra on 15/11/15.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModalLogin implements Parcelable{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("clientid")
    @Expose
    private String clientid;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("name")
    @Expose
    private String name;
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
    private String address;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("zip")
    @Expose
    private String zip;

    public ModalLogin(){

    }

    public ModalLogin(Parcel in) {
        status = in.readString();
        clientid = in.readString();
        businessName = in.readString();
        name = in.readString();
        emailid = in.readString();
        password = in.readString();
        mobile = in.readString();
        address = in.readString();
        state = in.readString();
        city = in.readString();
        website = in.readString();
        zip = in.readString();
    }

    public static final Creator<ModalLogin> CREATOR = new Creator<ModalLogin>() {
        @Override
        public ModalLogin createFromParcel(Parcel in) {
            return new ModalLogin(in);
        }

        @Override
        public ModalLogin[] newArray(int size) {
            return new ModalLogin[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(clientid);
        parcel.writeString(businessName);
        parcel.writeString(name);
        parcel.writeString(emailid);
        parcel.writeString(password);
        parcel.writeString(mobile);
        parcel.writeString(address);
        parcel.writeString(state);
        parcel.writeString(city);
        parcel.writeString(website);
        parcel.writeString(zip);
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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
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
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The state
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     * The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     * The city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The website
     */
    public String getWebsite() {
        return website;
    }

    /**
     *
     * @param website
     * The website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     *
     * @return
     * The zip
     */
    public String getZip() {
        return zip;
    }

    /**
     *
     * @param zip
     * The zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }


}
