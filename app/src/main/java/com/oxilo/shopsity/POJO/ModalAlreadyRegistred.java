package com.oxilo.shopsity.POJO;

/**
 * Created by ericbasendra on 15/11/15.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModalAlreadyRegistred {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("errorCode")
    @Expose
    private String errorCode;
    @SerializedName("errorString")
    @Expose
    private String errorString;

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
     * The errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     *
     * @param errorCode
     * The errorCode
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     *
     * @return
     * The errorString
     */
    public String getErrorString() {
        return errorString;
    }

    /**
     *
     * @param errorString
     * The errorString
     */
    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

}

