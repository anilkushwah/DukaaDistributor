package com.dollop.distributor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllItemDTO {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("retailer_id")
    @Expose
    private String retailerId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_qty")
    @Expose
    private String productQty;
    @SerializedName("product_amount")
    @Expose
    private String productAmount;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("discount_amount")
    @Expose
    private String discountAmount;
    @SerializedName("product_discounted_price")
    @Expose
    private String productDiscountedPrice;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("pack_size")
    @Expose
    private String packSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(String retailerId) {
        this.retailerId = retailerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getProductDiscountedPrice() {
        return productDiscountedPrice;
    }

    public void setProductDiscountedPrice(String productDiscountedPrice) {
        this.productDiscountedPrice = productDiscountedPrice;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }
}
