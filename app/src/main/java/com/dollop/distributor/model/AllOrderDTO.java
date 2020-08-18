package com.dollop.distributor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllOrderDTO {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("gen_order_id")
    @Expose
    private String genOrderId;
    @SerializedName("retailer_id")
    @Expose
    private String retailerId;
    @SerializedName("distributor_id")
    @Expose
    private String distributorId;
    @SerializedName("offer_id")
    @Expose
    private String offerId;
    @SerializedName("offer_json")
    @Expose
    private String offerJson;
    @SerializedName("coupon_id")
    @Expose
    private String couponId;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("offer_discount_amount")
    @Expose
    private String offerDiscountAmount;
    @SerializedName("coupon_discount_amount")
    @Expose
    private String couponDiscountAmount;
    @SerializedName("product_discounted_price")
    @Expose
    private String productDiscountedPrice;
    @SerializedName("paid_amount")
    @Expose
    private String paidAmount;
    @SerializedName("address_id")
    @Expose
    private String addressId;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("transaction_mode")
    @Expose
    private String transactionMode;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("driver_id")
    @Expose
    private String driverId;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("itemCount")
    @Expose
    private String itemCount;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("distributer_mobile")
    @Expose
    private String distributerMobile;
    @SerializedName("distributor_image")
    @Expose
    private Object distributorImage;
    @SerializedName("retailer_name")
    @Expose
    private String retailerName;
    @SerializedName("retailer_mobile")
    @Expose
    private String retailerMobile;
    @SerializedName("retailer_email")
    @Expose
    private String retailerEmail;
    @SerializedName("order_status_data")
    @Expose
    private String orderStatusData;
    @SerializedName("item")
    @Expose
    private ArrayList<AllItemDTO> item = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenOrderId() {
        return genOrderId;
    }

    public void setGenOrderId(String genOrderId) {
        this.genOrderId = genOrderId;
    }

    public String getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(String retailerId) {
        this.retailerId = retailerId;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferJson() {
        return offerJson;
    }

    public void setOfferJson(String offerJson) {
        this.offerJson = offerJson;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOfferDiscountAmount() {
        return offerDiscountAmount;
    }

    public void setOfferDiscountAmount(String offerDiscountAmount) {
        this.offerDiscountAmount = offerDiscountAmount;
    }

    public String getCouponDiscountAmount() {
        return couponDiscountAmount;
    }

    public void setCouponDiscountAmount(String couponDiscountAmount) {
        this.couponDiscountAmount = couponDiscountAmount;
    }

    public String getProductDiscountedPrice() {
        return productDiscountedPrice;
    }

    public void setProductDiscountedPrice(String productDiscountedPrice) {
        this.productDiscountedPrice = productDiscountedPrice;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionMode() {
        return transactionMode;
    }

    public void setTransactionMode(String transactionMode) {
        this.transactionMode = transactionMode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistributerMobile() {
        return distributerMobile;
    }

    public void setDistributerMobile(String distributerMobile) {
        this.distributerMobile = distributerMobile;
    }

    public Object getDistributorImage() {
        return distributorImage;
    }

    public void setDistributorImage(Object distributorImage) {
        this.distributorImage = distributorImage;
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

    public String getRetailerEmail() {
        return retailerEmail;
    }

    public void setRetailerEmail(String retailerEmail) {
        this.retailerEmail = retailerEmail;
    }

    public String getOrderStatusData() {
        return orderStatusData;
    }

    public void setOrderStatusData(String orderStatusData) {
        this.orderStatusData = orderStatusData;
    }

    public ArrayList<AllItemDTO> getItem() {
        return item;
    }

    public void setItem(ArrayList<AllItemDTO> item) {
        this.item = item;
    }
}
