package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.NetworkUtil;
import com.dollop.distributor.UtilityTools.UserAccount;
import com.dollop.distributor.UtilityTools.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.internal.Util;

public class ForgotActivity extends AppCompatActivity implements View.OnClickListener  {

    Button btn_forgot_send;
    EditText et_forgot_mobile_number;
    TextView tv_forgot_mobile_req;
    LinearLayout ll_forgot_mobi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        tv_forgot_mobile_req = findViewById(R.id.tv_forgot_mobile_req);
        btn_forgot_send = findViewById(R.id.btn_forgot_send);
        et_forgot_mobile_number = findViewById(R.id.et_forgot_mobile_number);
        ll_forgot_mobi = findViewById(R.id.ll_forgot_mobi);

        btn_forgot_send.setOnClickListener(this);

        boolean status = NetworkUtil.getConnectivityStatus(ForgotActivity.this);
        if(status == true) {

        }else{
            Utils.T(ForgotActivity.this,"No Internet Connection available. Please try again");
        }
    }


    @Override
    public void onClick(View v) {
        if(v ==btn_forgot_send){

            if (!UserAccount.isEmpty(et_forgot_mobile_number)){
                et_forgot_mobile_number.setError("please enter mobile number");
                et_forgot_mobile_number.requestFocus();
            }
           else if (!UserAccount.isPhoneNumberLength(et_forgot_mobile_number)){
                et_forgot_mobile_number.setError("Please enter 9 digit mobile number");
                et_forgot_mobile_number.requestFocus();
            }
            else{
                //Utils.I(ForgotActivity.this, ForgotMatchOtpActivity.class,null);
                Forgot();
            }
        }
    }

        private void Forgot() {
        {
            final Dialog dialog = Utils.initProgressDialog(this);
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.distributor_forgot_password, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();

                    Log.e("forgot_password:", "forgot_password:--" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                       // Toast.makeText(ForgotActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                        if (jsonObject.getInt("status")==200) {

                            String otpdata = jsonObject.getString("data");

                            Bundle bundle   = new Bundle();
                            bundle.putString("otp",otpdata);
                            bundle.putString("mobile",et_forgot_mobile_number.getText().toString());
                            Utils.I(ForgotActivity.this, ForgotMatchOtpActivity.class,bundle);

                        }else {
                            Utils.T(ForgotActivity.this, jsonObject.getString("message"));
                        }

                    } catch (JSONException e) {
                        dialog.dismiss();

                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();

                    NetworkResponse networkResponse = error.networkResponse;

                    String errorMessage = "Unknown error";
                    if (networkResponse == null) {
                        if (error.getClass().equals(TimeoutError.class)) {
                            errorMessage = "Request timeout";
                            Utils.T(ForgotActivity.this, errorMessage + "please check Internet connection");
                        } else if (error.getClass().equals(NoConnectionError.class)) {
                            errorMessage = "Failed to connect server";
                        }
                    } else {
                        String result = new String(networkResponse.data);
                        try {
                            JSONObject response = new JSONObject(result);
                            String status = response.getString("status");
                            String message = response.getString("message");
                            Log.e("Error Status", status);
                            Log.e("Error Message", message);
                            if (networkResponse.statusCode == 404) {
                                errorMessage = "Resource not found";
                            } else if (networkResponse.statusCode == 401) {
                                errorMessage = message + " Please login again";
                            } else if (networkResponse.statusCode == 400) {
                                errorMessage = message + " Check your inputs";
                            } else if (networkResponse.statusCode == 500) {
                                errorMessage = message + " Something is getting wrong";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> stringStringHashMap = new HashMap<>();
                    stringStringHashMap.put("mobile",et_forgot_mobile_number.getText().toString());

                    Log.e("ForfotParameter::", "paramforgot:--" + stringStringHashMap);
                    return stringStringHashMap;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    25000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
        }
    }
}