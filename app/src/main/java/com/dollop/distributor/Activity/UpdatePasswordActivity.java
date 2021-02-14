package com.dollop.distributor.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class UpdatePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_update_pass;
    TextView tv_forgot_confirm_pass_text;
    EditText edt_update_confirm_pass, edt_update_pass;
    String distributor_id;
    LinearLayout ll_update_pass, ll_update_confirm_pass;
    TextView tv_confim_password_match, tv_confirm_pass_text_red, tv_new_pass_text_red;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        distributor_id = getIntent().getExtras().getString("distributor_id");
        initializationView();
        TextChangedListenerMethod();


    }

    private void TextChangedListenerMethod() {
        edt_update_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 0 && edt_update_confirm_pass.getText().length() <= 0) {
                    ll_update_confirm_pass.setBackgroundResource(R.drawable.et_edittext_bck);
                    tv_confirm_pass_text_red.setVisibility(View.GONE);
                }
                if (edt_update_confirm_pass.getText().length() > 5) {
                    if (edt_update_pass.getText().equals(edt_update_confirm_pass.getText())) {
                        tv_new_pass_text_red.setVisibility(View.GONE);
                        ll_update_pass.setBackgroundResource(R.drawable.et_edittext_bck);
                        tv_confim_password_match.setVisibility(View.VISIBLE);
                        ll_update_confirm_pass.setBackgroundResource(R.drawable.pass_match_back);
                    } else {
                        tv_confirm_pass_text_red.setVisibility(View.VISIBLE);
                        tv_confim_password_match.setVisibility(View.GONE);
                        ll_update_confirm_pass.setBackgroundResource(R.drawable.errorset);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() <= 0) {
                    edt_update_confirm_pass.getText().clear();
                }
            }
        });
        edt_update_confirm_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               /* if (!edt_update_confirm_pass.getText().toString().equals("")) {
                    String newPassword = edt_update_pass.getText().toString();
                    String confirmPassword = edt_update_confirm_pass.getText().toString();
                    if (newPassword.equals(confirmPassword)) {
                        tv_forgot_confirm_pass_text.setVisibility(View.GONE);
                    } else {
                        tv_forgot_confirm_pass_text.setVisibility(View.VISIBLE);
                    }
                } else {

                }*/

                if (s.length() <= 0 && edt_update_pass.getText().length() <= 0) {
                    ll_update_confirm_pass.setBackgroundResource(R.drawable.et_edittext_bck);
                    // ll_createpass.setBackgroundResource(R.drawable.et_edittext_bck);
                    tv_confirm_pass_text_red.setVisibility(View.GONE);
                    tv_confim_password_match.setVisibility(View.GONE);
                }

                if (!edt_update_confirm_pass.getText().toString().equals("")) {
                    String newPassword = edt_update_pass.getText().toString();
                    String confirmPassword = edt_update_confirm_pass.getText().toString();
                    if (newPassword.equals(confirmPassword)) {
                        tv_confirm_pass_text_red.setVisibility(View.GONE);
                        tv_confim_password_match.setVisibility(View.VISIBLE);
                        ll_update_confirm_pass.setBackgroundResource(R.drawable.pass_match_back);
                        tv_new_pass_text_red.setVisibility(View.GONE);
                        ll_update_pass.setBackgroundResource(R.drawable.et_edittext_bck);
                    } else {
                        tv_confirm_pass_text_red.setVisibility(View.VISIBLE);
                        tv_confim_password_match.setVisibility(View.GONE);
                        ll_update_confirm_pass.setBackgroundResource(R.drawable.errorset);
                    }
                } else {
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initializationView() {
        tv_new_pass_text_red = findViewById(R.id.tv_new_pass_text_red);
        tv_confim_password_match = findViewById(R.id.tv_confim_password_match);
        tv_confirm_pass_text_red = findViewById(R.id.tv_confirm_pass_text_red);

        btn_update_pass = findViewById(R.id.btn_update_pass);
        //   tv_update_pass = findViewById(R.id.tv_update_pass);
        edt_update_confirm_pass = findViewById(R.id.edt_update_confirm_pass);
        edt_update_pass = findViewById(R.id.edt_update_pass);
        ll_update_pass = findViewById(R.id.ll_update_pass);
        ll_update_confirm_pass = findViewById(R.id.ll_update_confirm_pass);

        boolean status = NetworkUtil.getConnectivityStatus(UpdatePasswordActivity.this);
        if (status == true) {

        } else {
            Utils.T(UpdatePasswordActivity.this, "No Internet Connection available. Please try again");
        }

        btn_update_pass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_update_pass) {

            if (!UserAccount.isPasswordValid(edt_update_pass)) {
                edt_update_pass.setError("Please Enter 6 digit Password");
                edt_update_pass.requestFocus();
            } else if (!UserAccount.isPasswordValid(edt_update_confirm_pass)) {
                edt_update_confirm_pass.setError("Please Enter 6 digit Confirm Password");
                edt_update_confirm_pass.requestFocus();
            } else {
                if (!edt_update_confirm_pass.getText().toString().equals(edt_update_pass.getText().toString())) {
                    Utils.T(UpdatePasswordActivity.this, "password and new password not match ");
                } else {
                    Updatepass();
                }
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

                   // Toast.makeText(UpdatePasswordActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {
                        String otpdata = jsonObject.getString("data");
                        Utils.I_clear(UpdatePasswordActivity.this, LoginActivity.class, null);
                    }else {
                        Utils.T(UpdatePasswordActivity.this,jsonObject.getString("message"));
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
                stringStringHashMap.put("distributor_id", distributor_id);

                stringStringHashMap.put("password", edt_update_pass.getText().toString());
                stringStringHashMap.put("type", "forgotPassword");
               // type:forgotPassword
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