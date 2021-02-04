package com.dollop.distributor.database;

import android.database.sqlite.SQLiteDatabase;

import com.dollop.distributor.UtilityTools.Utils;

/**
 * Created by CRUD-PC on 10/5/2016.
 */

/**/
public class UserModel {
    public static final String TABLE_NAME = "UserModelDukaa";
    public static final String KEY_ID = "_id";
    public static final String KEY_DISTRIBUTOR_ID = "distributorId";
    public static final String KEY_EMAIL_ID = "email";
    public static final String KEY_NAME_ID = "name";
    public static final String KEY_IMAGE_ID = "image";
    public static final String KEY_MOBILE_ID = "mobile";
    public static final String KEY_SET_TIME = "time";
    public static final String KEY_MF_slot1_start_time = "mf_start";
    public static final String KEY_MF_slot1_end_time = "mf_close";
    public static final String KEY_SS_slot1_start_time = "ss_start";
    public static final String KEY_SS_slot1_end_time = "ss_close";
    public static final String KEY_SET_LOCATION = "location";
    public static final String KEY_STORE_LAT = "store_lat";
    public static final String KEY_STORE_LONG = "store_long";


    public static void creteTable(SQLiteDatabase db) {
        String CREATE_CLIENTTABLE = "create table " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_DISTRIBUTOR_ID + " text," +
                KEY_EMAIL_ID + " text," +
                KEY_NAME_ID + " text," +
                KEY_IMAGE_ID + " text, " +
                KEY_SET_TIME + " text, " +
                KEY_SET_LOCATION + " text, " +
                KEY_MF_slot1_start_time + " text, " +
                KEY_MF_slot1_end_time + " text, " +
                KEY_SS_slot1_start_time + " text, " +
                KEY_SS_slot1_end_time + " text, " +
                KEY_STORE_LAT + " text, " +
                KEY_STORE_LONG + " text, " +
                KEY_MOBILE_ID + " text " + ")";

        db.execSQL(CREATE_CLIENTTABLE);
        Utils.E("check Create table::" + CREATE_CLIENTTABLE);
    }

    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    String distributorId;
    String name;
    String mobile;
    String email;
    String image;
    String time;
    String location;
    String mf_start;
    String mf_close;
    String ss_start;
    String ss_close;
    String store_lat;
    String store_long;
    String keyid;



    public String getStore_lat() {
        return store_lat;
    }

    public void setStore_lat(String store_lat) {
        this.store_lat = store_lat;
    }

    public String getStore_long() {
        return store_long;
    }

    public void setStore_long(String store_long) {
        this.store_long = store_long;
    }

    public String getMf_start() {
        return mf_start;
    }

    public void setMf_start(String mf_start) {
        this.mf_start = mf_start;
    }

    public String getMf_close() {
        return mf_close;
    }

    public void setMf_close(String mf_close) {
        this.mf_close = mf_close;
    }

    public String getSs_start() {
        return ss_start;
    }

    public void setSs_start(String ss_start) {
        this.ss_start = ss_start;
    }

    public String getSs_close() {
        return ss_close;
    }

    public void setSs_close(String ss_close) {
        this.ss_close = ss_close;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }



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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
