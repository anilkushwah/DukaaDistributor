package com.dollop.distributor.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.dollop.distributor.R;

public class PastorderDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView pastorderdetails_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pastorder_details);

        pastorderdetails_back = findViewById(R.id.order_back) ;

        pastorderdetails_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == pastorderdetails_back){finish();
        }
    }
}