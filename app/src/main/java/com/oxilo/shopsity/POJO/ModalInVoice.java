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
 * Created by ericbasendra on 21/11/15.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModalInVoice {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("invoice_list")
    @Expose
    private List<List<InvoiceList>> invoiceList = new ArrayList<List<InvoiceList>>();

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
     * The invoiceList
     */
    public List<List<InvoiceList>> getInvoiceList() {
        return invoiceList;
    }

    /**
     *
     * @param invoiceList
     * The invoice_list
     */
    public void setInvoiceList(List<List<InvoiceList>> invoiceList) {
        this.invoiceList = invoiceList;
    }

}


