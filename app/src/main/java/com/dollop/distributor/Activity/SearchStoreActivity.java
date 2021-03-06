package com.dollop.distributor.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.NetworkUtil;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.SearchStoreProductAdapter;
import com.dollop.distributor.model.SearchStoreModelList;

import java.util.ArrayList;
import java.util.List;

public class SearchStoreActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView rv_search_store_Id;
    List<SearchStoreModelList>searchStoreModelLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_store);
        rv_search_store_Id = findViewById(R.id.rv_search_store_Id);

        boolean status = NetworkUtil.getConnectivityStatus(SearchStoreActivity.this);
        if(status == true) {

        }else{
            Utils.T(SearchStoreActivity.this,"No Internet Connection available. Please try again");
        }


        SearchStoreModelList searchStoreModelList = new SearchStoreModelList();
        searchStoreModelList.setSearchRating("4.5");
        searchStoreModelList.setSearchStoreImage(R.drawable.no_product);
        searchStoreModelList.setSearchStoreName("The Fresh Market");
        searchStoreModelList.setSearchStoreVarietyName("Fruits and Veggies");
        searchStoreModelList.setSearchStoreOffPrice("Rs. 200");
        searchStoreModelList.setSearchStoreProductOff("Extra 10% Off on purchase of");
        searchStoreModelLists.add(searchStoreModelList);

        SearchStoreModelList searchStoreModelList1 = new SearchStoreModelList();
        searchStoreModelList1.setSearchRating("4.5");
        searchStoreModelList1.setSearchStoreImage(R.drawable.no_product);
        searchStoreModelList1.setSearchStoreName("Harvert Market");
        searchStoreModelList1.setSearchStoreVarietyName("Fruits and Veggies");
        searchStoreModelList1.setSearchStoreOffPrice("Rs. 400");
        searchStoreModelList1.setSearchStoreProductOff("Extra 25% Off on purchase of");
        searchStoreModelLists.add(searchStoreModelList1);

        SearchStoreModelList searchStoreModelList2 = new SearchStoreModelList();
        searchStoreModelList2.setSearchRating("4.5");
        searchStoreModelList2.setSearchStoreImage(R.drawable.no_product);
        searchStoreModelList2.setSearchStoreName("Whole Food Market");
        searchStoreModelList2.setSearchStoreVarietyName("Fruits and Veggies");
        searchStoreModelList2.setSearchStoreProductOff("Extra 10% Off on purchase of");
        searchStoreModelLists.add(searchStoreModelList2);

        SearchStoreModelList searchStoreModelList3 = new SearchStoreModelList();
        searchStoreModelList3.setSearchRating("4.5");
        searchStoreModelList3.setSearchStoreImage(R.drawable.no_product);
        searchStoreModelList3.setSearchStoreName("The Fresh Market");
        searchStoreModelList3.setSearchStoreVarietyName("Fruits and Veggies");
        searchStoreModelList3.setSearchStoreOffPrice("Rs. 200");
        searchStoreModelList3.setSearchStoreProductOff("Extra 10% Off on purchase of");
        searchStoreModelLists.add(searchStoreModelList3);



        rv_search_store_Id.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_search_store_Id.setLayoutManager(layoutManager);

        SearchStoreProductAdapter searchStoreProductAdapter = new SearchStoreProductAdapter(this,searchStoreModelLists);
        rv_search_store_Id.setAdapter(searchStoreProductAdapter);

    }

    @Override
    public void onClick(View v) {

    }
}
