package com.dollop.distributor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dollop.distributor.Activity.SearchMapsActivity;
import com.dollop.distributor.Fragment.SetTimingFragment;

public class LocationActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout btn_use_Location_Id,add_location_manually;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        add_location_manually = findViewById(R.id.add_location_manually);
        btn_use_Location_Id = findViewById(R.id.btn_use_Location_Id);
        btn_use_Location_Id.setOnClickListener(this);
        add_location_manually.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
          if (v == btn_use_Location_Id){
            Intent intent = new Intent(LocationActivity.this, SetTimingFragment.class);
            startActivity(intent);
            finish();
        }

          else if(v == add_location_manually){
              Intent intent = new Intent(LocationActivity.this, SearchMapsActivity.class);
              startActivity(intent);
              finish();
          }
    }
}
