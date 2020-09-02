package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.AssignOrderdetailsAdapter;
import com.dollop.distributor.adapter.ShowOrderAdapter;
import com.dollop.distributor.model.ShoworderModel;

import java.util.ArrayList;
import java.util.List;

public class AssignOrderDetailsActivity extends AppCompatActivity {

    RecyclerView rv_assign_od_list;
    RecyclerView All_Complains_recyclerviewId;
    List<ShoworderModel> showModelList = new ArrayList<>();
    TextView assign_driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_order_details);

        rv_assign_od_list = findViewById(R.id.rv_assign_od_list);
        assign_driver = findViewById(R.id.assign_driver);

        assign_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.I(AssignOrderDetailsActivity.this,AssignActivity.class,null);
            }
        });


       /* ShoworderModel modal = new ShoworderModel();

        modal.setAmount("60.00");
        modal.setName("Apple");
        modal.setItemquantity("(1kg)");
        modal.setOrderqty("3");
        modal.setOrderprice("20");
     //   modal.setImage(R.drawable.soft_drink);
        showModelList.add(modal);

        ShoworderModel modal1 = new ShoworderModel();
        modal1.setAmount("40.00");
        modal1.setName("Banana");
        modal1.setItemquantity("(12pcs)");
        modal1.setOrderqty("2");
        modal1.setOrderprice("20");
     //  modal1.setImage(R.drawable.soft_drink);
        showModelList.add(modal1);*/



        rv_assign_od_list.setLayoutManager(new LinearLayoutManager(AssignOrderDetailsActivity.this, RecyclerView.VERTICAL, false));
        AssignOrderdetailsAdapter showAdapter = new AssignOrderdetailsAdapter(AssignOrderDetailsActivity.this, showModelList);
        rv_assign_od_list.setAdapter(showAdapter);

    }
}
