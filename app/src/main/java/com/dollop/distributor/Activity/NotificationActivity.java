package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.dollop.distributor.R;
import com.dollop.distributor.adapter.NotificationAdapter;
import com.dollop.distributor.model.NotificationModelList;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView rv_notification_Id;
    List<NotificationModelList>notificationModelLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        rv_notification_Id= findViewById(R.id.rv_notification_Id);

        rv_notification_Id.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_notification_Id.setLayoutManager(layoutManager);

        NotificationModelList notificationModelList = new NotificationModelList();
        notificationModelList.setNotifcationTime("Today, 10:15");
        notificationModelList.setNotificationDescription("Your order num #153424 will arrive you on tomorrow.");
        notificationModelLists.add(notificationModelList);

        NotificationModelList notificationModelList1 = new NotificationModelList();
        notificationModelList1.setNotifcationTime("Today, 10:15");
        notificationModelList1.setNotificationDescription("Your order num #153424 will arrive you on tomorrow.");
        notificationModelLists.add(notificationModelList1);

        NotificationModelList notificationModelList2 = new NotificationModelList();
        notificationModelList2.setNotifcationTime("Today, 10:15");
        notificationModelList2.setNotificationDescription("Your order num #153424 will arrive you on tomorrow.");
        notificationModelLists.add(notificationModelList2);

        NotificationModelList notificationModelList3 = new NotificationModelList();
        notificationModelList3.setNotifcationTime("Today, 10:15");
        notificationModelList3.setNotificationDescription("Your order num #153424 will arrive you on tomorrow.");
        notificationModelLists.add(notificationModelList3);

        NotificationAdapter notificationAdapter= new NotificationAdapter(this,notificationModelLists);
        rv_notification_Id.setAdapter(notificationAdapter);

    }

    @Override
    public void onClick(View v) {

    }
}
