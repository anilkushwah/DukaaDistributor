package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.NetworkUtil;
import com.dollop.distributor.UtilityTools.Utils;

public class BankDetailsActivity extends AppCompatActivity {

    ImageView bankdetails_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);




        bankdetails_back = findViewById(R.id.bankdetails_back);

        boolean status = NetworkUtil.getConnectivityStatus(BankDetailsActivity.this);
        if(status == true) {

        }else{
            Utils.T(BankDetailsActivity.this,"No Internet Connection available. Please try again");
        }

        bankdetails_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
