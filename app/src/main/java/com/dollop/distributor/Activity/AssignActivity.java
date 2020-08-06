package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utils;

public class AssignActivity extends AppCompatActivity {

    ImageView assign_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign);

        assign_back = findViewById(R.id.assign_back);

        assign_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.I(AssignActivity.this,OrderDetailsActivity.class,null);
            }
        });
    }
}
