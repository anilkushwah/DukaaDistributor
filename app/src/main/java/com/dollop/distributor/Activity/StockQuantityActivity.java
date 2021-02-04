package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

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
import com.dollop.distributor.Fragment.ProductChildFragment;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.NetworkUtil;
import com.dollop.distributor.UtilityTools.SavedData;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.StockAdapterAdapter;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.AddProduct_model;
import com.dollop.distributor.model.CategoryModel;
import com.dollop.distributor.model.ProductModel;
import com.dollop.distributor.model.StockQuantity_model;
import com.dollop.distributor.model.SubCategoryModel;
import com.dollop.distributor.model.SubCategoty_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StockQuantityActivity extends AppCompatActivity {
    ImageView back_btn_image;
    RecyclerView rv_stock_item;
    ArrayList<StockQuantity_model> stockQuantityModels;
    StockAdapterAdapter stockAdapterAdapter;
    private ArrayList<CategoryModel> categotyNameList;
    private ArrayList<SubCategoryModel> SubcategotyNameList;
    Spinner sp_product_category, sp_product_sub_category;
    private String Sub_Cat_Id = "";
    String Cat_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_quantity);

        categotyNameList = new ArrayList<>();
        stockQuantityModels = new ArrayList<>();
        SubcategotyNameList = new ArrayList<>();

        rv_stock_item = findViewById(R.id.rv_stock_item);
        sp_product_sub_category = findViewById(R.id.sp_product_sub_category);
        sp_product_category = findViewById(R.id.sp_product_category);
        back_btn_image = findViewById(R.id.back_btn_image);
        rv_stock_item.setLayoutManager(new LinearLayoutManager(StockQuantityActivity.this));

        boolean status = NetworkUtil.getConnectivityStatus(StockQuantityActivity.this);
        if (status == true) {

        } else {
            Utils.T(StockQuantityActivity.this, "No Internet Connection available. Please try again");
        }
        back_btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getProduct(Cat_Id, Sub_Cat_Id);

        Categoty();

        sp_product_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CategoryModel selectedStatus = (CategoryModel) parent.getSelectedItem();
                Cat_Id = selectedStatus.getId();
                Sub_Cat_Id = "0";
                SubcategotyNameList.clear();
                sp_product_category.setPrompt("");
                SubCategoty(Cat_Id);
                getProduct(Cat_Id, Sub_Cat_Id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        sp_product_sub_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                SubCategoryModel selectedStatus = (SubCategoryModel) parent.getSelectedItem();
                Sub_Cat_Id = selectedStatus.getId();


                if (position > 0) {

                    // SubCategoty();
                }
                getProduct(Cat_Id, Sub_Cat_Id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }


    public void getProduct(final String category_id, final String sub_category_id) {

        //   final Dialog dialog = Utils.initProgressDialog(getActivity());
        RequestQueue queue = Volley.newRequestQueue(StockQuantityActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.get_product, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   dialog.dismiss();

                Utils.E("check product::" + response);
                //Log.e("distributer Category:-", "category:--" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {

                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        stockQuantityModels.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            StockQuantity_model productModel = new StockQuantity_model();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            productModel.product_name = jsonObject1.getString("product_name");
                            //   productModel.price_per_case = jsonObject1.getString("price_per_case");

                            productModel.Id = jsonObject1.getString("product_id");
                            productModel.product_image = jsonObject1.getString("product_image");
                            productModel.product_availability = jsonObject1.getString("product_availability");
                            productModel.original_price = jsonObject1.getString("original_price");
                            productModel.selling_price = jsonObject1.getString("selling_price");
                            productModel.description = jsonObject1.getString("product_description");
                            productModel.packing_id = jsonObject1.getString("packing_id");
                            productModel.unit_per_packing_weight = jsonObject1.getString("unit_per_packing_weight");
                            productModel.total_unit = jsonObject1.getString("total_unit");
                            productModel.total_weight = jsonObject1.getString("total_weight");
                            productModel.unit = jsonObject1.getString("unit");
                            productModel.discount = jsonObject1.getString("discount");
                            productModel.item_code = jsonObject1.getString("item_code");
                            // productModel.rating = jsonObject1.getString("rating");
                            productModel.sub_category_name = jsonObject1.getString("sub_category_name");
                            productModel.packing = jsonObject1.getString("packing");

                            productModel.category_name = jsonObject1.getString("category_name");
                            productModel.brand = jsonObject1.getString("brand");
                            productModel.is_tax = jsonObject1.getString("is_tax");
                            productModel.tax_id = jsonObject1.getString("tax_id");
                            productModel.offer_id = jsonObject1.getString("offer_id");
                            productModel.valid_to = jsonObject1.getString("valid_to");
                            productModel.valid_from = jsonObject1.getString("valid_from");
                            productModel.discount_amount = jsonObject1.getString("discount_amount");
                            productModel.discount_percent = jsonObject1.getString("discount_percent");
                            productModel.offer_title = jsonObject1.getString("title");
                            productModel.offer_image = jsonObject1.getString("image");
                            productModel.stock_quantity = jsonObject1.getString("stock_quantity");

                            stockQuantityModels.add(productModel);
                        }

                        rv_stock_item.setLayoutManager(new LinearLayoutManager(StockQuantityActivity.this));
                        stockAdapterAdapter = new StockAdapterAdapter(StockQuantityActivity.this, stockQuantityModels, Cat_Id, Sub_Cat_Id);
                        rv_stock_item.setAdapter(stockAdapterAdapter);
                        stockAdapterAdapter.notifyDataSetChanged();
                    } else {
                        //   Utils.T(getActivity(), jsonObject.getString("message"));
                    }


                } catch (JSONException e) {
                    // dialog.dismiss();

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //dialog.dismiss();

                NetworkResponse networkResponse = error.networkResponse;

                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                        Utils.T(StockQuantityActivity.this, errorMessage + "please check Internet connection");
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

                if (SavedData.get_AccessType().equals("Distributor")) {
                    stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());

                } else {
                    stringStringHashMap.put("distributor_id", SavedData.get_MainMemberId());

                }
                stringStringHashMap.put("type", "Distributor");
                stringStringHashMap.put("category_id", category_id);
                stringStringHashMap.put("sub_category_id", sub_category_id);
                Utils.E("checkProductChild::" + stringStringHashMap);

                return stringStringHashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }


    private void Categoty() {
        final Dialog dialog = Utils.initProgressDialog(StockQuantityActivity.this);
        RequestQueue queue = Volley.newRequestQueue(StockQuantityActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.category, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                Log.e("distributer Category:-", "category:--" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //  Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {

                        Utils.E("Category Response:-" + response);

                        JSONArray dataArray = jsonObject.getJSONArray("allCategory");
                        Utils.E("dataArray :-" + dataArray);
                        categotyNameList.clear();
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject dataobject = dataArray.getJSONObject(i);

                            AddProduct_model addProduct_model = new AddProduct_model();
                            CategoryModel model = new CategoryModel();

                            if (!dataobject.isNull("category_id")) {
                                addProduct_model.setId(dataobject.getString("category_id"));
                                model.setId(dataobject.getString("category_id"));
                            }

                            if (!dataobject.isNull("name")) {
                                addProduct_model.setName(dataobject.getString("name"));
                                model.setName(dataobject.getString("name"));

                            }

                            if (!dataobject.isNull("image")) {
                                addProduct_model.setImage(dataobject.getString("image"));
                            }

                            if (!dataobject.isNull("is_active")) {
                                addProduct_model.setIs_active(dataobject.getString("is_active"));
                            }
                            if (!dataobject.isNull("is_delete")) {
                                addProduct_model.setIs_delete(dataobject.getString("is_delete"));
                            }
                            if (!dataobject.isNull("create_date")) {
                                addProduct_model.setCreate_date(dataobject.getString("create_date"));
                            }

                            Utils.E("category_list" + categotyNameList);

                            categotyNameList.add(model);

                        }

                        ArrayAdapter<CategoryModel> arrayAdapter = new ArrayAdapter<CategoryModel>(
                                StockQuantityActivity.this, R.layout.simple_spinner_dropdown_item, categotyNameList);
                        sp_product_category.setAdapter(arrayAdapter);

                    } else {
                        //  Utils.T(getActivity(), jsonObject.getString("message"));
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
                        Utils.T(StockQuantityActivity.this, errorMessage + "please check Internet connection");
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
                if (SavedData.get_AccessType().equals("Distributor")) {
                    stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());

                } else {
                    stringStringHashMap.put("distributor_id", SavedData.get_MainMemberId());

                }

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

    private void SubCategoty(String cat_Id) {
        SubcategotyNameList.clear();
        //final Dialog dialog = Utils.initProgressDialog(getActivity());
        RequestQueue queue = Volley.newRequestQueue(StockQuantityActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.subcategory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // dialog.dismiss();

                Utils.E("distributer SubCategory:-" + "Subcategory:--" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    // Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {

                        Utils.E("SubCategory Response:-" + response);

                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        Utils.E("dataArray :-" + dataArray);
                        SubcategotyNameList.clear();

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject dataobject = dataArray.getJSONObject(i);

                            SubCategoty_model SubCategoty_model = new SubCategoty_model();
                            SubCategoryModel model = new SubCategoryModel();

                            if (!dataobject.isNull("sub_category_id")) {
                                SubCategoty_model.setId(dataobject.getString("sub_category_id"));
                                model.setId(dataobject.getString("sub_category_id"));
                            }

                            if (!dataobject.isNull("name")) {
                                SubCategoty_model.setName(dataobject.getString("name"));
                                model.setName(dataobject.getString("name"));
                            }

                            if (!dataobject.isNull("category_id")) {
                                SubCategoty_model.setCategory_id(dataobject.getString("category_id"));
                            }

                            if (!dataobject.isNull("is_active")) {
                                SubCategoty_model.setIs_active(dataobject.getString("is_active"));
                            }
                            if (!dataobject.isNull("is_delete")) {
                                SubCategoty_model.setIs_delete(dataobject.getString("is_delete"));
                            }
                            if (!dataobject.isNull("create_date")) {
                                SubCategoty_model.setCreate_date(dataobject.getString("create_date"));
                            }

                            SubcategotyNameList.add(model);
                            Utils.E("Subcategory_list" + SubcategotyNameList);

                        }
                        if (SubcategotyNameList.size() > 0) {
                            ArrayAdapter<SubCategoryModel> subcategotyAdapter =
                                    new ArrayAdapter<SubCategoryModel>(StockQuantityActivity.this,
                                            R.layout.simple_spinner_dropdown_item, SubcategotyNameList);

                            sp_product_sub_category.setAdapter(subcategotyAdapter);
                        }


                    }else {
                        SubcategotyNameList.clear();
                        SubCategoryModel model = new SubCategoryModel();
                        model.setId("0");
                        model.setName("No Sub Category");
                        SubcategotyNameList.add(model);
                        ArrayAdapter<SubCategoryModel> subcategotyAdapter = new ArrayAdapter<SubCategoryModel>(
                                StockQuantityActivity.this, R.layout.simple_spinner_dropdown_item, SubcategotyNameList);

                        sp_product_sub_category.setAdapter(subcategotyAdapter);

                    }

                } catch (JSONException e) {
                    // dialog.dismiss();

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  dialog.dismiss();

                NetworkResponse networkResponse = error.networkResponse;

                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                        Utils.T(StockQuantityActivity.this, errorMessage + "please check Internet connection");
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
                HashMap<String, String> subcategoryParam = new HashMap<>();
                subcategoryParam.put("category_id", Cat_Id);
                subcategoryParam.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
                Log.e("SubCategoryParameter::", "SubCategory:--" + subcategoryParam);
                return subcategoryParam;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

}
