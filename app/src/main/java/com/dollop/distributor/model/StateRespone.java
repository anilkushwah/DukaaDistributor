package com.dollop.distributor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateRespone {


    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("state_code")
    @Expose
    public String stateCode;
    @SerializedName("country_id")
    @Expose
    public String countryId;
    @SerializedName("status")
    @Expose
    public String status;

}
