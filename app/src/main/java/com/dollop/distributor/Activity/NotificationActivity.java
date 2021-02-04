package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.NetworkUtil;
import com.dollop.distributor.UtilityTools.SavedData;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.NotificationAdapter;
import com.dollop.distributor.adapter.TotalEarningAdapter;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.NewOderlist;
import com.dollop.distributor.model.NotificationData;
import com.dollop.distributor.model.NotificationModelList;
import com.dollop.distributor.model.OrderModel;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rv_notification_Id;
    List<NotificationModelList> notificationModelLists = new ArrayList<>();
    ImageView noti_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        rv_notification_Id = findViewById(R.id.rv_notification_Id);
        noti_back = findViewById(R.id.noti_back);
        noti_back.setOnClickListener(this);

        boolean status = NetworkUtil.getConnectivityStatus(NotificationActivity.this);
        if (status == true) {

        } else {
            Utils.T(NotificationActivity.this, "No Internet Connection available. Please try again");
        }

        getNotificationMethod();


    }

    private void getNotificationMethod() {

        final Dialog dialog = Utils.initProgressDialog(NotificationActivity.this);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> stringStringHashMap = new HashMap<>();

        if (SavedData.get_AccessType().equals("Distributor")) {
            stringStringHashMap.put("customer_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());

        } else {
            stringStringHashMap.put("customer_id", SavedData.get_MainMemberId());


        }
        stringStringHashMap.put("type", "Distributor");
        Utils.E("checkNewOrder::" + stringStringHashMap);
        Call<NotificationData> call = apiService.get_all_notification(stringStringHashMap);
        call.enqueue(new Callback<NotificationData>() {
            @Override
            public void onResponse(Call<NotificationData> call, retrofit2.Response<NotificationData> response) {
                dialog.dismiss();
                try {
                    NotificationData body = response.body();

                    if (body.getStatus() == 200) {

                        notificationModelLists = body.getData();
                        rv_notification_Id.setHasFixedSize(true);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(NotificationActivity.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        rv_notification_Id.setLayoutManager(layoutManager);
                        NotificationAdapter notificationAdapter = new NotificationAdapter(NotificationActivity.this, notificationModelLists);
                        rv_notification_Id.setAdapter(notificationAdapter);

                    } else {
                        Utils.T(NotificationActivity.this, body.getMessage());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<NotificationData> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                dialog.dismiss();

            }
        });

    }


    @Override
    public void onClick(View v) {
        if (v == noti_back) {
            finish();
        }
    }
}
