
package com.dollop.distributor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("retailer_id")
    @Expose
    public String retailerId;
    @SerializedName("order_id")
    @Expose
    public String orderId;
    @SerializedName("product_id")
    @Expose
    public String productId;
    @SerializedName("offer_id")
    @Expose
    public String offerId;
    @SerializedName("product_qty")
    @Expose
    public String productQty;
    @SerializedName("product_amount")
    @Expose
    public String productAmount;
    @SerializedName("total_amount")
    @Expose
    public String totalAmount;
    @SerializedName("discount_amount")
    @Expose
    public String discountAmount;
    @SerializedName("product_discounted_price")
    @Expose
    public String productDiscountedPrice;
    @SerializedName("create_date")
    @Expose
    public String createDate;
    @SerializedName("product_name")
    @Expose
    public String productName;
    @SerializedName("packing")
    @Expose
    public String packing;
    @SerializedName("tax_name")
    @Expose
    public String taxName;

}
