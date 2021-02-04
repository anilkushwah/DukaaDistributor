package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.NetworkUtil;
import com.dollop.distributor.UtilityTools.Utils;

public class AssignActivity extends AppCompatActivity {


    TextView arrived_driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign);


        arrived_driver = findViewById(R.id.arrived_driver);

        boolean status = NetworkUtil.getConnectivityStatus(AssignActivity.this);
        if(status == true) {

        }else{
            Utils.T(AssignActivity.this,"No Internet Connection available. Please try again");
        }

        arrived_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("arrived_driver","arrived_driver");
                Utils.I(AssignActivity.this,OrderDetailsActivity.class,bundle);
            }
        });
    }


}
