package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dollop.distributor.Fragment.AcceptCreditRequestFragment;
import com.dollop.distributor.Fragment.CancelCreditRequestFragment;
import com.dollop.distributor.Fragment.NewCreditRequestFragment;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.NetworkUtil;
import com.dollop.distributor.UtilityTools.SavedData;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.NewCreditReqAdapter;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.CreditRequestResponse;
import com.dollop.distributor.model.NewCreditReq_Model;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class AllCreditRequestActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView allcredit_back;
    TabLayout tabLayout;
    LinearLayout new_order_LL, ll_reqtab_accepted, ll_tab_cancelled, ll_reqtab_new, main_requesttab, ll_credit_Setting, fragment_container;
    TextView tv_reqnew, tv_accepted, tv_cancelled, new_count, accepted_count, cancelled_count;
    public static int NewOrderCount = 0, ProcessingOrderCount = 0;
    String creditstatus = "";
    ArrayList<NewCreditReq_Model> newCreditReq_models;
    ArrayList<NewCreditReq_Model> approvedCreditReq_models;
    ArrayList<NewCreditReq_Model> canceledCreditReq_models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_credit_request);

        initializationView();

    }

    private void initializationView() {

        newCreditReq_models = new ArrayList<>();
        approvedCreditReq_models = new ArrayList<>();
        canceledCreditReq_models = new ArrayList<>();

        if (getIntent().getExtras() != null) {
            creditstatus = getIntent().getExtras().getString("status").toString();
            Utils.E("accept" + creditstatus);

        }

        boolean status = NetworkUtil.getConnectivityStatus(AllCreditRequestActivity.this);

        if (status == true) {

        } else {
            Utils.T(AllCreditRequestActivity.this, "No Internet Connection available. Please try again");
        }

        fragment_container = findViewById(R.id.fragment_container);
        allcredit_back = findViewById(R.id.allcredit_back);
        ll_credit_Setting = findViewById(R.id.ll_credit_Setting);
        cancelled_count = findViewById(R.id.cancelled_count);
        main_requesttab = findViewById(R.id.main_requesttab);
        ll_reqtab_accepted = findViewById(R.id.ll_reqtab_accepted);
        ll_tab_cancelled = findViewById(R.id.ll_tab_cancelled);
        ll_reqtab_new = findViewById(R.id.ll_reqtab_new);
        tv_reqnew = findViewById(R.id.tv_reqnew);
        new_count = findViewById(R.id.new_count);
        accepted_count = findViewById(R.id.accepted_count);
        tv_cancelled = findViewById(R.id.tv_cancelled);
        tv_accepted = findViewById(R.id.tv_accepted);
        tabLayout = findViewById(R.id.tabscreditrequest);
        new_order_LL = findViewById(R.id.new_order_LL);


        ll_reqtab_new.setOnClickListener(this);
        ll_credit_Setting.setOnClickListener(this);
        ll_reqtab_accepted.setOnClickListener(this);
        main_requesttab.setOnClickListener(this);
        ll_tab_cancelled.setOnClickListener(this);
        allcredit_back.setOnClickListener(this);

        tabLayout.addTab(tabLayout.newTab().setText("New"));
        tabLayout.addTab(tabLayout.newTab().setText("Accepted"));
        tabLayout.addTab(tabLayout.newTab().setText("Cancelled"));

        replaceFragment(new NewCreditRequestFragment());
        get_credit_request("Pending");
        get_credit_request1("Approved");
        get_credit_request2("Canceled");

        tab_newMethod();

    }


    public void tab_newMethod() {

        ll_reqtab_new.setBackgroundResource(R.drawable.tab_background_selected);
        tv_reqnew.setTextColor(getResources().getColor(R.color.colorBlue));
        new_order_LL.setBackgroundResource(R.drawable.ic_tab_blue_circle);
        new_count.setTextColor(getResources().getColor(R.color.white));
        get_credit_request("Pending");
    }

    public void Unceletct_newMethod() {
        ll_reqtab_new.setBackgroundResource(R.drawable.ordertabselected_back);
        tv_reqnew.setTextColor(getResources().getColor(R.color.white));
        new_order_LL.setBackgroundResource(R.drawable.ic_tab_circle);
        new_count.setTextColor(getResources().getColor(R.color.colorBlue));


    }

    public void reqtab_accepted() {
        ll_reqtab_accepted.setBackgroundResource(R.drawable.tab_background_selected);
        tv_accepted.setTextColor(getResources().getColor(R.color.colorBlue));
        get_credit_request1("Approved");
    }

    public void Unselect_accpete() {
        ll_reqtab_accepted.setBackgroundResource(R.drawable.ordertabselected_back);
        tv_accepted.setTextColor(getResources().getColor(R.color.white));
    }

    public void CancleMethod() {
        ll_tab_cancelled.setBackgroundResource(R.drawable.tab_background_selected);
        tv_cancelled.setTextColor(getResources().getColor(R.color.colorBlue));

        get_credit_request2("Canceled");
    }

    public void Unselect_Cancle() {
        ll_tab_cancelled.setBackgroundResource(R.drawable.ordertabselected_back);
        tv_cancelled.setTextColor(getResources().getColor(R.color.white));

    }

    @Override
    public void onClick(View v) {
        if (v == allcredit_back) {

            Utils.I_clear(AllCreditRequestActivity.this, HomeActivity.class, null);

        } else if (v == ll_credit_Setting) {

            Utils.I(AllCreditRequestActivity.this, CreditSettingActivity.class, null);

        } else if (v == ll_reqtab_new) {

            replaceFragment(new NewCreditRequestFragment());
            tab_newMethod();

            Unselect_accpete();
            Unselect_Cancle();

        } else if (v == ll_reqtab_accepted) {

            reqtab_accepted();
            replaceFragment(new AcceptCreditRequestFragment());
            Unceletct_newMethod();
            Unselect_Cancle();

        } else if (v == ll_tab_cancelled) {

            replaceFragment(new CancelCreditRequestFragment());
            CancleMethod();

            Unceletct_newMethod();
            Unselect_accpete();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void get_credit_request(final String status) {
        final Dialog dialog = Utils.initProgressDialog(AllCreditRequestActivity.this);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        if (SavedData.get_AccessType().equals("Distributor")) {
            stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());

        } else {
            stringStringHashMap.put("distributor_id", SavedData.get_MainMemberId());

        }
        stringStringHashMap.put("status", status);


        Call<CreditRequestResponse> call = apiService.get_credit_request(stringStringHashMap);
        call.enqueue(new Callback<CreditRequestResponse>() {
            @Override
            public void onResponse(Call<CreditRequestResponse> call, retrofit2.Response<CreditRequestResponse> response) {
                dialog.dismiss();
                try {
                    CreditRequestResponse body = response.body();
                    newCreditReq_models.clear();

                    if (body.getStatus() == 200) {

                        newCreditReq_models = body.getData();
                        new_count.setText("" + newCreditReq_models.size());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<CreditRequestResponse> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                dialog.dismiss();

            }
        });

    }

    private void get_credit_request1(final String status) {
        final Dialog dialog = Utils.initProgressDialog(AllCreditRequestActivity.this);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        if (SavedData.get_AccessType().equals("Distributor")) {
            stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());

        } else {
            stringStringHashMap.put("distributor_id", SavedData.get_MainMemberId());

        }
        stringStringHashMap.put("status", status);


        Call<CreditRequestResponse> call = apiService.get_credit_request(stringStringHashMap);
        call.enqueue(new Callback<CreditRequestResponse>() {
            @Override
            public void onResponse(Call<CreditRequestResponse> call, retrofit2.Response<CreditRequestResponse> response) {
                dialog.dismiss();
                try {
                    CreditRequestResponse body = response.body();
                    approvedCreditReq_models.clear();

                    if (body.getStatus() == 200) {

                        approvedCreditReq_models = body.getData();
                        accepted_count.setText("" + approvedCreditReq_models.size());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<CreditRequestResponse> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                dialog.dismiss();

            }
        });

    }

    private void get_credit_request2(final String status) {
        final Dialog dialog = Utils.initProgressDialog(AllCreditRequestActivity.this);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        if (SavedData.get_AccessType().equals("Distributor")) {
            stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());

        } else {
            stringStringHashMap.put("distributor_id", SavedData.get_MainMemberId());

        }
        stringStringHashMap.put("status", status);


        Call<CreditRequestResponse> call = apiService.get_credit_request(stringStringHashMap);
        call.enqueue(new Callback<CreditRequestResponse>() {
            @Override
            public void onResponse(Call<CreditRequestResponse> call, retrofit2.Response<CreditRequestResponse> response) {
                dialog.dismiss();
                try {
                    CreditRequestResponse body = response.body();
                    canceledCreditReq_models.clear();

                    if (body.getStatus() == 200) {

                        canceledCreditReq_models = body.getData();
                        cancelled_count.setText("" + canceledCreditReq_models.size());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<CreditRequestResponse> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                dialog.dismiss();

            }
        });

    }

}