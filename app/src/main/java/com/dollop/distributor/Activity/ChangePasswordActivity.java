package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dollop.distributor.R;

public class ChangePasswordActivity extends AppCompatActivity {


    ImageView iv_create_pass_arrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        iv_create_pass_arrow = findViewById(R.id.iv_create_pass_arrow);
        iv_create_pass_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
