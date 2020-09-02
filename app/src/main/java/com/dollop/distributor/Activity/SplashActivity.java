package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.dollop.distributor.MainActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.I(SplashActivity.this, MainActivity.class,null);
                finish();
            }
        }, 5000);

    }
}