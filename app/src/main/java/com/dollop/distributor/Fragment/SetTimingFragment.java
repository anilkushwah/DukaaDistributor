package com.dollop.distributor.Fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.dollop.distributor.HomeActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class SetTimingFragment extends AppCompatActivity   implements View.OnClickListener {

    ImageView iv_set_back_arrow;
    TextView time_save;
    TextView sone_open,sone_close,stwo_open,stwo_close,ssone_open,ssone_close,sstwo_open,sstwo_close;
    private int  mHour, mMinute;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_set_timing);

        time_save = findViewById(R.id.time_save);
        iv_set_back_arrow = findViewById(R.id.iv_set_back_arrow);

        sone_open = findViewById(R.id.sone_open);
        sone_close = findViewById(R.id.sone_close);
        stwo_open = findViewById(R.id.stwo_open);
        stwo_close = findViewById(R.id.stwo_close);
        ssone_open = findViewById(R.id.ssone_open);
        ssone_close = findViewById(R.id.ssone_close);
        sstwo_open = findViewById(R.id.sstwo_open);
        sstwo_close = findViewById(R.id.sstwo_close);



        sone_open.setOnClickListener(this);
        sone_close.setOnClickListener(this);
        stwo_open.setOnClickListener(this);
        stwo_close.setOnClickListener(this);

        ssone_open.setOnClickListener(this);
        ssone_close.setOnClickListener(this);
        sstwo_open.setOnClickListener(this);
        sstwo_close.setOnClickListener(this);

        iv_set_back_arrow.setOnClickListener(this);
        time_save.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v== iv_set_back_arrow){
            finish();
        }

       else if(v ==sone_open){
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(SetTimingFragment.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String AM_PM;
                    if (selectedHour >=0 && selectedHour < 12){
                        AM_PM = "AM";
                    } else {
                        AM_PM = "PM";
                    }
                    sone_open.setText( selectedHour + ":" + selectedMinute+" "+AM_PM);
                }
            }, hour, minute, false);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
       else if(v ==sone_close){
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(SetTimingFragment.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String AM_PM;
                    if (selectedHour >=0 && selectedHour < 12){
                        AM_PM = "AM";
                    } else {
                        AM_PM = "PM";
                    }
                    sone_close.setText( selectedHour + ":" + selectedMinute+" "+AM_PM);
                }
            }, hour, minute, true);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
       else if(v ==stwo_open){
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(SetTimingFragment.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String AM_PM;
                    if (selectedHour >=0 && selectedHour < 12){
                        AM_PM = "AM";
                    } else {
                        AM_PM = "PM";
                    }
                    stwo_open.setText( selectedHour + ":" + selectedMinute+" "+AM_PM);
                }
            }, hour, minute, true);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
       else if(v ==stwo_close){
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(SetTimingFragment.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String AM_PM;
                    if (selectedHour >=0 && selectedHour < 12){
                        AM_PM = "AM";
                    } else {
                        AM_PM = "PM";
                    }
                    stwo_close.setText( selectedHour + ":" + selectedMinute+" "+AM_PM);
                }
            }, hour, minute, true);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
       else if(v ==ssone_open){
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(SetTimingFragment.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String AM_PM;
                    if (selectedHour >=0 && selectedHour < 12){
                        AM_PM = "AM";
                    } else {
                        AM_PM = "PM";
                    }
                    ssone_open.setText( selectedHour + ":" + selectedMinute+" "+AM_PM);
                }
            }, hour, minute, true);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
       else if(v ==ssone_close){
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(SetTimingFragment.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String AM_PM;
                    if (selectedHour >=0 && selectedHour < 12){
                        AM_PM = "AM";
                    } else {
                        AM_PM = "PM";
                    }
                    ssone_close.setText( selectedHour + ":" + selectedMinute+" "+AM_PM);
                }
            }, hour, minute, true);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
       else if(v ==sstwo_open){
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(SetTimingFragment.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String AM_PM;
                    if (selectedHour >=0 && selectedHour < 12){
                        AM_PM = "AM";
                    } else {
                        AM_PM = "PM";
                    }
                    sstwo_open.setText( selectedHour + ":" + selectedMinute+" "+AM_PM);
                }
            }, hour, minute, true);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
       else if(v ==sstwo_close){
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(SetTimingFragment.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String AM_PM;
                    if (selectedHour >=0 && selectedHour < 12){
                        AM_PM = "AM";
                    } else {
                        AM_PM = "PM";
                    }
                    sstwo_close.setText( selectedHour + ":" + selectedMinute+" "+AM_PM);
                }
            }, hour, minute, true);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }

        else if(v== time_save){
        //    EditText sone_open,sone_close,stwo_open,stwo_close,ssone_open,ssone_close,sstwo_open,sstwo_close;

            if (sone_open.getText().equals("Select Time")){
               Utils.T(SetTimingFragment.this,"Please Select Slot-1 Open Time");
            } else if (sone_close.getText().equals("Select Time")){
                Utils.T(SetTimingFragment.this,"Please Select Slot-1 Close Time");
            }
            else if (stwo_open.getText().equals("Select Time")){
                Utils.T(SetTimingFragment.this,"Please Select Slot-2 Open Time");
            }
            else if (stwo_close.getText().equals("Select Time")){
                Utils.T(SetTimingFragment.this,"Please Select Slot-2 Close Time");
            }
            else if (ssone_open.getText().equals("Select Time")){
                Utils.T(SetTimingFragment.this,"Please Select Slot-1 Open Time");
            }
            else if (ssone_close.getText().equals("Select Time")){
                Utils.T(SetTimingFragment.this,"Please Select Slot-1 Close Time");
            }
            else if (sstwo_open.getText().equals("Select Time")){
                Utils.T(SetTimingFragment.this,"Please Select Slot-2 Open Time");
            }
            else if (sstwo_close.getText().equals("Select Time")){
                Utils.T(SetTimingFragment.this,"Please Select Slot-1 Close Time");
            }
            else {
                setTime();
            }
           /* Intent intent = new Intent(SetTimingFragment.this, HomeActivity.class);
            startActivity(intent);*/
        }
    }

    private void setTime() {
        {
            final Dialog dialog = Utils.initProgressDialog(this);
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.set_timing_shop, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();

                    Log.e("distributerLogin:", "Login:--" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        Toast.makeText(SetTimingFragment.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                        if (jsonObject.getInt("status")==200) {

                            String otpdata = jsonObject.getString("data");

                            Utils.E("otpdata:-"+otpdata);

                            Intent intent = new Intent(SetTimingFragment.this, HomeActivity.class);
                            startActivity(intent);
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
                            Utils.T(SetTimingFragment.this, errorMessage + "please check Internet connection");
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

                    setTimeParam.put("distributor_id","1");
                    setTimeParam.put("MF_slot1_start_time",sone_open.getText().toString());
                    setTimeParam.put("MF_slot1_end_time",sone_close.getText().toString());
                    setTimeParam.put("MF_slot2_start_time",stwo_open.getText().toString());
                    setTimeParam.put("MF_slot2_end_time",stwo_close.getText().toString());
                    setTimeParam.put("SS_slot1_start_time",ssone_open.getText().toString());
                    setTimeParam.put("SS_slot1_end_time",ssone_close.getText().toString());
                    setTimeParam.put("SS_slot2_start_time",sstwo_open.getText().toString());
                    setTimeParam.put("SS_slot2_end_time",sstwo_close.getText().toString());

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
    }

}
