package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dollop.distributor.R;
import com.dollop.distributor.adapter.ProductRelatedAdapter;
import com.dollop.distributor.adapter.ViewPagerAdapter;
import com.dollop.distributor.model.ProductRelatedModelList;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    ViewPager vpimgs;
    LinearLayout llindicators;
    List<View> viewList = new ArrayList<>();
    List<ProductRelatedModelList>productRelatedModelLists = new ArrayList<>();
    ImageView[] dots=null;
    int pos;
    RecyclerView rv_product_related_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        llindicators = findViewById(R.id.llindicators_product);
        vpimgs = findViewById(R.id.vproduct_details);
        rv_product_related_list = findViewById(R.id.rv_product_related_list);
        viewList.clear();

        LayoutInflater inflater = getLayoutInflater();
        LinearLayout relativeLayout1 = (LinearLayout) inflater.inflate(R.layout.productdetailslatout1,null);
        LinearLayout relativeLayout2 = (LinearLayout) inflater.inflate(R.layout.productdetailslayout2,null);
        LinearLayout relativeLayout3 = (LinearLayout) inflater.inflate(R.layout.productdetailslayout3,null);
        viewList.add(relativeLayout1);
        viewList.add(relativeLayout2);
        viewList.add(relativeLayout3);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this,viewList);
        vpimgs.setAdapter(viewPagerAdapter);
        final int dotsCount=viewPagerAdapter.getCount();
        llindicators.setVisibility(View.VISIBLE);
        dots=setUiPageViewController(llindicators,dotsCount);

        final ImageView[] finalDots = dots;


        vpimgs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pos = position;

                if(finalDots!=null) {
                    for (int i = 0; i < dotsCount; i++) {
                        finalDots[i].setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.nonselectedproductitem_dot));
                    }
                    finalDots[pos].setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.selectedproductitem_dot));
                }
                if (pos==2){

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ProductRelatedModelList productRelatedModelList = new ProductRelatedModelList();
        productRelatedModelList.setProductRealtedImage(R.drawable.backeryy);
        productRelatedModelList.setProductRelatedPrice("34");
        productRelatedModelList.setProductRelatedPriceCutoff("12");
        productRelatedModelList.setProductRelatedProductName("Banana");
        productRelatedModelList.setProductRelatedProductVariety("Fruits");
        productRelatedModelLists.add(productRelatedModelList);


        ProductRelatedModelList productRelatedModelList1 = new ProductRelatedModelList();
        productRelatedModelList1.setProductRealtedImage(R.drawable.backeryy);
        productRelatedModelList1.setProductRelatedPrice("34");
        productRelatedModelList1.setProductRelatedPriceCutoff("12");
        productRelatedModelList1.setProductRelatedProductName("Banana");
        productRelatedModelList1.setProductRelatedProductVariety("Fruits");
        productRelatedModelLists.add(productRelatedModelList1);

        ProductRelatedModelList productRelatedModelList2 = new ProductRelatedModelList();
        productRelatedModelList2.setProductRealtedImage(R.drawable.backeryy);
        productRelatedModelList2.setProductRelatedPrice("34");
        productRelatedModelList2.setProductRelatedPriceCutoff("12");
        productRelatedModelList2.setProductRelatedProductName("Banana");
        productRelatedModelList2.setProductRelatedProductVariety("Fruits");
        productRelatedModelLists.add(productRelatedModelList2);


        rv_product_related_list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_product_related_list.setLayoutManager(layoutManager);

        ProductRelatedAdapter productRelatedAdapter= new ProductRelatedAdapter(this,productRelatedModelLists);
        rv_product_related_list.setAdapter(productRelatedAdapter);


    }

    @Override
    public void onClick(View v) {

    }

    private ImageView[] setUiPageViewController(LinearLayout pager_indicator, int dotsCount) {

        ImageView[] dots = new ImageView[dotsCount];
        pager_indicator.removeAllViews();
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(this.getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(8, 0, 8, 0);
            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(this.getResources().getDrawable(R.drawable.selecteditem_dot));

        return dots;
    }
}
