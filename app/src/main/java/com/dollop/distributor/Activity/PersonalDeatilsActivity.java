package com.dollop.distributor.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.ManageMemberModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PersonalDeatilsActivity extends AppCompatActivity {

    TextView create_name, create_email, create_phone, create_store_address, create_city, create_country, create_designation, tool_pro_name, tool_pro_email,
            create_mpesa_user, create_till, create_kra, access_Type;
    String type = "";
    ImageView image, edit_profile_back;
    LinearLayout store_layout, city_LL, county_LL, payment_LL, till_LL, kra_LL, access_type_LL;
    ManageMemberModel manageMemberModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_deatils);

        type = getIntent().getStringExtra("type");
        manageMemberModel = (ManageMemberModel) getIntent().getSerializableExtra("data");

        initializationView();
        setData();

    }


    private void getProfile() {
        final Dialog dialog = Utils.initProgressDialog(PersonalDeatilsActivity.this);
        RequestQueue queue = Volley.newRequestQueue(PersonalDeatilsActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.get_distributor, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                Log.e("getProfile:", "profile:--" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                   // Toast.makeText(PersonalDeatilsActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {

                        JSONArray jsonArray = jsonObject.getJSONArray("distributor");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            if (!object.isNull("name")) {
                                create_name.setText(object.getString("name"));
                                tool_pro_name.setText(object.getString("name"));
                            }
                            if (!object.isNull("email")) {
                                create_email.setText(object.getString("email"));
                                tool_pro_email.setText(object.getString("email"));
                            }
                            if (!object.isNull("mobile")) {
                                create_phone.setText(object.getString("mobile"));
                            }

                            if (!object.isNull("store_address")) {
                                create_store_address.setText(object.getString("store_address"));
                            }
                            if (!object.isNull("designation")) {
                                create_designation.setText(object.getString("designation"));
                            }
                            if (!object.isNull("country")) {
                                create_country.setText(object.getString("country"));
                            }
                            if (!object.isNull("city")) {
                                create_city.setText(object.getString("city"));
                            }
                            if (!object.isNull("mpesa_user_name")) {
                                create_mpesa_user.setText(object.getString("mpesa_user_name"));
                            }
                            if (!object.isNull("till_number")) {
                                create_till.setText("Till Num  " + object.getString("till_number"));
                            }
                            if (!object.isNull("kra_pin")) {
                                create_kra.setText("KRA Pin  " + object.getString("kra_pin"));
                            }
                            if (!object.isNull("image")) {
                                Picasso.get().load(Const.URL.HOST_URL +
                                        object.getString("image")).into(image);

                            }
                        }
                    }else {
                        Utils.T(PersonalDeatilsActivity.this, jsonObject.getString("message"));
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
                        Utils.T(PersonalDeatilsActivity.this, errorMessage + "please check Internet connection");
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
                //   ProfileParam.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId().toString());

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


    private void setData() {
        if (type.equals("profile")) {
            payment_LL.setVisibility(View.GONE);
            till_LL.setVisibility(View.GONE);
            kra_LL.setVisibility(View.GONE);
            getProfile();
        } else {
            create_name.setText(manageMemberModel.getName());
            tool_pro_name.setText(manageMemberModel.getName());
            create_email.setText(manageMemberModel.getEmail());
            tool_pro_email.setText(manageMemberModel.getEmail());
            create_phone.setText(manageMemberModel.getMobile());
            create_designation.setText(manageMemberModel.getDesignation());
            access_Type.setText(manageMemberModel.getAccess_type());

            city_LL.setVisibility(View.GONE);
            county_LL.setVisibility(View.GONE);

            store_layout.setVisibility(View.GONE);
            payment_LL.setVisibility(View.GONE);
            till_LL.setVisibility(View.GONE);
            kra_LL.setVisibility(View.GONE);
            access_type_LL.setVisibility(View.VISIBLE);

            if (manageMemberModel.getProfile_pic() != null) {
                Picasso.get().load(Const.URL.HOST_URL + manageMemberModel.getProfile_pic()).into(image);

            }

        }
    }

    private void initializationView() {

        create_name = findViewById(R.id.create_name);
        create_email = findViewById(R.id.create_email);
        create_phone = findViewById(R.id.create_phone);
        create_store_address = findViewById(R.id.create_store_address);
        create_city = findViewById(R.id.create_city);
        create_country = findViewById(R.id.create_country);
        create_designation = findViewById(R.id.create_designation);
        tool_pro_name = findViewById(R.id.tool_pro_name);
        tool_pro_email = findViewById(R.id.tool_pro_email);
        create_mpesa_user = findViewById(R.id.create_mpesa_user);
        create_till = findViewById(R.id.create_till);
        create_kra = findViewById(R.id.create_kra);
        store_layout = findViewById(R.id.store_layout);
        city_LL = findViewById(R.id.city_LL);
        county_LL = findViewById(R.id.county_LL);
        payment_LL = findViewById(R.id.payment_LL);
        till_LL = findViewById(R.id.till_LL);
        kra_LL = findViewById(R.id.kra_LL);
        image = findViewById(R.id.image);
        access_Type = findViewById(R.id.access_Type);
        access_type_LL = findViewById(R.id.access_type_LL);
        edit_profile_back = findViewById(R.id.edit_profile_back);

        edit_profile_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
