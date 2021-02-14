package com.dollop.distributor.ui.user;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
import com.dollop.distributor.Activity.BankDetailsActivity;
import com.dollop.distributor.Activity.ChangePasswordActivity;
import com.dollop.distributor.Activity.CreditSettingActivity;
import com.dollop.distributor.Activity.EditProfileFragment;
import com.dollop.distributor.Activity.LoginActivity;
import com.dollop.distributor.Activity.MPESActivity;
import com.dollop.distributor.Activity.ManageDocumentActivity;
import com.dollop.distributor.Activity.ManageMemberActivity;
import com.dollop.distributor.Activity.MyRatingActivity;
import com.dollop.distributor.Activity.NotificationActivity;
import com.dollop.distributor.Activity.OfferActivity;
import com.dollop.distributor.Activity.OrderHistory;
import com.dollop.distributor.Activity.PersonalDeatilsActivity;
import com.dollop.distributor.Activity.SetTimingActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.SavedData;
import com.dollop.distributor.UtilityTools.SessionManager;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.database.UserDataHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserFragment extends Fragment implements View.OnClickListener {
    LinearLayout manage_member_parent_LL, ll_statics, ll_products, ll_order_history, ll_setbusiness_hours, ll_mpesa, ll_bankdetails, ll_notifications, ll_craete_pass, ll_per_info, ll_manage_member, ll_credit_setting, ll_store_setting, ll_managedoc;
    LinearLayout parent_payment_LL, ll_myRating;
    Button bt_editprofile;
    ImageView user_image, logout_image, back_back;
    TextView tv_profileName, tv_profileEmail, toolbar_logout;
    SessionManager sessionManager;
    LinearLayout ll_offer;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_profile, container, false);
        sessionManager = new SessionManager(getActivity());
        initializationView(root);
        getProfile();

        return root;
    }


    private void getProfile() {
        final Dialog dialog = Utils.initProgressDialog(getActivity());
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.get_distributor, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                Log.e("getProfile:", "profile:--" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    // Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {

                        JSONArray jsonArray = jsonObject.getJSONArray("distributor");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            if (!object.isNull("name")) {
                                tv_profileName.setText(object.getString("name"));
                            }
                            if (!object.isNull("email")) {
                                tv_profileEmail.setText(object.getString("email"));
                            }


                            if (!object.isNull("image")) {
                                Picasso.get().load(Const.URL.HOST_URL + object.getString("image")).into(user_image);

                            }
                        }
                    } else {
                        Utils.T(getActivity(), jsonObject.getString("message"));
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
                HashMap<String, String> ProfileParam = new HashMap<>();
                //   ProfileParam.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
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
    public void onClick(View v) {
        if (v == ll_offer) {
            Utils.I(getActivity(), OfferActivity.class, null);
        }
        if (v == logout_image) {

            new AlertDialog.Builder(getActivity())
                    .setMessage("Are you sure you want to Logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            SavedData.saveactiveUser("");
                            UserDataHelper.getInstance().deleteAll();
                            Utils.I_clear(getActivity(), LoginActivity.class, null);
                            SavedData.saveactiveUser("");
                            SavedData.saveDisID("");
                            SavedData.saveAccessType("");
                            SavedData.saveMainMemberID("");
                            sessionManager.setLoginSession(false);


                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

        }

        if (v == ll_statics) {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.navigation_dashboard);
            /* Intent intent = new Intent(getActivity(), SavedAddressActivity.class);
            startActivity(intent);*/
        } else if (v == ll_products) {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.navigation_allItem);
            /*Intent intent = new Intent(getActivity(), MyOrderActivity.class);
            startActivity(intent);*/
        } else if (v == ll_order_history) {

            Intent intent = new Intent(getActivity(), OrderHistory.class);
            startActivity(intent);
            //  Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_earning, mBundle);
           /*  Intent intent = new Intent(getActivity(),ShowOrderActivity.class);
            startActivity(intent);*/

        } else if (v == ll_setbusiness_hours) {
            Intent intent = new Intent(getActivity(), SetTimingActivity.class).putExtra("type", "profile");
            startActivity(intent);
        } else if (v == ll_managedoc) {
            Intent intent = new Intent(getActivity(), ManageDocumentActivity.class);
            startActivity(intent);
        } else if (v == ll_store_setting) {
            Intent intent = new Intent(getActivity(), SetTimingActivity.class);
            startActivity(intent);
        } else if (v == ll_mpesa) {
            Intent intent = new Intent(getActivity(), MPESActivity.class);
            startActivity(intent);
        } else if (v == ll_bankdetails) {
            Intent intent = new Intent(getActivity(), BankDetailsActivity.class);
            startActivity(intent);
        } else if (v == ll_notifications) {
            Intent intent = new Intent(getActivity(), NotificationActivity.class);
            startActivity(intent);
        } else if (v == ll_craete_pass) {
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        } else if (v == ll_per_info) {
            Intent intent = new Intent(getActivity(), PersonalDeatilsActivity.class)
                    .putExtra("type", "profile");
            startActivity(intent);
        } else if (v == bt_editprofile) {
            Intent intent = new Intent(getActivity(), EditProfileFragment.class);
            startActivity(intent);


            // Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.editProfile);
        } else if (v == ll_manage_member) {
            Utils.I(getActivity(), ManageMemberActivity.class, null);
        } else if (v == ll_credit_setting) {
            Utils.I(getActivity(), CreditSettingActivity.class, null);
        } else if (v == toolbar_logout) {

            new AlertDialog.Builder(getActivity())
                    .setMessage("Are you sure you want to Logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            SavedData.saveactiveUser("");
                            UserDataHelper.getInstance().deleteAll();
                            Utils.I_clear(getActivity(), LoginActivity.class, null);
                            SavedData.saveactiveUser("");
                            SavedData.saveDisID("");
                            SavedData.saveAccessType("");
                            SavedData.saveMainMemberID("");
                            sessionManager.setLoginSession(false);


                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

        } else if (v == back_back) {
            getActivity().onBackPressed();
        } else if (v == ll_myRating) {
            startActivity(new Intent(getActivity(), MyRatingActivity.class));
        }

    }


    @Override
    public void onResume() {
        initializationView(root);
        getProfile();
        super.onResume();

    }


    private void initializationView(View root) {

        ll_managedoc = root.findViewById(R.id.ll_managedoc);
        ll_store_setting = root.findViewById(R.id.ll_store_setting);
        ll_credit_setting = root.findViewById(R.id.ll_credit_setting);
        ll_notifications = root.findViewById(R.id.ll_notifications);
        ll_statics = root.findViewById(R.id.ll_statics);
        ll_products = root.findViewById(R.id.ll_products);
        ll_order_history = root.findViewById(R.id.ll_order_history);
        bt_editprofile = root.findViewById(R.id.bt_edit_profile);
        ll_setbusiness_hours = root.findViewById(R.id.ll_setbusiness_hours);
        ll_mpesa = root.findViewById(R.id.ll_mpesa);
        ll_bankdetails = root.findViewById(R.id.ll_bankdetails);
        tv_profileName = root.findViewById(R.id.tv_profileName);
        tv_profileEmail = root.findViewById(R.id.tv_profileEmail);
        ll_craete_pass = root.findViewById(R.id.ll_craete_pass);
        ll_per_info = root.findViewById(R.id.ll_per_info);
        toolbar_logout = root.findViewById(R.id.toolbar_logout);
        ll_manage_member = root.findViewById(R.id.ll_manage_member);
        user_image = root.findViewById(R.id.user_image);
        manage_member_parent_LL = root.findViewById(R.id.manage_member_parent_LL);
        parent_payment_LL = root.findViewById(R.id.parent_payment_LL);
        logout_image = root.findViewById(R.id.logout_image);
        ll_offer = root.findViewById(R.id.ll_offer);
        back_back = root.findViewById(R.id.back_back);
        ll_myRating = root.findViewById(R.id.ll_myRating);


        ll_managedoc.setOnClickListener(this);
        ll_craete_pass.setOnClickListener(this);
        ll_notifications.setOnClickListener(this);
        ll_setbusiness_hours.setOnClickListener(this);
        bt_editprofile.setOnClickListener(this);
        ll_order_history.setOnClickListener(this);
        ll_statics.setOnClickListener(this);
        ll_products.setOnClickListener(this);
        ll_mpesa.setOnClickListener(this);
        ll_bankdetails.setOnClickListener(this);
        ll_per_info.setOnClickListener(this);
        toolbar_logout.setOnClickListener(this);
        ll_manage_member.setOnClickListener(this);
        ll_credit_setting.setOnClickListener(this);
        ll_store_setting.setOnClickListener(this);
        logout_image.setOnClickListener(this);
        ll_offer.setOnClickListener(this);
        back_back.setOnClickListener(this);
        ll_myRating.setOnClickListener(this);


        if (SavedData.get_AccessType().equals("Partial Access")) {

            manage_member_parent_LL.setVisibility(View.GONE);
            parent_payment_LL.setVisibility(View.GONE);
            ll_managedoc.setVisibility(View.GONE);
            ll_statics.setVisibility(View.GONE);
            ll_setbusiness_hours.setVisibility(View.GONE);
            ll_credit_setting.setVisibility(View.GONE);
            ll_craete_pass.setVisibility(View.GONE);
            bt_editprofile.setVisibility(View.GONE);
            ll_offer.setVisibility(View.GONE);
            ll_order_history.setVisibility(View.GONE);

        } else if (SavedData.get_AccessType().equals("Full Aceess")) {
            manage_member_parent_LL.setVisibility(View.GONE);
            bt_editprofile.setVisibility(View.GONE);
            ll_order_history.setVisibility(View.VISIBLE);
        }
    }

}
