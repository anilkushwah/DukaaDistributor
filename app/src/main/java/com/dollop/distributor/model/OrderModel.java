
package com.dollop.distributor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderModel {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public ArrayList<Datum> data = null;

    @SerializedName("accept_count")
    @Expose
    public String acceptCount;
    @SerializedName("packed_count")
    @Expose
    public String packedCount;
    @SerializedName("dispatch_count")
    @Expose
    public String dispatchCount;
    @SerializedName("delivered_count")
    @Expose
    public String deliveredCount;

    @SerializedName("ExcelFileName")
    @Expose
    public String ExcelFileName;

    @SerializedName("earnTotalAmount")
    @Expose
    public String earnTotalAmount;


}
