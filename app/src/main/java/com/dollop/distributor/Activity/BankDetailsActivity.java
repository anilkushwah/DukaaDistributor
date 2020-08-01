package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dollop.distributor.R;

public class BankDetailsActivity extends AppCompatActivity {

    ImageView bankdetails_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);

        bankdetails_back = findViewById(R.id.bankdetails_back);

        bankdetails_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
