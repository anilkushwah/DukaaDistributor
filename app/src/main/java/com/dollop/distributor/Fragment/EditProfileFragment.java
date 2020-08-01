package com.dollop.distributor.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.dollop.distributor.Activity.CreateAccountActivity;
import com.dollop.distributor.LoginActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.UserAccount;
import com.dollop.distributor.UtilityTools.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class EditProfileFragment extends AppCompatActivity implements View.OnClickListener {

    ImageView edit_profile_back;
    EditText create_name,create_store_location,create_store_address,create_email,create_phone;
    Spinner crate_country;
    TextView update_profile;
    TextView tool_pro_name,tool_pro_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_editprofile);

        tool_pro_name = findViewById(R.id.tool_pro_name);
        tool_pro_email = findViewById(R.id.tool_pro_email);
        edit_profile_back = findViewById(R.id.edit_profile_back);
        update_profile = findViewById(R.id.btn_update_profile);
        edit_profile_back.setOnClickListener(this);
        update_profile.setOnClickListener(this);


        create_name = findViewById(R.id.create_name);
        create_store_location = findViewById(R.id.create_store_location);
        create_store_address = findViewById(R.id.create_store_address);
        create_email = findViewById(R.id.create_email);
        create_phone = findViewById(R.id.create_phone);
        crate_country = findViewById(R.id.crate_country);
        getProfile();
    }

    @Override
    public void onClick(View v) {

        if(v== edit_profile_back){
          finish();
        }

        else if(v== update_profile){

            if (!UserAccount.isEmpty(create_name)){
                create_name.setError("Please enter name");
                create_name.requestFocus();
            }  else if (!UserAccount.isEmpty(create_store_location)){
                create_store_location.setError("Please enter Location");
                create_store_location.requestFocus();
            }else if (!UserAccount.isEmpty(create_store_address)){
                create_store_address.setError("Please enter Store Address");
                create_store_address.requestFocus();
            }
            else if(crate_country.getSelectedItem().equals("Select Country")){
                Utils.T(EditProfileFragment.this,"please select Country name");
            }

            else if (!UserAccount.isEmpty(create_email)){
                create_email.setError("Please enter email");
                create_email.requestFocus();
            }
            else if (!UserAccount.isPhoneNumberLength(create_phone)){
                create_phone.setError("Please enter 10 digit mobile number");
                create_phone.requestFocus();
            }
            else {
                EditProfile();
            }
        }
    }


 


    private void EditProfile() {
        {
            final Dialog dialog = Utils.initProgressDialog(this);
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.distributor_register, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();

                    Log.e("distributersign:", "signup:--" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        Toast.makeText(EditProfileFragment.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                        if (jsonObject.getInt("status")==200) {

                            String otpdata = jsonObject.getString("data");

                            Intent intent = new Intent(EditProfileFragment.this, LoginActivity.class);
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
                            Utils.T(EditProfileFragment.this, errorMessage + "please check Internet connection");
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
                    HashMap<String, String> signupParam  = new HashMap<>();
                    signupParam.put("name",create_name.getText().toString());
                    signupParam.put("mobile",create_phone.getText().toString());
                    signupParam.put("email_id",create_email.getText().toString());
                    signupParam.put("country",crate_country.getSelectedItem().toString());
                   // signupParam.put("city",crate_city.getSelectedItem().toString());
                    signupParam.put("store_location",create_store_location.getText().toString());
                    signupParam.put("store_address",create_store_address.getText().toString());
                  //  signupParam.put("contact_name",create_key_name.getText().toString());

                    Log.e("SignupParameter::", "param:--" + signupParam);
                    return signupParam;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    25000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
        }
    }

    private void getProfile() {
        {
            final Dialog dialog = Utils.initProgressDialog(EditProfileFragment.this);
            RequestQueue queue = Volley.newRequestQueue(EditProfileFragment.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.get_distributor, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();

                    Log.e("getProfile:", "profile:--" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        Toast.makeText(EditProfileFragment.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                        if (jsonObject.getInt("status")==200) {

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for(int i =0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                if(!object.isNull("name")){
                                    create_name.setText(object.getString("name"));
                                    tool_pro_name.setText(object.getString("name"));
                                } if(!object.isNull("email_id")){
                                    create_email.setText(object.getString("email_id"));
                                    tool_pro_email.setText(object.getString("email_id"));
                                }
                                if(!object.isNull("mobile")){
                                    create_phone.setText(object.getString("mobile"));
                                }

                                if(!object.isNull("store_location")){
                                    create_store_location.setText(object.getString("store_location"));
                                }
                                if(!object.isNull("store_address")){
                                    create_store_address.setText(object.getString("store_address"));
                                }
                              /*  if(!object.isNull("contact_name")){
                                    create_key_name.setText(object.getString("contact_name"));
                                }*/

                            }


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
                            Utils.T(EditProfileFragment.this, errorMessage + "please check Internet connection");
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
                    HashMap<String, String> ProfileParam  = new HashMap<>();
                    ProfileParam.put("distributor_id","1");


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
}
