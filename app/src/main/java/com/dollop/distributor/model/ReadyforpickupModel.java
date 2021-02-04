package com.dollop.distributor.model;

public class ReadyforpickupModel {
    String ShopName,orderId,date,totalAMount,ordrStatus,unitcount,retailerName,gen_orderId;

    public void setGen_orderId(String gen_orderId) {
        this.gen_orderId = gen_orderId;
    }

    public String getGen_orderId() {
        return gen_orderId;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public String getShopName() {
        return ShopName;
    }

    public String getUnitcount() {
        return unitcount;
    }

    public void setUnitcount(String unitcount) {
        this.unitcount = unitcount;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalAMount() {
        return totalAMount;
    }

    public void setTotalAMount(String totalAMount) {
        this.totalAMount = totalAMount;
    }

    public String getOrdrStatus() {
        return ordrStatus;
    }

    public void setOrdrStatus(String ordrStatus) {
        this.ordrStatus = ordrStatus;
    }
}

