package com.dollop.distributor.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.R;
import com.dollop.distributor.adapter.PastOrderListAdapter;
import com.dollop.distributor.model.PastOrder_Model;

import java.util.ArrayList;

public class CutomerDetailsCancelActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout ll_pastorder,show_past_order;
    RecyclerView rv_pastorder;
    PastOrderListAdapter pastOrderListAdapter;
    ArrayList<PastOrder_Model> pastOrder_models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutomer_details_cancel);

        ll_pastorder = findViewById(R.id.ll_pastorder);
        show_past_order = findViewById(R.id.show_past_order);
        rv_pastorder = findViewById(R.id.rv_pastorder);

        rv_pastorder.setLayoutManager(new LinearLayoutManager(CutomerDetailsCancelActivity.this));
        pastOrderListAdapter = new PastOrderListAdapter(CutomerDetailsCancelActivity.this,pastOrder_models);
        rv_pastorder.setAdapter(pastOrderListAdapter);

        ll_pastorder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == ll_pastorder){
            show_past_order.setVisibility(View.VISIBLE);
        }
    }
}