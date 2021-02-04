package com.dollop.distributor.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.dollop.distributor.UtilityTools.MarshMallowPermission;
import com.dollop.distributor.UtilityTools.SavedData;
import com.dollop.distributor.UtilityTools.UserAccount;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.UtilityTools.multipart.VolleyMultipartRequest;
import com.dollop.distributor.UtilityTools.multipart.VolleySingleton;
import com.dollop.distributor.adapter.CategoryAdapter;
import com.dollop.distributor.adapter.SubCategoryAdapter;
import com.dollop.distributor.adapter.TotalEarningAdapter;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.AddProduct_model;
import com.dollop.distributor.model.CategoryModel;
import com.dollop.distributor.model.ProductModel;
import com.dollop.distributor.model.SubCategoryModel;
import com.dollop.distributor.model.SubCategoty_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.dollop.distributor.UtilityTools.multipart.AppHelper.getFileDataFromDrawable;

public class CreateOfferActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner offer_type;
    public static final int GALLERY = 5;
    protected static final int CAMERA_REQUEST = 6;
    RadioButton category_radio, sub_category_radio, radio_Product;
    RadioGroup m_radio_group;
    String[] country = {"Select Offer", "PERCENT", "FLAT"};
    static Dialog dialog;
    static Dialog dialog1;
    static Dialog dialog2;
    private ArrayList<CategoryModel> categotyNameList = new ArrayList<>();
    private ArrayList<SubCategoryModel> SubcategotyNameList = new ArrayList<>();
    public static String category_id, category_name;
    CategoryAdapter mCategoryAdapter;
    SubCategoryAdapter mSubCategoryAdapter;
    public static String category_type = "";
    LinearLayout discount_percentage_LL, discount_amount_LL;
    TextView selected_category_name, tv_add_offer, date_to_tv, date_from_tv;
    String valueType;
    EditText edt_discount_percentage, edt_discount_amount, edit_Title, edt_description;
    boolean seletectValue = false;
    LinearLayout date_to_layout, date_from_layout;
    DatePickerDialog datePickerDialog;
    int mYear_To = 0;
    int mMonth_To = 0;
    int mDay_To = 0;
    private long fromTimeMils;
    LinearLayout offer_image_LL;
    ImageView user_profile, sub_member_back;
    Bitmap profileBitmap;
    Uri profileUri;
    boolean imageSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_create_offer);

        initializationView();
        CheckedChangeListenerMethod();


    }

    private void CheckedChangeListenerMethod() {
        sub_member_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.I_clear(CreateOfferActivity.this, OfferActivity.class, null);
            }
        });

        offer_type.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, country);
        aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        offer_type.setAdapter(aa);

        offer_image_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarshMallowPermission marshMallowPermission = new MarshMallowPermission(CreateOfferActivity.this);

                if (marshMallowPermission.checkPermissionForCamera()) {
                    profileMethod();
                } else {
                    marshMallowPermission.requestPermissionForCamera();
                }
            }
        });
        date_from_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                date_to_tv.setText("");
                mYear_To = 0;
                mMonth_To = 0;
                mDay_To = 0;

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                datePickerDialog = new DatePickerDialog(CreateOfferActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                String dateString = format.format(calendar.getTime());

                                date_from_tv.setText(dateString);
                                fromTimeMils = calendar.getTimeInMillis();

                                mYear_To = calendar.get(Calendar.YEAR);
                                mMonth_To = calendar.get(Calendar.MONTH);
                                mDay_To = calendar.get(Calendar.DAY_OF_MONTH);
                                seletectValue = true;

                            }

                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

            }
        });


        date_to_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seletectValue) {
                    datePickerDialog = new DatePickerDialog(CreateOfferActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(year, monthOfYear, dayOfMonth);
                                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                    String dateString = format.format(calendar.getTime());
                                    date_to_tv.setText(dateString);


                                }
                            }, mYear_To, mMonth_To, mDay_To);
                    datePickerDialog.show();
                    datePickerDialog.getDatePicker().setMinDate(fromTimeMils);
                    //  datePickerDialog.getDatePicker().setMaxDate(fromTimeMils);

                } else {
                    Utils.T(CreateOfferActivity.this, "Seletct First Date From");
                    //  Toast.makeText(getActivity(), "Seletct First Date From", Toast.LENGTH_LONG).show();
                }


            }
        });

        m_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (category_radio.isChecked()) {

                    Categoty();

                    dialog1.setContentView(R.layout.state_layout);
                    EditText et_enter_name_id = dialog1.findViewById(R.id.et_enter_name_id);
                    RecyclerView country_RV = dialog1.findViewById(R.id.country_RV);
                    dialog1.setCanceledOnTouchOutside(true);
                    dialog1.show();

                    et_enter_name_id.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            try {
                                mCategoryAdapter.getFilter().filter(charSequence);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                    mCategoryAdapter = new CategoryAdapter(CreateOfferActivity.this, categotyNameList, dialog1);
                    country_RV.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                    country_RV.setAdapter(mCategoryAdapter);
                    mCategoryAdapter.notifyDataSetChanged();

                } else if (sub_category_radio.isChecked()) {
                    SubCategoty();

                    dialog2.setContentView(R.layout.state_layout);
                    EditText et_enter_name_id = dialog2.findViewById(R.id.et_enter_name_id);
                    RecyclerView country_RV = dialog2.findViewById(R.id.country_RV);
                    dialog2.setCanceledOnTouchOutside(true);
                    dialog2.show();

                    et_enter_name_id.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            try {
                                mSubCategoryAdapter.getFilter().filter(charSequence);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                    mSubCategoryAdapter = new SubCategoryAdapter(CreateOfferActivity.this, SubcategotyNameList, dialog2);
                    country_RV.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                    country_RV.setAdapter(mSubCategoryAdapter);
                    mSubCategoryAdapter.notifyDataSetChanged();


                } else if (radio_Product.isChecked()) {
                    category_type = "product";
                    selected_category_name.setVisibility(View.GONE);
                    selected_category_name.setText("");
                    category_id = "";
                }
            }
        });
        tv_add_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!imageSelected) {
                    Utils.T(CreateOfferActivity.this, "Select Offer Image");
                } else if (!UserAccount.isEmpty(edit_Title)) {
                    edit_Title.setError("Enter Title");
                    edit_Title.requestFocus();
                } else if (offer_type.getSelectedItem().toString().equals("Select Offer")) {
                    Utils.T(CreateOfferActivity.this, "Select Offer");
                } else if (valueType.equals("PERCENT")) {
                    int percentageValue = 0;

                    if (percentageValue != 0) {
                        percentageValue = Integer.parseInt(edt_discount_percentage.getText().toString());
                    }

                    if (!UserAccount.isEmpty(edt_discount_percentage)) {
                        edt_discount_percentage.setError("Enter discount percentage");
                        edt_discount_percentage.requestFocus();
                    } else if (percentageValue > 99) {
                        Utils.T(CreateOfferActivity.this, "percentage value should not be inserted grether than 99 ");
                    } else if (category_type.equals("")) {
                        Utils.T(CreateOfferActivity.this, "Select category");
                    } else if (!UserAccount.isEmpty(edt_description)) {
                        edt_description.setError("Enter description");
                        edt_description.requestFocus();
                    } else if (date_from_tv.getText().toString().equals("")) {
                        Utils.T(CreateOfferActivity.this, "Select Date From");
                    } else if (date_to_tv.getText().toString().equals("")) {
                        Utils.T(CreateOfferActivity.this, "Select Date To");
                    } else {
                        add_offer();
                    }
                } else if (valueType.equals("FLAT")) {
                    if (!UserAccount.isEmpty(edt_discount_amount)) {
                        edt_discount_amount.setError("Enter discount_amount");
                        edt_discount_amount.requestFocus();
                    } else if (category_type.equals("")) {
                        Utils.T(CreateOfferActivity.this, "Select category");
                    } else if (!UserAccount.isEmpty(edt_description)) {
                        edt_description.setError("Enter description");
                        edt_description.requestFocus();
                    } else if (date_from_tv.getText().toString().equals("")) {
                        Utils.T(CreateOfferActivity.this, "Select Date From");
                    } else if (date_to_tv.getText().toString().equals("")) {
                        Utils.T(CreateOfferActivity.this, "Select Date To");
                    } else {
                        add_offer();
                    }
                }


            }
        });
    }
    private void initializationView() {

        dialog = new Dialog(CreateOfferActivity.this, R.style.dialogstyle);
        dialog1 = new Dialog(CreateOfferActivity.this, R.style.dialogstyle);
        dialog2 = new Dialog(CreateOfferActivity.this, R.style.dialogstyle);


        offer_type = findViewById(R.id.offer_type);
        category_radio = findViewById(R.id.category_radio);
        sub_category_radio = findViewById(R.id.sub_category_radio);
        radio_Product = findViewById(R.id.radio_Product);
        m_radio_group = findViewById(R.id.m_radio_group);
        discount_percentage_LL = findViewById(R.id.discount_percentage_LL);
        discount_amount_LL = findViewById(R.id.discount_amount_LL);
        selected_category_name = findViewById(R.id.selected_category_name);
        tv_add_offer = findViewById(R.id.tv_add_offer);
        edt_discount_percentage = findViewById(R.id.edt_discount_percentage);
        edt_discount_amount = findViewById(R.id.edt_discount_amount);
        edit_Title = findViewById(R.id.edit_Title);
        edt_description = findViewById(R.id.edt_description);
        date_to_layout = findViewById(R.id.date_to_layout);
        date_from_layout = findViewById(R.id.date_from_layout);
        date_to_tv = findViewById(R.id.date_to_tv);
        date_from_tv = findViewById(R.id.date_from_tv);
        offer_image_LL = findViewById(R.id.offer_image_LL);
        user_profile = findViewById(R.id.user_profile);
        sub_member_back = findViewById(R.id.sub_member_back);


    }

    private void profileMethod() {


        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, GALLERY);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        valueType = country[position];

        if (valueType.equals("PERCENT")) {
            discount_percentage_LL.setVisibility(View.VISIBLE);
            discount_amount_LL.setVisibility(View.GONE);
            edt_discount_amount.setText("");

        } else if (valueType.equals("FLAT")) {
            discount_amount_LL.setVisibility(View.VISIBLE);
            discount_percentage_LL.setVisibility(View.GONE);
            edt_discount_percentage.setText("");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void Categoty() {

        final Dialog dialog = Utils.initProgressDialog(CreateOfferActivity.this);
        RequestQueue queue = Volley.newRequestQueue(CreateOfferActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.URL.get_all_category, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                Log.e("distributer Category:-", "category:--" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //  Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {

                        Utils.E("Category Response:-" + response);

                        JSONArray dataArray = jsonObject.getJSONArray("category");
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


                            Utils.E("category_list" + categotyNameList);

                            categotyNameList.add(model);
                            mCategoryAdapter.notifyDataSetChanged();
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
                        Utils.T(CreateOfferActivity.this, errorMessage + "please check Internet connection");
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


    private void SubCategoty() {
        //final Dialog dialog = Utils.initProgressDialog(getActivity());
        RequestQueue queue = Volley.newRequestQueue(CreateOfferActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.URL.get_all_sub_category, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // dialog.dismiss();

                Utils.E("distributer SubCategory:-" + "Subcategory:--" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    // Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {

                        Utils.E("SubCategory Response:-" + response);

                        JSONArray dataArray = jsonObject.getJSONArray("sub_category");
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


                            SubcategotyNameList.add(model);

                            mSubCategoryAdapter.notifyDataSetChanged();
                            Utils.E("Subcategory_list" + SubcategotyNameList);

                        }


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
                //  dialog.dismiss();

                NetworkResponse networkResponse = error.networkResponse;

                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                        Utils.T(CreateOfferActivity.this, errorMessage + "please check Internet connection");
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

    /*  */

    public void setCategoryName() {
        selected_category_name.setVisibility(View.VISIBLE);
        selected_category_name.setText(category_name);

    }


    private void add_offer() {
        final Dialog dialog = Utils.initProgressDialog(this);
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, Const.URL.add_offer, new com.android.volley.Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                dialog.dismiss();
                String resultResponse = new String(response.data);

                Utils.E("resultResponse::" + resultResponse);

                try {
                    JSONObject result = new JSONObject(resultResponse);

                    int status = result.getInt("status");
                    String msg = result.getString("message");
                    String otp = result.getString("data");

                    if (status == 200) {
                        Utils.I_clear(CreateOfferActivity.this, OfferActivity.class, null);
                    }

                } catch (JSONException e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;

                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                        Utils.T(getApplicationContext(), "Request timeout please check Internet connection");
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        //   response.getJSONArray(result);
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
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String, String> stringStringHashMap = new HashMap<>();

                if (SavedData.get_AccessType().equals("Distributor")) {
                    stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());

                } else {
                    stringStringHashMap.put("distributor_id", SavedData.get_MainMemberId());

                }

                stringStringHashMap.put("category_type", category_type);
                stringStringHashMap.put("category_type_id", category_id);
                stringStringHashMap.put("type", valueType);
                stringStringHashMap.put("title", edit_Title.getText().toString());
                stringStringHashMap.put("discount_percent", edt_discount_percentage.getText().toString());
                stringStringHashMap.put("discount_amount", edt_discount_amount.getText().toString());
                stringStringHashMap.put("description", edt_description.getText().toString());
                stringStringHashMap.put("valid_from", date_from_tv.getText().toString());
                stringStringHashMap.put("valid_to", date_to_tv.getText().toString());


                Log.e("addmember:", "param:--" + stringStringHashMap);
                Utils.E("SIGNUPIMAGEINFO" + stringStringHashMap);

                return stringStringHashMap;

            }

            @Override
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() throws IOException {
                Map<String, DataPart> params = new HashMap<>();
                if (user_profile.getDrawable() != null) {
                    params.put("image", new VolleyMultipartRequest.DataPart(System.currentTimeMillis() + ".png",
                            getFileDataFromDrawable(getApplicationContext(),
                                    user_profile.getDrawable()), "image/png"));
                }


                Utils.E("image>>" + params);
                return params;

            }
        };
        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(multipartRequest);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALLERY) {
            profileUri = data.getData();
            user_profile.setImageURI(profileUri);
            imageSelected = true;
        } else if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
            imageSelected = true;
            profileBitmap = (Bitmap) data.getExtras().get("data");
            user_profile.setImageBitmap(profileBitmap);

        }
    }


}