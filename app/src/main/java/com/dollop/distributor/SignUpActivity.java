package com.dollop.distributor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tv_GoSignInId;
    ImageView iv_signup_back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        tv_GoSignInId= findViewById(R.id.tv_GoSignInId);
        iv_signup_back_arrow= findViewById(R.id.iv_signup_back_arrow);


        tv_GoSignInId.setOnClickListener(this);
        iv_signup_back_arrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tv_GoSignInId){
            Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else if(v == iv_signup_back_arrow){
            onBackPressed();
        }
    }
}
