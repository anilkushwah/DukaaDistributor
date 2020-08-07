package com.dollop.distributor.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.dollop.distributor.UtilityTools.UserAccount;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.model.AddProduct_model;
import com.dollop.distributor.model.SubCategoty_model;
import com.dollop.distributor.model.breakfastdaModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BreakfastDaFragment extends Fragment {

    public BreakfastDaFragment() {
        // Required empty public constructor
    }


    RecyclerView rv_breakfast;
    ArrayList<breakfastdaModel> breakfastdaModels = new ArrayList<>();

    BreakfastdaAdapter  breakfastdaAdapter;
    FrameLayout emp_addbtn;

    EditText add_product_name,sku_code,item_code,price_per_case,inc_vat,retail_price,pro_discription;
    Spinner sp_categoty,sp_sub_category,sp_unit_per_case,sp_total_unit,sp_packsize;
    Button btn_add_product;

    ArrayList<String> categotyNameList = new ArrayList<>();
    ArrayList<String> SubcategotyNameList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
             View root = inflater.inflate(R.layout.fragment_breakfastda, container, false);
        rv_breakfast = root.findViewById(R.id.rv_breakfastda);
        emp_addbtn = root.findViewById(R.id.emp_addbtn);

        breakfastshowDataList();

        Categoty();


        categotyNameList.add("select  category");
        SubcategotyNameList.add("select sub category");

        emp_addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog popdialog = new Dialog(getContext()) ;
                popdialog.requestWindowFeature(Window.FEATURE_RIGHT_ICON);
                popdialog.setContentView(R.layout.add_itempopup);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(popdialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                popdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popdialog.getWindow().setAttributes(lp);
                // dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id

                ImageView additemClose = popdialog.findViewById(R.id.add_item_close);
                Button btn_add_product = popdialog.findViewById(R.id.btn_add_product);
                sp_categoty = popdialog.findViewById(R.id.sp_product_category);
                sp_sub_category= popdialog.findViewById(R.id.sp_product_sub_category);
                sp_unit_per_case= popdialog.findViewById(R.id.sp_unit_per_case);
                sp_total_unit= popdialog.findViewById(R.id.sp_total_unit);
                sp_packsize= popdialog.findViewById(R.id.sp_packsize);

                add_product_name= popdialog.findViewById(R.id.add_product_name);
                sku_code= popdialog.findViewById(R.id.sku_code);
                item_code= popdialog.findViewById(R.id.item_code);
                price_per_case= popdialog.findViewById(R.id.price_per_Case);
                inc_vat= popdialog.findViewById(R.id.incl_vat);
                retail_price= popdialog.findViewById(R.id.retail_price);
                pro_discription= popdialog.findViewById(R.id.product_discription);
                btn_add_product= popdialog.findViewById(R.id.btn_add_product);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, categotyNameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_categoty.setAdapter(arrayAdapter);
                sp_categoty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String Name = parent.getItemAtPosition(position).toString();

                        if(position>0){
                        SubCategoty();}

                    }
                    @Override
                    public void onNothingSelected(AdapterView <?> parent) {
                    }
                });

                additemClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popdialog.dismiss();
                    }
                });
                btn_add_product.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (!UserAccount.isEmpty(add_product_name)){
                            add_product_name.setError("Please enter product name");
                            add_product_name.requestFocus();
                        }

                        else if (!UserAccount.isEmpty(sku_code)){
                            sku_code.setError("Please enter sku code");
                            sku_code.requestFocus();
                        }
                        else if (!UserAccount.isEmpty(item_code)){
                            item_code.setError("Please enter item code");
                            item_code.requestFocus();
                        }
                        else if (!UserAccount.isEmpty(price_per_case)){
                            price_per_case.setError("Please enter price per case");
                            price_per_case.requestFocus();
                        }
                        else if (!UserAccount.isEmpty(inc_vat)){
                            inc_vat.setError("Please enter factory price");
                            inc_vat.requestFocus();
                        }
                        else if (!UserAccount.isEmpty(retail_price)){
                            retail_price.setError("Please enter retail price");
                            retail_price.requestFocus();
                        }
                        else if (!UserAccount.isEmpty(pro_discription)){
                            pro_discription.setError("Please enter discription");
                            pro_discription.requestFocus();
                        }

                        else{
                            AddProduct();
                        }
                    }
                });
                popdialog.show();
            }
        });

        return root;
    }



        private void AddProduct() {
            {
                final Dialog dialog = Utils.initProgressDialog(getActivity());
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.add_product, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();

                        Utils.E("distributer add product:-" +"add product:--" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getActivity(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                            if (jsonObject.getInt("status")==200) {

                                Utils.E("add product Response:-"+jsonObject);
                                String item = jsonObject.getString("data");
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
                                Utils.T(getActivity(), errorMessage + "please check Internet connection");
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
                        HashMap<String, String> addproductParam = new HashMap<>();
                         addproductParam.put("distributor_id","1");
                         addproductParam.put("category_id","1");
                         addproductParam.put("sub_category_id","1");
                         addproductParam.put("product_name",add_product_name.getText().toString());
                         addproductParam.put("unit_per_case",sp_unit_per_case.getSelectedItem().toString());
                         addproductParam.put("total_unit",sp_total_unit.getSelectedItem().toString());
                         addproductParam.put("item_sku_code",sku_code.getText().toString());
                         addproductParam.put("item_code",item_code.getText().toString());
                         addproductParam.put("pack_size",sp_packsize.getSelectedItem().toString());
                         addproductParam.put("price_per_case",price_per_case.getText().toString());
                         addproductParam.put("factory_price",inc_vat.getText().toString());
                         addproductParam.put("rec_retail_price",retail_price.getText().toString());
                         addproductParam.put("description",pro_discription.getText().toString());

                        Log.e("Add ProductParam::", "paramAddproduct:--" + addproductParam);
                        return addproductParam;
                    }
                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        25000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(stringRequest);
            }
        }



    private void Categoty() {
        {
            final Dialog dialog = Utils.initProgressDialog(getActivity());
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.category, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();

                    Log.e("distributer Category:-", "category:--" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        Toast.makeText(getActivity(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                        if (jsonObject.getInt("status")==200) {

                            Utils.E("Category Response:-"+response);

                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            Utils.E("dataArray :-"+dataArray);

                            for(int i =0; i<dataArray.length(); i++){
                                JSONObject dataobject = dataArray.getJSONObject(i);

                                AddProduct_model  addProduct_model = new AddProduct_model();

                                if(!dataobject.isNull("id")){
                                    addProduct_model.setId(dataobject.getString("id"));
                                }

                                if(!dataobject.isNull("name")){
                                    addProduct_model.setName(dataobject.getString("name"));

                                    categotyNameList.add(dataobject.getString("name"));
                                }

                                if(!dataobject.isNull("image")){
                                    addProduct_model.setImage(dataobject.getString("image"));
                                }

                                if(!dataobject.isNull("is_active")){
                                    addProduct_model.setIs_active(dataobject.getString("is_active"));
                                }
                                if(!dataobject.isNull("is_delete")){
                                    addProduct_model.setIs_delete(dataobject.getString("is_delete"));
                                }
                                if(!dataobject.isNull("create_date")){
                                    addProduct_model.setCreate_date(dataobject.getString("create_date"));
                                }

                                Utils.E("category_list"+categotyNameList);

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
                            Utils.T(getActivity(), errorMessage + "please check Internet connection");
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
                  //  stringStringHashMap.put("mobile",et_login_mobile_number.getText().toString());
                  //  stringStringHashMap.put("type","distributer");
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
    }

    private void SubCategoty() {
        {
            //final Dialog dialog = Utils.initProgressDialog(getActivity());
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.subcategory, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                   // dialog.dismiss();

                    Utils.E("distributer SubCategory:-"+ "Subcategory:--" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        Toast.makeText(getActivity(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                        if (jsonObject.getInt("status")==200) {

                            Utils.E("SubCategory Response:-"+response);

                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            Utils.E("dataArray :-"+dataArray);



                            for(int i =0; i<dataArray.length(); i++){
                                JSONObject dataobject = dataArray.getJSONObject(i);

                                SubCategoty_model  SubCategoty_model = new SubCategoty_model();


                                if(!dataobject.isNull("Id")){
                                    SubCategoty_model.setId(dataobject.getString("Id"));
                                }

                                if(!dataobject.isNull("name")){
                                    SubCategoty_model.setName(dataobject.getString("name"));

                                    SubcategotyNameList.add(dataobject.getString("name"));
                                }

                                if(!dataobject.isNull("category_id")){
                                    SubCategoty_model.setCategory_id(dataobject.getString("category_id"));
                                }

                                if(!dataobject.isNull("is_active")){
                                    SubCategoty_model.setIs_active(dataobject.getString("is_active"));
                                }
                                if(!dataobject.isNull("is_delete")){
                                    SubCategoty_model.setIs_delete(dataobject.getString("is_delete"));
                                }
                                if(!dataobject.isNull("create_date")){
                                    SubCategoty_model.setCreate_date(dataobject.getString("create_date"));
                                }

                                if(SubcategotyNameList.size()>0) {
                                    ArrayAdapter<String> subcategotyAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, SubcategotyNameList);
                                    subcategotyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    sp_sub_category.setAdapter(subcategotyAdapter);
                                }

                                Utils.E("Subcategory_list"+SubcategotyNameList);

                            }

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
                            Utils.T(getActivity(), errorMessage + "please check Internet connection");
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
                    subcategoryParam.put("category_id", String.valueOf(sp_categoty.getSelectedItemPosition()));
                   // subcategoryParam.put("category_id", String.valueOf(position).toString());
                  //  stringStringHashMap.put("type","distributer");
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

    private void breakfastshowDataList() {
        breakfastdaModel model = new breakfastdaModel();
        model.setAmount("25.00");
        model.setName("Brown Bread");
        model.setStock("In Stock");
        breakfastdaModels.add(model);

        breakfastdaModel model1 = new breakfastdaModel();
        model1.setAmount("65.00");
        model1.setName("Peanuts Butter");
        model1.setStock("Out of Stock");
        breakfastdaModels.add(model1);


        breakfastdaModel model2 = new breakfastdaModel();
        model2.setAmount("55.00");
        model2.setName("Freash Paneer");
        model2.setStock("In Stock");
        breakfastdaModels.add(model2);


        breakfastdaModel model3 = new breakfastdaModel();
        model3.setAmount("15.00");
        model3.setName("Low Fat Milk");
        model3.setStock("Out of Stock");
        breakfastdaModels.add(model3);



        breakfastdaModel model4 = new breakfastdaModel();
        model4.setAmount("25.00");
        model4.setName("Amul Shake");
        model4.setStock("In Stock");
        breakfastdaModels.add(model4);


        breakfastdaModel model5 = new breakfastdaModel();
        model5.setAmount("8.00");
        model5.setName("Amul Butter");
        model5.setStock("In Stock");
        breakfastdaModels.add(model5);


        breakfastdaModel model6 = new breakfastdaModel();
        model6.setAmount("35.00");
        model6.setName("Amul Milk");
        model6.setStock("Out of Stock");
        breakfastdaModels.add(model6);

        rv_breakfast.setLayoutManager(new LinearLayoutManager(getActivity()));
        breakfastdaAdapter = new BreakfastdaAdapter(getActivity(),breakfastdaModels);
        rv_breakfast.setAdapter(breakfastdaAdapter);
    }



    public class BreakfastdaAdapter extends RecyclerView.Adapter<BreakfastdaAdapter.MyViewHolder> {
        Context context;
        List<breakfastdaModel> breakfastdaModels = new ArrayList<>();

        int allitemdata;
        String stock;


        public BreakfastdaAdapter(Context context, List<breakfastdaModel> breakfastdaModels) {
            this.context = context;
            this.breakfastdaModels = breakfastdaModels;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.breakfast_itemlist,null);
            return new MyViewHolder(view);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            breakfastdaModel  current  = breakfastdaModels.get(position);

            holder.bf_name.setText(current.getName());
            holder.bf_amount.setText(current.getAmount());


            stock = current.getStock().toString();

            if(stock.equals("Out of Stock")){
                holder.bf_stock.setText(current.getStock());
                holder.bf_stock.setTextColor(Color.parseColor("#ec1515"));
                //  holder.bf_stock.setTextColor(R.color.colorRed);
            }
            else{
                holder.bf_stock.setText(current.getStock());
                holder.bf_stock.setTextColor(Color.parseColor("#14a809"));
            }

            //  if (allitemdata == position){
            if (position == (getItemCount()-1)){

                holder.free_space_Add_items.setVisibility(View.VISIBLE);
            } else {
                holder.free_space_Add_items.setVisibility(View.GONE);
            }

            holder.bf_menu.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onClick(View v) {


                    PopupMenu popup = new PopupMenu(context, v);
                    popup.getMenuInflater().inflate(R.menu.bf_menu_popup, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem menu_item) {
                            int id = menu_item.getItemId();

                            if (R.id.menu_editdetails == id){
                                ////////
                                final Dialog popdialog = new Dialog(getContext()) ;
                                popdialog.requestWindowFeature(Window.FEATURE_RIGHT_ICON);
                                popdialog.setContentView(R.layout.add_itempopup);
                                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                lp.copyFrom(popdialog.getWindow().getAttributes());
                                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                                popdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                popdialog.getWindow().setAttributes(lp);
                                // dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id

                                ImageView additemClose = popdialog.findViewById(R.id.add_item_close);
                                Button btn_add_product = popdialog.findViewById(R.id.btn_add_product);
                                sp_categoty = popdialog.findViewById(R.id.sp_product_category);
                                sp_sub_category= popdialog.findViewById(R.id.sp_product_sub_category);
                                sp_unit_per_case= popdialog.findViewById(R.id.sp_unit_per_case);
                                sp_total_unit= popdialog.findViewById(R.id.sp_total_unit);
                                sp_packsize= popdialog.findViewById(R.id.sp_packsize);

                                add_product_name= popdialog.findViewById(R.id.add_product_name);
                                sku_code= popdialog.findViewById(R.id.sku_code);
                                item_code= popdialog.findViewById(R.id.item_code);
                                price_per_case= popdialog.findViewById(R.id.price_per_Case);
                                inc_vat= popdialog.findViewById(R.id.incl_vat);
                                retail_price= popdialog.findViewById(R.id.retail_price);
                                pro_discription= popdialog.findViewById(R.id.product_discription);
                                btn_add_product= popdialog.findViewById(R.id.btn_add_product);

                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, categotyNameList);
                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sp_categoty.setAdapter(arrayAdapter);
                                sp_categoty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String Name = parent.getItemAtPosition(position).toString();

                                        if(position>0){
                                            SubCategoty();}

                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView <?> parent) {
                                    }
                                });

                                additemClose.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        popdialog.dismiss();
                                    }
                                });
                                btn_add_product.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        if (!UserAccount.isEmpty(add_product_name)){
                                            add_product_name.setError("Please enter product name");
                                            add_product_name.requestFocus();
                                        }

                                        else if (!UserAccount.isEmpty(sku_code)){
                                            sku_code.setError("Please enter sku code");
                                            sku_code.requestFocus();
                                        }
                                        else if (!UserAccount.isEmpty(item_code)){
                                            item_code.setError("Please enter item code");
                                            item_code.requestFocus();
                                        }
                                        else if (!UserAccount.isEmpty(price_per_case)){
                                            price_per_case.setError("Please enter price per case");
                                            price_per_case.requestFocus();
                                        }
                                        else if (!UserAccount.isEmpty(inc_vat)){
                                            inc_vat.setError("Please enter factory price");
                                            inc_vat.requestFocus();
                                        }
                                        else if (!UserAccount.isEmpty(retail_price)){
                                            retail_price.setError("Please enter retail price");
                                            retail_price.requestFocus();
                                        }
                                        else if (!UserAccount.isEmpty(pro_discription)){
                                            pro_discription.setError("Please enter discription");
                                            pro_discription.requestFocus();
                                        }

                                        else{
                                            AddProduct();
                                        }
                                    }
                                });
                                popdialog.show();

                                /////
                            }

                            else  if (R.id.menu_delete == id){}

                            return true;
                        }
                    });

                    MenuPopupHelper menuHelper = new MenuPopupHelper(context, (MenuBuilder) popup.getMenu(), v);
                    menuHelper.setForceShowIcon(true);
                    menuHelper.setGravity(Gravity.END);
                    menuHelper.show();
                    //popup.show();

                }


            });



        }

        @Override
        public int getItemCount() {

            //  allitemdata=(breakfastdaModels.size()-1);
            return breakfastdaModels.size();
        }



        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView bf_name,bf_stock,bf_amount;
            ImageView bf_menu;
            LinearLayout free_space_Add_items;
            Switch breakfastswitch;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                free_space_Add_items = itemView.findViewById(R.id.free_space_Add_items);
                bf_name = itemView.findViewById(R.id.bf_name);
                bf_stock = itemView.findViewById(R.id.bf_stock);
                bf_amount = itemView.findViewById(R.id.bf_amount);
                bf_menu = itemView.findViewById(R.id.bf_menu);
            }
        }
    }



}
