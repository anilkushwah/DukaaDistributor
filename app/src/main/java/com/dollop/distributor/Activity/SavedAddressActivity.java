package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.dollop.distributor.R;
import com.dollop.distributor.adapter.SavedAddressAdapter;
import com.dollop.distributor.model.savedaddress_model;

import java.util.ArrayList;
import java.util.List;

public class SavedAddressActivity extends AppCompatActivity {


    RecyclerView rv_saved_address;
    List<savedaddress_model> savedaddress_models = new ArrayList<>();
    SavedAddressAdapter savedAddressAdapter;
    LinearLayout ll_add_address;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_address);



        rv_saved_address = findViewById(R.id.rv_savedaddress);
        ll_add_address = findViewById(R.id.ll_add_address);

        savedaddress_model model = new savedaddress_model();
        model.setAddressname("KIOSK");
        model.setFulladdress("451/07, Sapphire City ,Indore,MP\nIndore India");
        savedaddress_models.add(model);


        savedaddress_model model1 = new savedaddress_model();
        model1.setAddressname("Other");
        model1.setFulladdress("07/12,Premium Towership,Indore,MP\nIndore India");
        savedaddress_models.add(model1);


        savedaddress_model model2 = new savedaddress_model();
        model2.setAddressname("KIOSK");
        model2.setFulladdress("12, Block-14 omaxe Phase -2,Indore,MP\nIndore India");
        savedaddress_models.add(model2);



        rv_saved_address.setLayoutManager(new LinearLayoutManager(SavedAddressActivity.this));

        savedAddressAdapter = new SavedAddressAdapter(SavedAddressActivity.this,savedaddress_models);
        rv_saved_address.setAdapter(savedAddressAdapter);

        ll_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent    = new Intent(SavedAddressActivity.this,SearchMapsActivity.class);
                startActivity(intent);
            }
        });
        
    }
}
