package com.dollop.distributor.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dollop.distributor.UtilityTools.Utils;

import java.util.ArrayList;

/**
 * Created by CRUD-PC on 10/7/2016.
 */
public class UserDataHelper {
    private static UserDataHelper instance;
    private SQLiteDatabase db;
    private DataManager dm;
    Context cx;

    public UserDataHelper(Context cx) {
        instance = this;
        this.cx = cx;
        dm = new DataManager(cx, DataManager.DATABASE_NAME, null, DataManager.DATABASE_VERSION);
    }

    public static UserDataHelper getInstance() {
        return instance;
    }

    public void open() {
        db = dm.getWritableDatabase();
    }

    public void close() {
        //  db.close();
    }

    public void read() {
        db = dm.getReadableDatabase();
    }

    public void delete(int companyId) {
        open();
        db.delete(UserModel.TABLE_NAME, UserModel.KEY_ID + " = '" + companyId + "'", null);
        close();
    }

    public void deleteAll() {
        open();
        db.delete(UserModel.TABLE_NAME, null, null);
        close();
    }

    private boolean isExist(UserModel userModel) {
        read();
        Cursor cur = db.rawQuery("select * from " + UserModel.TABLE_NAME + " where " + UserModel.KEY_ID + "='"
                + userModel.getKeyid() + "'", null);
        if (cur.moveToFirst()) {
            return true;
        }
        return false;
    }

    public void insertData(UserModel userModel) {
        open();
        ContentValues values = new ContentValues();
        values.put(UserModel.KEY_ID, userModel.getKeyid());
        values.put(UserModel.KEY_DISTRIBUTOR_ID, userModel.getDistributorId());
        values.put(UserModel.KEY_EMAIL_ID, userModel.getEmail());
        values.put(UserModel.KEY_IMAGE_ID, userModel.getImage());
        values.put(UserModel.KEY_MOBILE_ID, userModel.getMobile());
        values.put(UserModel.KEY_NAME_ID, userModel.getName());

        if (!isExist(userModel)) {
            Utils.E("insert successfully");
            db.insert(UserModel.TABLE_NAME, null, values);
        } else {
            Utils.E("update successfully" + userModel.getDistributorId());
            db.update(UserModel.TABLE_NAME, values, UserModel.KEY_ID + "=" + userModel.getKeyid(), null);
        }
        close();
    }

    public ArrayList<UserModel> getList() {
        ArrayList<UserModel> userItem = new ArrayList<UserModel>();
        read();
        Cursor cursor = db.rawQuery("select * from " + UserModel.TABLE_NAME, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToLast();
            do {
                UserModel taxiModel = new UserModel();
                taxiModel.setKeyid(cursor.getString(cursor.getColumnIndex(UserModel.KEY_ID)));
                taxiModel.setDistributorId(cursor.getString(cursor.getColumnIndex(UserModel.KEY_DISTRIBUTOR_ID)));
                taxiModel.setEmail(cursor.getString(cursor.getColumnIndex(UserModel.KEY_EMAIL_ID)));
                taxiModel.setMobile(cursor.getString(cursor.getColumnIndex(UserModel.KEY_MOBILE_ID)));
                taxiModel.setImage(cursor.getString(cursor.getColumnIndex(UserModel.KEY_IMAGE_ID)));
                taxiModel.setName(cursor.getString(cursor.getColumnIndex(UserModel.KEY_NAME_ID)));
                userItem.add(taxiModel);
            } while ((cursor.moveToPrevious()));
            cursor.close();
        }
        close();
        return userItem;
    }
}