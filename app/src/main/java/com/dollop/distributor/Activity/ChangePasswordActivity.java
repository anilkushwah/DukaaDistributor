package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.dollop.distributor.database.UserDataHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv_create_pass_arrow, new_pass_img,confirm_pass_img, old_password_img;
    TextView btn_passsubmit,tv_create_pass_text_red,tv_confirm_pass_text_red,tv_confim_password_match;
    EditText old_pass,new_pass,confirm_new_pass;
    String passwordVisiblity = "hide";
    String con_passwordVisiblity = "hide";
    String old_passwordVisiblity = "hide";
    TextView current_password_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initializationView();
        TextChangedListenerMethod();

    }

    private void TextChangedListenerMethod() {

        old_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (old_pass.getText().toString().length() < 5) {
                    current_password_error.setVisibility(View.VISIBLE);
                    current_password_error.setText("password must be at least 6 characters ");
                } else if (old_pass.getText().toString().length() > 5) {
                    current_password_error.setVisibility(View.GONE);
                }
            }
        });


        new_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (new_pass.getText().toString().length() < 5) {
                    tv_create_pass_text_red.setVisibility(View.VISIBLE);
                    tv_create_pass_text_red.setText("password must be at least 6 characters ");
                } else if (new_pass.getText().toString().length() > 5) {
                    tv_create_pass_text_red.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        confirm_new_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                if (confirm_new_pass.getText().toString().length() > new_pass.getText().toString().length()) {
                    tv_confirm_pass_text_red.setText("Passwords does not match!");
                    tv_confirm_pass_text_red.setVisibility(View.VISIBLE);
                } else if (!confirm_new_pass.getText().toString().equals(new_pass.getText().toString())) {
                    tv_confirm_pass_text_red.setText("Passwords does not match!");
                    tv_confirm_pass_text_red.setVisibility(View.VISIBLE);
                } else {
                    tv_confirm_pass_text_red.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


    }

    private void initializationView() {
        old_pass = findViewById(R.id.old_pass);
        new_pass = findViewById(R.id.new_pass);
        confirm_new_pass = findViewById(R.id.confirm_new_pass);
        tv_create_pass_text_red = findViewById(R.id.tv_create_pass_text_red);
        tv_confirm_pass_text_red = findViewById(R.id.tv_confirm_pass_text_red);
        tv_confim_password_match = findViewById(R.id.tv_confim_password_match);
        iv_create_pass_arrow = findViewById(R.id.iv_create_pass_arrow);
        btn_passsubmit = findViewById(R.id.btn_passsubmit);
        new_pass_img = findViewById(R.id.new_pass_img);
        confirm_pass_img = findViewById(R.id.confirm_pass_img);
        old_password_img = findViewById(R.id.old_password_img);
        current_password_error = findViewById(R.id.current_password_error);

        boolean status = NetworkUtil.getConnectivityStatus(ChangePasswordActivity.this);
        if(status == true) {

        }else{
            Utils.T(ChangePasswordActivity.this,"No Internet Connection available. Please try again");
        }


        btn_passsubmit.setOnClickListener(this);
        iv_create_pass_arrow.setOnClickListener(this);
        new_pass_img.setOnClickListener(this);
        confirm_pass_img.setOnClickListener(this);
        old_password_img.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        if( btn_passsubmit == v){

            if(!UserAccount.isPasswordValid(old_pass)){
                current_password_error.setVisibility(View.VISIBLE);
                current_password_error.setText("password must be at least 6 characters ");
                old_pass.requestFocus();
            }
            else  if(!UserAccount.isPasswordValid(new_pass)){
                tv_create_pass_text_red.setVisibility(View.VISIBLE);
                tv_create_pass_text_red.setText("password must be at least 6 characters ");
                new_pass.requestFocus();
            }
            else if(!UserAccount.isPasswordValid(confirm_new_pass)){
               // confirm_new_pass.setError("Enter min 6 digit Confirm password");
                confirm_new_pass.requestFocus();
                tv_confirm_pass_text_red.setText("Passwords does not match!");
                tv_confirm_pass_text_red.setVisibility(View.VISIBLE);
            }
            else{
                if(!new_pass.getText().toString().equals(confirm_new_pass.getText().toString())){
                   // Utils.T(ChangePasswordActivity.this,"Password and Confirm Password not match");
                    tv_confirm_pass_text_red.setText("Password and Confirm Password not match");
                    tv_confirm_pass_text_red.setVisibility(View.VISIBLE);
                }
                else{
                    Updatepass();
                }
            }
        }
        else if(v == iv_create_pass_arrow){
            finish();
        }else if (v == new_pass_img) {
            if (passwordVisiblity.equals("hide")) {
                passwordVisiblity = "show";
                new_pass_img.setBackgroundResource(R.drawable.ic_baseline_visibility_off_24);
                new_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

            } else if (passwordVisiblity.equals("show")) {
                passwordVisiblity = "hide";
                new_pass_img.setBackgroundResource(R.drawable.ic_baseline_visibility_24);
                new_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        } else if (v == confirm_pass_img) {
            if (con_passwordVisiblity.equals("hide")) {
                con_passwordVisiblity = "show";
                confirm_pass_img.setBackgroundResource(R.drawable.ic_baseline_visibility_off_24);
                confirm_new_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

            } else if (con_passwordVisiblity.equals("show")) {
                con_passwordVisiblity = "hide";
                confirm_pass_img.setBackgroundResource(R.drawable.ic_baseline_visibility_24);
                confirm_new_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }else if (v == old_password_img) {
            if (old_passwordVisiblity.equals("hide")) {
                old_passwordVisiblity = "show";
                old_password_img.setBackgroundResource(R.drawable.ic_baseline_visibility_off_24);
                old_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

            } else if (old_passwordVisiblity.equals("show")) {
                old_passwordVisiblity = "hide";
                old_password_img.setBackgroundResource(R.drawable.ic_baseline_visibility_24);
                old_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }

    }

    private void Updatepass() {
        final Dialog dialog = Utils.initProgressDialog(this);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.distributor_change_password, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                Log.e("dukkaLogin:", "dukkaLogin:--" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    //Toast.makeText(ChangePasswordActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status")==200) {
                        String otpdata = jsonObject.getString("data");
                        Utils.I_clear(ChangePasswordActivity.this, HomeActivity.class,null);
                    }else {
                        Utils.T(ChangePasswordActivity.this, jsonObject.getString("message"));
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
                        Utils.T(ChangePasswordActivity.this, errorMessage + "please check Internet connection");
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
                stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
                stringStringHashMap.put("password",new_pass.getText().toString());
                stringStringHashMap.put("old_password",old_pass.getText().toString());

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
}


