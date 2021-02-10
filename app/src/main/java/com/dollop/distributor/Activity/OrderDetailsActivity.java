package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import com.dollop.distributor.UtilityTools.Utility;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.UtilityTools.multipart.AppHelper;
import com.dollop.distributor.UtilityTools.multipart.VolleyMultipartRequest;
import com.dollop.distributor.adapter.AllItemAdapter;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.firebase.Config;
import com.dollop.distributor.model.Datum;
import com.dollop.distributor.model.OrderModel;
import com.dollop.distributor.model.ShoworderModel;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;


import com.simplify.ink.InkView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class OrderDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout ll_assign, ll_cancel, ll_order_set, tvDispatchLL, detail_LL;
    TextView tracking, cancel, order_asssign, orderid, past_order_Tv;
    Button btn_traking;
    ImageView order_back;


    RecyclerView All_Complains_recyclerviewId;
    List<ShoworderModel> showModelList = new ArrayList<>();
    private TextView tv_retailer_name;
    private TextView tvRetailerContactNoId;
    // private TextView tvorderDateId;
    private ImageView final_image;
    ImageView ivRetailerImageId;
    private TextView tvTotalAmountId;
    private TextView tvPaymentTypeId;
    private TextView tvOrderTypeId;
    private TextView tvAddressId;
    private TextView tvTotalWeightId;
    private TextView tvSubTotalId;
    private TextView driver_name_tv;
    private TextView tvDelivaryChargeId, sign_clear;
    private TextView tvTDiscountId, driver_genral_id;
    private Datum newOrderModel;
    private TextView tvTotalBelowAmountId;
    private RecyclerView rvAllItemId;
    private OrderDetailsActivity activity;

    boolean assignOrder = false;
    String type = "";
    Context mContext;
    InkView ink;
    boolean imageValue = false;
    private TextView tvMpesaReceiptNumberId;
    private TextView tvTransactionDateId;
    private TextView tvTransactionId;
    private TextView tv_address;
    private TextView tvPaymentStatusId;
    private TextView delivery_time;
    private TextView tvDelivaryTypeId;
    private TextView tv_transaction_id;
    private ImageView ivDeliveryTypeId;
    private TextView tvDiscountId;
    private TextView driver_id;
    private TextView driver_no_tv;
    private TextView Registration_no_tv;
    private String MpesaReceiptNumber = "";
    private ScrollView scrollViewId;
    LinearLayout ll_driver_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        sign_clear = findViewById(R.id.sign_clear);
        ll_driver_data = findViewById(R.id.ll_driver_data);
        driver_no_tv = findViewById(R.id.driver_no_tv);

        initializationView();




        ink = findViewById(R.id.ink);
        ink.setFlags(InkView.FLAG_INTERPOLATION | InkView.FLAG_RESPONSIVE_WIDTH);
        ink.setColor(getResources().getColor(android.R.color.black));
        ink.setMinStrokeWidth(1.5f);
        ink.setMaxStrokeWidth(6f);
        scrollViewId = findViewById(R.id.scrollViewId);
        ink.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disable the scroll view to intercept the touch event
                        scrollViewId.requestDisallowInterceptTouchEvent(true);
                        return false;
                    case MotionEvent.ACTION_UP:
                        // Allow scroll View to interceot the touch event
                        scrollViewId.requestDisallowInterceptTouchEvent(false);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        scrollViewId.requestDisallowInterceptTouchEvent(true);
                        return false;
                    default:
                        return true;
                }
            }
        });

        sign_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ink.clear();
            }
        });


    }


    private void dispatchMethod(final Bitmap bitmap) {
        final Dialog dialog = Utils.initProgressDialog(this);
        RequestQueue queue = Volley.newRequestQueue(this);
        VolleyMultipartRequest stringRequest = new VolleyMultipartRequest(Request.Method.POST, Const.URL.order_dispatch, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                dialog.dismiss();
                String sData = new String(response.data);
                Utils.E("dispatchMethod:Response:" + sData);
                try {
                    //  String sData = new String(response.data);
                    JSONObject jsonObject = new JSONObject(sData);

                    // Toast.makeText(activity, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {
                        setResult(103);
                        finish();

                        // Utils.I_clear(ChangePasswordActivity.this, HomeActivity.class,null);
                    } else {

                        Utils.T(OrderDetailsActivity.this, "Please wait driver should come pickup first");
                    }

                } catch (JSONException e) {
                    dialog.dismiss();
                    Utils.E("dispatchMethod:Exception:" + sData);
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
            protected Map<String, DataPart> getByteData() throws AuthFailureError, IOException {
                String timeName = "" + new Date().getTime();
                HashMap<String, DataPart> stringDataPartHashMap = new HashMap<>();
                stringDataPartHashMap.put("driver_signature_img", new DataPart(timeName + ".jpeg", AppHelper.getFileDataBitmap(bitmap), "image/png"));
                Utils.E("dispatchMethod:getByteData:" + stringDataPartHashMap);
                return stringDataPartHashMap;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
                stringStringHashMap.put("order_id", newOrderModel.id);
                Utils.E("dispatchMethod:getParams:" + stringStringHashMap);
                return stringStringHashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }


    @Override
    public void onClick(View v) {

        if (v == order_asssign) {

            if (order_asssign.getText().toString().equals("Assign")) {

                asignpopup();

            } else if (order_asssign.getText().toString().equals("Dispatch")) {
                //   SignaturePopup();

                Bitmap bitmap = ink.getBitmap(getResources().getColor(R.color.white));
                ;
                final_image.setImageBitmap(bitmap);
                Utils.E("ink.isViewEmpty()::" + ink.isViewEmpty());
                if (ink.getBitmap() != null && !ink.isViewEmpty()) {
                    dispatchMethod(bitmap);
                } else {
                    Utils.T(OrderDetailsActivity.this, "Add Driver Signature");
                }


            } else if (order_asssign.getText().toString().equals("Track")) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("orderDetail", newOrderModel);
                Utils.I(activity, MapsActivity.class, bundle);
                finish();
            }

        } else if (v == btn_traking) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("orderDetail", newOrderModel);
            Utils.I(activity, MapsActivity.class, bundle);
            finish();
        } else if (v == cancel) {
            cancelPopup();
        } else if (v == order_back) {
            finish();
        } else if (v == past_order_Tv) {
            startActivity(new Intent(OrderDetailsActivity.this, OrderHistory.class));
        }
    }

    private void assignOrder() {
        final Dialog dialog = Utils.initProgressDialog(OrderDetailsActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.assign_order, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Utils.E("getNewOrder::" + response);
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 200) {

                     /*   final Dialog dialog = new Dialog(OrderDetailsActivity.this, R.style.dialogstyle);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.progress_dialog_for_accept_request);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setCancelable(false);
                        dialog.show();

                        final Handler handler = new Handler();
                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                if (!((Activity) mContext).isFinishing()) {
                                    getOrderDetail(dialog);
                                }


                            }
                        };

                          handler.postDelayed(runnable, 30000);*/

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("orderDetail", newOrderModel);
                        Utils.I(activity, MapsActivity.class, bundle);


                    } else {
                        Utils.T(OrderDetailsActivity.this, jsonObject.getString("message"));
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
                    Utils.T(OrderDetailsActivity.this, errorMessage + " No Internet Connection available. Please try again");
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

                stringStringHashMap.put("order_id", newOrderModel.id);
                stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
                Utils.E("GetNewOrder:params:" + stringStringHashMap);
                return stringStringHashMap;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(OrderDetailsActivity.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

    }


    private void asignpopup() {
        assignOrder = false;
        assignOrder();


    }

    private void getOrderDetail(final Dialog dialogMain) {
        final Dialog dialog = Utils.initProgressDialog(OrderDetailsActivity.this);


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
        stringStringHashMap.put("order_id", newOrderModel.id);

        Call<OrderModel> call = apiService.getAllOrder(stringStringHashMap);
        call.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, retrofit2.Response<OrderModel> response) {
                dialog.dismiss();
                try {
                    OrderModel body = response.body();

                    if (body.status == 200) {
                        ArrayList<Datum> datumArrayList = body.data;
                        for (int i = 0; i < datumArrayList.size(); i++) {
                            newOrderModel = datumArrayList.get(i);
                            Utils.E("UtilsData ArrayList::" + newOrderModel.driverId);
                            if (newOrderModel.driverId != null) {
                                dialogMain.dismiss();
                                Utils.T(OrderDetailsActivity.this, "Order Assign to driver");
                                setResult(RESULT_OK);

                                finish();
                            } else {
                                Utils.T(OrderDetailsActivity.this, "Order Reassign to driver");
                                dialogMain.dismiss();
                            }


                        }


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                dialog.dismiss();

            }
        });

    }

    private void cancelPopup() {

        final Dialog myDialog = new Dialog(OrderDetailsActivity.this);
        myDialog.requestWindowFeature(Window.FEATURE_RIGHT_ICON);
        //myDialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
        myDialog.setContentView(R.layout.cancel_order_popup);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(myDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.getWindow().setAttributes(lp);
        myDialog.show();

        TextView tv_submit = myDialog.findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Utils.E("Order Details:Order Details");

            String message = intent.getStringExtra("action");
            Utils.E("Order Details:Order Details");
            if (message.equals("order_accepted")) {

                if (!((Activity) mContext).isFinishing()) {

                    OrderDetailsActivity.this.setResult(RESULT_OK);
                    finish();
                }


            } else if (message.equals("order_packed")) {

            } else if (message.equals("order_dispatch")) {


            } else if (message.equals("order_deliverd")) {

            }
            // Accept = 1, packed = 2, dispatch = 3, picked = 4, Delivered = 5, 6 = canceled

            Utils.E("message:Order Details" + message);

        }
    };
TextView tvDriverSignatureId;

    private void initializationView() {

        mContext = OrderDetailsActivity.this;

        LocalBroadcastManager.getInstance(OrderDetailsActivity.this).registerReceiver(mMessageReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        newOrderModel = (Datum) getIntent().getSerializableExtra("orderData");


        type = getIntent().getStringExtra("type");
        activity = OrderDetailsActivity.this;


        Utils.E("checkOrderDetail:delivery:" + newOrderModel.deliveryType);
        Utils.E("checkOrderDetail:orderStatus:" + newOrderModel.orderStatus);
        Utils.E("driverid" + newOrderModel.driverId);
        order_asssign = findViewById(R.id.order_asssign);


        All_Complains_recyclerviewId = findViewById(R.id.All_Complains_recyclerviewId);
        driver_id = findViewById(R.id.driver_id);
        driver_name_tv = findViewById(R.id.driver_name_tv);
        order_back = findViewById(R.id.order_back);
        tv_retailer_name = findViewById(R.id.tv_retailer_name);
        tvRetailerContactNoId = findViewById(R.id.tv_retailer_mobile);

        tvDelivaryTypeId = findViewById(R.id.tvDelivaryTypeId);
        tv_address = findViewById(R.id.tv_address);
        Registration_no_tv = findViewById(R.id.Registration_no_tv);


        delivery_time = findViewById(R.id.delivery_time);
        tvDiscountId = findViewById(R.id.tvDiscountId);
        tvTotalAmountId = findViewById(R.id.tvTotalAmountId);
        tvPaymentTypeId = findViewById(R.id.tv_paymentMode);
        tvOrderTypeId = findViewById(R.id.tvDelivaryTypeId);
        tv_transaction_id = findViewById(R.id.tv_transaction_id);
        tvAddressId = findViewById(R.id.tvAddressId);
        tvTotalWeightId = findViewById(R.id.tvTotalWeightId);
        tvDelivaryChargeId = findViewById(R.id.tvDelivaryChargeId);
        tvSubTotalId = findViewById(R.id.tvSubTotalId);
        tvTDiscountId = findViewById(R.id.tvTDiscountId);
        tvTotalBelowAmountId = findViewById(R.id.tvTotalBelowAmountId);
        tvPaymentStatusId = findViewById(R.id.tvPaymentStatusId);
        rvAllItemId = findViewById(R.id.rvAllItemId);
        orderid = findViewById(R.id.orderid);
        past_order_Tv = findViewById(R.id.past_order_Tv);
        //  payment_status_tv = findViewById(R.id.tvPaymentStatusId);
        tvDispatchLL = findViewById(R.id.tvDispatchLL);
        detail_LL = findViewById(R.id.detail_LL);
        driver_genral_id = findViewById(R.id.driver_id);
        final_image = findViewById(R.id.final_image);

        ivRetailerImageId = findViewById(R.id.iv_reatailer_image);
        //tvorderDateId = findViewById(R.id.tvorderDateId);
        ll_order_set = findViewById(R.id.ll_order_set);
        ll_cancel = findViewById(R.id.ll_cancel);
        ll_assign = findViewById(R.id.ll_assign);
        tvDriverSignatureId = findViewById(R.id.tvDriverSignatureId);

        // order_dispatch = findViewById(R.id.order_dispatch);

        //  order_accespt = findViewById(R.id.order_accept);
        btn_traking = findViewById(R.id.btn_traking);
        cancel = findViewById(R.id.tv_orcancel);
        ivDeliveryTypeId = findViewById(R.id.ivDeliveryTypeId);
        tv_retailer_name.setText(newOrderModel.shopName);
        tv_address.setVisibility(View.GONE);
        tvRetailerContactNoId.setText("No.- " + newOrderModel.retailerContact);
        delivery_time.setText(Utility.strToDate(newOrderModel.createDate));
        Utils.E("newOrderModel.driverId::" + newOrderModel.driverId);
        Utils.E(String.valueOf(newOrderModel.driverId == null));
        if (newOrderModel.driverId != null) {
            ll_driver_data.setVisibility(View.VISIBLE);
            driver_no_tv.setVisibility(View.GONE);
            driver_id.setText("#" + newOrderModel.national_id);
            driver_name_tv.setText(newOrderModel.driver_name);
            Registration_no_tv.setText(newOrderModel.vehicle_registrion_number);
        } else {
            ll_driver_data.setVisibility(View.GONE);
            driver_no_tv.setVisibility(View.VISIBLE);

        }


        tvTotalAmountId.setText("Total Amount: KSh " + newOrderModel.paidAmount);

        tvPaymentTypeId.setText(newOrderModel.transactionMode);
        tvOrderTypeId.setText(newOrderModel.deliveryType);
        tvAddressId.setText(newOrderModel.retailerAddress);
        tvTotalWeightId.setText(newOrderModel.totalWeight + " " + newOrderModel.weightUnit);
        tvDelivaryChargeId.setText(getString(R.string.currency_sign) + " " + newOrderModel.deliveryCharge);
        tvSubTotalId.setText(getString(R.string.currency_sign) + " " + newOrderModel.paidAmount);
        tvTotalBelowAmountId.setText(getString(R.string.currency_sign) + " " + newOrderModel.productDiscountedPrice);
        tvDiscountId.setText(getString(R.string.currency_sign) + " " + newOrderModel.couponDiscountAmount);
        orderid.setText("Order ID - #000" + newOrderModel.id);
        if (newOrderModel.deliveryType.equals("Delivery")) {
            ivDeliveryTypeId.setImageResource(R.drawable.ic_shopping_cart_one);
        } else {
            ivDeliveryTypeId.setImageResource(R.drawable.ic_hosting);
        }
        try {
            JSONObject jsonObject = new JSONObject(newOrderModel.tansactionResponse);
            MpesaReceiptNumber = jsonObject.getString("MpesaReceiptNumber");
            tv_transaction_id.setText("" + MpesaReceiptNumber);
        } catch (Exception e) {

        }
        tvDelivaryTypeId.setText("" + newOrderModel.deliveryType);


        if (newOrderModel.retailerImage != null) {
            Picasso.get().load(Const.URL.HOST_URL + newOrderModel.retailerImage).error(R.drawable.ic_user)
                    .into(ivRetailerImageId);
        }


        //payment_status_tv.setText(newOrderModel);


        order_back.setOnClickListener(this);
        cancel.setOnClickListener(this);
        // order_accespt.setOnClickListener(this);
        order_asssign.setOnClickListener(this);
        btn_traking.setOnClickListener(this);
        past_order_Tv.setOnClickListener(this);


        if (newOrderModel.orderStatus.equals("1")) {
            order_asssign.setText("Assign");

            detail_LL.setVisibility(View.VISIBLE);
            if (newOrderModel.deliveryType.equals("Self PickUp")) {
                order_asssign.setText("Dispatch");

                tvDispatchLL.setVisibility(View.VISIBLE);
                detail_LL.setVisibility(View.VISIBLE);

                tvDriverSignatureId.setText("Retailor Signature Here");
                driver_no_tv.setText(newOrderModel.retailerName+" , "+newOrderModel.shopName);

            }

        } else if (newOrderModel.orderStatus.equals("2")) {
            order_asssign.setText("Dispatch");

            tvDispatchLL.setVisibility(View.VISIBLE);
            detail_LL.setVisibility(View.VISIBLE);

        } else if (newOrderModel.orderStatus.equals("4")) {
            order_asssign.setText("Dispatch");

            tvDispatchLL.setVisibility(View.VISIBLE);
            detail_LL.setVisibility(View.VISIBLE);

        } else if (newOrderModel.orderStatus.equals("3")) {

            order_asssign.setText("Track");
        } else if (newOrderModel.orderStatus.equals("5")) {
            order_asssign.setVisibility(View.GONE);
            tvDispatchLL.setVisibility(View.GONE);

        }


        rvAllItemId.setLayoutManager(new LinearLayoutManager(this));
        AllItemAdapter allItemAdapter = new AllItemAdapter(OrderDetailsActivity.this, newOrderModel.products);
        rvAllItemId.setAdapter(allItemAdapter);
        for (int i = 0; i < newOrderModel.products.size(); i++) {


        }


        boolean status = NetworkUtil.getConnectivityStatus(OrderDetailsActivity.this);
        if (status == true) {

        } else {
            Utils.T(OrderDetailsActivity.this, "No Internet Connection available. Please try again");
        }
        if (type.equals("earning")) {
            ll_order_set.setVisibility(View.GONE);
            past_order_Tv.setVisibility(View.GONE);
        }

    }


}
