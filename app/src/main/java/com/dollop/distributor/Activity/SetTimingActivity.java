package com.dollop.distributor.Activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

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
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.database.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class SetTimingActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv_set_back_arrow;
    TextView time_save;
    TextView sone_open, sone_close, ssone_open, ssone_close;
    //,stwo_open,stwo_close,,sstwo_open,sstwo_close;
    private int mHour_OpenTime, mMinute_OpenTime;
    private int mHour_off, mMinute_off;
    boolean status;
    String type = "";
    TimePicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_set_timing);

        type = getIntent().getStringExtra("type");

        time_save = findViewById(R.id.time_save);
        iv_set_back_arrow = findViewById(R.id.iv_set_back_arrow);
        sone_open = findViewById(R.id.sone_open);
        sone_close = findViewById(R.id.sone_close);
        ssone_open = findViewById(R.id.ssone_open);
        ssone_close = findViewById(R.id.ssone_close);
        status = NetworkUtil.getConnectivityStatus(SetTimingActivity.this);

      if (type.equals("profile")) {
            if (status == true) {
                getProfile();
            } else {
                Utils.T(SetTimingActivity.this, "No Internet Connection available. Please try again");
            }
        }

        sone_open.setOnClickListener(this);
        sone_close.setOnClickListener(this);
        ssone_open.setOnClickListener(this);
        ssone_close.setOnClickListener(this);
        iv_set_back_arrow.setOnClickListener(this);
        time_save.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == iv_set_back_arrow) {
            onBackPressed();
        } else if (v == sone_open) {

            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(SetTimingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                    String AM_PM;
                    if (selectedHour >= 0 && selectedHour < 12) {
                        AM_PM = "AM";
                    } else {
                        AM_PM = "PM";
                    }

                    if (selectedHour > 12) {

                        selectedHour = selectedHour - 12;
                    }

                    String mString_Hours = String.valueOf(selectedHour);
                    String mString_Minute = String.valueOf(selectedMinute);

                    String mStrHours = String.valueOf(selectedHour);
                    String mStrMinute =String.valueOf(selectedMinute);

                    if (mString_Hours.toString().length() == 1) {
                        mStrHours = "0" + mString_Hours;
                    }

                    if (mString_Minute.toString().length() == 1) {
                        mStrMinute = "0" + mString_Minute;
                    }

                    mHour_OpenTime = selectedHour;
                    mMinute_OpenTime = selectedMinute;
                    sone_open.setText(mStrHours + ":" + mStrMinute + " " + AM_PM);

                }
            }, hour, minute, false);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();


        } else if (v == sone_close) {


            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(SetTimingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String AM_PM;
                    if (selectedHour >= 0 && selectedHour < 12) {
                        AM_PM = "AM";
                    } else {
                        AM_PM = "PM";
                    }

                    if (selectedHour > 12) {

                        selectedHour = selectedHour - 12;
                    }

                    String mString_Hours = String.valueOf(selectedHour);
                    String mString_Minute = String.valueOf(selectedMinute);

                    String mStrHours =String.valueOf(selectedHour);
                    String mStrMinute = String.valueOf(selectedMinute);

                    if (mString_Hours.toString().length() == 1) {
                        mStrHours = "0" + mString_Hours;
                    }

                    if (mString_Minute.toString().length() == 1) {
                        mStrMinute = "0" + mString_Minute;
                    }


                    sone_close.setText(mStrHours + ":" + mStrMinute + " " + AM_PM);
                }
            }, mHour_OpenTime, mMinute_OpenTime, false);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        } else if (v == ssone_open) {

            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(SetTimingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String AM_PM;
                    if (selectedHour >= 0 && selectedHour < 12) {
                        AM_PM = "AM";
                    } else {
                        AM_PM = "PM";
                    }
                    if (selectedHour > 12) {

                        selectedHour = selectedHour - 12;
                    }

                    String mString_Hours = String.valueOf(selectedHour);
                    String mString_Minute = String.valueOf(selectedMinute);

                    String mStrHours =String.valueOf(selectedHour);
                    String mStrMinute =String.valueOf(selectedMinute);


                    if (mString_Hours.toString().length() == 1) {
                        mStrHours = "0" + mString_Hours;
                    }

                    if (mString_Minute.toString().length() == 1) {
                        mStrMinute = "0" + mString_Minute;
                    }

                    mHour_off = selectedHour;
                    mMinute_off = selectedMinute;

                    ssone_open.setText(mStrHours + ":" + mStrMinute + " " + AM_PM);
                }
            }, hour, minute, false);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        } else if (v == ssone_close) {

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(SetTimingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String AM_PM;
                    if (selectedHour >= 0 && selectedHour < 12) {
                        AM_PM = "AM";
                    } else {
                        AM_PM = "PM";
                    }
                    if (selectedHour > 12) {

                        selectedHour = selectedHour - 12;
                    }

                    String mString_Hours = String.valueOf(selectedHour);
                    String mString_Minute = String.valueOf(selectedMinute);

                    String mStrHours = String.valueOf(selectedHour);
                    String mStrMinute = String.valueOf(selectedMinute);

                    if (mString_Hours.toString().length() == 1) {
                        mStrHours = "0" + mString_Hours;
                    }

                    if (mString_Minute.toString().length() == 1) {
                        mStrMinute = "0" + mString_Minute;
                    }

                    ssone_close.setText(mStrHours + ":" + mStrMinute + " " + AM_PM);
                }
            }, mHour_off, mMinute_off, false);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        } else if (v == time_save) {

            if (sone_open.getText().equals("Select Open Time")) {
                Utils.T(SetTimingActivity.this, "Please Select Mon-Fri Open Time");
            } else if (sone_close.getText().equals("Select Close Time")) {
                Utils.T(SetTimingActivity.this, "Please Select Mon-Fri Close Time");
            } else if (ssone_open.getText().equals("Select Open Time")) {
                Utils.T(SetTimingActivity.this, "Please Select Sat-Sun Open Time");
            } else if (ssone_close.getText().equals("Select Close Time")) {
                Utils.T(SetTimingActivity.this, "Please Select Sat-Sun Close Time");
            } else {

                if (status) {
                    setTime();
                } else {
                    Utils.T(SetTimingActivity.this, "No Internet Connection available. Please try again");
                }


            }

        }
    }

    private void setTime() {
        final Dialog dialog = Utils.initProgressDialog(this);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.set_timing_shop, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                Log.e("distributerLogin:", "Login:--" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("jsonObject:", "jsonObject:--" + jsonObject);

                    //   Toast.makeText(SetTimingActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    if (jsonObject.getInt("status") == 200) {

                        if (type.equals("profile")) {
                            Utils.I_clear(SetTimingActivity.this, HomeActivity.class, null);

                        } else {
                            JSONObject dataobj = jsonObject.getJSONObject("data");

                            UserModel userModel = UserDataHelper.getInstance().getList().get(0);
                            userModel.setMf_start(dataobj.getString("MF_slot1_start_time").toString());
                            userModel.setMf_close(dataobj.getString("MF_slot1_end_time").toString());
                            userModel.setSs_start(dataobj.getString("SS_slot1_start_time").toString());
                            userModel.setSs_close(dataobj.getString("SS_slot1_end_time").toString());
                            UserDataHelper.getInstance().insertData(userModel);

                            SavedData.saveSetTime("Active");
                            startActivity(new Intent(SetTimingActivity.this, SearchMapsActivity.class)
                                    .putExtra("type", type));
                            finish();
                        }


                    } else {
                        Utils.T(SetTimingActivity.this, jsonObject.getString("message"));
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
                        Utils.T(SetTimingActivity.this, errorMessage + "please check Internet connection");
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
                HashMap<String, String> setTimeParam = new HashMap<>();

                setTimeParam.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
                setTimeParam.put("MF_slot1_start_time", sone_open.getText().toString());
                setTimeParam.put("MF_slot1_end_time", sone_close.getText().toString());
                setTimeParam.put("SS_slot1_start_time", ssone_open.getText().toString());
                setTimeParam.put("SS_slot1_end_time", ssone_close.getText().toString());

                Log.e("setTimeparam::", "All :--" + setTimeParam);
                return setTimeParam;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    private void getProfile() {
        final Dialog dialog = Utils.initProgressDialog(SetTimingActivity.this);
        RequestQueue queue = Volley.newRequestQueue(SetTimingActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.get_distributor, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                Log.e("getProfile:", "profile:--" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    // Toast.makeText(SetTimingActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {

                        JSONArray jsonArray = jsonObject.getJSONArray("distributor");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            if (!object.isNull("MF_slot1_start_time")) {
                                sone_open.setText("" + object.getString("MF_slot1_start_time"));
                            }
                            if (!object.isNull("MF_slot1_end_time")) {
                                sone_close.setText("" + object.getString("MF_slot1_end_time"));
                            }

                            if (!object.isNull("SS_slot1_start_time")) {
                                ssone_open.setText("" + object.getString("SS_slot1_start_time"));

                            }
                            if (!object.isNull("SS_slot1_end_time")) {
                                ssone_close.setText("" + object.getString("SS_slot1_end_time"));
                            }

                        }
                    } else {
                        Utils.T(SetTimingActivity.this, jsonObject.getString("message"));
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
                        Utils.T(SetTimingActivity.this, errorMessage + "please check Internet connection");
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
