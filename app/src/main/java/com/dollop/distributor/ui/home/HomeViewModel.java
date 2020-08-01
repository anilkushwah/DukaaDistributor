package com.dollop.distributor.ui.home;

public class HomeViewModel {
    String productHeading,productDescription,productVarity;
    int homeProductImage;

    public String getProductVarity() {
        return productVarity;
    }

    public void setProductVarity(String productVarity) {
        this.productVarity = productVarity;
    }

    public String getProductHeading() {
        return productHeading;
    }

    public void setProductHeading(String productHeading) {
        this.productHeading = productHeading;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getHomeProductImage() {
        return homeProductImage;
    }

    public void setHomeProductImage(int homeProductImage) {
        this.homeProductImage = homeProductImage;
    }
}