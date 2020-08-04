package com.dollop.distributor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dollop.distributor.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ViewPager vpimgs;
    LinearLayout llindicators;
    List<View> viewList = new ArrayList<>();
    ImageView[] dots=null;
    int pos;
    Button btnsplashNextId;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llindicators = findViewById(R.id.llindicators);
        vpimgs = findViewById(R.id.vpimgs);
        btnsplashNextId = findViewById(R.id.btnsplashNextId);
        viewList.clear();


        LayoutInflater inflater = getLayoutInflater();
        RelativeLayout relativeLayout1 = (RelativeLayout) inflater.inflate(R.layout.splashscreenlatout1,null);
        RelativeLayout relativeLayout2 = (RelativeLayout) inflater.inflate(R.layout.splashscreenlayout2,null);
        RelativeLayout relativeLayout3 = (RelativeLayout) inflater.inflate(R.layout.splashscreenlayout3,null);
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
                        finalDots[i].setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.nonselecteditem_dot));
                    }
                    finalDots[pos].setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.selecteditem_dot));
                }
                if (pos==2){
                    btnsplashNextId.setText("Done");
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnsplashNextId.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        if (v == btnsplashNextId) {
            switch (v.getId()) {
                case R.id.btnsplashNextId:
                    if (pos <= 1) {
                        vpimgs.setCurrentItem(pos + 1);
                    }else if (pos == 2){
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                    break;

            }
        }
    }
}
