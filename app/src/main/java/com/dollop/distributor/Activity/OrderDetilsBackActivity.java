package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.ShowOrderAdapter;
import com.dollop.distributor.model.ShoworderModel;

import java.util.ArrayList;
import java.util.List;

public class OrderDetilsBackActivity extends AppCompatActivity   implements View.OnClickListener{


    ImageView signature_set;
    LinearLayout ll_sign_box;
    TextView sign_submit,order_accespt,order_dispatch,tracking;

    RecyclerView All_Complains_recyclerviewId;
    List<ShoworderModel> showModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detils_back);

        All_Complains_recyclerviewId = findViewById(R.id.All_Complains_recyclerviewId);

        tracking = findViewById(R.id.tv_tracking);
        order_dispatch = findViewById(R.id.order_dispatch);
        order_accespt = findViewById(R.id.order_accept);
        signature_set = findViewById(R.id.signature_set);
        ll_sign_box = findViewById(R.id.ll_sign_box);
        sign_submit = findViewById(R.id.sign_submit);

        tracking.setOnClickListener(this);
        order_accespt.setOnClickListener(this);
        sign_submit.setOnClickListener(this);
        sign_submit.setOnClickListener(this);
        order_dispatch.setOnClickListener(this);

        list();

    }

    @Override
    public void onClick(View v) {

        if(v == signature_set){
            ll_sign_box.setVisibility(View.VISIBLE);
        }

        else if(v == sign_submit){
            ll_sign_box.setVisibility(View.GONE);
        } else if(v == order_accespt){
            Utils.I(OrderDetilsBackActivity.this,AssignActivity.class,null);
        } else if(v == order_dispatch){
            Utils.I(OrderDetilsBackActivity.this,DeliveryCompletedActivity.class,null);
        }else if(v == tracking){
            Utils.I(OrderDetilsBackActivity.this,TrackingActivity.class,null);
        }

    }

    private void list() {
        ShoworderModel modal = new ShoworderModel();

        modal.setAmount("60.00");
        modal.setName("Apple");
        modal.setItemquantity("(1kg)");
        modal.setOrderqty("3");
        modal.setOrderprice("20");
        modal.setImage(R.drawable.soft_drink);
        showModelList.add(modal);




        All_Complains_recyclerviewId.setLayoutManager(new LinearLayoutManager(OrderDetilsBackActivity.this, RecyclerView.VERTICAL, false));
        ShowOrderAdapter showAdapter = new ShowOrderAdapter(OrderDetilsBackActivity.this, showModelList);
        All_Complains_recyclerviewId.setAdapter(showAdapter);
    }


}
