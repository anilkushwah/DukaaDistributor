package com.dollop.distributor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CreditRequestResponse {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("pending_count")
    @Expose
    private String pendingCount;
    @SerializedName("approved_count")
    @Expose
    private String approvedCount;
    @SerializedName("cancel_count")
    @Expose
    private String cancelCount;
    @SerializedName("data")
    @Expose
    private ArrayList<NewCreditReq_Model> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPendingCount() {
        return pendingCount;
    }

    public void setPendingCount(String pendingCount) {
        this.pendingCount = pendingCount;
    }

    public String getApprovedCount() {
        return approvedCount;
    }

    public void setApprovedCount(String approvedCount) {
        this.approvedCount = approvedCount;
    }

    public String getCancelCount() {
        return cancelCount;
    }

    public void setCancelCount(String cancelCount) {
        this.cancelCount = cancelCount;
    }

    public ArrayList<NewCreditReq_Model> getData() {
        return data;
    }

    public void setData(ArrayList<NewCreditReq_Model> data) {
        this.data = data;
    }
}
