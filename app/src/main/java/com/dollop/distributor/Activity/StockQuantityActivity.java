package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.dollop.distributor.R;
import com.dollop.distributor.adapter.StockAdapterAdapter;
import com.dollop.distributor.model.StockQuantity_model;

import java.util.ArrayList;

public class StockQuantityActivity extends AppCompatActivity {

    RecyclerView rv_stock_item;
    ArrayList<StockQuantity_model> stockQuantityModels = new ArrayList<>();
    StockAdapterAdapter  stockAdapterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_quantity);

        rv_stock_item = findViewById(R.id.rv_stock_item);
        rv_stock_item.setLayoutManager(new GridLayoutManager(this, 2));

        list();
    }

    private void list() {

        StockQuantity_model   stock_model = new StockQuantity_model();
        stock_model.setName("Fresh Onion");
        stock_model.setQuantity("15");
        stockQuantityModels.add(stock_model);

        StockQuantity_model   stock_model1 = new StockQuantity_model();
        stock_model1.setName("Bnanas");
        stock_model1.setQuantity("15");
        stockQuantityModels.add(stock_model1);

        StockQuantity_model   stock_model2 = new StockQuantity_model();
        stock_model2.setName("Coca Coal");
        stock_model2.setQuantity("105");
        stockQuantityModels.add(stock_model2);

        StockQuantity_model   stock_model3 = new StockQuantity_model();
        stock_model3.setName("Lays");
        stock_model3.setQuantity("1500");
        stockQuantityModels.add(stock_model3);

        stockAdapterAdapter = new StockAdapterAdapter(StockQuantityActivity.this,stockQuantityModels);
        rv_stock_item.setAdapter(stockAdapterAdapter);
    }
}
