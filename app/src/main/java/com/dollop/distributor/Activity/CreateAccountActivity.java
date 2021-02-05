package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.MarshMallowPermission;
import com.dollop.distributor.UtilityTools.TouchViewPagerIMageView;
import com.dollop.distributor.UtilityTools.UserAccount;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.UtilityTools.multipart.VolleyMultipartRequest;
import com.dollop.distributor.adapter.OfferAdapter;
import com.dollop.distributor.adapter.StateAdapter;
import com.dollop.distributor.database.CityAdapter;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.CityData;
import com.dollop.distributor.model.CityResponse;
import com.dollop.distributor.model.StateData;
import com.dollop.distributor.model.StateRespone;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static com.dollop.distributor.UtilityTools.multipart.AppHelper.getFileDataFromDrawable;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_business_permit = 100;
    private static final int PICK_ID_copy = 200;
    private static final int PICK_certificateof_incorporation = 300;
    private static final int RETURN_POLICY = 400;

    public static final int Cover_GALLERY = 1;
    protected static final int Cover_CAMERA_REQUEST = 2;
    private static final String TAG = "CreateAccountActivity";
    Bitmap profileBitmap, coverBitmap, certificateof_incorporationBitmap, business_permitBitmap, copyofdirectorBitmap;
    Uri profileUri, coverUri, certificateof_incorporationUri, business_permitUri, copyofdirectorUri;
    public static final int GALLERY = 5;
    protected static final int CAMERA_REQUEST = 6;
    boolean certificateofCamera = false, current_business = false, copy_of_director = false;
    public static final int GALLERY_certificateof_incorporation = 7;
    protected static final int CAMERA_REQUEST_certificateof_incorporation = 8;
    public static final int GALLERY_business_permit = 9;
    protected static final int CAMERA_REQUEST_business_permit = 10;

    public static final int GALLERY_PICK_ID = 11;
    protected static final int CAMERA_REQUEST_PICK_ID = 12;


    private ScaleGestureDetector mScaleGestureDetector;
    ImageView urrent_business_image;
    TouchViewPagerIMageView touchViewPagerIMageView;
    TextView tv_return_policy;
    TextView btn_sav, tv_bussines_permit, create_tv_login, tv_create_pass_text_red;
    ImageView iv_create_back_arrow, cover_image, user_image;
    LinearLayout cover_photo, ll_update_img;
    RelativeLayout ll_createpass, ll_confim;
    LinearLayout ll_bussines_permit, ll_return_policy;
    private String fileTimeStr_business = "", fileTimeStr_kra_pin = "", fileTimeStr_id_proof = "", file_return_policy;
    private byte[] inputDatabusiness_permit, inputDatakra_pin, inputDataid_proof, intReturn_policy;
    private String mimeType_business, mimeType_kra_pin, mimeType_id_proof, return_policy;
    Boolean flag = false;
    static TextView city_tv, state_tv, state_error_tv, city_error_tv;
    TextView country_tv, your_designation_error_tv, KRA_Pin_error_tv, mpesa_error_tv;
    TextView fullname_error_tv, StoreName_error_tv, EmailID_error_tv, store_address_error_tv;
    TextView password_error_tv, confirm_password_error_tv, contact_Number_error_tv, country_error_tv;
    EditText create_name, create_password, confirm_pass,
            create_store_address, create_email, create_phone, create_desi_field, create_kra_pin, create_shopname;
    //  Spinner /*crate_country,*/ crate_city;
    EditText minimum_Order_price_edt, edt_description;
    TextView return_policy_error_tv;
    TextView till_number_error_tv, minimum_order_error_tv, shop_discription_error_tv, select_current_business_error;
    ImageView business_permit_image;
    private float mScaleFactor = 1.0f;
    static Dialog dialog;
    static Dialog dialog1;
    static ArrayList<CityResponse> mCityResponseArrayList;
    public static ArrayList<StateRespone> mStateResponeArrayList;
    public static String to_state_name, to_state_id, city_id;
    boolean pdfUploadCurrentBusiness = false, pdfCopy = false, Pdfcertificate = false;
    CityAdapter mCityAdapter;
    StateAdapter mStateAdapter;
    ImageView show_confirm_password_img, show_password_img;
    String passwordVisiblity = "hide";
    String con_passwordVisiblity = "hide";
    private boolean otpScreenBoolean = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        initializationView();
        TextChangedListenerMethod();
        stateMethod();

    }


    @Override
    public void onClick(View v) {
        if (v == ll_bussines_permit) {


            final CharSequence[] options = {"Camera", "Gallery", "PDF", "Cancel"};

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Choose your profile picture");

            builder.setItems(options, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (options[item].equals("Camera")) {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA_REQUEST_business_permit);

                    } else if (options[item].equals("Gallery")) {
                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(gallery, GALLERY_business_permit);

                    } else if (options[item].equals("PDF")) {

                        //Intent intent = getFileChooserIntent1();
                        //startActivityForResult(intent, PICK_certificateof_incorporation);
                        Intent intent = getFileChooserIntent1();
                        startActivityForResult(intent, PICK_business_permit);

                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });

            builder.show();
        } else if (v == btn_sav) {

            if (cover_image.getDrawable() == null) {
                Utils.T(CreateAccountActivity.this, "Select Shop Image");

            } else if (!UserAccount.isEmpty(create_name)) {
                // create_name.setError("Enter name");
                fullname_error_tv.setVisibility(View.VISIBLE);
                fullname_error_tv.setText("Enter Full Name");
                create_name.requestFocus();
            } else if (!UserAccount.isEmpty(create_shopname)) {
                // create_shopname.setError("Enter Shop Name");
                StoreName_error_tv.setVisibility(View.VISIBLE);
                StoreName_error_tv.setText("Enter Shop Name");
                create_shopname.requestFocus();
            } else if (!UserAccount.isEmailValid(create_email)) {
                EmailID_error_tv.setVisibility(View.VISIBLE);
                EmailID_error_tv.setText("Enter Valid Email-Id");
                create_email.requestFocus();
            } else if (!UserAccount.isEmpty(create_store_address)) {
                store_address_error_tv.setVisibility(View.VISIBLE);
                store_address_error_tv.setText("Enter Store address");
                create_store_address.requestFocus();
            } else if (!UserAccount.isPasswordValid(create_password)) {
                password_error_tv.setVisibility(View.VISIBLE);
                password_error_tv.setText("Password must be at least 6 characters");
                create_password.requestFocus();
            } else if (!UserAccount.isPasswordValid(confirm_pass)) {

                confirm_password_error_tv.setText("Password must be at least 6 characters");
                confirm_password_error_tv.setVisibility(View.VISIBLE);
                confirm_pass.requestFocus();

            } else if (!UserAccount.isPhoneNumberLength(create_phone)) {
                contact_Number_error_tv.setVisibility(View.VISIBLE);
                contact_Number_error_tv.setText("Enter 9 digit mobile number");
                create_phone.requestFocus();
            } else if (state_tv.getText().equals("")) {

                state_error_tv.setText("Select state name");
                state_error_tv.setVisibility(View.VISIBLE);

            } else if (city_tv.getText().toString().equals("")) {
                city_error_tv.setText("Select city name");
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
            } else if (tv_bussines_permit.getText().equals("Upload")) {

                select_current_business_error.setVisibility(View.VISIBLE);
                select_current_business_error.setText("Upload Current Bussines Permit");

            } else if (tv_return_policy.getText().equals("Upload")) {
                return_policy_error_tv.setVisibility(View.VISIBLE);
                return_policy_error_tv.setText("Upload Return Policy");
            } else if (!UserAccount.isEmpty(edt_description)) {
                edt_description.requestFocus();
                shop_discription_error_tv.setText("Enter Shop Description");
                shop_discription_error_tv.setVisibility(View.VISIBLE);
            } else {

                if (!create_password.getText().toString().equals(confirm_pass.getText().toString())) {
                    //  Utils.T(CreateAccountActivity.this, "password and confirm password not matched");
                    confirm_password_error_tv.setText("Password and confirm password not matched");
                    confirm_password_error_tv.setVisibility(View.VISIBLE);
                } else {
                    Register();
                }
            }
        } else if (v == iv_create_back_arrow) {

            finish();
        } else if (v == create_tv_login) {

            finish();

        } else if (v == ll_return_policy) {
            MarshMallowPermission marshMallowPermission = new MarshMallowPermission(CreateAccountActivity.this);

            if (marshMallowPermission.checkPermissionForCamera()) {

                Intent intent = getFileChooserIntent1();
                startActivityForResult(intent, RETURN_POLICY);

            } else {
                marshMallowPermission.requestPermissionForCamera();
            }

        } else if (v == cover_photo) {
            MarshMallowPermission marshMallowPermission = new MarshMallowPermission(CreateAccountActivity.this);

            if (marshMallowPermission.checkPermissionForCamera()) {
                coverMethod();
            } else {
                marshMallowPermission.requestPermissionForCamera();
            }
        } else if (v == ll_update_img) {
            MarshMallowPermission marshMallowPermission = new MarshMallowPermission(CreateAccountActivity.this);

            if (marshMallowPermission.checkPermissionForCamera()) {
                profileMethod();
            } else {
                marshMallowPermission.requestPermissionForCamera();
            }
        } else if (v == business_permit_image) {
            if (current_business) {


                final Dialog dialog1 = new Dialog(CreateAccountActivity.this, R.style.dialogstyle); // Context, this, etc.
                dialog1.setContentView(R.layout.zoom_image_layout);
                touchViewPagerIMageView = dialog1.findViewById(R.id.image_preview);
                touchViewPagerIMageView.setImageBitmap(business_permitBitmap);
                dialog1.setCanceledOnTouchOutside(true);
                mScaleGestureDetector = new ScaleGestureDetector(CreateAccountActivity.this, new ScaleListener());
                dialog1.setCancelable(true);

                dialog1.show();

            } else {

                final Dialog dialog1 = new Dialog(CreateAccountActivity.this, R.style.dialogstyle); // Context, this, etc.
                dialog1.setContentView(R.layout.zoom_image_layout);
                touchViewPagerIMageView = dialog1.findViewById(R.id.image_preview);
                touchViewPagerIMageView.setImageURI(business_permitUri);
                dialog1.setCanceledOnTouchOutside(true);
                mScaleGestureDetector = new ScaleGestureDetector(CreateAccountActivity.this, new ScaleListener());
                dialog1.setCancelable(true);

                dialog1.show();

            }
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

            mStateAdapter = new StateAdapter(CreateAccountActivity.this, mStateResponeArrayList, "FROM", dialog1);
            country_RV.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
            country_RV.setAdapter(mStateAdapter);
            mStateAdapter.notifyDataSetChanged();

        } else if (v == show_password_img) {
            if (passwordVisiblity.equals("hide")) {
                passwordVisiblity = "show";
                show_password_img.setBackgroundResource(R.drawable.ic_baseline_visibility_off_24);
                create_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

            } else if (passwordVisiblity.equals("show")) {
                passwordVisiblity = "hide";
                show_password_img.setBackgroundResource(R.drawable.ic_baseline_visibility_24);
                create_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        } else if (v == show_confirm_password_img) {
            if (con_passwordVisiblity.equals("hide")) {
                con_passwordVisiblity = "show";
                show_confirm_password_img.setBackgroundResource(R.drawable.ic_baseline_visibility_off_24);
                confirm_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

            } else if (con_passwordVisiblity.equals("show")) {
                con_passwordVisiblity = "hide";
                show_confirm_password_img.setBackgroundResource(R.drawable.ic_baseline_visibility_24);
                confirm_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }

    }

    @Override
    public void finish() {

        if (otpScreenBoolean) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(" Cancel ?")
                    .setMessage("You will lose all progress")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Utils.I_clear(CreateAccountActivity.this, LoginActivity.class, null);
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();

        }else{
            super.finish();
        }


    }

    public void stateMethod() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> hm = new HashMap<>();

        Log.d(TAG, "stateMethod: " + UserDataHelper.getInstance().getList());
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

    public void setCityList(String cityname, String id) {
        city_tv.setText(cityname);
        city_id = id;
        city_error_tv.setVisibility(View.GONE);
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
                        state_error_tv.setVisibility(View.GONE);

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

        mCityAdapter = new CityAdapter(CreateAccountActivity.this, mCityResponseArrayList, dialog, "creatAccount");
        country_RV.setLayoutManager(new LinearLayoutManager(CreateAccountActivity.this, RecyclerView.VERTICAL, false));
        country_RV.setAdapter(mCityAdapter);
        mCityAdapter.notifyDataSetChanged();

    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        mScaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f,
                    Math.min(mScaleFactor, 10.0f));
            touchViewPagerIMageView.setScaleX(mScaleFactor);
            touchViewPagerIMageView.setScaleY(mScaleFactor);
            return true;
        }
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

    private Intent getFileChooserIntent1() {

        String[] mimeTypes = {"application/pdf"};
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

    private Intent getFileChooserIntent() {

        String[] mimeTypes = {"image/*", "application/pdf"};
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
//
//        String[] mimeTypes = {"image/*", "application/pdf"};
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
//            if (mimeTypes.length > 0) {
//                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
//            }
//        } else {
//            String mimeTypesStr = "";
//
//            for (String mimeType : mimeTypes) {
//                mimeTypesStr += mimeType + "|";
//            }
//
//            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
//        }
//        return intent;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_business_permit && resultCode == Activity.RESULT_OK && data != null) {
            pdfUploadCurrentBusiness = true;
            business_permit_image.setVisibility(View.GONE);
            Uri ImageFileUri = data.getData();
            String path = "";
            Uri uri = data.getData();
            File file = new File(uri.getPath());//create path from uri
            final String[] split = file.getPath().split(":");//split the path.
            Log.e("docu01", "inent:01" + uri.getPath());
            ContentResolver cR = CreateAccountActivity.this.getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            mimeType_business = mime.getExtensionFromMimeType(cR.getType(uri));
            Log.e("mimeType", "mimeType" + mimeType_business);

            // Image_business_permit.setImageURI(ImageFileUri);
            fileTimeStr_business = System.currentTimeMillis() + "image." + mimeType_business;
            tv_bussines_permit.setText(fileTimeStr_business);
            tv_bussines_permit.setTextSize(15);
            flag = true;

            try {
                InputStream iStream = getContentResolver().openInputStream(ImageFileUri);
                inputDatabusiness_permit = getBytes(iStream);
                Log.e("inputData", "if(inputData=" + inputDatabusiness_permit);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_OK && requestCode == GALLERY_business_permit) {
            pdfUploadCurrentBusiness = false;
            business_permitUri = data.getData();
            tv_bussines_permit.setText("Upload.");
            business_permit_image.setImageURI(business_permitUri);
            business_permit_image.setVisibility(View.VISIBLE);
            current_business = false;

        } else if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_business_permit) {
            pdfUploadCurrentBusiness = false;
            business_permitBitmap = (Bitmap) data.getExtras().get("data");
            business_permit_image.setImageBitmap(business_permitBitmap);
            business_permit_image.setVisibility(View.VISIBLE);
            current_business = true;
            tv_bussines_permit.setText("Upload.");
        } else if (requestCode == PICK_ID_copy && resultCode == Activity.RESULT_OK && data != null) {

            pdfCopy = true;


            Uri ImageFileUri = data.getData();
            String path = "";
            Uri uri = data.getData();
            File file = new File(uri.getPath());//create path from uri
            final String[] split = file.getPath().split(":");//split the path.
            Log.e("docu01", "inent:01" + uri.getPath());
            ContentResolver cR = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            mimeType_id_proof = mime.getExtensionFromMimeType(cR.getType(uri));
            Log.e("mimeType", "mimeType" + mimeType_id_proof);

            // Image_business_permit.setImageURI(ImageFileUri);
            fileTimeStr_id_proof = System.currentTimeMillis() + "image." + mimeType_id_proof;


            flag = true;

            try {
                InputStream iStream = getContentResolver().openInputStream(ImageFileUri);
                inputDataid_proof = getBytes(iStream);
                Log.e("inputData", "if(inputData=" + inputDataid_proof);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_OK && requestCode == GALLERY_PICK_ID) {
            pdfCopy = false;

            copyofdirectorUri = data.getData();

            copy_of_director = false;

        } else if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_PICK_ID) {
            pdfCopy = false;

            copyofdirectorBitmap = (Bitmap) data.getExtras().get("data");

            copy_of_director = true;

        } else if (requestCode == PICK_certificateof_incorporation && resultCode == Activity.RESULT_OK && data != null) {
            Pdfcertificate = true;


            Uri ImageFileUri = data.getData();
            String path = "";
            Uri uri = data.getData();
            File file = new File(uri.getPath());//create path from uri
            final String[] split = file.getPath().split(":");//split the path.
            Log.e("docu01", "inent:01" + uri.getPath());
            ContentResolver cR = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            mimeType_kra_pin = mime.getExtensionFromMimeType(cR.getType(uri));
            Log.e("mimeType", "mimeType" + mimeType_kra_pin);

            // Image_business_permit.setImageURI(ImageFileUri);
            fileTimeStr_kra_pin = System.currentTimeMillis() + "image." + mimeType_kra_pin;

            flag = true;

            try {
                InputStream iStream = getContentResolver().openInputStream(ImageFileUri);
                inputDatakra_pin = getBytes(iStream);
                Log.e("inputData", "if(inputData=" + inputDatakra_pin);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_OK && requestCode == GALLERY_certificateof_incorporation) {
            Pdfcertificate = false;
            //     tv_certificate.setText("Upload.");
            certificateof_incorporationUri = data.getData();

            certificateofCamera = false;

        } else if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_certificateof_incorporation) {
            Pdfcertificate = false;
            certificateof_incorporationBitmap = (Bitmap) data.getExtras().get("data");

            certificateofCamera = true;

        } else if (requestCode == RETURN_POLICY && resultCode == Activity.RESULT_OK && data != null) {


            Uri ImageFileUri = data.getData();
            String path = "";
            Uri uri = data.getData();
            File file = new File(uri.getPath());//create path from uri
            final String[] split = file.getPath().split(":");//split the path.
            Log.e("docu01", "inent:01" + uri.getPath());
            ContentResolver cR = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return_policy = mime.getExtensionFromMimeType(cR.getType(uri));
            Log.e("mimeType", "mimeType" + return_policy);

            // Image_business_permit.setImageURI(ImageFileUri);
            file_return_policy = System.currentTimeMillis() + "image." + return_policy;

            tv_return_policy.setText(file_return_policy);
            tv_return_policy.setTextSize(15);
            flag = true;

            try {
                InputStream iStream = getContentResolver().openInputStream(ImageFileUri);
                intReturn_policy = getBytes(iStream);
                Log.e("inputData", "if(inputData=" + intReturn_policy);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_OK && requestCode == Cover_GALLERY) {
            coverUri = data.getData();
            cover_image.setImageURI(coverUri);

        } else if (resultCode == RESULT_OK && requestCode == Cover_CAMERA_REQUEST) {

            coverBitmap = (Bitmap) data.getExtras().get("data");
            cover_image.setImageBitmap(coverBitmap);


        } else if (resultCode == RESULT_OK && requestCode == GALLERY) {

            profileUri = data.getData();
            user_image.setImageURI(profileUri);

        } else if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {

            profileBitmap = (Bitmap) data.getExtras().get("data");
            user_image.setImageBitmap(profileBitmap);


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

    private void Register() {
        final Dialog dialog = Utils.initProgressDialog(CreateAccountActivity.this);
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Const.URL.distributor_register,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        dialog.dismiss();
                        try {
                            String resultResponse = new String(response.data);
                            JSONObject jsonObject = new JSONObject(resultResponse);

                            if (jsonObject.getInt("status") == 200) {

                                String otpdata = jsonObject.getString("data");
                                Bundle bundle = new Bundle();
                                bundle.putString("otp", otpdata);
                                bundle.putString("mobile", create_phone.getText().toString());
                                Utils.I(CreateAccountActivity.this, OtpSendActivity.class, bundle);
                                otpScreenBoolean = false;
                                finish();
                            } else {
                                Utils.T(CreateAccountActivity.this, jsonObject.getString("message"));
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

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> signupParam = new HashMap<>();
                signupParam.put("name", create_name.getText().toString());
                signupParam.put("shop_name", create_shopname.getText().toString());
                signupParam.put("email", create_email.getText().toString());
                signupParam.put("store_address", create_store_address.getText().toString());
                signupParam.put("password", create_password.getText().toString());
                signupParam.put("mobile", create_phone.getText().toString());
                signupParam.put("city", city_id);
                signupParam.put("state", to_state_id);
                signupParam.put("country", "113");
                signupParam.put("designation", create_desi_field.getText().toString());
                signupParam.put("kra_pin", create_kra_pin.getText().toString());

                signupParam.put("description", edt_description.getText().toString());
                signupParam.put("min_order_amount", minimum_Order_price_edt.getText().toString());
                Log.e("param", "" + signupParam);

                return signupParam;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError, IOException {
                Map<String, DataPart> params = new HashMap<>();
                if (flag) {
                    params.put("return_policy", new DataPart(file_return_policy, intReturn_policy, "pdf"));
                }
                if (pdfUploadCurrentBusiness) {
                    params.put("business_permit", new DataPart(fileTimeStr_business, inputDatabusiness_permit, "pdf"));

                } else {
                    if (business_permit_image.getDrawable() != null) {
                        params.put("business_permit", new DataPart(System.currentTimeMillis() + ".png",
                                getFileDataFromDrawable(getApplicationContext(),
                                        business_permit_image.getDrawable()), "image/png"));


                    }
                }

                if (pdfCopy) {
                    params.put("id_proof", new DataPart(fileTimeStr_id_proof, inputDataid_proof, "pdf"));

                }
                if (Pdfcertificate) {
                    params.put("certificate_img", new DataPart(fileTimeStr_kra_pin, inputDatakra_pin, "pdf"));

                }


                if (cover_image.getDrawable() != null) {
                    params.put("shop_image", new DataPart(System.currentTimeMillis() + ".png",
                            getFileDataFromDrawable(getApplicationContext(),
                                    cover_image.getDrawable()), "image/png"));


                }

              /*  if (user_image.getDrawable() != null) {
                    params.put("image", new DataPart(System.currentTimeMillis() + ".png",
                            getFileDataFromDrawable(getApplicationContext(),
                                    user_image.getDrawable()), "image/png"));


                }
*/
                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                40000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(CreateAccountActivity.this).add(volleyMultipartRequest);


    }

    private void TextChangedListenerMethod() {
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

        create_password.addTextChangedListener(new TextWatcher() {
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
                if (UserAccount.isPasswordValid(create_password)) {
                    password_error_tv.setVisibility(View.GONE);
                } else {
                    password_error_tv.setVisibility(View.VISIBLE);
                    password_error_tv.setText("Password must be at least 6 characters");
                }

            }
        });
        confirm_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                if (confirm_pass.getText().toString().length() > create_password.getText().toString().length()) {
                    confirm_password_error_tv.setText("password and confirm password not match!");
                    confirm_password_error_tv.setVisibility(View.VISIBLE);
                } else if (!confirm_pass.getText().toString().equals(create_password.getText().toString())) {
                    confirm_password_error_tv.setText("password and confirm password not match!");
                    confirm_password_error_tv.setVisibility(View.VISIBLE);
                } else {
                    confirm_password_error_tv.setVisibility(View.GONE);
                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {


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


    private void initializationView() {
        dialog = new Dialog(CreateAccountActivity.this, R.style.dialogstyle);
        dialog1 = new Dialog(CreateAccountActivity.this, R.style.dialogstyle);
        tv_create_pass_text_red = findViewById(R.id.tv_create_pass_text_red);
        ll_createpass = findViewById(R.id.ll_createpass);
        ll_confim = findViewById(R.id.ll_confim);
        confirm_pass = findViewById(R.id.confirm_pass);

        create_tv_login = findViewById(R.id.create_tv_login);
        create_shopname = findViewById(R.id.create_shopname);
        create_kra_pin = findViewById(R.id.create_kra_pin);
        tv_bussines_permit = findViewById(R.id.tv_bussines_permit);


        ll_bussines_permit = findViewById(R.id.ll_bussines_permit);

        create_name = findViewById(R.id.create_name);
        // crate_city = findViewById(R.id.crate_city);
        create_store_address = findViewById(R.id.create_store_address);
        create_email = findViewById(R.id.create_email);
        create_password = findViewById(R.id.create_password);
        country_tv = findViewById(R.id.country_tv);
        city_tv = findViewById(R.id.city_tv);
        state_tv = findViewById(R.id.state_tv);
        show_confirm_password_img = findViewById(R.id.show_confirm_password_img);
        show_password_img = findViewById(R.id.show_password_img);
        create_desi_field = findViewById(R.id.create_desi_field);
        create_phone = findViewById(R.id.create_phone);
        btn_sav = findViewById(R.id.btn_sav);
        iv_create_back_arrow = findViewById(R.id.iv_create_back_arrow);
        fullname_error_tv = findViewById(R.id.fullname_error_tv);
        EmailID_error_tv = findViewById(R.id.EmailID_error_tv);
        StoreName_error_tv = findViewById(R.id.StoreName_error_tv);
        store_address_error_tv = findViewById(R.id.store_address_error_tv);
        password_error_tv = findViewById(R.id.password_error_tv);
        confirm_password_error_tv = findViewById(R.id.confirm_password_error_tv);
        contact_Number_error_tv = findViewById(R.id.contact_Number_error_tv);
        country_error_tv = findViewById(R.id.country_error_tv);
        city_error_tv = findViewById(R.id.city_error_tv);
        your_designation_error_tv = findViewById(R.id.your_designation_error_tv);
        KRA_Pin_error_tv = findViewById(R.id.KRA_Pin_error_tv);
        mpesa_error_tv = findViewById(R.id.mpesa_error_tv);
        till_number_error_tv = findViewById(R.id.till_number_error_tv);
        minimum_Order_price_edt = findViewById(R.id.minimum_Order_price_edt);
        minimum_order_error_tv = findViewById(R.id.minimum_order_error_tv);
        ll_return_policy = findViewById(R.id.ll_return_policy);
        tv_return_policy = findViewById(R.id.tv_return_policy);
        edt_description = findViewById(R.id.edt_description);
        shop_discription_error_tv = findViewById(R.id.shop_discription_error_tv);
        cover_photo = findViewById(R.id.cover_photo);
        cover_image = findViewById(R.id.cover_image);
        ll_update_img = findViewById(R.id.ll_update_img);
        user_image = findViewById(R.id.user_image);
        select_current_business_error = findViewById(R.id.select_current_business_error);

        return_policy_error_tv = findViewById(R.id.return_policy_error_tv);

        business_permit_image = findViewById(R.id.business_permit_image);
        // certificate_image = findViewById(R.id.certificate_image);

        state_error_tv = findViewById(R.id.state_error_tv);

        ll_bussines_permit.setOnClickListener(this);

        btn_sav.setOnClickListener(this);
        iv_create_back_arrow.setOnClickListener(this);
        create_tv_login.setOnClickListener(this);
        ll_return_policy.setOnClickListener(this);
        cover_photo.setOnClickListener(this);
        ll_update_img.setOnClickListener(this);
        business_permit_image.setOnClickListener(this);


        city_tv.setOnClickListener(this);
        state_tv.setOnClickListener(this);
        show_confirm_password_img.setOnClickListener(this);

        show_password_img.setOnClickListener(this);
        mCityResponseArrayList = new ArrayList<>();
        mStateResponeArrayList = new ArrayList<>();

       


      /*  crate_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!crate_city.getSelectedItem().equals("Select City")) {
                    city_error_tv.setVisibility(View.GONE);
                }
                ((TextView) parent.getChildAt(0)).setTextSize(18);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/

     /*   crate_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!crate_country.getSelectedItem().equals("Select Country")) {
                    country_error_tv.setVisibility(View.GONE);
                }
                //  ((TextView) parent.getChildAt(0)).setTextSize(18);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/

    /*  AdapterView.OnItemSelectedListener OnCatSpinnerCL = new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                ((TextView) parent.getChildAt(0)).setTextSize(5);

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        };*/

    }

}
