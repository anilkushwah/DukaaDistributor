package com.dollop.distributor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class NewCreditReq_Model implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("distributor_id")
    @Expose
    private String distributorId;
    @SerializedName("retailer_id")
    @Expose
    private String retailerId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("acc_no")
    @Expose
    private String accNo;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("retailer_name")
    @Expose
    private String retailerName;
    @SerializedName("retailer_mobile")
    @Expose
    private String retailerMobile;
    @SerializedName("retailer_address")
    @Expose
    private String retailerAddress;
    @SerializedName("retailer_image")
    @Expose
    private String retailerImage;
    @SerializedName("retailer_shop_name")
    @Expose
    private String retailerShopName;
    @SerializedName("orders")
    @Expose
    private ArrayList<TotalEarningmodel> orders = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(String retailerId) {
        this.retailerId = retailerId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public String getRetailerMobile() {
        return retailerMobile;
    }

    public void setRetailerMobile(String retailerMobile) {
        this.retailerMobile = retailerMobile;
    }

    public String getRetailerAddress() {
        return retailerAddress;
    }

    public void setRetailerAddress(String retailerAddress) {
        this.retailerAddress = retailerAddress;
    }

    public String getRetailerImage() {
        return retailerImage;
    }

    public void setRetailerImage(String retailerImage) {
        this.retailerImage = retailerImage;
    }

    public String getRetailerShopName() {
        return retailerShopName;
    }

    public void setRetailerShopName(String retailerShopName) {
        this.retailerShopName = retailerShopName;
    }

    public ArrayList<TotalEarningmodel> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<TotalEarningmodel> orders) {
        this.orders = orders;
    }
}
