package com.dollop.distributor.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.dollop.distributor.UtilityTools.MarshMallowPermission;
import com.dollop.distributor.UtilityTools.SavedData;
import com.dollop.distributor.UtilityTools.UserAccount;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.UtilityTools.multipart.VolleyMultipartRequest;
import com.dollop.distributor.adapter.StateAdapter;
import com.dollop.distributor.database.CityAdapter;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.database.UserModel;
import com.dollop.distributor.model.CityData;
import com.dollop.distributor.model.CityResponse;
import com.dollop.distributor.model.StateData;
import com.dollop.distributor.model.StateRespone;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static com.dollop.distributor.UtilityTools.multipart.AppHelper.getFileDataFromDrawable;


public class EditProfileFragment extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_business_permit = 100;
    private static final int PICK_ID_copy = 200;
    private static final int PICK_certificateof_incorporation = 300;
    TextView tv_bussines_permit, tv_confirm_pass_text_red, tv_confim_password_match, tv_create_pass_text_red;
    ImageView user_image, edit_profile_back, cover_image;
    LinearLayout  ll_update_img;
    //private String fileTimeStr_business = "", fileTimeStr_kra_pin = "", fileTimeStr_id_proof = "";
    private byte[] inputDatabusiness_permit, inputDatakra_pin, inputDataid_proof;
    private String mimeType_business, mimeType_kra_pin, mimeType_id_proof;
    Boolean flag = false;
    Bitmap profileBitmap, coverBitmap;
    Uri profileUri, coverUri;
    public static final int GALLERY = 5;
    protected static final int CAMERA_REQUEST = 6;
    static TextView city_tv, state_tv;
    public static String to_state_name, to_state_id, city_id;

    public static final int Cover_GALLERY = 1;
    protected static final int Cover_CAMERA_REQUEST = 2;
    TextView country_error_tv, city_error_tv, country_tv;
    TextView mpesa_error_tv, till_number_error_tv, shop_discription_error_tv, minimum_order_error_tv;
    TextView btn_update_profile, fullname_error_tv, StoreName_error_tv, EmailID_error_tv, store_address_error_tv;
    TextView tool_pro_name, tool_pro_email, contact_Number_error_tv, your_designation_error_tv, KRA_Pin_error_tv;
    EditText create_name, confirm_pass,
            create_store_address, create_email, create_phone, create_desi_field, create_kra_pin, create_shopname;
    // Spinner crate_country, crate_city;
    EditText edt_description, minimum_Order_price_edt;
    LinearLayout cover_photo;
    static Dialog dialog, dialog1;
    public static ArrayList<CityResponse> mCityResponseArrayList;
    CityAdapter mCityAdapter;
    StateAdapter mStateAdapter;
    public static ArrayList<StateRespone> mStateResponeArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_editprofile);

        initializationView();
        TextChangedListenerMethod();

        getProfile();
        stateMethod();


    }


    public void stateMethod() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> hm = new HashMap<>();

        hm.put("country_id", "113");
        hm.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
        Call<StateData> call = apiService.getState(hm);
        call.enqueue(new Callback<StateData>() {
            @Override
            public void onResponse(Call<StateData> call, retrofit2.Response<StateData> response) {

                try {
                    StateData body = response.body();

                    if (body.status == 200) {

                        mStateResponeArrayList = body.data;
                        dialog.cancel();


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<StateData> call, Throwable t) {
                call.cancel();
                t.printStackTrace();

            }
        });
    }


    private Intent getFileChooserIntent() {
        String[] mimeTypes = {"image/*", "application/pdf", "video/*"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";

            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }

            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }

        return intent;
    }

    private Intent showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        return intent;
    }


    @Override
    public void onClick(View v) {
        if (v == cover_photo) {
            MarshMallowPermission marshMallowPermission = new MarshMallowPermission(EditProfileFragment.this);

            if (marshMallowPermission.checkPermissionForCamera()) {
                coverMethod();
            } else {
                marshMallowPermission.requestPermissionForCamera();
            }

        }
        if (v == ll_update_img) {
            MarshMallowPermission marshMallowPermission = new MarshMallowPermission(EditProfileFragment.this);

            if (marshMallowPermission.checkPermissionForCamera()) {
                profileMethod();
            } else {
                marshMallowPermission.requestPermissionForCamera();
            }

        } else if (v == btn_update_profile) {


            if (!UserAccount.isEmpty(create_name)) {
                //  create_name.setError("Please enter name");
                fullname_error_tv.setVisibility(View.VISIBLE);
                fullname_error_tv.setText("Enter Full Name");
                create_name.requestFocus();
            } else if (!UserAccount.isEmpty(create_shopname)) {
                // create_shopname.setError("Please enter Shop Name");
                StoreName_error_tv.setVisibility(View.VISIBLE);
                StoreName_error_tv.setText("Enter Shop Name");
                create_shopname.requestFocus();
            } else if (!UserAccount.isEmailValid(create_email)) {
                //create_email.setError("Please enter valid email address");
                EmailID_error_tv.setVisibility(View.VISIBLE);
                EmailID_error_tv.setText("Enter Valid Email-Id");
                create_email.requestFocus();
            } else if (!UserAccount.isEmpty(create_store_address)) {
                store_address_error_tv.setVisibility(View.VISIBLE);
                store_address_error_tv.setText("Enter Store address");
                //  create_store_address.setError("Please enter Store Address");
                create_store_address.requestFocus();
            } else if (!UserAccount.isPhoneNumberLength(create_phone)) {
                //  create_phone.setError("Please enter 9 digit mobile number");
                contact_Number_error_tv.setVisibility(View.VISIBLE);
                contact_Number_error_tv.setText("Enter 9 digit mobile number");
                create_phone.requestFocus();
            } else if (city_tv.getText().toString().equals("Select City")) {
                city_error_tv.setText("Select City name");
                city_error_tv.setVisibility(View.VISIBLE);
            } else if (!UserAccount.isEmpty(create_desi_field)) {
                your_designation_error_tv.setVisibility(View.VISIBLE);
                your_designation_error_tv.setText("Enter designation");
                create_desi_field.requestFocus();
            } else if (!UserAccount.isEmpty(create_kra_pin)) {
                KRA_Pin_error_tv.setVisibility(View.VISIBLE);
                KRA_Pin_error_tv.setText("Enter KRA Pin");
                create_kra_pin.requestFocus();
            } else if (!UserAccount.isEmpty(minimum_Order_price_edt)) {
                minimum_order_error_tv.setText("Enter Minimum Order");
                minimum_Order_price_edt.requestFocus();
                minimum_order_error_tv.setVisibility(View.VISIBLE);
            } else if (!UserAccount.isEmpty(edt_description)) {
                edt_description.setError("Please Enter  Description");
                edt_description.requestFocus();
            } else {
                Register();

            }


        } else if (v == edit_profile_back) {
            onBackPressed();
        } else if (v == city_tv) {

            cityListMethod();

        } else if (v == state_tv) {
            city_tv.setText("");
            city_id = "";
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
                        mStateAdapter.getFilter().filter(charSequence);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            mStateAdapter = new StateAdapter(EditProfileFragment.this, mStateResponeArrayList, "EditProfile", dialog1);
            country_RV.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
            country_RV.setAdapter(mStateAdapter);
            mStateAdapter.notifyDataSetChanged();

        }
    }

    private void coverMethod() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, Cover_CAMERA_REQUEST);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, Cover_GALLERY);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

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


    private void getProfile() {
        final Dialog dialog = Utils.initProgressDialog(EditProfileFragment.this);
        RequestQueue queue = Volley.newRequestQueue(EditProfileFragment.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.get_distributor, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("Edit Profile:", "response:--" + response);
                    // Toast.makeText(EditProfileFragment.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

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
                                create_desi_field.setText(object.getString("designation"));
                            }
                            if (!object.isNull("shop_name")) {
                                create_shopname.setText(object.getString("shop_name"));
                            }

                            if (!object.isNull("kra_pin")) {
                                create_kra_pin.setText(object.getString("kra_pin"));
                            }
/*
                            if (!object.isNull("mpesa_user_name")) {
                                create_mpesa_user_name.setText(object.getString("mpesa_user_name"));
                            }
*/

/*
                            if (!object.isNull("till_number")) {
                                create_till_number.setText(object.getString("till_number"));
                            }
*/


                            if (!object.isNull("state")) {
                                to_state_id = object.getString("state");
                            }

                            if (!object.isNull("state_name")) {
                                state_tv.setText(object.getString("state_name"));
                            }

                            if (!object.isNull("description")) {
                                edt_description.setText(object.getString("description"));
                            }


                            if (!object.isNull("image")) {
                                Picasso.get().load(Const.URL.HOST_URL + object.getString("image")).into(user_image);

                            }

                            if (!object.isNull("shop_image")) {
                                Picasso.get().load(Const.URL.HOST_URL + object.getString("shop_image")).into(cover_image);

                            }
                            if (!object.isNull("city")) {
                               city_id = object.getString("city");
                            }
                            if (!object.isNull("city_name")) {
                                city_tv.setText(object.getString("city_name"));
                            }

                            minimum_Order_price_edt.setText(object.getString("min_order_amount"));


                        }
                    } else {
                        Utils.T(EditProfileFragment.this, jsonObject.getString("message"));
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
boolean profilepicBoolean=false;

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALLERY) {
            profileUri = data.getData();
            user_image.setImageURI(profileUri);
            profilepicBoolean=true;

        } else if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {

            profileBitmap = (Bitmap) data.getExtras().get("data");
            user_image.setImageBitmap(profileBitmap);
            profilepicBoolean=true;

        }
        if (resultCode == RESULT_OK && requestCode == Cover_GALLERY) {
            coverUri = data.getData();
            cover_image.setImageURI(coverUri);
            profilepicBoolean=true;

        } else if (resultCode == RESULT_OK && requestCode == Cover_CAMERA_REQUEST) {

            coverBitmap = (Bitmap) data.getExtras().get("data");
            cover_image.setImageBitmap(coverBitmap);
            profilepicBoolean=true;

        }
    }


    private void Register() {
        final Dialog dialog = Utils.initProgressDialog(EditProfileFragment.this);
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Const.URL.update_distributor,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.e("Enter regidtration:", "fg:--");
                        Log.e("registeration:", "Reg :-" + response);
                        dialog.dismiss();
                        try {
                            String resultResponse = new String(response.data);
                            Log.e("resultResponse:", "Reg :-" + resultResponse);
                            JSONObject jsonObject = new JSONObject(resultResponse);
                            Log.e("jsonObject:", "Reg jsonObject :-" + jsonObject);

                            //  Utils.T(EditProfileFragment.this, jsonObject.getString("message"));

                            if (jsonObject.getInt("status") == 200) {


                                JSONObject data = jsonObject.getJSONObject("data");
                                UserModel userModel = new UserModel();
                                userModel.setDistributorId(data.getString("distributor_id"));
                                userModel.setImage(data.getString("image"));
                                //  userModel.setEmail(data.getString("email"));
                                userModel.setName(data.getString("name"));
                                userModel.setMobile(data.getString("contact_name"));

                                UserDataHelper.getInstance().insertData(userModel);
                                SavedData.saveactiveUser("Active");
                                SavedData.saveDisID(data.getString("distributor_id"));
                                SavedData.saveimage(data.getString("image"));
                                onBackPressed();
                                finish();
                                //  Utils.I(EditProfileFragment.this, HomeActivity.class, null);

                            } else {
                                Utils.T(EditProfileFragment.this, jsonObject.getString("message"));

                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Log.e("error", "error::" + error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> signupParam = new HashMap<>();
                signupParam.put("name", create_name.getText().toString());
                signupParam.put("shop_name", create_shopname.getText().toString());
                signupParam.put("email", create_email.getText().toString());
                signupParam.put("store_address", create_store_address.getText().toString());
                signupParam.put("country", "113");
                signupParam.put("contact_name", create_phone.getText().toString());
                signupParam.put("city", city_id);
                signupParam.put("state", to_state_id);
                signupParam.put("designation", create_desi_field.getText().toString());
                signupParam.put("kra_pin", create_kra_pin.getText().toString());
               // signupParam.put("mpesa_user_name", create_mpesa_user_name.getText().toString());
               // signupParam.put("till_number", create_till_number.getText().toString());
                signupParam.put("distributor_id", SavedData.get_Distributor_id());
                signupParam.put("description", edt_description.getText().toString());
                Log.e("SignupParameter::", "param:--" + signupParam);

                return signupParam;

            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError, IOException {
                Map<String, DataPart> params = new HashMap<>();
                if (user_image.getDrawable() != null&&profilepicBoolean) {
                    params.put("image", new DataPart(System.currentTimeMillis() + ".png",
                            getFileDataFromDrawable(getApplicationContext(),
                                    user_image.getDrawable()), "image/png"));


                }

                Log.e("imagefornewpost", "=" + params);
                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                40000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(EditProfileFragment.this).add(volleyMultipartRequest);


    }

    public void setCityList(String cityname, String id) {
        city_tv.setText(cityname);
        city_id = id;
    }

    private void initializationView() {

        dialog = new Dialog(EditProfileFragment.this, R.style.dialogstyle);
        dialog1 = new Dialog(EditProfileFragment.this, R.style.dialogstyle);

        tv_create_pass_text_red = findViewById(R.id.tv_create_pass_text_red);
        tv_confim_password_match = findViewById(R.id.tv_confim_password_match);
        tv_confirm_pass_text_red = findViewById(R.id.tv_confirm_pass_text_red);

        confirm_pass = findViewById(R.id.confirm_pass);

       // create_mpesa_user_name = findViewById(R.id.create_mpesa_user_name);
        //create_tv_login = findViewById(R.id.create_tv_login);
        create_shopname = findViewById(R.id.create_shopname);
        create_kra_pin = findViewById(R.id.create_kra_pin);
        tv_bussines_permit = findViewById(R.id.tv_bussines_permit);

        ll_update_img = findViewById(R.id.ll_update_img);
        user_image = findViewById(R.id.user_image);
        btn_update_profile = findViewById(R.id.btn_update_profile);
        tool_pro_name = findViewById(R.id.tool_pro_name);
        tool_pro_email = findViewById(R.id.tool_pro_email);
        edit_profile_back = findViewById(R.id.edit_profile_back);
        edt_description = findViewById(R.id.edt_description);
        cover_photo = findViewById(R.id.cover_photo);
        cover_image = findViewById(R.id.cover_image);
        fullname_error_tv = findViewById(R.id.fullname_error_tv);
        StoreName_error_tv = findViewById(R.id.StoreName_error_tv);
        EmailID_error_tv = findViewById(R.id.EmailID_error_tv);
        contact_Number_error_tv = findViewById(R.id.contact_Number_error_tv);
        your_designation_error_tv = findViewById(R.id.your_designation_error_tv);
        KRA_Pin_error_tv = findViewById(R.id.KRA_Pin_error_tv);
        mpesa_error_tv = findViewById(R.id.mpesa_error_tv);
        till_number_error_tv = findViewById(R.id.till_number_error_tv);
        shop_discription_error_tv = findViewById(R.id.shop_discription_error_tv);
        minimum_Order_price_edt = findViewById(R.id.minimum_Order_price_edt);
        minimum_order_error_tv = findViewById(R.id.minimum_order_error_tv);
        country_error_tv = findViewById(R.id.country_error_tv);
        city_error_tv = findViewById(R.id.city_error_tv);
        country_tv = findViewById(R.id.country_tv);
        create_name = findViewById(R.id.create_name);
        // crate_city = findViewById(R.id.crate_city);
        create_store_address = findViewById(R.id.create_store_address);
        create_email = findViewById(R.id.create_email);
        store_address_error_tv = findViewById(R.id.store_address_error_tv);
        // crate_country = findViewById(R.id.crate_country);
        create_desi_field = findViewById(R.id.create_desi_field);
        create_phone = findViewById(R.id.create_phone);
        city_tv = findViewById(R.id.city_tv);
        state_tv = findViewById(R.id.state_tv);
        create_email.setEnabled(false);
        create_phone.setEnabled(false);
        ll_update_img.setOnClickListener(this);
        btn_update_profile.setOnClickListener(this);
        edit_profile_back.setOnClickListener(this);
        cover_photo.setOnClickListener(this);
        city_tv.setOnClickListener(this);
        state_tv.setOnClickListener(this);

        mCityResponseArrayList = new ArrayList<>();
        mStateResponeArrayList = new ArrayList<>();

    }

    private void TextChangedListenerMethod() {

        create_name.addTextChangedListener(new TextWatcher() {
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
                if (UserAccount.isEmpty(create_name)) {
                    fullname_error_tv.setVisibility(View.GONE);
                } else {
                    fullname_error_tv.setVisibility(View.VISIBLE);
                    fullname_error_tv.setText("Enter Full Name");
                }

            }
        });

        create_shopname.addTextChangedListener(new TextWatcher() {
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
                if (UserAccount.isEmpty(create_shopname)) {
                    StoreName_error_tv.setVisibility(View.GONE);
                } else {
                    StoreName_error_tv.setVisibility(View.VISIBLE);
                    StoreName_error_tv.setText("Enter Shop Name");
                }

            }
        });

        create_email.addTextChangedListener(new TextWatcher() {
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
                if (UserAccount.isEmailValid(create_email)) {
                    EmailID_error_tv.setVisibility(View.GONE);
                } else {
                    EmailID_error_tv.setVisibility(View.VISIBLE);
                    EmailID_error_tv.setText("Enter Valid Email-Id");
                }

            }
        });


        create_store_address.addTextChangedListener(new TextWatcher() {
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
                if (UserAccount.isEmpty(create_store_address)) {
                    store_address_error_tv.setVisibility(View.GONE);
                } else {
                    store_address_error_tv.setVisibility(View.VISIBLE);
                    store_address_error_tv.setText("Enter Store address");
                }

            }
        });


        create_phone.addTextChangedListener(new TextWatcher() {
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
                if (UserAccount.isPhoneNumberLength(create_phone)) {
                    contact_Number_error_tv.setVisibility(View.GONE);
                } else {
                    contact_Number_error_tv.setVisibility(View.VISIBLE);
                    contact_Number_error_tv.setText("Enter 9 digit mobile number");
                }

            }
        });

        create_desi_field.addTextChangedListener(new TextWatcher() {
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
                if (UserAccount.isEmpty(create_desi_field)) {
                    your_designation_error_tv.setVisibility(View.GONE);
                } else {

                    your_designation_error_tv.setVisibility(View.VISIBLE);
                    your_designation_error_tv.setText("Enter designation");
                }

            }
        });
        create_kra_pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (UserAccount.isEmpty(create_kra_pin) && UserAccount.isValidKRAPin(create_kra_pin)) {
                    KRA_Pin_error_tv.setVisibility(View.GONE);
                } else {

                    KRA_Pin_error_tv.setVisibility(View.VISIBLE);
                    KRA_Pin_error_tv.setFocusable(true);
                }
            }
        });




/*
        create_till_number.addTextChangedListener(new TextWatcher() {
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
                if (UserAccount.isEmpty(create_till_number)) {
                    till_number_error_tv.setVisibility(View.GONE);
                } else {
                    till_number_error_tv.setVisibility(View.VISIBLE);
                    till_number_error_tv.setText("Enter Till Number");

                }

            }
        });
*/

        minimum_Order_price_edt.addTextChangedListener(new TextWatcher() {
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
                if (UserAccount.isEmpty(minimum_Order_price_edt)) {
                    minimum_order_error_tv.setVisibility(View.GONE);
                } else {
                    minimum_order_error_tv.setVisibility(View.VISIBLE);
                    minimum_order_error_tv.setText("Enter Minimum Order");

                }

            }
        });

        edt_description.addTextChangedListener(new TextWatcher() {
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
                if (UserAccount.isEmpty(edt_description)) {
                    shop_discription_error_tv.setVisibility(View.GONE);
                } else {
                    shop_discription_error_tv.setText("Enter Shop Description ");
                    shop_discription_error_tv.setVisibility(View.VISIBLE);

                }

            }
        });

    }

    private void cityListMethod() {
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
                    mCityAdapter.getFilter().filter(charSequence);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mCityAdapter = new CityAdapter(EditProfileFragment.this, mCityResponseArrayList, dialog, "edit");
        country_RV.setLayoutManager(new LinearLayoutManager(EditProfileFragment.this, RecyclerView.VERTICAL, false));
        country_RV.setAdapter(mCityAdapter);
        mCityAdapter.notifyDataSetChanged();

    }


    public static void cityToMethod(String id) {


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> hm = new HashMap<>();

        hm.put("state_id", id);
        hm.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
        Call<CityData> call = apiService.get_city(hm);
        call.enqueue(new Callback<CityData>() {
            @Override
            public void onResponse(Call<CityData> call, retrofit2.Response<CityData> response) {

                try {
                    CityData body = response.body();
                    if (body.status == 200) {

                        mCityResponseArrayList = body.data;
                        state_tv.setText(to_state_name);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<CityData> call, Throwable t) {
                call.cancel();
                t.printStackTrace();

            }
        });
    }


}
