package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dollop.distributor.R;
import com.dollop.distributor.adapter.WishListProductAdapter;
import com.dollop.distributor.model.WishListProductsModelList;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class WishListActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rv_wishlist_products_Id;
    LinearLayout lv_serrrr;
    TextView tv_wishlist_Edit,tv_wishlist_Cancel;
    List<WishListProductsModelList>wishListProductsModelLists = new ArrayList<>();
    WishListProductAdapter wishListProductAdapter;
    CollapsingToolbarLayout collapsingtoolbar_layoutId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv_serrrr = findViewById(R.id.lv_serrrr);

        collapsingtoolbar_layoutId=(CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back_left);
        toolbar.setTitle("WishList");
        toolbar.setRight(R.layout.custom_notificatin_layout);

        collapsingtoolbar_layoutId.setTitle("WishList");
        collapsingtoolbar_layoutId.setCollapsedTitleTextColor(Color.parseColor("#FFFFFF"));
        collapsingtoolbar_layoutId.setExpandedTitleTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));

        AppBarLayout mAppBarLayout = (AppBarLayout)findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();

                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    toolbar.setTitle("WishList");
                    // showOption(R.id.action_info);
                } else if (isShow) {
                    isShow = false;
                    // hideOption(R.id.action_info);
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        rv_wishlist_products_Id = findViewById(R.id.rv_wishlist_products_Id);
        tv_wishlist_Edit = findViewById(R.id.tv_wishlist_Edit);
        tv_wishlist_Cancel = findViewById(R.id.tv_wishlist_Cancel);

        WishListProductsModelList wishListProductsModelList = new WishListProductsModelList();
        wishListProductsModelList.setWishListProductImage(R.drawable.backeryy);
        wishListProductsModelList.setCategoryTypeProduct("Snacks");
        wishListProductsModelList.setWishlistProductActualPrice("74");
        wishListProductsModelList.setWishlistProductName("Coca Cola - Soft Drink");
        wishListProductsModelList.setWishlistproductPriceOff("20");
        wishListProductsModelLists.add(wishListProductsModelList);


        WishListProductsModelList wishListProductsModelList1 = new WishListProductsModelList();
        wishListProductsModelList1.setWishListProductImage(R.drawable.backeryy);
        wishListProductsModelList1.setCategoryTypeProduct("Vegetables");
        wishListProductsModelList1.setWishlistProductActualPrice("50");
        wishListProductsModelList1.setWishlistProductName("Onions");
        wishListProductsModelList1.setWishlistproductPriceOff("10");
        wishListProductsModelLists.add(wishListProductsModelList1);


        WishListProductsModelList wishListProductsModelList2 = new WishListProductsModelList();
        wishListProductsModelList2.setWishListProductImage(R.drawable.backeryy);
        wishListProductsModelList2.setCategoryTypeProduct("Snacks");
        wishListProductsModelList2.setWishlistProductActualPrice("70");
        wishListProductsModelList2.setWishlistProductName("Lays Family Packs");
        wishListProductsModelList2.setWishlistproductPriceOff("25");
        wishListProductsModelLists.add(wishListProductsModelList2);


        WishListProductsModelList wishListProductsModelList3 = new WishListProductsModelList();
        wishListProductsModelList3.setWishListProductImage(R.drawable.backeryy);
        wishListProductsModelList3.setCategoryTypeProduct("Fruits");
        wishListProductsModelList3.setWishlistProductActualPrice("100");
        wishListProductsModelList3.setWishlistProductName("Mango");
        wishListProductsModelList3.setWishlistproductPriceOff("38");
        wishListProductsModelLists.add(wishListProductsModelList3);

        WishListProductsModelList wishListProductsModelList4 = new WishListProductsModelList();
        wishListProductsModelList4.setWishListProductImage(R.drawable.backeryy);
        wishListProductsModelList4.setCategoryTypeProduct("Snacks");
        wishListProductsModelList4.setWishlistProductActualPrice("70");
        wishListProductsModelList4.setWishlistProductName("Lays Family Packs");
        wishListProductsModelList4.setWishlistproductPriceOff("25");
        wishListProductsModelLists.add(wishListProductsModelList4);


        WishListProductsModelList wishListProductsModelList5 = new WishListProductsModelList();
        wishListProductsModelList5.setWishListProductImage(R.drawable.backeryy);
        wishListProductsModelList5.setCategoryTypeProduct("Fruits");
        wishListProductsModelList5.setWishlistProductActualPrice("120");
        wishListProductsModelList5.setWishlistProductName("Apple");
        wishListProductsModelList5.setWishlistproductPriceOff("32");
        wishListProductsModelLists.add(wishListProductsModelList5);


        rv_wishlist_products_Id.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_wishlist_products_Id.setLayoutManager(layoutManager);

        wishListProductAdapter = new WishListProductAdapter(this,wishListProductsModelLists);
        rv_wishlist_products_Id.setAdapter(wishListProductAdapter);

        tv_wishlist_Edit.setOnClickListener(this);
        tv_wishlist_Cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tv_wishlist_Edit){
            tv_wishlist_Edit.setVisibility(View.GONE);
            tv_wishlist_Cancel.setVisibility(View.VISIBLE);
            wishListProductAdapter.getDeletePosition(1);
            wishListProductAdapter.notifyDataSetChanged();

        }else if (v == tv_wishlist_Cancel){
            tv_wishlist_Cancel.setVisibility(View.GONE);
            tv_wishlist_Edit.setVisibility(View.VISIBLE);
            wishListProductAdapter.getDeletePosition(0);
            wishListProductAdapter.notifyDataSetChanged();

        }
    }
}
