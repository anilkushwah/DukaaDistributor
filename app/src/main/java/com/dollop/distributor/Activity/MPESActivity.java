package com.dollop.distributor.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.NetworkUtil;
import com.dollop.distributor.UtilityTools.Utils;

public class MPESActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mpesa_back_arrow;
    LinearLayout ll_amount;
    TextView tv_link_mpesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_p_e_s);

        tv_link_mpesa =  findViewById(R.id.tv_link_mpesa);
        ll_amount =  findViewById(R.id.ll_amount);
        mpesa_back_arrow =  findViewById(R.id.mpesa_back_arrow);
        mpesa_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_link_mpesa.setOnClickListener(this);



        boolean status = NetworkUtil.getConnectivityStatus(MPESActivity.this);
        if(status == true) {

        }else{
            Utils.T(MPESActivity.this,"No Internet Connection available. Please try again");
        }
    }

    @Override
    public void onClick(View v) {
        if(tv_link_mpesa == v){
            ll_amount.setVisibility(View.VISIBLE);
            tv_link_mpesa.setVisibility(View.GONE);
        }
    }
}
