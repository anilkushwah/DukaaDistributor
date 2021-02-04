package com.dollop.distributor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationModelList {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("customer_id")
    @Expose
    public String customerId;
    @SerializedName("order_id")
    @Expose
    public String orderId;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("notification_type")
    @Expose
    public String notificationType;
    @SerializedName("is_read")
    @Expose
    public String isRead;
    @SerializedName("deleted")
    @Expose
    public String deleted;
    @SerializedName("created_date")
    @Expose
    public String createdDate;
}
