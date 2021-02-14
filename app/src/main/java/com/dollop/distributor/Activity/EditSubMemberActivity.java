package com.dollop.distributor.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.MarshMallowPermission;
import com.dollop.distributor.UtilityTools.UserAccount;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.UtilityTools.multipart.VolleyMultipartRequest;
import com.dollop.distributor.UtilityTools.multipart.VolleySingleton;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.ManageMemberModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.dollop.distributor.UtilityTools.multipart.AppHelper.getFileDataFromDrawable;

public class EditSubMemberActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView sub_member_back, user_profile;
    RadioButton full_radio, partial_radio, access_type;
    TextView tv_add_member;
    RadioGroup m_radio_group;
    EditText m_name, m_phone, m_email, m_designation, m_empId;
    Activity activity = EditSubMemberActivity.this;
    LinearLayout company_image_LL;
    public static final int GALLERY = 5;
    protected static final int CAMERA_REQUEST = 6;
    ManageMemberModel manageMemberModel;
    Bitmap profileBitmap;
    Uri profileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sub_member);

        manageMemberModel = (ManageMemberModel) getIntent().getSerializableExtra("data");
        initializationView();
        setData();



    }

    @Override
    public void onClick(View v) {
        if (v == sub_member_back) {
            Utils.I_clear(EditSubMemberActivity.this, ManageMemberActivity.class, null);
        } else if (v == company_image_LL) {

            MarshMallowPermission marshMallowPermission = new MarshMallowPermission(EditSubMemberActivity.this);

            if (marshMallowPermission.checkPermissionForCamera()) {
                openGallery();
            } else {
                marshMallowPermission.requestPermissionForCamera();
            }
        } else if (v == m_radio_group) {
            if (full_radio.isSelected()) {
                full_radio.setBackgroundResource(R.drawable.list_blue);
                partial_radio.setBackgroundResource(R.drawable.list_light_blue);
            } else if (partial_radio.isSelected()) {
                full_radio.setBackgroundResource(R.drawable.list_light_blue);
                partial_radio.setBackgroundResource(R.drawable.list_blue);
            }
        } else if (v == tv_add_member) {
            int selectedId = m_radio_group.getCheckedRadioButtonId();
            access_type = (RadioButton) findViewById(selectedId);


            if (!UserAccount.isEmpty(m_name)) {
                m_name.setError("Enter member name");
                m_name.requestFocus();
            } else if (!UserAccount.isPhoneNumberLength(m_phone)) {
                m_phone.setError("Enter 9 digit mobile number");
                m_phone.requestFocus();
            } else if (!UserAccount.isEmailValid(m_email)) {
                m_email.setError("Enter valid email address");
                m_email.requestFocus();
            } else if (!UserAccount.isEmpty(m_designation)) {
                m_designation.setError("Enter designation");
                m_designation.requestFocus();
            } else if (!UserAccount.isEmpty(m_empId)) {
                m_empId.setError("Enter employee id");
                m_empId.requestFocus();
            } else if (m_radio_group.getCheckedRadioButtonId() == -1) {

                Utils.T(activity, "Select access type");
            } else {
                // addMember();
                registrationMethod();
            }
        }

    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALLERY) {
            profileUri = data.getData();
            user_profile.setImageURI(profileUri);
            user_profile.setBackground(getResources().getDrawable(R.color.white));
        } else if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {

            profileBitmap = (Bitmap) data.getExtras().get("data");
            user_profile.setImageBitmap(profileBitmap);
            user_profile.setBackground(getResources().getDrawable(R.color.white));

        }
    }


    private void openGallery() {


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


    private void registrationMethod() {
        final Dialog dialog = Utils.initProgressDialog(this);
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, Const.URL.update_sub_member, new com.android.volley.Response.Listener<NetworkResponse>() {
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

                   // Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
                    if (status == 200) {
                        Utils.I_clear(activity, ManageMemberActivity.class, null);
                        finish();
                        finishAffinity();
                    }else {
                        Utils.T(EditSubMemberActivity.this, msg);
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

                stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
                stringStringHashMap.put("name", m_name.getText().toString());
                stringStringHashMap.put("designation", m_designation.getText().toString());

                if (full_radio.isChecked()) {
                    stringStringHashMap.put("access_type", "Full Aceess");
                } else if (partial_radio.isChecked()) {
                    stringStringHashMap.put("access_type", "Partial Access");
                }

                stringStringHashMap.put("id", manageMemberModel.getId());
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utils.I_clear(EditSubMemberActivity.this, ManageMemberActivity.class, null);
    }




    private void initializationView() {
        m_name = findViewById(R.id.m_name);
        m_phone = findViewById(R.id.m_phone);
        m_email = findViewById(R.id.m_email);
        m_designation = findViewById(R.id.m_designation);
        m_empId = findViewById(R.id.m_empId);
        tv_add_member = findViewById(R.id.tv_add_member);
        m_radio_group = findViewById(R.id.m_radio_group);
        company_image_LL = findViewById(R.id.company_image_LL);
        full_radio = findViewById(R.id.full_radio);
        partial_radio = findViewById(R.id.partial_radio);
        sub_member_back = findViewById(R.id.sub_member_back);
        user_profile = findViewById(R.id.user_profile);
        sub_member_back.setOnClickListener(this);
        tv_add_member.setOnClickListener(this);
        m_radio_group.setOnClickListener(this);
        company_image_LL.setOnClickListener(this);
    }


    private void setData() {
        m_name.setText(manageMemberModel.getName());
        m_email.setText(manageMemberModel.getEmail());
        m_phone.setText(manageMemberModel.getMobile());
        m_empId.setText(manageMemberModel.getEmp_id());
        m_designation.setText(manageMemberModel.getDesignation());

        m_email.setEnabled(false);
        m_phone.setEnabled(false);
        m_empId.setEnabled(false);

        if (manageMemberModel.getAccess_type().equals("Full Aceess")) {
            full_radio.setChecked(true);
        } else if (manageMemberModel.getAccess_type().equals("Partial Access")) {
            partial_radio.setChecked(true);
        }
        if (manageMemberModel.getProfile_pic() != null) {
            Picasso.get().load(Const.URL.HOST_URL + manageMemberModel.getProfile_pic()).into(user_profile);
            user_profile.setBackground(getResources().getDrawable(R.color.white));
        }
    }
}