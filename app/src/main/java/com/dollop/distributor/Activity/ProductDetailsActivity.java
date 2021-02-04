package com.dollop.distributor.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.ProductDetailsModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView vpimgs;
    ImageView prod_back;
    private TextView product_TV, product_type, tv_ProductActualPrice, tv_CutPrice_Show;
    private TextView tv_description, taxes, product_brand, packaging_type;
    private TextView unit_per_packging;
    private TextView packing_unit;
    private TextView weight_per_unit;
    private TextView total_weight, discount_tv;
    String sub_category_id, distributor_id, category_id;
    private String product_id;
    private TextView descriptions, stock_quantity_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sub_category_id = bundle.getString("sub_category_id");
            distributor_id = bundle.getString("distributor_id");
            category_id = bundle.getString("category_id");
            product_id = bundle.getString("product_id");
        }

        initializeView();
    }

    private void initializeView() {
        descriptions = findViewById(R.id.descriptions);
        vpimgs = findViewById(R.id.vproduct_details);
        prod_back = findViewById(R.id.prod_back);
        product_TV = findViewById(R.id.product_TV);
        product_type = findViewById(R.id.product_type);
        tv_ProductActualPrice = findViewById(R.id.tv_ProductActualPrice);
        tv_CutPrice_Show = findViewById(R.id.tv_CutPrice_Show);
        tv_description = findViewById(R.id.tv_description);
        product_brand = findViewById(R.id.product_brand);
        unit_per_packging = findViewById(R.id.unit_per_packging);
        packing_unit = findViewById(R.id.packing_unit);
        total_weight = findViewById(R.id.total_weight);
        taxes = findViewById(R.id.taxes);
        packaging_type = findViewById(R.id.packaging_type);
        weight_per_unit = findViewById(R.id.weight_per_unit);
        stock_quantity_tv = findViewById(R.id.stock_quantity_tv);
        discount_tv = findViewById(R.id.discount_tv);
        prod_back.setOnClickListener(this);
        GetProductDetails();
    }

    private void GetProductDetails() {
        final Dialog dialog = Utils.initProgressDialog(ProductDetailsActivity.this);
        RequestQueue queue = Volley.newRequestQueue(ProductDetailsActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.get_product, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Utils.E("get_product:-" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 200) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject object = data.getJSONObject(i);
                            ProductDetailsModel productDetailsModel = new ProductDetailsModel();
                            productDetailsModel.product_id = object.getString("product_id");
                            productDetailsModel.distributor_id = object.getString("distributor_id");
                            productDetailsModel.category_id = object.getString("category_id");
                            productDetailsModel.sub_category_id = object.getString("sub_category_id");
                            productDetailsModel.brand = object.getString("brand");
                            productDetailsModel.product_name = object.getString("product_name");
                            productDetailsModel.packing_id = object.getString("packing_id");
                            productDetailsModel.unit_per_packing_weight = object.getString("unit_per_packing_weight");
                            productDetailsModel.total_unit = object.getString("total_unit");
                            productDetailsModel.total_weight = object.getString("total_weight");
                            productDetailsModel.unit = object.getString("unit");
                            productDetailsModel.discount = object.getString("discount");
                            productDetailsModel.original_price = object.getString("original_price");
                            productDetailsModel.selling_price = object.getString("selling_price");
                            productDetailsModel.item_code = object.getString("item_code");
                            productDetailsModel.product_image = object.getString("product_image");
                            productDetailsModel.product_availability = object.getString("product_availability");
                            productDetailsModel.description = object.getString("description");
                            productDetailsModel.is_active = object.getString("is_active");
                            productDetailsModel.is_delete = object.getString("is_delete");
                            productDetailsModel.create_date = object.getString("create_date");
                            productDetailsModel.category_name = object.getString("category_name");
                            productDetailsModel.sub_category_name = object.getString("sub_category_name");
                            productDetailsModel.offer_id = object.getString("offer_id");
                            productDetailsModel.is_tax = object.getString("is_tax");
                            productDetailsModel.tax_id = object.getString("tax_id");
                            productDetailsModel.packing = object.getString("packing");
                            productDetailsModel.tax_name = object.getString("tax_name");
                            productDetailsModel.tax_percent = object.getString("tax_percent");
                            productDetailsModel.stock_quantity = object.getString("stock_quantity");
                            productDetailsModel.discount_percent = object.getString("discount_percent");

                            setData(productDetailsModel);
                        }


                    }else {
                        Utils.T(ProductDetailsActivity.this, jsonObject.getString("message"));
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
                        Utils.T(ProductDetailsActivity.this, errorMessage + "please check Internet connection");
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");
                        Utils.E("Error Status" + status);
                        Utils.E("Error Message" + message);
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
                HashMap<String, String> params = new HashMap<>();
                params.put("product_id", product_id);
                params.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
                params.put("type", "Distributor");

                Utils.E("get_product:-" + "sub_category:--" + params);
                return params;
            }


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    ProductDetailsModel productDetailsModel;

    private void setData(final ProductDetailsModel productDetailsModel) {
        this.productDetailsModel = productDetailsModel;
        // viewList.clear();
        // viewList.add(Const.URL.HOST_URL + productDetailsModel.product_image);
        Picasso.get().load(Const.URL.HOST_URL + productDetailsModel.product_image).error(R.drawable.no_img).into(vpimgs);

        taxes.setText("(" + "Incl. of " + productDetailsModel.tax_percent + "% " + productDetailsModel.tax_name + ")");

        product_TV.setText(productDetailsModel.brand);
        product_brand.setText(productDetailsModel.brand);

        packaging_type.setText(productDetailsModel.packing);
        product_type.setText(productDetailsModel.product_name);
        unit_per_packging.setText("Unit per " + productDetailsModel.packing);
        packing_unit.setText(productDetailsModel.total_unit);
        weight_per_unit.setText(productDetailsModel.unit_per_packing_weight + " " + productDetailsModel.unit);
        total_weight.setText(productDetailsModel.total_weight + " " + productDetailsModel.unit + " per " + productDetailsModel.packing);

        if (!productDetailsModel.selling_price.equals(productDetailsModel.original_price)) {
            tv_CutPrice_Show.setPaintFlags(tv_CutPrice_Show.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tv_CutPrice_Show.setText(getString(R.string.currency_sign) + " " + productDetailsModel.original_price + "/-");
        } else {
            tv_CutPrice_Show.setVisibility(View.INVISIBLE);
        }
        tv_ProductActualPrice.setText(getString(R.string.currency_sign) + " " + productDetailsModel.selling_price + "/-");
        tv_CutPrice_Show.setText(getString(R.string.currency_sign) + " " + productDetailsModel.original_price + "/-");
        //tv_productQuantity.setText(productDetailsModel.original_price);
        descriptions.setText(productDetailsModel.description);
        stock_quantity_tv.setText(productDetailsModel.stock_quantity);
        discount_tv.setText(productDetailsModel.discount_percent);
        // product_brand_name.setText(productDetailsModel.brand);



    }

    int QuantityCounter = 1;

    @Override
    public void onClick(View v) {
        if (v == prod_back) {
            onBackPressed();
        }

    }







}
