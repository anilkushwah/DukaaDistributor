package com.dollop.distributor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RatingDTO {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("rating_count")
    @Expose
    public Integer ratingCount;
    @SerializedName("review_count")
    @Expose
    public Integer reviewCount;
    @SerializedName("data")
    @Expose
    public ArrayList<MyReviewModel> data = null;
}
