package com.dollop.distributor.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.dollop.distributor.UtilityTools.SavedData;
import com.dollop.distributor.UtilityTools.UserAccount;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.database.UserDataHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreditSettingActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView credit_setback;
    EditText et_credit_amount;
    TextView tv_reqamount, tv_updateamount, limit_history_TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_setting);

        initializationView();
        getProfile();

    }


    @Override
    public void onClick(View v) {

        if (v==limit_history_TV){

        }
        if (v == credit_setback) {
            finish();
        } else if (v == tv_updateamount) {
            if (!UserAccount.isEmpty(et_credit_amount)) {
                et_credit_amount.setError("please Enter Credit Limit");
                et_credit_amount.requestFocus();
            } else {
                if (!et_credit_amount.getText().toString().equals("0")) {
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(" Confirmation !")
                            .setMessage("Are You Sure You Want To Update Your Credit Limit?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    UploadAmount(et_credit_amount.getText().toString());
                                }

                            })
                            .setNegativeButton("No", null)
                            .show();
                } else {
                    UploadAmount(et_credit_amount.getText().toString());
                }

            }
        }
    }


    private void UploadAmount(final String value) {
        final Dialog dialog = Utils.initProgressDialog(this);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.set_credit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                Log.e("dukkaLogin:", "dukkaLogin:--" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    //Toast.makeText(CreditSettingActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {
                        finish();
                        tv_reqamount.setText(""+value);
                    } else {
                        Utils.T(CreditSettingActivity.this, jsonObject.getString("message"));
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
                        Utils.T(CreditSettingActivity.this, errorMessage + "please check Internet connection");
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
                stringStringHashMap.put("distributor_id", SavedData.get_Distributor_id().toString());
                stringStringHashMap.put("credit", et_credit_amount.getText().toString());
                Log.e("credit limit::", "limit:--" + stringStringHashMap);
                return stringStringHashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    private void initializationView() {

        tv_updateamount = findViewById(R.id.tv_updateamount);
        et_credit_amount = findViewById(R.id.et_credit_amount);
        credit_setback = findViewById(R.id.credit_setback);
        tv_reqamount = findViewById(R.id.tv_reqamount);
        limit_history_TV = findViewById(R.id.limit_history_TV);

        credit_setback.setOnClickListener(this);
        tv_updateamount.setOnClickListener(this);

        boolean status = NetworkUtil.getConnectivityStatus(CreditSettingActivity.this);
        if (status == true) {

        } else {
            Utils.T(CreditSettingActivity.this, "No Internet Connection available. Please try again");
        }

        et_credit_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               // tv_reqamount.setText("$" + s);
            }
        });
    }

    private void getProfile() {
        final Dialog dialog = Utils.initProgressDialog(CreditSettingActivity.this);
        RequestQueue queue = Volley.newRequestQueue(CreditSettingActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.get_distributor, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                Log.e("getProfile:", "profile:--" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    // Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {

                        JSONArray jsonArray = jsonObject.getJSONArray("distributor");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            if (!object.isNull("credit")) {
                                tv_reqamount.setText("$" + object.getString("credit"));
                            }

                        }
                    } else {
                        Utils.T(CreditSettingActivity.this, jsonObject.getString("message"));
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
                        Utils.T(CreditSettingActivity.this, errorMessage + "please check Internet connection");
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
                HashMap<String, String> ProfileParam = new HashMap<>();
                //   ProfileParam.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
                ProfileParam.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
                Log.e("ProfileParam::", "param:--" + ProfileParam);
                return ProfileParam;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

}