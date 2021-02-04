package com.dollop.distributor.model;

public class StockQuantity_model {

    public String product_name;
    public String price_per_case;
    public String Id;
    public String product_image;
    public String product_availability;
    public String original_price;
    public String selling_price;
    public String description;
    public String packing_id;
    public String unit_per_packing_weight;
    public String total_unit;
    public String total_weight;
    public String unit;
    public String discount;
    public String item_code;
    public String rating;
    public String stock_quantity;
    public String category_name;
    public String packing;
    public String sub_category_name;
    public String brand;
    public String name, amount, stock;
    public String is_tax, valid_from, valid_to,offer_id;
    public String tax_id, offer_title, offer_image, discount_percent, discount_amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
