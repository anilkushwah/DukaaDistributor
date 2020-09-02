package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dollop.distributor.R;

public class CreditSettingActivity extends AppCompatActivity  implements View.OnClickListener {

    ImageView credit_setback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_setting);

        credit_setback = findViewById(R.id.credit_setback);
        credit_setback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == credit_setback){
            finish();
        }
    }
}