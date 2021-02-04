package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.chaos.view.PinView;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.NetworkUtil;
import com.dollop.distributor.UtilityTools.SavedData;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.database.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpSendActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView iv_login_back_arrow;
    TextView btn_submit_otp;
    PinView firstPinView;
    String mobile, data;
    TextView tv_resend_Ot, tv_resend_otp_timer;
    CountDownTimer countDownTimer;
    LinearLayout lv_cound_down_timerForgot_Id;
    Activity activity = OtpSendActivity.this;
    boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_send);

        initializationView();
        getTimer();
    }

    private void initializationView() {
        tv_resend_Ot = findViewById(R.id.tv_resend_Ot);
        btn_submit_otp = findViewById(R.id.btn_submit_otp);
        tv_resend_otp_timer = findViewById(R.id.tv_resend_otp_timer);
        lv_cound_down_timerForgot_Id = findViewById(R.id.lv_cound_down_timerForgot_Id);
        firstPinView = findViewById(R.id.firstPinView);
        iv_login_back_arrow = findViewById(R.id.iv_login_back_arrow);

        btn_submit_otp.setOnClickListener(this);
        tv_resend_Ot.setOnClickListener(this);
        iv_login_back_arrow.setOnClickListener(this);

        status = NetworkUtil.getConnectivityStatus(OtpSendActivity.this);
        if (status == true) {

        } else {
            Utils.T(OtpSendActivity.this, "No Internet Connection available. Please try again");
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            data = getIntent().getExtras().getString("otp", "");
            mobile = getIntent().getExtras().getString("mobile", "");
            // firstPinView.setText(data);
            Utils.E("otp>>>"+ data);
        }
    }


    @Override
    public void onClick(View v) {

        if (v == iv_login_back_arrow) {
            onBackPressed();
        }
        if (v == btn_submit_otp) {
            String otp = firstPinView.getText().toString();
            if (otp.isEmpty()) {
                firstPinView.setError("Please Enter Otp");
                firstPinView.requestFocus();
            } else if (otp.length() != 4) {
                firstPinView.setError("please enter 4 digit otp");
                firstPinView.requestFocus();
            } else {
                if (status) {
                    // Intent intent = new Intent(OtpSendActivity.this,LocationActivity.class);
                    // startActivity(intent);
                    matchOtp();
                } else {
                    Utils.T(OtpSendActivity.this, "No Internet Connection available. Please try again");
                }
            }

        } else if (v == tv_resend_Ot) {
            if (status) {
                lv_cound_down_timerForgot_Id.setVisibility(View.VISIBLE);
                tv_resend_Ot.setVisibility(View.GONE);
                firstPinView.getText().clear();
                getTimer();
                Resend();
            } else {
                Utils.T(OtpSendActivity.this, "No Internet Connection available. Please try again");
            }
        }
    }

    private void matchOtp() {
        final Dialog dialog = Utils.initProgressDialog(this);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.match_otp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                Log.e("distributerOtp:", "Otp:--" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    // Toast.makeText(OtpSendActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {

                        JSONObject data = jsonObject.getJSONObject("data");
                        UserModel userModel = new UserModel();
                        userModel.setDistributorId(data.getString("distributor_id"));
                        userModel.setImage(data.getString("image"));
                        userModel.setEmail(data.getString("email"));
                        userModel.setName(data.getString("name"));
                        userModel.setMobile(data.getString("mobile"));

                        UserDataHelper.getInstance().insertData(userModel);
                        SavedData.saveactiveUser("Active");
                        SavedData.saveDisID(data.getString("distributor_id").toString());
                        SavedData.saveAccessType(data.getString("access_type"));
                        SavedData.saveMainMemberID(data.getString("sub_member_distributor_id"));

                        //  Utils.I_finish(OtpSendActivity.this, SetTimingActivity.class, null);
                        startActivity(new Intent(OtpSendActivity.this, SetTimingActivity.class)
                                .putExtra("type", "otpPage"));
                        finish();


                    } else {
                        Utils.T(OtpSendActivity.this, jsonObject.getString("message"));
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
                        Utils.T(OtpSendActivity.this, errorMessage + "please check Internet connection");
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
                stringStringHashMap.put("mobile", mobile);
                stringStringHashMap.put("type", "distributor");
                stringStringHashMap.put("otp", firstPinView.getText().toString());
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


    public void getTimer() {
        Log.e("cgecch", "::");
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv_resend_otp_timer.setText((millisUntilFinished / 30000) + ":" + (millisUntilFinished % 30000 / 1000) + " |");
                tv_resend_Ot.setVisibility(View.GONE);

            }

            @Override
            public void onFinish() {
                lv_cound_down_timerForgot_Id.setVisibility(View.GONE);
                tv_resend_Ot.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void Resend() {
        final Dialog dialog = Utils.initProgressDialog(this);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.distributor_resend_otp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                Log.e("distributerResend:", "Resend:--" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //Toast.makeText(activity, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    if (jsonObject.getInt("status") == 200) {
                        String otpdata = jsonObject.getString("data");
                        Utils.T_C_L(activity, "" + otpdata);
                        Utils.E( "otp>>>>"+otpdata);
                        //  firstPinView.setText(otpdata);
                    } else {
                        Utils.T(OtpSendActivity.this, jsonObject.getString("message"));
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
                        Utils.T(activity, errorMessage + "please check Internet connection");
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
                stringStringHashMap.put("mobile", mobile);
                stringStringHashMap.put("type", "distributor");
                Log.e("ResnedParameter::", "paramResned:--" + stringStringHashMap);
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
