package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.dollop.distributor.LoginActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.UserAccount;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.UtilityTools.multipart.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity  implements View.OnClickListener {

     private static final int GALLERY = 123;
    private String mimeType;
    TextView btn_sav;
    ImageView iv_create_back_arrow;
    private String fileTimeStr = "";
    /*private byte[] inputData;
    private boolean imageCheck = false;
    private boolean im_upload_business = false;
    private boolean im_upload_kra_pin = false;
    private boolean im_upload_id_proof = false;

    private static final int SELECT_FILE1 = 1;
    private static final int SELECT_FILE2 = 2;
    private static final int SELECT_FILE3 = 3;

    private static final int PICK_business_permit = 100 ;
    private static final int PICK_kra_pin = 200 ;
    private static final int PICK_id_proof = 300 ;
*/

  /*  private String fileTimeStr_business = "",fileTimeStr_kra_pin ="",fileTimeStr_id_proof="";
    private byte[] inputDatabusiness_permit,inputDatakra_pin,inputDataid_proof;
    private String mimeType_business,mimeType_kra_pin,mimeType_id_proof;
    Boolean flag = false;*/


    EditText create_name,create_dis_location,create_store_location,create_password,create_store_address,create_email,create_key_name,create_phone,create_desi_field;
    Spinner  crate_country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

      /*  upload_id_proof = findViewById(R.id.upload_id_proof);
        upload_kra_pin = findViewById(R.id.upload_kra_pin);
        upload_business_permit = findViewById(R.id.upload_business_permit);*/
        create_name = findViewById(R.id.create_name);
        create_dis_location = findViewById(R.id.create_dis_location);
        create_store_address = findViewById(R.id.create_store_address);
        create_email = findViewById(R.id.create_email);
        create_password= findViewById(R.id.create_password);
        crate_country = findViewById(R.id.crate_country);
        create_desi_field = findViewById(R.id.create_desi_field);
        create_phone = findViewById(R.id.create_phone);
        create_store_location = findViewById(R.id.create_store_location);
        btn_sav = findViewById(R.id.btn_sav);
        iv_create_back_arrow = findViewById(R.id.iv_create_back_arrow);

       /* upload_business_permit.setOnClickListener(this);
        upload_kra_pin.setOnClickListener(this);
        upload_id_proof.setOnClickListener(this);*/
        btn_sav.setOnClickListener(this);
        iv_create_back_arrow.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {
       /* if(v== upload_id_proof){
            im_upload_id_proof = true;
            Intent intent=  getFileChooserIntent_idproof();
            startActivityForResult(intent,PICK_id_proof);
        }
        else if(v== upload_business_permit){
            im_upload_business = true;
            Intent intent=  getFileChooserIntent_business();
            startActivityForResult(intent,PICK_business_permit);
        }
        else if(v== upload_kra_pin){
            im_upload_kra_pin = true;
            Intent intent=  getFileChooserIntent_kra_pin();
            startActivityForResult(intent,PICK_kra_pin);
        }*/
          if(v== btn_sav){
            if (!UserAccount.isEmpty(create_name)){
                create_name.setError("Please enter name");
                create_name.requestFocus();
            }  else if (!UserAccount.isEmpty(create_dis_location)){
                create_dis_location.setError("Please enter Location");
                create_dis_location.requestFocus();
            }else if (!UserAccount.isEmpty(create_store_address)){
                create_store_address.setError("Please enter Store Address");
                create_store_address.requestFocus();
            }
            else if (!UserAccount.isEmpty(create_email)){
                create_email.setError("Please enter email");
                create_email.requestFocus();
            }

            else if (!UserAccount.isEmpty(create_password)){
                create_password.setError("Please enter password");
                create_password.requestFocus();
            }



            else if(crate_country.getSelectedItem().equals("Select Country")){
                Utils.T(CreateAccountActivity.this,"please select Country name");
            }

            else if (!UserAccount.isEmpty(create_desi_field)){
                create_desi_field.setError("Please enter designation ");
                create_desi_field.requestFocus();
            }

            else if (!UserAccount.isPhoneNumberLength(create_phone)){
                create_phone.setError("Please enter 10 digit mobile number");
                create_phone.requestFocus();
            }
            else {
                Register();

            }
        }
        else if(v == iv_create_back_arrow){
            finish();
        }
    }


    private Intent getFileChooserIntent_business() {
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

    private Intent getFileChooserIntent_idproof() {
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

    private Intent getFileChooserIntent_kra_pin() {
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


   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY && resultCode == Activity.RESULT_OK) {
            Uri ImageFileUri = data.getData();
            String path = "";
            Uri uri = data.getData();
            File file = new File(uri.getPath());//create path from uri
            final String[] split = file.getPath().split(":");//split the path.

            Log.e("docu01", "inent:01" + uri.getPath());

            ContentResolver cR = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            mimeType = mime.getExtensionFromMimeType(cR.getType(uri));
            Log.e("mimeType", "mimeType" + mimeType);


            if (mimeType.contains("pdf")) {

                fileTimeStr = System.currentTimeMillis() + "doc.pdf";

                if(im_upload_business){
                    upload_business_permit.setText(fileTimeStr);
                }
                else if(im_upload_kra_pin){
                    upload_kra_pin.setText(fileTimeStr);
                }
                else if(im_upload_id_proof){
                    upload_id_proof.setText(fileTimeStr);
                }

            } else if (mimeType.contains("png") || mimeType.contains("jpg") || mimeType.contains("jpeg")) {
                fileTimeStr = System.currentTimeMillis() + "image." + mimeType;

                if(im_upload_business){
                    upload_business_permit.setText(fileTimeStr);
                    upload_business_permit.setTextSize(8);
                }
                 if(im_upload_kra_pin){
                    upload_kra_pin.setText(fileTimeStr);
                    upload_kra_pin.setTextSize(8);
                }
                 if(im_upload_id_proof){
                    upload_id_proof.setText(fileTimeStr);
                    upload_id_proof.setTextSize(8);
                }


            }
            try {
                InputStream iStream = getContentResolver().openInputStream(ImageFileUri);
                inputData = getBytes(iStream);
                Log.e("inputData", "if(inputData=" + inputData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/



   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_business_permit && resultCode == Activity.RESULT_OK  && data != null){

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
            upload_business_permit.setText(fileTimeStr_business);
            upload_business_permit.setTextSize(8);
            flag = true;

            try {
                InputStream iStream = getContentResolver().openInputStream(ImageFileUri);
                inputDatabusiness_permit = getBytes(iStream);
                Log.e("inputData", "if(inputData=" + inputDatabusiness_permit);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(requestCode == PICK_id_proof && resultCode == Activity.RESULT_OK  && data != null){

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


            upload_id_proof.setText(mimeType_id_proof);
            upload_id_proof.setTextSize(8);
            flag = true;

            try {
                InputStream iStream = getContentResolver().openInputStream(ImageFileUri);
                inputDataid_proof = getBytes(iStream);
                Log.e("inputData", "if(inputData=" + inputDataid_proof);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(requestCode == PICK_kra_pin && resultCode == Activity.RESULT_OK  && data != null){
            Uri ImageFileUri = data.getData();
            String path = "";
            Uri uri = data.getData();
            File file = new File(uri.getPath());//create path from uri
            final String[] split = file.getPath().split(":");//split the path.
            Log.e("docu01", "inent:01" + uri.getPath());
            ContentResolver cR =   getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            mimeType_kra_pin = mime.getExtensionFromMimeType(cR.getType(uri));
            Log.e("mimeType", "mimeType" + mimeType_kra_pin);

           // Image_business_permit.setImageURI(ImageFileUri);
            fileTimeStr_kra_pin = System.currentTimeMillis() + "image." + mimeType_kra_pin;
            upload_kra_pin.setText(fileTimeStr_kra_pin);
            upload_kra_pin.setTextSize(8);
            flag = true;

            try {
                InputStream iStream =   getContentResolver().openInputStream(ImageFileUri);
                inputDatakra_pin = getBytes(iStream);
                Log.e("inputData", "if(inputData=" + inputDatakra_pin);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

           */
   /* if (requestCode == GALLERY && resultCode == Activity.RESULT_OK) {
            Uri ImageFileUri = data.getData();
            String path = "";
            Uri uri = data.getData();
            File file = new File(uri.getPath());//create path from uri
            final String[] split = file.getPath().split(":");//split the path.
            Log.e("docu01", "inent:01" + uri.getPath());
            ContentResolver cR =  getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            mimeType = mime.getExtensionFromMimeType(cR.getType(uri));
            Log.e("mimeType", "mimeType" + mimeType);

            if (mimeType.contains("pdf")) {
                fileTimeStr = System.currentTimeMillis() + "doc.pdf";
                upload_kra_pin.setText(fileTimeStr_kra_pin);
                upload_kra_pin.setTextSize(8);


            } else if (mimeType.contains("png") || mimeType.contains("jpg") || mimeType.contains("jpeg")) {
                Image_business_permit.setImageURI(ImageFileUri);
                fileTimeStr = System.currentTimeMillis() + "image." + mimeType;
                upload_product.setText(fileTimeStr);
                upload_product.setTextSize(8);
                flag = true;
            }
            try {
                InputStream iStream = getActivity().getContentResolver().openInputStream(ImageFileUri);
                inputData = getBytes(iStream);
                Log.e("inputData", "if(inputData=" + inputData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*//*

    }
*/
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
        // final ProgressDialog dialog=ProgressDialog.show(this,"","Loading please wait...",true);
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,Const.URL.distributor_register,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {


                        Log.e("registeration:", "Reg :-" + response);
                        dialog.dismiss();

                        try {
                            String sresponse= new String(response.data);
                            JSONObject jsonObject = new JSONObject(sresponse);
                            Toast.makeText(CreateAccountActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();


                            if (jsonObject.getInt("status")==200) {
                                String otpdata = jsonObject.getString("data");

                                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                                startActivity(intent);
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
                        Log.e("error","error::"+error);
                    }
                }) {
            @Override
            protected Map<String, DataPart> getByteData()  throws AuthFailureError, IOException {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();

                if(true) {

                 //   params.put("business_permit", new DataPart(fileTimeStr_business, inputDatabusiness_permit, "image/png"));
               //     params.put("id_proof", new DataPart(fileTimeStr_id_proof, inputDataid_proof, "image/png"));
                //    params.put("kra_pin", new DataPart(fileTimeStr_kra_pin, inputDatakra_pin, "image/png"));

                }

                Log.e("imagefornewpost","="+params);
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> signupParam  = new HashMap<>();
                signupParam.put("name",create_name.getText().toString());
                signupParam.put("city",create_dis_location.getText().toString());
                signupParam.put("store_address",create_store_address.getText().toString());
                signupParam.put("email_id",create_email.getText().toString());
                signupParam.put("password",create_password.getText().toString());
                signupParam.put("designation",create_desi_field.getText().toString());
                signupParam.put("country",crate_country.getSelectedItem().toString());
                signupParam.put("mobile",create_phone.getText().toString());
                //     signupParam.put("store_location",create_store_location.getText().toString());
                // signupParam.put("contact_name",create_key_name.getText().toString());
                // signupParam.put("id","2");
                Log.e("SignupParameter::", "param:--" + signupParam);
                return signupParam;
            }



        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                40000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(CreateAccountActivity.this).add(volleyMultipartRequest);
    }
////normal registerd without image down
  /*  private void Register() {
        {
            final Dialog dialog = Utils.initProgressDialog(this);
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.distributor_register, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();

                    Log.e("distributersign:", "signup:--" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        Toast.makeText(CreateAccountActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                        if (jsonObject.getInt("status")==200) {

                            String otpdata = jsonObject.getString("data");

                            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                            startActivity(intent);
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
                            Utils.T(CreateAccountActivity.this, errorMessage + "please check Internet connection");
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
                    HashMap<String, String> signupParam  = new HashMap<>();
                    signupParam.put("name",create_name.getText().toString());
                    signupParam.put("city",create_dis_location.getText().toString());
                    signupParam.put("store_address",create_store_address.getText().toString());
                    signupParam.put("email_id",create_email.getText().toString());
                    signupParam.put("password",create_password.getText().toString());
                    signupParam.put("cpassword",confirm_pass.getText().toString());
                    signupParam.put("designation",create_desi_field.getText().toString());
                    signupParam.put("country",crate_country.getSelectedItem().toString());
                    signupParam.put("mobile",create_phone.getText().toString());
                    //     signupParam.put("store_location",create_store_location.getText().toString());
                    // signupParam.put("contact_name",create_key_name.getText().toString());
                    // signupParam.put("id","2");
                    Log.e("SignupParameter::", "param:--" + signupParam);
                    return signupParam;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    25000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
        }
    }*/



}
