
package com.dollop.distributor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Serializable {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("gen_order_id")
    @Expose
    public String genOrderId;
    @SerializedName("retailer_id")
    @Expose
    public String retailerId;
    @SerializedName("distributor_id")
    @Expose
    public String distributorId;
    @SerializedName("coupon_id")
    @Expose
    public String couponId;
    @SerializedName("total_amount")
    @Expose
    public String totalAmount;
    @SerializedName("coupon_discount_amount")
    @Expose
    public String couponDiscountAmount;
    @SerializedName("product_discounted_price")
    @Expose
    public String productDiscountedPrice;
    @SerializedName("paid_amount")
    @Expose
    public String paidAmount;
    @SerializedName("total_weight")
    @Expose
    public String totalWeight;
    @SerializedName("weight_unit")
    @Expose
    public String weightUnit;
    @SerializedName("tax")
    @Expose
    public String tax;
    @SerializedName("address_id")
    @Expose
    public String addressId;
    @SerializedName("transaction_id")
    @Expose
    public String transactionId;
    @SerializedName("transaction_mode")
    @Expose
    public String transactionMode;
    @SerializedName("transaction_response")
    @Expose
    public String tansactionResponse;
    @SerializedName("delivery_type")
    @Expose
    public String deliveryType;
    @SerializedName("schedule_time")
    @Expose
    public String scheduleTime;
    @SerializedName("order_status")
    @Expose
    public String orderStatus;
    @SerializedName("service_charge")
    @Expose
    public String serviceCharge;

    @SerializedName("delivery_charge")
    @Expose
    public String deliveryCharge;

    @SerializedName("driver_id")
    @Expose
    public String driverId="";

    @SerializedName("national_id")
    @Expose
    public String national_id="";

    @SerializedName("driver_name")
    @Expose
    public String driver_name;

    @SerializedName("vehicle_registrion_number")
    @Expose
    public String vehicle_registrion_number;

    @SerializedName("driver_signature_img")
    @Expose
    public Object driverSignatureImg;
    @SerializedName("vehicle_type")
    @Expose
    public String vehicleType;
    @SerializedName("create_date")
    @Expose
    public String createDate;
    @SerializedName("shop_image")
    @Expose
    public String shopImage;
    @SerializedName("retailer_image")
    @Expose
    public String retailerImage;
    @SerializedName("shop_name")
    @Expose
    public String shopName;
    @SerializedName("retailer_name")
    @Expose
    public String retailerName;
    @SerializedName("retailer_contact")
    @Expose
    public String retailerContact;
    @SerializedName("item_count")
    @Expose
    public String itemCount;
    @SerializedName("store_lat")
    @Expose
    public String storeLat;
    @SerializedName("store_long")
    @Expose
    public String storeLong;
    @SerializedName("distributor_address")
    @Expose
    public String distributorAddress;
    @SerializedName("retailer_lat")
    @Expose
    public String retailerLat;
    @SerializedName("retailer_long")
    @Expose
    public String retailerLong;

    @SerializedName("retailer_address")
    @Expose
    public String retailerAddress;

    @SerializedName("products")
    @Expose
    public ArrayList<Product> products = null;

}