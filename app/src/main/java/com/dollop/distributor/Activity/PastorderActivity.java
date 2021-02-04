package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.view.View;
import android.widget.ImageView;

import com.dollop.distributor.R;
import com.dollop.distributor.adapter.PastOrderAdapter;
import com.dollop.distributor.adapter.TotalEarningAdapter;
import com.dollop.distributor.model.pastOrderModel;

import java.util.ArrayList;

public class PastorderActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv_pastorder;
    ImageView past_back;

    Activity activity = PastorderActivity.this;

    PastOrderAdapter pastOrderAdapter;
    ArrayList<pastOrderModel> pastOrderModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pastorder);
        initializationView();
    }

    private void initializationView() {
        rv_pastorder = findViewById(R.id.rv_pastorder);
        past_back = findViewById(R.id.past_back);
        rv_pastorder.setLayoutManager(new LinearLayoutManager(activity));
        pastOrderAdapter = new PastOrderAdapter(activity,pastOrderModels);
        rv_pastorder.setAdapter(pastOrderAdapter);
        past_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == past_back){
            finish();
        }
    }
}