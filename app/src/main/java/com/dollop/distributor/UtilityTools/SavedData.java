package com.dollop.distributor.UtilityTools;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SavedData {



    private static final String adminlogin = "adminlogin";
    private static final String emp_name = "empname";
    private static final String emp_image = "empimage";
    private static final String active_user = "active_user";
    private static final String set_time = "set_time";
    private static final String set_location = "set_location";
    private static final String set_Distributor_id = "distributor_id";
    private static final String accesse_type = "accesse_type";
    private static final String DEVICE_TOKEN = "DEVICE_TOKEN";
    private static final String sub_member_distributor_id = "sub_member_distributor_id";
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor editor;





    /*Order ID 146381161*/





    public static SharedPreferences getInstance() {
        if (prefs == null) {
            prefs = PreferenceManager.getDefaultSharedPreferences(AppController.getInstance());
        }
        return prefs;
    }






    public static String getSet_time() {
        return getInstance().getString(set_time, "");
    }

    public static void saveSetTime(String startKm) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(set_time, startKm);
        editor.commit();
        editor.apply();
    }
    public static String get_Distributor_id() {
        return getInstance().getString(set_Distributor_id, "");
    }

    public static void saveDisID(String startKm) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(set_Distributor_id, startKm);
        editor.commit();
        editor.apply();
    }
    public static String getSet_location() {
        return getInstance().getString(set_location, "");
    }

    public static void saveSetlocation(String startKm) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(set_location, startKm);
        editor.commit();
        editor.apply();
    }

    public static String getActive_user() {
        return getInstance().getString(active_user, "");
    }

    public static void saveactiveUser(String startKm) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(active_user, startKm);
        editor.commit();
        editor.apply();
    }

    public static String getEmp_name() {
        return getInstance().getString(emp_name, "0");
    }

    public static String getEmp_image() {
        return getInstance().getString(emp_image, "0");
    }

    public static void saveimage(String startKm) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(emp_image, startKm);
        editor.commit();
        editor.apply();
    }

    public static String getAdminlogin() {
        return getInstance().getString(adminlogin, "0");
    }


    public static void saveempname(String startKm) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(emp_name, startKm);
        editor.commit();
        editor.apply();
    }

    public static void saveadminlogin(String startKm) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(adminlogin, startKm);
        editor.commit();
        editor.apply();
    }

    public static String get_AccessType() {
        return getInstance().getString(accesse_type, "");
    }

    public static void saveAccessType(String startKm) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(accesse_type, startKm);
        editor.commit();
        editor.apply();
    }

    public static String get_MainMemberId() {
        return getInstance().getString(sub_member_distributor_id, "");
    }

    public static void saveMainMemberID(String startKm) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(sub_member_distributor_id, startKm);
        editor.commit();
        editor.apply();
    }

    public static String get_DeviceToken() {
        return getInstance().getString(DEVICE_TOKEN, "");
    }

    public static void saveDeviceTokan(String startKm) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(DEVICE_TOKEN, startKm);
        editor.commit();
        editor.apply();
    }


}