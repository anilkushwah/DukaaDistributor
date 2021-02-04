package com.dollop.distributor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyReviewModel {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("distributor_id")
    @Expose
    public String distributorId;
    @SerializedName("retailer_id")
    @Expose
    public String retailerId;
    @SerializedName("rating")
    @Expose
    public String rating;
    @SerializedName("review_title")
    @Expose
    public String reviewTitle;
    @SerializedName("review_description")
    @Expose
    public String reviewDescription;
    @SerializedName("create_date")
    @Expose
    public String createDate;
    @SerializedName("retailer_name")
    @Expose
    public String retailerName;
    @SerializedName("retailer_image")
    @Expose
    public String retailerImage;

}
