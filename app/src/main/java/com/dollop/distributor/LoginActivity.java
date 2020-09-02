package com.dollop.distributor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.dollop.distributor.Activity.CreateAccountActivity;
import com.dollop.distributor.Activity.ForgotActivity;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.UserAccount;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.database.UserDataHelper;
import com.facebook.CallbackManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_GoSignUp_Id;
    String token;
    Activity activity = LoginActivity.this;
    LinearLayout lv_google_signIn;
    private static final String EMAIL = "email";
    CallbackManager callbackManager;
    TextView tv_GoSignUp_Id;
    EditText et_login_mobile_number, et_login_pass;
    TextView btn_send_otp,tv_forgot;
    ImageView iv_login_back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv_forgot = findViewById(R.id.tv_forgot);
        tv_GoSignUp_Id = findViewById(R.id.tv_GoSignUp_Id);
        et_login_mobile_number = findViewById(R.id.et_login_mobile_number);
        btn_send_otp = findViewById(R.id.btn_send_otp);
        iv_login_back_arrow = findViewById(R.id.iv_login_back_arrow);
        et_login_pass = findViewById(R.id.et_login_pass);
        btn_send_otp.setOnClickListener(this);
        tv_GoSignUp_Id.setOnClickListener(this);
        iv_login_back_arrow.setOnClickListener(this);
        tv_forgot.setOnClickListener(this);

        if (UserDataHelper.getInstance().getList().size() > 0) {
            Utils.I_clear(activity, HomeActivity.class, null);
        }
    }


    @Override
    public void onClick(View v) {

        if (v == btn_send_otp) {
            String mobile = et_login_mobile_number.getText().toString();
            if (!UserAccount.isEmpty(et_login_mobile_number)) {
                et_login_mobile_number.setError("Please enter mobile number or email id");
                et_login_mobile_number.requestFocus();
            } /*else if (!UserAccount.isPhoneNumberLength(et_login_mobile_number)) {
                et_login_mobile_number.setError("Please enter 10 digit mobile number");
                et_login_mobile_number.requestFocus();
            }*/
            else if (!UserAccount.isEmpty(et_login_pass)){
                et_login_pass.setError("Please enter password");
                et_login_pass.requestFocus();
            }
            else {
              //  Login();
                  Intent intent = new Intent(activity,LocationActivity.class);
                 startActivity(intent);
            }
           /* Intent intent = new Intent(activity,OtpSendActivity.class);
            startActivity(intent);*/
        } else if (v == tv_GoSignUp_Id) {
            //Intent intent = new Intent(activity,SignUpActivity.class);
            Intent intent = new Intent(activity, CreateAccountActivity.class);
            startActivity(intent);
        } else if (v == iv_login_back_arrow) {
            onBackPressed();
        }else if (v == tv_forgot) {
            Utils.I(LoginActivity.this, ForgotActivity.class,null);
        }
        /*else if (v == lv_google_signIn){
            signIn();
        }else if (v == btn_facebook_login_Id){

            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
            login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    // App code
                    Intent intent = new Intent(activity,LocationActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancel() {
                    // App code
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                }
            });
        } */
    }


    private void Login() {
        {
            final Dialog dialog = Utils.initProgressDialog(this);
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.login, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();

                    Log.e("distributerLogin:", "Login:--" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(activity, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        if (jsonObject.getInt("status") == 200) {
                            String otpdata = jsonObject.getString("data");
                            Utils.E("otpdata:-" + otpdata);
                            Intent intent = new Intent(activity, OtpSendActivity.class);
                            intent.putExtra("otp", otpdata);
                            intent.putExtra("mobile", et_login_mobile_number.getText().toString());
                            startActivity(intent);
                            finish();
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
}
