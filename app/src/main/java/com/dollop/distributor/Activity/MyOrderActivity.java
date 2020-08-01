package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.dollop.distributor.R;
import com.dollop.distributor.adapter.MyOrderAdapter;
import com.dollop.distributor.model.MyOderlist;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends AppCompatActivity {

    RecyclerView rv_myorder;
    List<MyOderlist> myOderlistList = new ArrayList<>();
    MyOrderAdapter myOrderAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        rv_myorder = findViewById(R.id.rv_myorder);

        MyOderlist model = new MyOderlist();
        model.setMarket_name("The Fresh Market");
        model.setOrder_title("Fritus and veggies");
        model.setOrder_item("1 * Bananas, 2kg Onions");
        model.setOrder_time("Today at 11:00 PM");
        model.setOder_amount("400");
        myOderlistList.add(model);

        MyOderlist model1 = new MyOderlist();
        model1.setMarket_name("The Fresh Market");
        model1.setOrder_title("Fritus and veggies");
        model1.setOrder_item("1 * Bananas, 2kg Onions");
        model1.setOrder_time("Today at 11:00 PM");
        model1.setOder_amount("400");
        myOderlistList.add(model1);


        MyOderlist model2 = new MyOderlist();
        model2.setMarket_name("The Fresh Market");
        model2.setOrder_title("Fritus and veggies");
        model2.setOrder_item("1 * Bananas, 2kg Onions");
        model2.setOrder_time("Today at 11:00 PM");
        model2.setOder_amount("400");
        myOderlistList.add(model2);


        MyOderlist model3 = new MyOderlist();
        model3.setMarket_name("The Fresh Market");
        model3.setOrder_title("Fritus and veggies");
        model3.setOrder_item("1 * Bananas, 2kg Onions");
        model3.setOrder_time("Today at 11:00 PM");
        model3.setOder_amount("400");
        myOderlistList.add(model3);


        rv_myorder.setLayoutManager(new LinearLayoutManager(MyOrderActivity.this));
        myOrderAdapter = new MyOrderAdapter(MyOrderActivity.this,myOderlistList);
        rv_myorder.setAdapter(myOrderAdapter);

    }
}
