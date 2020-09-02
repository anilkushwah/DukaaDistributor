package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.dollop.distributor.LoginActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.UserAccount;
import com.dollop.distributor.UtilityTools.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdatePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_update_pass;
    TextView tv_update_pass,tv_forgot_confirm_pass;
    EditText edt_update_confirm_pass,edt_update_pass;
    LinearLayout ll_update_pass,ll_update_confirm_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        btn_update_pass = findViewById(R.id.btn_update_pass);
        tv_update_pass = findViewById(R.id.tv_update_pass);
        tv_forgot_confirm_pass = findViewById(R.id.tv_forgot_confirm_pass);
        edt_update_confirm_pass = findViewById(R.id.edt_update_confirm_pass);
        edt_update_pass = findViewById(R.id.edt_update_pass);
        ll_update_pass = findViewById(R.id.ll_update_pass);
        ll_update_confirm_pass = findViewById(R.id.ll_update_confirm_pass);

        btn_update_pass.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == btn_update_pass){

            if (!UserAccount.isEmpty(edt_update_pass)){
                edt_update_pass.setError("Please Enter Password");
                edt_update_pass.requestFocus();
            }
            else if (!UserAccount.isEmpty(edt_update_confirm_pass)){
                edt_update_confirm_pass.setError("Please Enter Confirm Password");
                edt_update_confirm_pass.requestFocus();
            }

            else{

                if(! edt_update_confirm_pass.getText().toString().equals(edt_update_pass.getText().toString())){
                    Utils.T(UpdatePasswordActivity.this,"password and new password not match ");
                }
                else {
                    Utils.I_clear(UpdatePasswordActivity.this, LoginActivity.class,null);
                    //Updatepass();
                }
            }
        }
    }

  /*  private void Updatepass() {
        {
            final Dialog dialog = Utils.initProgressDialog(this);
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.change_password, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();

                    Log.e("dukkaLogin:", "dukkaLogin:--" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        Toast.makeText(UpdatePasswordActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                        if (jsonObject.getInt("status")==200) {

                            String otpdata = jsonObject.getString("data");

                            Utils.I_clear(UpdatePasswordActivity.this, LoginActivity.class,null);
                           *//* Intent intent = new Intent(UpdatePasswordActivity.this, OtpSendActivity.class);
                            intent.putExtra("otp",otpdata.toString());
                            intent.putExtra("mobile",et_login_mobile_number.getText().toString());
                            startActivity(intent);*//*
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
                            Utils.T(UpdatePasswordActivity.this, errorMessage + "please check Internet connection");
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
                    stringStringHashMap.put("retailer_id", "4");
                    stringStringHashMap.put("password",edt_update_pass.getText().toString());
                    stringStringHashMap.put("cpassword",edt_update_confirm_pass.getText().toString());
                    Log.e("LoginParameter::", "paramLogin:--" + stringStringHashMap);
                    return stringStringHashMap;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    25000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
        }
    }*/
}