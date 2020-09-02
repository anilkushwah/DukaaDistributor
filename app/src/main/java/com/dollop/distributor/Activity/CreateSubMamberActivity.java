package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dollop.distributor.R;

public class CreateSubMamberActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView sub_member_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sub_mamber);

        sub_member_back = findViewById(R.id.sub_member_back);

        sub_member_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == sub_member_back){
            finish();
        }
    }
}