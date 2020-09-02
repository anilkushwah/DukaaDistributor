package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.PastOrderListAdapter;
import com.dollop.distributor.model.PastOrder_Model;

import java.util.ArrayList;

public class CutomerDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout ll_pastorder,show_past_order;
    RecyclerView rv_pastorder;
    PastOrderListAdapter pastOrderListAdapter;
    ArrayList<PastOrder_Model> pastOrder_models = new ArrayList<>();

    TextView CD_accept,CD_decline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutomer_details);

        CD_accept = findViewById(R.id.CD_accept);
        CD_decline = findViewById(R.id.CD_decline);
        ll_pastorder = findViewById(R.id.ll_pastorder);
        show_past_order = findViewById(R.id.show_past_order);
        rv_pastorder = findViewById(R.id.rv_pastorder);

        rv_pastorder.setLayoutManager(new LinearLayoutManager(CutomerDetailsActivity.this));
        pastOrderListAdapter = new PastOrderListAdapter(CutomerDetailsActivity.this,pastOrder_models);
        rv_pastorder.setAdapter(pastOrderListAdapter);

        CD_accept.setOnClickListener(this);
        ll_pastorder.setOnClickListener(this);
        CD_decline.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == ll_pastorder){
            show_past_order.setVisibility(View.VISIBLE);
        }

        else if(v == CD_accept){
            Utils.I(CutomerDetailsActivity.this,CutomerDetailsAcceptActivity.class,null);
        }
        else if(v == CD_decline){
            Utils.I(CutomerDetailsActivity.this,CutomerDetailsCancelActivity.class,null);
        }
    }
}