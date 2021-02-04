package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.NetworkUtil;
import com.dollop.distributor.UtilityTools.Utils;

public class TrackingActivity extends AppCompatActivity {

    ImageView track_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        track_back = findViewById(R.id.track_back);

        boolean status = NetworkUtil.getConnectivityStatus(TrackingActivity.this);
        if(status == true) {

        }else{
            Utils.T(TrackingActivity.this,"No Internet Connection available. Please try again");
        }

        track_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.I_clear(TrackingActivity.this, HomeActivity.class,null);
            }
        });
    }
}
