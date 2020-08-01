package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dollop.distributor.R;

public class MPESActivity extends AppCompatActivity {

    ImageView mpesa_back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_p_e_s);

        mpesa_back_arrow =  findViewById(R.id.mpesa_back_arrow);
        mpesa_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
