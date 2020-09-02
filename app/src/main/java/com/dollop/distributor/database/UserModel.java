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


    public static void creteTable(SQLiteDatabase db) {
        String CREATE_CLIENTTABLE = "create table " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_DISTRIBUTOR_ID + " text," +
                KEY_EMAIL_ID + " text," +
                KEY_NAME_ID + " text," +
                KEY_IMAGE_ID + " text, " +
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

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    String keyid;


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
