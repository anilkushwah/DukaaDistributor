package com.dollop.distributor.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.R;
import com.dollop.distributor.adapter.ShowOrderAdapter;
import com.dollop.distributor.model.ShoworderModel;

import java.util.ArrayList;
import java.util.List;

public class NewOrderActivity extends AppCompatActivity implements View.OnClickListener {


    RecyclerView All_Complains_recyclerviewId;
    List<ShoworderModel> showModelList = new ArrayList<>();
    ImageView order_back;
    TextView pastorder;
    TextView order_accept,order_decline;
    String order_status;
    LinearLayout ll_set_order,ll_order_complete;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        //getWindow().setStatusBarColor(getResources().getColor(R.color.white, this.getTheme()));

        ll_set_order = findViewById(R.id.ll_set_order);
        ll_order_complete = findViewById(R.id.ll_order_complete);
        order_decline = findViewById(R.id.order_decline);
        pastorder = findViewById(R.id.tv_pastorder);
        order_accept = findViewById(R.id.order_accept);
        order_back = findViewById(R.id.order_back);
        All_Complains_recyclerviewId = findViewById(R.id.All_Complains_recyclerviewId);

        order_status = (String) getIntent().getExtras().getString("order_status");

        if(order_status.equals("complete")){
            ll_set_order.setVisibility(View.GONE);
            ll_order_complete.setVisibility(View.VISIBLE);
        }


        ll_order_complete.setOnClickListener(this);
        order_back.setOnClickListener(this);
        order_decline.setOnClickListener(this);
        order_accept.setOnClickListener(this);
        pastorder.setOnClickListener(this);



        list();

    }


    @Override
    public void onClick(View v) {
        if(v==ll_order_complete){
            finish();
        }
        else if(v==order_back){
            finish();
        }
        else if(v==order_decline){
            finish();
        }else if(v==order_accept){
            Intent intent  = new Intent(NewOrderActivity.this,StatusordershowActivity.class);
            startActivity(intent);
        }else if(v==pastorder){
            Intent intent  = new Intent(NewOrderActivity.this,DeliveryCompletedActivity.class);
            startActivity(intent);
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

        ShoworderModel modal1 = new ShoworderModel();
        modal1.setAmount("40.00");
        modal1.setName("Banana");
        modal1.setItemquantity("(12pcs)");
        modal1.setOrderqty("2");
        modal1.setOrderprice("20");
        modal1.setImage(R.drawable.soft_drink);
        showModelList.add(modal1);

        ShoworderModel modal2 = new ShoworderModel();


        modal2.setAmount("40.00");
        modal2.setName("Mango");
        modal2.setItemquantity("(2kg)");
        modal2.setOrderqty("2");
        modal2.setOrderprice("20");
        modal2.setImage(R.drawable.soft_drink);
        showModelList.add(modal2);

        ShoworderModel modal3 = new ShoworderModel();
        modal3.setOrderqty("3");
        modal3.setOrderprice("30");
        modal3.setAmount("90.00");
        modal3.setName("Coca-cola");
        modal3.setItemquantity("(2Ltr)");
        modal3.setImage(R.drawable.soft_drink);
        showModelList.add(modal3);

        ShoworderModel moda = new ShoworderModel();
        moda.setOrderqty("3");
        moda.setOrderprice("40");
        moda.setAmount("120.00");
        moda.setName("Apple");
        moda.setItemquantity("(1kg)");
        moda.setImage(R.drawable.soft_drink);
        showModelList.add(moda);


        All_Complains_recyclerviewId.setLayoutManager(new LinearLayoutManager(NewOrderActivity.this, RecyclerView.VERTICAL, false));
        ShowOrderAdapter showAdapter = new ShowOrderAdapter(NewOrderActivity.this, showModelList);
        All_Complains_recyclerviewId.setAdapter(showAdapter);
    }

}
