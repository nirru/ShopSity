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
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InVoiceObject {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("sub_order_id")
    @Expose
    private String subOrderId;
    @SerializedName("product_sku")
    @Expose
    private String productSku;
    @SerializedName("order_amount")
    @Expose
    private String orderAmount;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("updated_by")
    @Expose
    private String updatedBy;
    @SerializedName("update_date")
    @Expose
    private String updateDate;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("cust_id")
    @Expose
    private String custId;
    @SerializedName("cust_name")
    @Expose
    private String custName;
    @SerializedName("cust_email")
    @Expose
    private String custEmail;
    @SerializedName("cust_mobile")
    @Expose
    private String custMobile;
    @SerializedName("cust_country")
    @Expose
    private String custCountry;
    @SerializedName("cust_pincode")
    @Expose
    private Object custPincode;
    @SerializedName("invoice_id")
    @Expose
    private String invoiceId;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("surcharge")
    @Expose
    private String surcharge;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("partner_code")
    @Expose
    private String partnerCode;
    @SerializedName("isFreePlan")
    @Expose
    private String isFreePlan;

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
     * The subOrderId
     */
    public String getSubOrderId() {
        return subOrderId;
    }

    /**
     *
     * @param subOrderId
     * The sub_order_id
     */
    public void setSubOrderId(String subOrderId) {
        this.subOrderId = subOrderId;
    }

    /**
     *
     * @return
     * The productSku
     */
    public String getProductSku() {
        return productSku;
    }

    /**
     *
     * @param productSku
     * The product_sku
     */
    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    /**
     *
     * @return
     * The orderAmount
     */
    public String getOrderAmount() {
        return orderAmount;
    }

    /**
     *
     * @param orderAmount
     * The order_amount
     */
    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**
     *
     * @return
     * The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency
     * The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     *
     * @return
     * The orderDate
     */
    public String getOrderDate() {
        return orderDate;
    }

    /**
     *
     * @param orderDate
     * The order_date
     */
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    /**
     *
     * @return
     * The orderStatus
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     *
     * @param orderStatus
     * The order_status
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     *
     * @return
     * The createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy
     * The created_by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return
     * The createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     *
     * @param createDate
     * The create_date
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @return
     * The updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     *
     * @param updatedBy
     * The updated_by
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     *
     * @return
     * The updateDate
     */
    public String getUpdateDate() {
        return updateDate;
    }

    /**
     *
     * @param updateDate
     * The update_date
     */
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    /**
     *
     * @return
     * The remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     *
     * @param remarks
     * The remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     *
     * @return
     * The custId
     */
    public String getCustId() {
        return custId;
    }

    /**
     *
     * @param custId
     * The cust_id
     */
    public void setCustId(String custId) {
        this.custId = custId;
    }

    /**
     *
     * @return
     * The custName
     */
    public String getCustName() {
        return custName;
    }

    /**
     *
     * @param custName
     * The cust_name
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     *
     * @return
     * The custEmail
     */
    public String getCustEmail() {
        return custEmail;
    }

    /**
     *
     * @param custEmail
     * The cust_email
     */
    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    /**
     *
     * @return
     * The custMobile
     */
    public String getCustMobile() {
        return custMobile;
    }

    /**
     *
     * @param custMobile
     * The cust_mobile
     */
    public void setCustMobile(String custMobile) {
        this.custMobile = custMobile;
    }

    /**
     *
     * @return
     * The custCountry
     */
    public String getCustCountry() {
        return custCountry;
    }

    /**
     *
     * @param custCountry
     * The cust_country
     */
    public void setCustCountry(String custCountry) {
        this.custCountry = custCountry;
    }

    /**
     *
     * @return
     * The custPincode
     */
    public Object getCustPincode() {
        return custPincode;
    }

    /**
     *
     * @param custPincode
     * The cust_pincode
     */
    public void setCustPincode(Object custPincode) {
        this.custPincode = custPincode;
    }

    /**
     *
     * @return
     * The invoiceId
     */
    public String getInvoiceId() {
        return invoiceId;
    }

    /**
     *
     * @param invoiceId
     * The invoice_id
     */
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     *
     * @return
     * The tax
     */
    public String getTax() {
        return tax;
    }

    /**
     *
     * @param tax
     * The tax
     */
    public void setTax(String tax) {
        this.tax = tax;
    }

    /**
     *
     * @return
     * The surcharge
     */
    public String getSurcharge() {
        return surcharge;
    }

    /**
     *
     * @param surcharge
     * The surcharge
     */
    public void setSurcharge(String surcharge) {
        this.surcharge = surcharge;
    }

    /**
     *
     * @return
     * The discount
     */
    public String getDiscount() {
        return discount;
    }

    /**
     *
     * @param discount
     * The discount
     */
    public void setDiscount(String discount) {
        this.discount = discount;
    }

    /**
     *
     * @return
     * The partnerCode
     */
    public String getPartnerCode() {
        return partnerCode;
    }

    /**
     *
     * @param partnerCode
     * The partner_code
     */
    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    /**
     *
     * @return
     * The isFreePlan
     */
    public String getIsFreePlan() {
        return isFreePlan;
    }

    /**
     *
     * @param isFreePlan
     * The isFreePlan
     */
    public void setIsFreePlan(String isFreePlan) {
        this.isFreePlan = isFreePlan;
    }

}


