package com.dollop.distributor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDTO {

    @SerializedName("distributor_id")
    @Expose
    private String distributorId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("store_location")
    @Expose
    private String storeLocation;
    @SerializedName("store_address")
    @Expose
    private String storeAddress;
    @SerializedName("store_lat")
    @Expose
    private String storeLat;
    @SerializedName("store_long")
    @Expose
    private String storeLong;
    @SerializedName("contact_name")
    @Expose
    private String contactName;
    @SerializedName("offer_id")
    @Expose
    private String offerId;
    @SerializedName("offer_json")
    @Expose
    private String offerJson;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("business_permit")
    @Expose
    private String businessPermit;
    @SerializedName("kra_pin")
    @Expose
    private String kraPin;
    @SerializedName("id_proof")
    @Expose
    private String idProof;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("is_delete")
    @Expose
    private String isDelete;
    @SerializedName("create_date")
    @Expose
    private String createDate;

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreLat() {
        return storeLat;
    }

    public void setStoreLat(String storeLat) {
        this.storeLat = storeLat;
    }

    public String getStoreLong() {
        return storeLong;
    }

    public void setStoreLong(String storeLong) {
        this.storeLong = storeLong;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBusinessPermit() {
        return businessPermit;
    }

    public void setBusinessPermit(String businessPermit) {
        this.businessPermit = businessPermit;
    }

    public String getKraPin() {
        return kraPin;
    }

    public void setKraPin(String kraPin) {
        this.kraPin = kraPin;
    }

    public String getIdProof() {
        return idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public UserDTO(String distributorId, String name, String mobile, String email,
                   String password, String designation, String otp, String country, String state, String city,
                   String storeLocation, String storeAddress, String storeLat, String storeLong, String contactName,
                   String offerId, String offerJson, String image, String businessPermit, String kraPin,
                   String idProof, String isActive, String isDelete, String createDate) {
        this.distributorId = distributorId;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.designation = designation;
        this.otp = otp;
        this.country = country;
        this.state = state;
        this.city = city;
        this.storeLocation = storeLocation;
        this.storeAddress = storeAddress;
        this.storeLat = storeLat;
        this.storeLong = storeLong;
        this.contactName = contactName;
        this.offerId = offerId;
        this.offerJson = offerJson;
        this.image = image;
        this.businessPermit = businessPermit;
        this.kraPin = kraPin;
        this.idProof = idProof;
        this.isActive = isActive;
        this.isDelete = isDelete;
        this.createDate = createDate;
    }

    public UserDTO() {
    }
}
