package com.dollop.distributor.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.NewOrderAdapter;
import com.dollop.distributor.adapter.ShowOrderAdapter;
import com.dollop.distributor.database.UserModel;
import com.dollop.distributor.model.AllOrderDTO;
import com.dollop.distributor.model.NewOderlist;
import com.dollop.distributor.model.ShoworderModel;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class NewOrderActivity extends AppCompatActivity implements View.OnClickListener {

    Activity activity = NewOrderActivity.this;
    RecyclerView All_Complains_recyclerviewId;
    List<ShoworderModel> showModelList = new ArrayList<>();
    ImageView order_back, iv_reatailer_image;
    TextView pastorder, tv_retailer_name, tv_retailer_mobile, tv_address, tv_company_name, tv_mode_amount, tv_transaction_id;
    TextView order_accept, order_decline, tv_items_and_amount, tv_total_amount,
            tv_total_final_amount, tv_service_charges, tv_delivery_charges, tv_order_id, tv_paymentMode;
    String order_status;
    LinearLayout ll_set_order, ll_order_complete;
    private String Order_id;
    ArrayList<AllOrderDTO> mAllOrderDTOArrayList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        //getWindow().setStatusBarColor(getResources().getColor(R.color.white, this.getTheme()));

        ll_set_order = findViewById(R.id.ll_set_order);
        ll_order_complete = findViewById(R.id.ll_order_complete);
        order_decline = findViewById(R.id.order_decline);
        pastorder = findViewById(R.id.tv_pastorder);
        order_accept = findViewById(R.id.order_accept);
        order_back = findViewById(R.id.order_back);
        tv_retailer_name = findViewById(R.id.tv_retailer_name);
        tv_retailer_mobile = findViewById(R.id.tv_retailer_mobile);
        tv_address = findViewById(R.id.tv_address);
        tv_company_name = findViewById(R.id.tv_company_name);
        tv_mode_amount = findViewById(R.id.tv_mode_amount);
        tv_transaction_id = findViewById(R.id.tv_transaction_id);
        tv_items_and_amount = findViewById(R.id.tv_items_and_amount);
        tv_total_amount = findViewById(R.id.tv_total_amount);
        All_Complains_recyclerviewId = findViewById(R.id.All_Complains_recyclerviewId);
        tv_total_final_amount = findViewById(R.id.tv_total_final_amount);
        tv_service_charges = findViewById(R.id.tv_service_charges);
        tv_delivery_charges = findViewById(R.id.tv_delivery_charges);
        tv_order_id = findViewById(R.id.tv_order_id);
        iv_reatailer_image = findViewById(R.id.iv_reatailer_image);
        tv_paymentMode = findViewById(R.id.tv_paymentMode);

      //  order_status = (String) getIntent().getExtras().getString("order_status");
      //  Order_id = (String) getIntent().getExtras().getString("Order_id");

        order_status = "complete";
        Order_id = "1";

        if (order_status.equals("complete")) {
            ll_set_order.setVisibility(View.GONE);
            ll_order_complete.setVisibility(View.VISIBLE);
        }
        if (order_status.equals("canceled")) {
            ll_set_order.setVisibility(View.GONE);
            ll_order_complete.setVisibility(View.GONE);
        }
        ll_order_complete.setOnClickListener(this);
        order_back.setOnClickListener(this);
        order_decline.setOnClickListener(this);
        order_accept.setOnClickListener(this);
        pastorder.setOnClickListener(this);
        GetOrderDataById();
    }

    private void GetOrderDataById() {
        final Dialog dialog = Utils.initProgressDialog(activity);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.view_orders, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Utils.E("GetOrderDataById::" + response);
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 200) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONObject data = jsonArray.getJSONObject(0);
                        tv_retailer_name.setText(data.getString("retailer_name"));
                        tv_retailer_mobile.setText("+254-" + data.getString("retailer_mobile"));
                        tv_address.setText(data.getString("address"));
                        // tv_company_name.setText(data.getString("address"));
                        if (data.getString("transaction_mode").equals("cash")) {
                            tv_mode_amount.setText("UnPaid");
                            tv_paymentMode.setText(data.getString("transaction_mode"));
                            tv_transaction_id.setText("");

                        } else {
                            tv_mode_amount.setText("Paid");
                            tv_paymentMode.setText(data.getString("transaction_mode"));
                            tv_transaction_id.setText("#" + data.getString("transaction_id"));
                        }

                        tv_items_and_amount.setText(data.getString("itemCount"));
                        tv_total_amount.setText(data.getString("product_discounted_price"));
                        tv_total_final_amount.setText(data.getString("product_discounted_price"));
                        tv_service_charges.setText(data.getString("service_charge"));
                        tv_delivery_charges.setText(data.getString("delivery_charge"));
                        tv_order_id.setText(data.getString("gen_order_id"));
                        Picasso.get().load(Const.URL.HOST_URL + data.getString("retailer_image")).error(R.drawable.ic_user).into(iv_reatailer_image);
                        JSONArray item = data.getJSONArray("item");
                        for (int i = 0; i < item.length(); i++) {
                            JSONObject jsonObject2 = item.getJSONObject(i);
                            ShoworderModel modal = new ShoworderModel();
                            modal.id = jsonObject2.getString("id");
                            modal.retailer_id = jsonObject2.getString("retailer_id");
                            modal.order_id = jsonObject2.getString("order_id");
                            modal.product_id = jsonObject2.getString("product_id");
                            modal.product_qty = jsonObject2.getString("product_qty");
                            modal.product_amount = jsonObject2.getString("product_amount");
                            modal.total_amount = jsonObject2.getString("total_amount");
                            modal.discount_amount = jsonObject2.getString("discount_amount");
                            modal.product_discounted_price = jsonObject2.getString("product_discounted_price");
                            modal.create_date = jsonObject2.getString("create_date");
                            modal.product_name = jsonObject2.getString("product_name");
                            modal.pack_size = jsonObject2.getString("pack_size");
                            modal.product_image = jsonObject2.getString("product_image");
                            showModelList.add(modal);
                        }

                        All_Complains_recyclerviewId.setLayoutManager(new LinearLayoutManager(NewOrderActivity.this, RecyclerView.VERTICAL, false));
                        ShowOrderAdapter showAdapter = new ShowOrderAdapter(NewOrderActivity.this, showModelList);
                        All_Complains_recyclerviewId.setAdapter(showAdapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                if (error.getClass().equals(TimeoutError.class)) {
                    String errorMessage = "Request timeout";
                    Utils.T(activity, errorMessage + " No Internet Connection available. Please try again");
                }


            }
        }) {
            @Override
            public String getCacheKey() {
                return super.getCacheKey();
            }


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("order_id", Order_id);
                Utils.E("GetOrderDataById:params:" + stringStringHashMap);
                return stringStringHashMap;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(activity);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);


    }

    @Override
    public void onClick(View v) {
        if (v == ll_order_complete) {
            finish();
        } else if (v == order_back) {
            finish();
        } else if (v == order_decline) {
            finish();
        } else if (v == order_accept) {
            Intent intent = new Intent(NewOrderActivity.this, StatusordershowActivity.class);
            startActivity(intent);
        } else if (v == pastorder) {
            Intent intent = new Intent(NewOrderActivity.this, DeliveryCompletedActivity.class);
            startActivity(intent);
        }
    }

}
