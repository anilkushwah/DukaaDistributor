package com.dollop.distributor.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.SavedData;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.PastOrderListAdapter;
import com.dollop.distributor.model.NewCreditReq_Model;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CutomerDetailsAcceptActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout ll_pastorder, show_past_order, order_history_LL;
    RecyclerView rv_pastorder;
    PastOrderListAdapter pastOrderListAdapter;

    TextView CD_accept, CD_decline, order_history_TV;
    ImageView credit_setback, retailer_image;

    TextView cd_name, cd_storename, cd_acc, cd_address, cd_dis, myamount;
    NewCreditReq_Model newCreditReq_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutomer_details_accept);


        initializationView();




    }

    private void initializationView() {
        myamount = findViewById(R.id.myamount);
        cd_name = findViewById(R.id.cd_name);
        cd_storename = findViewById(R.id.cd_storename);
        cd_acc = findViewById(R.id.cd_acc);
        cd_address = findViewById(R.id.cd_address);
        cd_dis = findViewById(R.id.cd_dis);
        order_history_LL = findViewById(R.id.order_history_LL);

        credit_setback = findViewById(R.id.credit_setback);
        CD_accept = findViewById(R.id.CD_accept);
        CD_decline = findViewById(R.id.CD_decline);
        ll_pastorder = findViewById(R.id.ll_pastorder);
        show_past_order = findViewById(R.id.show_past_order);
        rv_pastorder = findViewById(R.id.rv_pastorder);
        retailer_image = findViewById(R.id.retailer_image);
        order_history_TV = findViewById(R.id.order_history_TV);


        CD_accept.setOnClickListener(this);
        ll_pastorder.setOnClickListener(this);
        CD_decline.setOnClickListener(this);
        credit_setback.setOnClickListener(this);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {


            newCreditReq_model = (NewCreditReq_Model) getIntent().getSerializableExtra("modelData");

            cd_name.setText(newCreditReq_model.getRetailerName());
            cd_storename.setText(newCreditReq_model.getRetailerShopName());
            cd_acc.setText(newCreditReq_model.getAmount());
            cd_dis.setText(newCreditReq_model.getDescription());
            cd_address.setText(""+newCreditReq_model.getRetailerAddress());
            myamount.setText("$" + newCreditReq_model.getAmount());

            if (newCreditReq_model.getRetailerImage() != null) {
                Picasso.get().load(Const.URL.HOST_URL + newCreditReq_model.getRetailerImage()).into(retailer_image);

            }

            rv_pastorder.setLayoutManager(new LinearLayoutManager(CutomerDetailsAcceptActivity.this));
            pastOrderListAdapter = new PastOrderListAdapter(CutomerDetailsAcceptActivity.this, newCreditReq_model.getOrders());
            rv_pastorder.setAdapter(pastOrderListAdapter);
            pastOrderListAdapter.notifyDataSetChanged();

            if (newCreditReq_model.getOrders()!=null){
                order_history_TV.setText("No Order History");
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v == ll_pastorder) {
            show_past_order.setVisibility(View.VISIBLE);
        }
        if (v == credit_setback) {
            finish();
        } else if (v == CD_accept) {

            reqAccept("Approved");

        }
    }


    private void reqAccept(final String status) {
        final Dialog dialog = Utils.initProgressDialog(CutomerDetailsAcceptActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.update_credit_request, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Utils.E("getNewOrder::" + response);
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getInt("status") == 200) {

                     /*   Bundle bundle    = new Bundle();
                        bundle.putString("status","1");
                        //  Intent intent = new Intent(CutomerDetailsActivity.this,AllCreditRequestActivity.class);
                        //  startActivityForResult(intent, 1);// Activity is started with requestCode 2
                        Utils.I(CutomerDetailsActivity.this,AllCreditRequestActivity.class,bundle);*/
                        finish();
                    }else {
                        Utils.T(CutomerDetailsAcceptActivity.this, jsonObject.getString("message"));
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
                    Utils.T(CutomerDetailsAcceptActivity.this, errorMessage + " No Internet Connection available. Please try again");
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

                stringStringHashMap.put("distributor_id", SavedData.get_Distributor_id());
                stringStringHashMap.put("request_id", newCreditReq_model.getRetailerId());
                stringStringHashMap.put("status", status);
                Utils.E("GetNewcredit:params:" + stringStringHashMap);
                return stringStringHashMap;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(CutomerDetailsAcceptActivity.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);


    }
}