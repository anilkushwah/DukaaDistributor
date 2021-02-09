package com.dollop.distributor.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.dollop.distributor.Activity.AllCreditRequestActivity;
import com.dollop.distributor.Activity.CreateOfferActivity;
import com.dollop.distributor.Activity.OfferActivity;
import com.dollop.distributor.Activity.ProductDetailsActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.SavedData;
import com.dollop.distributor.UtilityTools.SessionManager;
import com.dollop.distributor.UtilityTools.UserAccount;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.UtilityTools.multipart.VolleyMultipartRequest;
import com.dollop.distributor.adapter.CategoryAdapter;
import com.dollop.distributor.adapter.OfferAdapter;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.AddProduct_model;
import com.dollop.distributor.model.CategoryModel;
import com.dollop.distributor.model.OfferDTO;
import com.dollop.distributor.model.OfferResponse;
import com.dollop.distributor.model.ProductModel;
import com.dollop.distributor.model.SubCategoryModel;
import com.dollop.distributor.model.SubCategoty_model;
import com.dollop.distributor.model.TaxModel;
import com.dollop.distributor.model.UnitsModel;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;


public class ProductChildFragment extends Fragment {

    CategoryModel categoryModel;
    private ArrayList<ProductModel> productModelList = new ArrayList<>();
    private ArrayList<CategoryModel> categotyNameList = new ArrayList<>();
    TextView product_name_error_tv, category_error_tv, packing_unit_error, total_unit_error;
    EditText add_product_name, item_code, pro_discription, edt_stock_quantity;
    Spinner sp_categoty, sp_sub_category;
    Button btn_add_product;
    private TextView upload_product;
    private FrameLayout emp_addbtn;
    String Cat_Id;
    private ArrayList<SubCategoryModel> SubcategotyNameList = new ArrayList<>();
    private String Sub_Cat_Id = "";
    private String mimeType_business;
    private String fileTimeStr_business;
    private boolean flag;
    private byte[] itemImageByte;
    private ImageView ivProductImageId;
    private ProductAdapter productAdapter;
    private EditText etUnitPerpackingId;
    private EditText etTotlaUnitId;
    private EditText total_weight;
    private AutoCompleteTextView et_brand;
    private Spinner sp_total_PackingID;
    ArrayList<String> packageArrayList = new ArrayList<>();
    ArrayList<String> packageArrayListId = new ArrayList<>();
    private ArrayList<String> brandArraylist = new ArrayList<>();
    private ArrayList<String> brandArrayListId = new ArrayList<>();
    ArrayList<OfferResponse> mOfferResponseArrayList = new ArrayList<>();
    private Spinner spTaxId;
    private ArrayList<TaxModel> taxArrayList = new ArrayList<>();
    private ArrayList<String> taxStringList = new ArrayList<>();
    private String selectedTaxId = "";
    private Switch swTaxId;
    private LinearLayout llTaxId, second_LL, third_LL;
    private String isTaxAvailable = "0";
    private Spinner spUnitId;
    private ArrayList<UnitsModel> unitsModels = new ArrayList<>();
    private ArrayList<String> unitsStr = new ArrayList<>();
    private String selectedUnitsModel = "";
    EditText search_edt, percentag_off_edt;
    EditText orignal_price_edt;
    LinearLayout tex_ll, frist_LL, fourth_LL;
    static Dialog dialog;
    public static ProductChildFragment mProductChildFragment;
    static TextView apply_tv;
    TextView product_title, discount_price_tv;
    float orignal_pricec = 0;
    SessionManager sessionManager;

    public ProductChildFragment(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
        Utils.E("check CategoryModel::"+categoryModel.getId());

        // Required empty public constructor
    }

    public static String offername = "", offer_id = "";
    private static final int GALLERY = 123;
    RecyclerView rvProductId;
    OfferAdapter mOfferAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_review_child, container, false);
        View view = inflater.inflate(R.layout.fragment_product_child, container, false);
        Utils.E("check SelectedPositionId::"+ProductParentFragment.selectedPostion);

        initializationView(view);
        getOfferMethod();


        return view;
    }

    private void initializationView(View view) {
        taxArrayList = new ArrayList<>();
        taxStringList = new ArrayList<>();
        dialog = new Dialog(getActivity(), R.style.dialogstyle);
        rvProductId = view.findViewById(R.id.rvProductId);
        emp_addbtn = view.findViewById(R.id.emp_addbtn);
        search_edt = view.findViewById(R.id.search_edt);

        emp_addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEditProduct(null);

            }
        });


        getProduct();

        search_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    productAdapter.getFilter().filter(charSequence);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void addEditProduct(final ProductModel productModel) {

        final Dialog popdialog = new Dialog(getContext());
        popdialog.requestWindowFeature(Window.FEATURE_RIGHT_ICON);
        popdialog.setContentView(R.layout.add_itempopup);
        long dateItemCode = new Date().getTime();
        String itemCode = "IC-" + dateItemCode;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(popdialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        popdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popdialog.getWindow().setAttributes(lp);

        ImageView additemClose = popdialog.findViewById(R.id.add_item_close);
        Button btn_add_product = popdialog.findViewById(R.id.btn_add_product);
        sp_categoty = popdialog.findViewById(R.id.sp_product_category);
        sp_sub_category = popdialog.findViewById(R.id.sp_product_sub_category);
        sp_total_PackingID = popdialog.findViewById(R.id.sp_total_PackingID);
        upload_product = popdialog.findViewById(R.id.upload_product);
        spTaxId = popdialog.findViewById(R.id.spTaxId);
        swTaxId = popdialog.findViewById(R.id.swTaxId);
        et_brand = popdialog.findViewById(R.id.et_brand);
        apply_tv = popdialog.findViewById(R.id.apply_tv);
        percentag_off_edt = popdialog.findViewById(R.id.percentag_off_edt);
        tex_ll = popdialog.findViewById(R.id.tex_ll);
        frist_LL = popdialog.findViewById(R.id.frist_LL);
        product_name_error_tv = popdialog.findViewById(R.id.product_name_error_tv);
        category_error_tv = popdialog.findViewById(R.id.category_error_tv);
        packing_unit_error = popdialog.findViewById(R.id.packing_unit_error);
        total_unit_error = popdialog.findViewById(R.id.total_unit_error);
        edt_stock_quantity = popdialog.findViewById(R.id.edt_stock_quantity);
        fourth_LL = popdialog.findViewById(R.id.fourth_LL);
        getTax(productModel);
        ////
        product_title = popdialog.findViewById(R.id.product_title);
        /*   et_brand = popdialog.findViewById(R.id.etTotlaUnitId);*/
        ivProductImageId = popdialog.findViewById(R.id.ivProductImageId);
        TextView apply_title_tv = popdialog.findViewById(R.id.apply_title_tv);
        product_title.setText("Add Product");
        add_product_name = popdialog.findViewById(R.id.add_product_name);
        item_code = popdialog.findViewById(R.id.item_code);
        orignal_price_edt = popdialog.findViewById(R.id.orignal_price_edt);
        etUnitPerpackingId = popdialog.findViewById(R.id.etUnitPerpackingId);
        etTotlaUnitId = popdialog.findViewById(R.id.etTotlaUnitId);
        total_weight = popdialog.findViewById(R.id.total_weight);
        discount_price_tv = popdialog.findViewById(R.id.discount_price_tv);
        pro_discription = popdialog.findViewById(R.id.product_discription);
        llTaxId = popdialog.findViewById(R.id.llTaxId);
        spUnitId = popdialog.findViewById(R.id.spUnitId);
        second_LL = popdialog.findViewById(R.id.second_LL);
        third_LL = popdialog.findViewById(R.id.third_LL);
        item_code.setEnabled(false);
        item_code.setText(itemCode);
        TextChangedListener();
        btn_add_product = popdialog.findViewById(R.id.btn_add_product);
        apply_title_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerMethod();
            }
        });
        apply_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                offerMethod();


            }
        });


        swTaxId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    llTaxId.setVisibility(View.VISIBLE);
                    isTaxAvailable = "1";
                } else {
                    llTaxId.setVisibility(View.GONE);
                    isTaxAvailable = "0";
                }

            }
        });

        get_all_weight_unit();
        swTaxId.setChecked(false);
        categotyNameList.clear();

        Categoty(productModel);

        getPackaging();
        getBrands();
        sp_categoty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CategoryModel selectedStatus = (CategoryModel) parent.getSelectedItem();
                Cat_Id = selectedStatus.getId();
                Sub_Cat_Id = "0";
                SubcategotyNameList.clear();
                sp_sub_category.setPrompt("");
                SubCategoty(productModel);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        sp_sub_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                SubCategoryModel selectedStatus = (SubCategoryModel) parent.getSelectedItem();
                Sub_Cat_Id = selectedStatus.getId();


                if (position > 0) {

                    // SubCategoty();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        upload_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getFileChooserIntent();
                startActivityForResult(intent, GALLERY);
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

                if (!UserAccount.isEmpty(add_product_name)) {
                    add_product_name.requestFocus();
                    product_name_error_tv.setVisibility(View.VISIBLE);
                    frist_LL.setVisibility(View.VISIBLE);
                } else if (sp_categoty.getSelectedItem().equals("")) {
                    frist_LL.setVisibility(View.VISIBLE);
                    category_error_tv.setVisibility(View.VISIBLE);

                } else if (et_brand.getText().toString().equals("")) {
                    second_LL.setVisibility(View.VISIBLE);
                } else if (sp_total_PackingID.getSelectedItem().equals("")) {
                    third_LL.setVisibility(View.VISIBLE);
                    packing_unit_error.setVisibility(View.VISIBLE);

                } else if (!UserAccount.isEmpty(etTotlaUnitId)) {
                    third_LL.setVisibility(View.VISIBLE);
                    total_unit_error.setVisibility(View.VISIBLE);

                } else if (!UserAccount.isEmpty(item_code)) {
                    item_code.setError("Please enter item code");
                    item_code.requestFocus();
                } else if (!UserAccount.isEmpty(orignal_price_edt)) {
                    orignal_price_edt.setError("Please enter price per case");
                    orignal_price_edt.requestFocus();
                } else if (discount_price_tv.getText().toString().equals("")) {
                    discount_price_tv.setError("Please enter retail price");
                    discount_price_tv.requestFocus();
                } else if (!UserAccount.isEmpty(edt_stock_quantity)) {

                    fourth_LL.setVisibility(View.VISIBLE);
                    edt_stock_quantity.requestFocus();

                } else if (!UserAccount.isEmpty(pro_discription)) {
                    pro_discription.setError("Please enter discription");
                    pro_discription.requestFocus();
                } else {
                    String price_per_CaseStr = orignal_price_edt.getText().toString();
                    String retail_priceStr = discount_price_tv.getText().toString();
                    float price_per_CaseInt = Float.parseFloat(price_per_CaseStr);
                    float retail_priceStrInt = Float.parseFloat(retail_priceStr);
                    if (retail_priceStrInt > price_per_CaseInt) {
                        Utils.T(getActivity(), "Original price should smaller than offer ");
                    } else {

                        AddProduct(popdialog, productModel);
                    }

                }
            }
        });

        etUnitPerpackingId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String sTotalUnit = etTotlaUnitId.getText().toString();
                int totalUnitPackageWeight = 0;
                if (!sTotalUnit.equals("") && !etUnitPerpackingId.getText().toString().equals("")) {
                    int sTotalUnitInt = Integer.parseInt(sTotalUnit);
                    int unitPerpackingIdInt = Integer.parseInt(etUnitPerpackingId.getText().toString());
                    totalUnitPackageWeight = sTotalUnitInt * unitPerpackingIdInt;
                    total_weight.setText("" + totalUnitPackageWeight);


                }

            }
        });
        etTotlaUnitId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String sTotalUnit = etTotlaUnitId.getText().toString();
                double totalUnitPackageWeight = 0;

                if (!sTotalUnit.equals("") && !etUnitPerpackingId.getText().toString().equals("")) {
                    double sTotalUnitInt = Double.parseDouble(sTotalUnit);
                    double unitPerpackingIdInt = Double.parseDouble(etUnitPerpackingId.getText().toString());
                    totalUnitPackageWeight = sTotalUnitInt * unitPerpackingIdInt;
                    total_weight.setText("" + totalUnitPackageWeight);


                }

            }
        });
        if (productModel != null) {
            etUnitPerpackingId.setText(productModel.unit_per_packing_weight);
            et_brand.setText(productModel.brand);
            item_code.setText(productModel.item_code);
            add_product_name.setText(productModel.product_name);
            etTotlaUnitId.setText(productModel.total_unit);
            total_weight.setText(productModel.total_weight);
            orignal_price_edt.setText(productModel.original_price);
            discount_price_tv.setText(productModel.selling_price);
            pro_discription.setText(productModel.description);
            btn_add_product.setText("Update Product");
            product_title.setText("Update Product");
            int positionPackaging = packageArrayListId.indexOf(productModel.packing_id);
            Picasso.get().load(Const.URL.HOST_URL + productModel.product_image).error(R.drawable.ic_user_blue).into(ivProductImageId);
            edt_stock_quantity.setText(productModel.stock_quantity);
            percentag_off_edt.setText("" + productModel.discount);
            if (positionPackaging >= 0) {
                sp_total_PackingID.setSelection(positionPackaging);
            }
            if (productModel.is_tax.equals("1")) {
                swTaxId.setChecked(true);
            }

            if (!productModel.offer_id.equals("0")) {
                offer_id = productModel.offer_id;
                offername = productModel.offer_title;
                apply_tv.setText(productModel.offer_title);

            }


        }
        popdialog.show();

    }

    private void TextChangedListener() {

        percentag_off_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!percentag_off_edt.getText().toString().equals("") && !orignal_price_edt.getText().toString().equals("")) {
                    orignal_pricec = Float.parseFloat(orignal_price_edt.getText().toString());
                    float discount = Float.parseFloat(s.toString());
                    float discountAmount = (orignal_pricec * discount) / 100;


                    String abcString2 = (orignal_pricec - discountAmount) + "";
                    discount_price_tv.setText(abcString2);
                    discount_price_tv.setEnabled(false);
                } else {
                    discount_price_tv.setText(orignal_price_edt.getText().toString());
                }


            }
        });

        orignal_price_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!orignal_price_edt.getText().toString().equals("")) {
                    orignal_pricec = Float.parseFloat(orignal_price_edt.getText().toString());
                    if (!percentag_off_edt.getText().toString().equals("")) {
                        float discount = Float.parseFloat(percentag_off_edt.getText().toString());

                        float discountAmount = (orignal_pricec * discount) / 100;


                        String abcString2 = (orignal_pricec - discountAmount) + "";
                        discount_price_tv.setText(abcString2);
                        discount_price_tv.setEnabled(false);
                    } else {
                        discount_price_tv.setText(orignal_price_edt.getText().toString());
                    }
                } else {
                    discount_price_tv.setText("");
                }


            }
        });

    }

    private void offerMethod() {
        dialog.setContentView(R.layout.state_layout);
        EditText et_enter_name_id = dialog.findViewById(R.id.et_enter_name_id);
        RecyclerView country_RV = dialog.findViewById(R.id.country_RV);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        et_enter_name_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    mOfferAdapter.getFilter().filter(charSequence);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mOfferAdapter = new OfferAdapter(getActivity(), mOfferResponseArrayList, "addProduct", dialog);
        country_RV.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        country_RV.setAdapter(mOfferAdapter);
        mOfferAdapter.notifyDataSetChanged();
    }

    private void AddProduct(final Dialog popdialog, final ProductModel productModel) {

        final Dialog dialog = Utils.initProgressDialog(getActivity());
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        VolleyMultipartRequest stringRequest = new VolleyMultipartRequest(Request.Method.POST, Const.URL.add_product, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                dialog.dismiss();
                String data = new String(response.data);
                Utils.E("distributer add product:-" + "add product:--" + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);

                    // Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    if (jsonObject.getInt("status") == 200) {

                        Utils.E("add product Response:-" + jsonObject);
                        String item = jsonObject.getString("data");
                        popdialog.dismiss();

                        getProduct();
                    } else {
                        // Utils.T(getActivity(), jsonObject.getString("message"));
                    }

                } catch (Exception e) {
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

                if (SavedData.get_AccessType().equals("Distributor")) {
                    addproductParam.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());

                } else {
                    addproductParam.put("distributor_id", SavedData.get_MainMemberId());

                }

                addproductParam.put("category_id", Cat_Id);
                addproductParam.put("sub_category_id", Sub_Cat_Id);
                addproductParam.put("product_name", add_product_name.getText().toString());
                addproductParam.put("brand", et_brand.getText().toString());
                addproductParam.put("total_unit", etTotlaUnitId.getText().toString());
                addproductParam.put("unit", spUnitId.getSelectedItem().toString());
                addproductParam.put("discount", "0");
                addproductParam.put("unit_per_packing_weight", etUnitPerpackingId.getText().toString());
                addproductParam.put("packing_id", "" + packageArrayListId.get(sp_total_PackingID.getSelectedItemPosition()));
                addproductParam.put("total_weight", "" + total_weight.getText().toString());
                addproductParam.put("item_code", item_code.getText().toString());
                addproductParam.put("original_price", orignal_price_edt.getText().toString());
                addproductParam.put("selling_price", discount_price_tv.getText().toString());
                addproductParam.put("description", pro_discription.getText().toString());
                addproductParam.put("is_tax", isTaxAvailable);
                addproductParam.put("discount", percentag_off_edt.getText().toString());
                addproductParam.put("stock_quantity", edt_stock_quantity.getText().toString());

                addproductParam.put("offer_id", offer_id);

                if (isTaxAvailable.equals("1")) {
                    addproductParam.put("tax_id", selectedTaxId);
                }

                if (productModel != null) {
                    addproductParam.put("product_id", productModel.Id);
                }

                Utils.E("checkProductADdParatmeter::" + addproductParam);
                return addproductParam;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError, IOException {
//                return super.getByteData();

                HashMap<String, DataPart> stringDataPartHashMap = new HashMap<>();
                // stringDataPartHashMap.put("",)
                if (itemImageByte != null) {
                    stringDataPartHashMap.put("product_image", new DataPart(fileTimeStr_business, itemImageByte, "image/png"));
                }

                return stringDataPartHashMap;
            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    private Intent getFileChooserIntent() {
        String[] mimeTypes = {"image/*", "application/pdf", "video/*"};

        Intent intentbusiness = new Intent(Intent.ACTION_GET_CONTENT);
        intentbusiness.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intentbusiness.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intentbusiness.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";

            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }

            intentbusiness.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }

        return intentbusiness;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY && resultCode == Activity.RESULT_OK && data != null) {

            Uri ImageFileUri = data.getData();
            String path = "";
            Uri uri = data.getData();
            File file = new File(uri.getPath());//create path from uri
            final String[] split = file.getPath().split(":");//split the path.
            Log.e("docu01", "inent:01" + uri.getPath());
            ContentResolver cR = getActivity().getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            mimeType_business = mime.getExtensionFromMimeType(cR.getType(uri));
            Log.e("mimeType", "mimeType" + mimeType_business);

            // Image_business_permit.setImageURI(ImageFileUri);
            fileTimeStr_business = System.currentTimeMillis() + "image." + mimeType_business;
            upload_product.setText(fileTimeStr_business);
            upload_product.setTextSize(8);
            flag = true;
            ivProductImageId.setImageURI(uri);
            try {
                InputStream iStream = getActivity().getContentResolver().openInputStream(ImageFileUri);
                itemImageByte = getBytes(iStream);
                Log.e("inputData", "if(inputData=" + itemImageByte);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private void SubCategoty(final ProductModel productModel) {
        SubcategotyNameList.clear();
        //final Dialog dialog = Utils.initProgressDialog(getActivity());
        RequestQueue queue = Volley.newRequestQueue(getActivity());
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
                            ArrayAdapter<SubCategoryModel> subcategotyAdapter = new ArrayAdapter<SubCategoryModel>(getActivity(),
                                    R.layout.simple_spinner_dropdown_item, SubcategotyNameList);
                            subcategotyAdapter.setDropDownViewResource( R.layout.simple_spinner_dropdown_item);
                            sp_sub_category.setAdapter(subcategotyAdapter);
                        }


                        if (productModel != null) {
                            for (int i = 0; i < SubcategotyNameList.size(); i++) {
                                SubCategoryModel categoryModel = SubcategotyNameList.get(i);
                                if (categoryModel.getName().equals(productModel.sub_category_name)) {
                                    sp_sub_category.setSelection(i);
                                }


                            }
                        }

                    } else {
                        SubcategotyNameList.clear();
                        SubCategoryModel model = new SubCategoryModel();
                        model.setId("0");
                        model.setName("No Sub Category");
                        SubcategotyNameList.add(model);
                        ArrayAdapter<SubCategoryModel> subcategotyAdapter = new ArrayAdapter<SubCategoryModel>(getActivity(),  R.layout.simple_spinner_dropdown_item, SubcategotyNameList);
                        subcategotyAdapter.setDropDownViewResource( R.layout.simple_spinner_dropdown_item);
                        sp_sub_category.setAdapter(subcategotyAdapter);

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
                subcategoryParam.put("category_id", Cat_Id);
                subcategoryParam.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
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

    class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>
            implements Filterable {
        Context context;
        List<ProductModel> breakfastdaModels = new ArrayList<>();
        List<ProductModel> mList = new ArrayList<>();
        int allitemdata;
        String stock;
        int alldata;


        public ProductAdapter(Context context, List<ProductModel> breakfastdaModels) {
            this.context = context;
            this.breakfastdaModels = breakfastdaModels;
            this.mList = breakfastdaModels;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.product_item_list, null);
            return new MyViewHolder(view);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
            final ProductModel current = breakfastdaModels.get(position);
            if (position == 0) {
                holder.lastitem_space.setVisibility(View.GONE);
            } else {
                if (alldata == position) {
                    holder.lastitem_space.setVisibility(View.VISIBLE);
                } else {
                    holder.lastitem_space.setVisibility(View.GONE);
                }
            }
            holder.iv_WishListProductImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("product_id", current.Id);
                    Utils.I(getActivity(), ProductDetailsActivity.class, bundle);
                }
            });
            holder.tv_product_name.setText(current.product_name);
            holder.tv_category_name.setText(current.sub_category_name);
            holder.percent_tv.setText("" + current.discount + getResources().getString(R.string.percent));
            holder.tv_rec_retail_price.setText(getResources().getString(R.string.currency_sign) + " " + current.selling_price);

            holder.tv_factory_price.setText(getResources().getString(R.string.currency_sign) + " " + current.original_price);

            holder.tv_factory_price.setPaintFlags(holder.tv_factory_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            if (current.product_availability.equals("1")) {
                holder.swAvailableId.setChecked(true);
                holder.tvInStockId.setText("In stock");
            } else {
                holder.swAvailableId.setChecked(false);
                holder.tvInStockId.setText("Out of stock");
            }
            holder.swAvailableId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    setAvailableNotAvailable(current, isChecked);
                    // notifyDataSetChanged();
                }
            });
            if (current.product_image != null) {
                Picasso.get().load(Const.URL.HOST_URL + current.product_image)
                        .error(R.drawable.no_product).into(holder.iv_WishListProductImage);

            }

            holder.ivMenuProdcutId.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onClick(View v) {


                    PopupMenu popup = new PopupMenu(context, v);
                    popup.getMenuInflater().inflate(R.menu.bf_menu_popup, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem menu_item) {
                            int id = menu_item.getItemId();


                            if (R.id.menu_delete == id) {

                                showDeleteModel(productModelList.get(position));

                            } else if (R.id.menu_editdetails == id) {

                                addEditProduct(productModelList.get(position));

                            }

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
            //  alldata = (breakfastdaModels.size() - 1);
            return breakfastdaModels.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString().toLowerCase();
                    if (charString.isEmpty()) {
                        breakfastdaModels = mList;
                    } else {
                        ArrayList<ProductModel> filteredList = new ArrayList<>();
                        for (ProductModel row : mList) {

                            if (row.product_name.toLowerCase().contains(charSequence.toString().toLowerCase()))
                                filteredList.add(row);

                        }

                        breakfastdaModels = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = breakfastdaModels;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    breakfastdaModels = (ArrayList<ProductModel>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            private final TextView tv_product_name;
            private final TextView tv_category_name;
            private final TextView tv_rec_retail_price;
            private final TextView tv_factory_price, percent_tv;
            private final Switch swAvailableId;
            private final LinearLayout lastitem_space;
            private final TextView tvInStockId;
            ImageView ivMenuProdcutId;
            ImageView iv_WishListProductImage;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                tv_product_name = itemView.findViewById(R.id.tv_product_name);
                tv_category_name = itemView.findViewById(R.id.tv_category_name);
                tv_rec_retail_price = itemView.findViewById(R.id.tv_rec_retail_price);
                tv_factory_price = itemView.findViewById(R.id.tv_factory_price);
                swAvailableId = itemView.findViewById(R.id.swAvailableId);
                lastitem_space = itemView.findViewById(R.id.lastitem_space);
                ivMenuProdcutId = itemView.findViewById(R.id.ivMenuProdcutId);
                tvInStockId = itemView.findViewById(R.id.tvInStockId);
                iv_WishListProductImage = itemView.findViewById(R.id.iv_WishListProductImage);
                percent_tv = itemView.findViewById(R.id.percent_tv);


            }
        }

    }


    private void showDeleteModel(final ProductModel productModel) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
      //  builder.setTitle("Alert!");
        builder.setMessage("are you sure you want delete?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteProduct(productModel);

                        // Toast.makeText(getApplicationContext(), "This is a positive button", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Toast.makeText(getApplicationContext(), "This is a negative button", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                });
        AlertDialog alertdialog = builder.create();
        alertdialog.show();


    }

    private void getProduct() {
        //   final Dialog dialog = Utils.initProgressDialog(getActivity());
        RequestQueue queue = Volley.newRequestQueue(getActivity());
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
                        productModelList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            ProductModel productModel = new ProductModel();
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
                            productModel.category_name = jsonObject1.getString("category_name");
                            productModel.sub_category_name = jsonObject1.getString("sub_category_name");
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

                            productModelList.add(productModel);
                        }

                        rvProductId.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                        productAdapter = new ProductAdapter(getActivity(), productModelList);
                        rvProductId.setAdapter(productAdapter);

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
                stringStringHashMap.put("type", "Distributor");
                if (SavedData.get_AccessType().equals("Distributor")) {
                    stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());

                } else {
                    stringStringHashMap.put("distributor_id", SavedData.get_MainMemberId());

                }

                stringStringHashMap.put("category_id", categoryModel.getId());
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

    private void setAvailableNotAvailable(final ProductModel current, final boolean isChecked) {
        final Dialog dialog = Utils.initProgressDialog(getActivity());
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.update_product_availability, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Utils.E("check setAvailableNotAvailable::" + response);
                dialog.dismiss();
                getProduct();

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
                stringStringHashMap.put("product_id", current.Id);
                if (isChecked) {
                    stringStringHashMap.put("product_availability", "1");
                } else {
                    stringStringHashMap.put("product_availability", "0");

                }
                //  stringStringHashMap.put("product_id", current);

            /*    distributor_id:1
                product_id:1
                product_availability:0*/
                Utils.E("check setAvailableNotAvailable:Params:" + stringStringHashMap);
                return stringStringHashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }


    private void Categoty(final ProductModel productModel) {
        final Dialog dialog = Utils.initProgressDialog(getActivity());
        RequestQueue queue = Volley.newRequestQueue(getActivity());
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
                        ArrayAdapter<CategoryModel> arrayAdapter = new ArrayAdapter<CategoryModel>(getActivity(), R.layout.simple_spinner_dropdown_item, categotyNameList);
                        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        sp_categoty.setAdapter(arrayAdapter);
                        if (productModel != null) {
                            for (int i = 0; i < categotyNameList.size(); i++) {
                                CategoryModel categoryModel = categotyNameList.get(i);
                                if (categoryModel.getName().equals(productModel.category_name)) {
                                    sp_categoty.setSelection(i);
                                }


                            }
                        }
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
                if (SavedData.get_AccessType().equals("Distributor")) {
                    stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());

                } else {
                    stringStringHashMap.put("distributor_id", SavedData.get_MainMemberId());

                }
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

    private void deleteProduct(final ProductModel productModel) {
        final Dialog dialog = Utils.initProgressDialog(getActivity());
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.deleteProduct, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                Utils.E("check DeleteProduct::" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {
                        getProduct();

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
                stringStringHashMap.put("product_id", productModel.Id);
                stringStringHashMap.put("distributor_id",UserDataHelper.getInstance().getList().get(0).getDistributorId());
                Utils.E("check DeleteProduct:Param:" + stringStringHashMap);
                return stringStringHashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    private void getPackaging() {
        final Dialog dialog = Utils.initProgressDialog(getActivity());
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.get_all_packing, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                Utils.E("check DeleteProduct::" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //  Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {
                        JSONArray packingJsonArray = jsonObject.getJSONArray("packing");
                        packageArrayList.clear();
                        packageArrayListId.clear();
                        for (int i = 0; i < packingJsonArray.length(); i++) {
                            JSONObject jsonObject1 = packingJsonArray.getJSONObject(i);
                            // PackModel packModel = new PackModel();
                            String spacking_id = jsonObject1.getString("packing_id");
                            String packing = jsonObject1.getString("packing");
                            packageArrayList.add(packing);
                            packageArrayListId.add(spacking_id);

                        }
                        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),  R.layout.simple_spinner_dropdown_item, packageArrayList);
                        sp_total_PackingID.setAdapter(stringArrayAdapter);


                    } else {
                        // Utils.T(getActivity(), jsonObject.getString("message"));
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
                stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());

                Utils.E("check DeleteProduct:Param:" + stringStringHashMap);
                return stringStringHashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    private void getTax(final ProductModel productModel) {
        final Dialog dialog = Utils.initProgressDialog(getActivity());
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.get_all_tax, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                Utils.E("check DeleteProduct::" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {
                        JSONArray taxDataJsonArray = jsonObject.getJSONArray("taxData");

                        taxArrayList.clear();
                        taxStringList.clear();
                        for (int i = 0; i < taxDataJsonArray.length(); i++) {
                            JSONObject jsonObject1 = taxDataJsonArray.getJSONObject(i);
                            TaxModel taxModel = new TaxModel();
                            // {"status":200,"message":"success","taxData":[{"id":"1","name":"Basic
                            //Tax","percent":"10","is_active":"1","is_delete":"1","created_date":"2020-10-15 18:49:02"}]}
                            String id = jsonObject1.getString("id");
                            String name = jsonObject1.getString("name");
                            String percent = jsonObject1.getString("percent");
                            taxModel.id = id;
                            taxModel.name = name;
                            taxModel.percent = percent;
                            taxArrayList.add(taxModel);
                            taxStringList.add(name + "(" + percent + "%)");

                        }

                        if (taxArrayList.isEmpty()) {
                            tex_ll.setVisibility(View.GONE);
                        }

                        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),  R.layout.simple_spinner_dropdown_item, taxStringList);
                        spTaxId.setAdapter(stringArrayAdapter);
                        spTaxId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                selectedTaxId = taxArrayList.get(position).id;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        if (productModel != null) {
                            if (productModel.is_tax.equals("1")) {
                                swTaxId.setChecked(true);

                                for (int j = 0; j < taxArrayList.size(); j++) {

                                    if (productModel.tax_id.equals(taxArrayList.get(j).id)) {
                                        spTaxId.setSelection(j);
                                    }


                                }
                            }
                        }


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
                stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
                Utils.E("check DeleteProduct:Param:" + stringStringHashMap);
                return stringStringHashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    private void get_all_weight_unit() {
        final Dialog dialog = Utils.initProgressDialog(getActivity());
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.get_all_weight_unit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                Utils.E("check DeleteProduct::" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //   Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {
                        JSONArray units = jsonObject.getJSONArray("units");
                        unitsModels.clear();
                        unitsStr.clear();
                        for (int i = 0; i < units.length(); i++) {
                            JSONObject jsonObject1 = units.getJSONObject(i);
                            UnitsModel unitsModel = new UnitsModel();
                            unitsModel.id = jsonObject1.getString("id");
                            unitsModel.name = jsonObject1.getString("name");
                            unitsModel.is_active = jsonObject1.getString("is_active");
                            unitsModel.is_delete = jsonObject1.getString("is_delete");
                            unitsModel.created_date = jsonObject1.getString("created_date");
                            unitsModels.add(unitsModel);
                            unitsStr.add(jsonObject1.getString("name"));


                        }
                        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),
                                R.layout.simple_spinner_dropdown_item, unitsStr);
                        spUnitId.setAdapter(stringArrayAdapter);
                        spUnitId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                selectedUnitsModel = unitsModels.get(position).id;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                    } else {
                        // Utils.T(getActivity(), jsonObject.getString("message"));
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
                stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
                Utils.E("check DeleteProduct:Param:" + stringStringHashMap);
                return stringStringHashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    private void getBrands() {
        final Dialog dialog = Utils.initProgressDialog(getActivity());
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.get_all_brand, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                Utils.E("check getBrands::" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {

                        JSONArray jsonArray = jsonObject.getJSONArray("brand");
                        brandArraylist.clear();
                        brandArrayListId.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String brandId = jsonObject1.getString("id");
                            String brandName = jsonObject1.getString("name");
                            brandArraylist.add(brandName);
                            brandArrayListId.add(brandId);

                        }
                        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, brandArraylist);
                        et_brand.setAdapter(stringArrayAdapter);


                    } else {
                        // Utils.T(getActivity(), jsonObject.getString("message"));
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
                // stringStringHashMap.put("product_id", productModel.Id);
                stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
                Utils.E("check DeleteProduct:Param:" + stringStringHashMap);
                return stringStringHashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    private void getOfferMethod() {
        final Dialog dialog = Utils.initProgressDialog(getActivity());

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> stringStringHashMap = new HashMap<>();

        if (SavedData.get_AccessType().equals("Distributor")) {
            stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());

        } else {
            stringStringHashMap.put("distributor_id", SavedData.get_MainMemberId());
        }
        stringStringHashMap.put("category_type", "product");

        Utils.E("product param::" + stringStringHashMap);

        Call<OfferDTO> call = apiService.get_offer(stringStringHashMap);
        call.enqueue(new Callback<OfferDTO>() {
            @Override
            public void onResponse(Call<OfferDTO> call, retrofit2.Response<OfferDTO> response) {
                dialog.dismiss();
                try {
                    OfferDTO body = response.body();
                    mOfferResponseArrayList.clear();

                    if (body.getStatus() == 200) {

                        mOfferResponseArrayList = body.getData();

                      /*  rv_memberList.setLayoutManager(new LinearLayoutManager(OfferActivity.this));
                        mOfferAdapter = new OfferAdapter(OfferActivity.this, mOfferResponseArrayList);
                        rv_memberList.setAdapter(mOfferAdapter);
                        mOfferAdapter.notifyDataSetChanged();*/

                    } else {
                        Utils.T(getActivity(), body.getMessage());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<OfferDTO> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                dialog.dismiss();

            }
        });

    }

    public static void setOffername() {
        apply_tv.setText(offername);
    }

}