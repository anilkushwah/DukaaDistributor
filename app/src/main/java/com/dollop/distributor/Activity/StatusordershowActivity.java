package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dollop.distributor.R;
import com.dollop.distributor.adapter.ShowOrderAdapter;
import com.dollop.distributor.model.ShoworderModel;

import java.util.ArrayList;
import java.util.List;

public class StatusordershowActivity extends AppCompatActivity {

    RecyclerView All_Complains_recyclerviewId;
    List<ShoworderModel> showModelList = new ArrayList<>();
    ImageView order_back;
    TextView status_cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusordershow);

        status_cancel = findViewById(R.id.status_cancel);
        order_back = findViewById(R.id.order_back);
        All_Complains_recyclerviewId = findViewById(R.id.All_Complains_recyclerviewId);

        order_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ShoworderModel modal = new ShoworderModel();

        modal.setAmount("60.00");
        modal.setName("Apple");
        modal.setItemquantity("(1kg)");
        modal.setOrderqty("3");
        modal.setOrderprice("20");
        modal.setImage(R.drawable.soft_drink);
        showModelList.add(modal);

        ShoworderModel modal1 = new ShoworderModel();
        modal1.setAmount("40.00");
        modal1.setName("Banana");
        modal1.setItemquantity("(12pcs)");
        modal1.setOrderqty("2");
        modal1.setOrderprice("20");
        modal1.setImage(R.drawable.soft_drink);
        showModelList.add(modal1);

        ShoworderModel modal2 = new ShoworderModel();


        modal2.setAmount("40.00");
        modal2.setName("Mango");
        modal2.setItemquantity("(2kg)");
        modal2.setOrderqty("2");
        modal2.setOrderprice("20");
        modal2.setImage(R.drawable.soft_drink);
        showModelList.add(modal2);

        ShoworderModel modal3 = new ShoworderModel();
        modal3.setOrderqty("3");
        modal3.setOrderprice("30");
        modal3.setAmount("90.00");
        modal3.setName("Coca-cola");
        modal3.setItemquantity("(2Ltr)");
        modal3.setImage(R.drawable.soft_drink);
        showModelList.add(modal3);

        ShoworderModel moda = new ShoworderModel();
        moda.setOrderqty("3");
        moda.setOrderprice("40");
        moda.setAmount("120.00");
        moda.setName("Apple");
        moda.setItemquantity("(1kg)");
        moda.setImage(R.drawable.soft_drink);
        showModelList.add(moda);


        All_Complains_recyclerviewId.setLayoutManager(new LinearLayoutManager(StatusordershowActivity.this, RecyclerView.VERTICAL, false));
        ShowOrderAdapter showAdapter = new ShowOrderAdapter(StatusordershowActivity.this, showModelList);
        All_Complains_recyclerviewId.setAdapter(showAdapter);

        status_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(StatusordershowActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_RIGHT_ICON);
                dialog.setContentView(R.layout.cancel_popup);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setAttributes(lp);
                // dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id

                TextView order_cancel = dialog.findViewById(R.id.order_cancel);

                order_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();


            }
        });
    }
}
