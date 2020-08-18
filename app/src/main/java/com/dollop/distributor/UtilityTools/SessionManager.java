package com.dollop.distributor.UtilityTools;

import android.content.Context;
import android.content.SharedPreferences;

import com.dollop.distributor.model.UserDTO;


public class SessionManager {

    private static final String IS_LOGIN = "IsLogin";
    private static final String PREFER_NAME = "CUSTOIMER_APP";
    private static final String PREFER_NAME_GLOBEL = "CUSTOMER_APP_GLOBEL";

    private static final String Customer_id = "customer_id";
    private static final String Name = "name";
    private static final String Email = "email";
    private static final String Phone = "phone";
    private static final String Password = "password";
    private static final String Designation = "designation";
    private static final String Country = "country";
    private static final String State = "State";
    private static final String City = "city";

    private static final String Store_location = "store_location";
    private static final String Store_address = "store_address";
    private static final String Store_lat = "store_lat";
    private static final String Store_long = "store_long";
    private static final String Contact_name = "contact_name";

    Context _context;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor_globel;
    SharedPreferences pref;
    SharedPreferences pref_globel;//
    int PRIVATE_MODE = 0;


    public SessionManager(Context context) {
        this._context = context;
        this.pref = this._context.getSharedPreferences(PREFER_NAME, this.PRIVATE_MODE);
        this.pref_globel = this._context.getSharedPreferences(PREFER_NAME_GLOBEL, this.PRIVATE_MODE);
        this.editor = this.pref.edit();
        this.editor_globel = this.pref_globel.edit();
    }


    public void setLoginSession(boolean bool) {
        editor.putBoolean(IS_LOGIN, bool);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }


    public void setRegisterUser(UserDTO mUser) {
        editor.putString(Customer_id, mUser.getDistributorId());
        editor.putString(Name, mUser.getName());
        editor.putString(Email, mUser.getEmail());
        editor.putString(Phone, mUser.getMobile());
        editor.putString(Password, mUser.getPassword());
        editor.putString(Designation, mUser.getDesignation());
        editor.putString(Country, mUser.getCountry());
        editor.putString(State, mUser.getState());
        editor.putString(City, mUser.getCity());
        editor.putString(Store_location, mUser.getStoreLocation());
        editor.putString(Store_address, mUser.getStoreAddress());
        editor.putString(Store_lat, mUser.getStoreLat());
        editor.putString(Store_long, mUser.getStoreLong());
        editor.putString(Contact_name, mUser.getContactName());


        editor.commit();

    }



}
