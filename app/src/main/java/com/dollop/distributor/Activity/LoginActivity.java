package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.dollop.distributor.UtilityTools.NetworkUtil;
import com.dollop.distributor.UtilityTools.PermissionUtils;
import com.dollop.distributor.UtilityTools.SavedData;
import com.dollop.distributor.UtilityTools.SessionManager;
import com.dollop.distributor.UtilityTools.UserAccount;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.database.UserModel;
import com.dollop.distributor.firebase.MyFirebaseMessagingService;
import com.facebook.CallbackManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission_group.CAMERA;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_GoSignUp_Id;
    String token;
    Activity activity = LoginActivity.this;
    LinearLayout lv_google_signIn;
    private static final String EMAIL = "email";
    CallbackManager callbackManager;
    TextView tv_GoSignUp_Id, number_error_tv, password_error_tv;
    EditText et_login_mobile_number, et_login_pass;
    TextView btn_send_otp, tv_forgot;
    ImageView show_password_img;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 100;
    private static final int REQUEST_READ_PHONE_STATE = 90;
    SessionManager sessionManager;
    String m_deviceId;
    private boolean permissionGranted;
    String passwordVisiblity = "hide";
     ArrayList<String> listPermissionsNeeded=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        initializationView();
        TextChangedListenerMethod();
        MyFirebaseMessagingService.GenerateToken(LoginActivity.this);
        if (sessionManager.isLoggedIn()) {
            Utils.I_clear(activity, HomeActivity.class, null);
        }
        permissionGranted = checkAndRequestPermissions();


    }

    private void TextChangedListenerMethod() {
        et_login_mobile_number.addTextChangedListener(new TextWatcher() {
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
                if (UserAccount.isPhoneNumberLength(et_login_mobile_number)) {
                    number_error_tv.setVisibility(View.GONE);
                } else {
                    number_error_tv.setVisibility(View.VISIBLE);
                    number_error_tv.setText("Enter 9 digit mobile number");
                }

            }
        });

        et_login_pass.addTextChangedListener(new TextWatcher() {
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
                if (UserAccount.isPasswordValid(et_login_pass)) {
                    password_error_tv.setVisibility(View.GONE);
                } else {
                    password_error_tv.setVisibility(View.VISIBLE);
                    password_error_tv.setText("Password must be at least 6 characters");
                }

            }
        });


    }

    public String getDeviceId(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            m_deviceId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }
        return m_deviceId;
    }

    @Override
    public void onClick(View v) {

        if (v == btn_send_otp) {
            if (checkAndRequestPermissions()) {
                String mobile = et_login_mobile_number.getText().toString();
                if (!UserAccount.isEmpty(et_login_mobile_number)) {
                    number_error_tv.setVisibility(View.VISIBLE);
                    number_error_tv.setText("Enter mobile number");
                    et_login_mobile_number.requestFocus();
                } else if (!UserAccount.isPhoneNumberLength(et_login_mobile_number)) {
                    et_login_mobile_number.requestFocus();
                    number_error_tv.setVisibility(View.VISIBLE);
                    number_error_tv.setText("Enter 9 digit mobile number");
                } else if (!UserAccount.isPasswordValid(et_login_pass)) {
                    // et_login_pass.setError("Enter min 6 digit password");
                    password_error_tv.setVisibility(View.VISIBLE);
                    password_error_tv.setText("Password must be at least 6 characters");
                    et_login_pass.requestFocus();
                } else {
                    boolean status = NetworkUtil.getConnectivityStatus(LoginActivity.this);

                    if (status) {
                        getDeviceId(LoginActivity.this);
                        Login();
                    } else {
                        Utils.T(LoginActivity.this, "No Internet Connection available. Please try again");
                    }
                }
            }


        } else if (v == tv_GoSignUp_Id) {
            if (checkAndRequestPermissions()) {
                Intent intent = new Intent(activity, CreateAccountActivity.class);
                startActivity(intent);
            }
        } else if (v == tv_forgot) {
            Utils.I(LoginActivity.this, ForgotActivity.class, null);
        } else if (v == show_password_img) {
            if (passwordVisiblity.equals("hide")) {
                passwordVisiblity = "show";
                show_password_img.setBackgroundResource(R.drawable.ic_baseline_visibility_off_24);
                et_login_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

            } else if (passwordVisiblity.equals("show")) {
                passwordVisiblity = "hide";
                show_password_img.setBackgroundResource(R.drawable.ic_baseline_visibility_24);
                et_login_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

    private void Login() {
        final Dialog dialog = Utils.initProgressDialog(this);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                Log.e("distributerLogin:", "Login:--" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                  if (jsonObject.getInt("status") == 200) {



                        JSONObject data = jsonObject.getJSONObject("data");


                        UserModel userModel = new UserModel();
                        userModel.setDistributorId(data.getString("distributor_id"));
                        userModel.setImage(data.getString("image"));
                        userModel.setEmail(data.getString("email"));
                        userModel.setName(data.getString("name"));
                        userModel.setMobile(data.getString("mobile"));
                        userModel.setSs_start(data.getString("SS_slot1_start_time"));
                        userModel.setSs_close(data.getString("SS_slot1_end_time"));
                        UserDataHelper.getInstance().insertData(userModel);

                        SavedData.saveactiveUser("Active");
                        SavedData.saveDisID(data.getString("distributor_id"));
                        SavedData.saveAccessType(data.getString("access_type"));
                        SavedData.saveMainMemberID(data.getString("sub_member_distributor_id"));

                        if (data.getString("SS_slot1_start_time").equals("") && data.getString("MF_slot1_end_time").equals("")) {
                            //Utils.I_clear(activity, SetTimingActivity.class, null);
                            Intent intent = new Intent(activity, SetTimingActivity.class).putExtra("type", "Login");
                            startActivity(intent);
                        } else if (data.getJSONArray("address").length() == 0) {
                            Utils.I_clear(activity, SearchMapsActivity.class, null);
                        } else {

                            sessionManager.setLoginSession(true);
                            Utils.I_clear(activity, HomeActivity.class, null);

                        }

                    } else if (jsonObject.getInt("status") == 2) {


                        final Dialog dialog1 = new Dialog(LoginActivity.this, R.style.dialogstyle); // Context, this, etc.
                        dialog1.setContentView(R.layout.activity_successfull_registrion);
                        dialog1.setCanceledOnTouchOutside(true);
                        dialog1.setCancelable(true);
                        Button register_ok_btn = dialog1.findViewById(R.id.register_ok_btn);
                        register_ok_btn = dialog1.findViewById(R.id.register_ok_btn);
                        register_ok_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();
                            }
                        });
                        dialog1.show();


                    } else {
                        Utils.T(LoginActivity.this, jsonObject.getString("message"));

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
                        Utils.T(activity, errorMessage + "please check Internet connection");
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
                stringStringHashMap.put("mobile", et_login_mobile_number.getText().toString());
                stringStringHashMap.put("type", "distributor");
                stringStringHashMap.put("password", et_login_pass.getText().toString());

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

    private void initializationView() {
        tv_forgot = findViewById(R.id.tv_forgot);
        tv_GoSignUp_Id = findViewById(R.id.tv_GoSignUp_Id);
        et_login_mobile_number = findViewById(R.id.et_login_mobile_number);
        btn_send_otp = findViewById(R.id.btn_send_otp);
        number_error_tv = findViewById(R.id.number_error_tv);
        password_error_tv = findViewById(R.id.password_error_tv);

        et_login_pass = findViewById(R.id.et_login_pass);
        show_password_img = findViewById(R.id.show_password_img);
        btn_send_otp.setOnClickListener(this);
        tv_GoSignUp_Id.setOnClickListener(this);

        tv_forgot.setOnClickListener(this);
        show_password_img.setOnClickListener(this);


    }

    private boolean checkAndRequestPermissions() {

        int permissionReadStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWriteStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCallPhone = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int locationPermission = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int locationcoarse = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int read_phone_stage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);


        listPermissionsNeeded=new ArrayList<>();

        if (permissionReadStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionWriteStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionCallPhone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(ACCESS_FINE_LOCATION);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (locationcoarse != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (read_phone_stage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        Utils.E("listPermissionsNeeded::" + listPermissionsNeeded);
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            // Log.e("m_deviceId> 1", m_deviceId);
            return false;
        }
        return true;
    }

    private void displayNeverAskAgainDialog(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Before using this app please enable permission Manually which you denied");
        builder.setCancelable(false);
        builder.setPositiveButton("Permit Manually", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                boolean mayBeneverAskMaybeDeny = false;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == -1) {
                        mayBeneverAskMaybeDeny = true;

                    }

                }

                if (mayBeneverAskMaybeDeny) {
                    //TODO
                    displayNeverAskAgainDialog(listPermissionsNeeded.toString());
                }
                break;

            default:
                break;
        }
    }
}
