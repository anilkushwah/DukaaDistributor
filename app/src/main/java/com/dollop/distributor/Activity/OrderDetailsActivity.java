package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.ShowOrderAdapter;
import com.dollop.distributor.model.ShoworderModel;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity  implements View.OnClickListener {

    ImageView signature_set;
    LinearLayout ll_sign_box,ll_tracking;
    TextView sign_submit,order_accespt,order_dispatch,tracking,cancel;


    RecyclerView All_Complains_recyclerviewId;
    List<ShoworderModel> showModelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        All_Complains_recyclerviewId = findViewById(R.id.All_Complains_recyclerviewId);

        order_dispatch = findViewById(R.id.order_dispatch);
        tracking = findViewById(R.id.tv_tracking);
        order_accespt = findViewById(R.id.order_accept);
        signature_set = findViewById(R.id.signature_set);
        ll_sign_box = findViewById(R.id.ll_sign_box);
        sign_submit = findViewById(R.id.sign_submit);
        ll_tracking = findViewById(R.id.ll_tracking);
        cancel = findViewById(R.id.tv_orcancel);

        cancel.setOnClickListener(this);
        tracking.setOnClickListener(this);
        order_accespt.setOnClickListener(this);
        sign_submit.setOnClickListener(this);
        order_dispatch.setOnClickListener(this);
        signature_set.setOnClickListener(this);

        list();
    }


    @Override
    public void onClick(View v) {

        if(v == signature_set){
            ll_sign_box.setVisibility(View.VISIBLE);
        }

        else if(v == sign_submit){
            ll_sign_box.setVisibility(View.GONE);
            ll_tracking.setVisibility(View.VISIBLE);
        } else if(v == order_accespt){
            Utils.I(OrderDetailsActivity.this,AssignOrderDetailsActivity.class,null);
        } else if(v == order_dispatch){
            Utils.I(OrderDetailsActivity.this,DeliveryCompletedActivity.class,null);
        }else if(v == tracking){
            Utils.I(OrderDetailsActivity.this,TrackingActivity.class,null);
        }else if(v == cancel){
            cancelPopup();
        }
    }

    private void cancelPopup() {

        final Dialog myDialog = new Dialog(OrderDetailsActivity.this);
        myDialog.requestWindowFeature(Window.FEATURE_RIGHT_ICON);
        //myDialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
        myDialog.setContentView(R.layout.cancel_order_popup);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(myDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.getWindow().setAttributes(lp);
        myDialog.show();

        TextView tv_submit = myDialog.findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

    }


    private void list() {
       /* ShoworderModel modal = new ShoworderModel();

        modal.setAmount("60.00");
        modal.setName("Apple");
        modal.setItemquantity("(1kg)");
        modal.setOrderqty("3");
        modal.setOrderprice("20");
        modal.setImage(R.drawable.soft_drink);
        showModelList.add(modal);*/




        All_Complains_recyclerviewId.setLayoutManager(new LinearLayoutManager(OrderDetailsActivity.this, RecyclerView.VERTICAL, false));
        ShowOrderAdapter showAdapter = new ShowOrderAdapter(OrderDetailsActivity.this, showModelList);
        All_Complains_recyclerviewId.setAdapter(showAdapter);
    }

}
