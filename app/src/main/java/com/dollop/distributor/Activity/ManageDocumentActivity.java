package com.dollop.distributor.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;


import android.webkit.WebView;
import android.widget.ImageView;
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
import com.dollop.distributor.UtilityTools.NetworkUtil;
import com.dollop.distributor.UtilityTools.SavedData;
import com.dollop.distributor.UtilityTools.TouchViewPagerIMageView;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.retrofit.ApiClient;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.M)
public class ManageDocumentActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mgdoc_back, permit_image, id_copy_image, certificate_image;
    String permit_image_url;
    String copy_image_url;
    String certificate_image_url;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    TouchViewPagerIMageView image_preview;

    String return_policy = "";

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_document);

        initializationView();

        boolean status = NetworkUtil.getConnectivityStatus(ManageDocumentActivity.this);

        if (status == true) {
        } else {
            Utils.T(ManageDocumentActivity.this, "No Internet Connection available. Please try again");
        }

        getProfile();


        webView = (WebView)findViewById(R.id.help_webview);
        webView.getSettings().setJavaScriptEnabled(true);


        mScaleGestureDetector = new ScaleGestureDetector(ManageDocumentActivity.this, new ScaleListener());
    }

    private void initializationView() {

        mgdoc_back = findViewById(R.id.mgdoc_back);
        permit_image = findViewById(R.id.permit_image);
        id_copy_image = findViewById(R.id.id_copy_image);
        certificate_image = findViewById(R.id.certificate_image);


        mgdoc_back.setOnClickListener(this);
        permit_image.setOnClickListener(this);
        id_copy_image.setOnClickListener(this);
        certificate_image.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        if (v == mgdoc_back) {
            finish();
        }
        if (v == permit_image) {

            final Dialog dialog1 = new Dialog(ManageDocumentActivity.this, R.style.dialogstyle); // Context, this, etc.
            dialog1.setContentView(R.layout.zoom_image_layout);
            image_preview = dialog1.findViewById(R.id.image_preview);
            Picasso.get().load(Const.URL.HOST_URL + permit_image_url).into(image_preview);
            dialog1.setCanceledOnTouchOutside(true);
            mScaleGestureDetector = new ScaleGestureDetector(ManageDocumentActivity.this, new ScaleListener());
            dialog1.setCancelable(true);

            dialog1.show();

        }
        if (v == id_copy_image) {
            final Dialog dialog1 = new Dialog(ManageDocumentActivity.this, R.style.dialogstyle); // Context, this, etc.
            dialog1.setContentView(R.layout.zoom_image_layout);
            image_preview = dialog1.findViewById(R.id.image_preview);
            Picasso.get().load(Const.URL.HOST_URL + copy_image_url).into(image_preview);
            dialog1.setCanceledOnTouchOutside(true);
            dialog1.setCancelable(true);
            mScaleGestureDetector = new ScaleGestureDetector(ManageDocumentActivity.this, new ScaleListener());
            dialog1.show();
        }
        if (v == certificate_image) {

            final Dialog dialog1 = new Dialog(ManageDocumentActivity.this, R.style.dialogstyle); // Context, this, etc.
            dialog1.setContentView(R.layout.zoom_image_layout);
            image_preview = dialog1.findViewById(R.id.image_preview);
            Picasso.get().load(Const.URL.HOST_URL + certificate_image_url).into(image_preview);
            dialog1.setCanceledOnTouchOutside(true);
            mScaleGestureDetector = new ScaleGestureDetector(ManageDocumentActivity.this, new ScaleListener());
            dialog1.setCancelable(true);
            dialog1.show();
        }


    }


    private void getProfile() {
        final Dialog dialog = Utils.initProgressDialog(ManageDocumentActivity.this);
        RequestQueue queue = Volley.newRequestQueue(ManageDocumentActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.get_distributor, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

//                {"status":200,"message":"success","credit_amount":0,"distributor":

//                [{"distributor_id":"5","name":"geetika","mobile":"9907282210",
//                "email":"shop@gmail.com","password":"e10adc3949ba59abbe56e057f20f883e",
//                "designation":"Manager","otp":"7932","country":"2","state":null,"city":"1",
//                "store_location":"","store_address":"store address testing",
//                "description":"","store_lat":"12.9569233","store_long":"77.7011267",
//                "contact_name":"","shop_name":"shop testing","shop_image":null,
//                "business_permit":"uploads\/business_permit\/b5106e0edbdcbe4f5a08ed115e40580d_1.png",
//                "mpesa_user_name":"userName test","till_number":"123456","kra_pin":"123456",
//                "id_proof":"uploads\/id_proof\/b5106e0edbdcbe4f5a08ed115e40580d_3.png",
//                "certificate_img":"uploads\/certificate_img\/b5106e0edbdcbe4f5a08ed115e40580d_2.png",
//                "image":null,"credit":"500","token":null,"is_active":"1","is_delete":"1",
//                "create_date":"2020-10-16 17:49:24","MF_slot1_start_time":"5:49 AM",
//                "MF_slot1_end_time":"17:49 PM","SS_slot1_start_time":"6:49 AM",
//                "SS_slot1_end_time":"17:49 PM","category":[{"category_id":"1",
//                "name":"Fruit Vegitable","image":null,"is_active":"1","is_delete":"1",
//                "create_date":"2020-06-28 17:17:01"}],"distance":"","duration":""}]}

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("Edit Profile:", "response:--" + response);
                    //Toast.makeText(ManageDocumentActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {

                        JSONArray jsonArray = jsonObject.getJSONArray("distributor");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);


                            Picasso.get().load(Const.URL.HOST_URL + object.getString("business_permit"))
                                    .into(permit_image);
                            permit_image_url = object.getString("business_permit");
//
                            Picasso.get().load(Const.URL.HOST_URL + object.getString("id_proof"))
                                    .into(id_copy_image);
                            copy_image_url = object.getString("id_proof");

                            Picasso.get().load(Const.URL.HOST_URL + object.getString("certificate_img"))
                                    .into(certificate_image);
                            certificate_image_url = object.getString("certificate_img");

                            return_policy = object.getString("return_policy");
                            Log.e("return_policy", ""+ApiClient.BASE_URL+return_policy);

                            webView.getSettings().setSupportZoom(true);
                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.loadUrl(ApiClient.BASE_URL+return_policy);

                        }
                    } else {
                        Utils.T(ManageDocumentActivity.this, jsonObject.getString("message"));
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
                        Utils.T(ManageDocumentActivity.this, errorMessage + "please check Internet connection");
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
                ProfileParam.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());                Log.e("ProfileParam::", "param:--" + ProfileParam);
                return ProfileParam;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
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
            permit_image.setScaleX(mScaleFactor);
            permit_image.setScaleY(mScaleFactor);
            return true;
        }
    }


}