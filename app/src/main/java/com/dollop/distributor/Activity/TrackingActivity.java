package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dollop.distributor.R;

public class TrackingActivity extends AppCompatActivity {

    ImageView track_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        track_back = findViewById(R.id.track_back);

        track_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
            }
        });
    }
}
