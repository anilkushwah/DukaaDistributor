package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.CreditRequestTabsAdapter;
import com.dollop.distributor.adapter.OrderTabsAdapter;
import com.google.android.material.tabs.TabLayout;

public class AllCreditRequestActivity extends AppCompatActivity  implements View.OnClickListener {

    ImageView allcredit_back;

    TabLayout tabLayout;
    ViewPager allrequestviewPager;
    CreditRequestTabsAdapter requestTabsAdapter;
    LinearLayout ll_reqtab_accepted, ll_tab_cancelled, ll_reqtab_new, main_requesttab,ll_credit_Setting;
    TextView tv_reqnew,tv_accepted, tv_cancelled, new_count,accepted_count, cancelled_count;
    public static int NewOrderCount = 0, ProcessingOrderCount = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_credit_request);

        allcredit_back =findViewById(R.id.allcredit_back);
        ll_credit_Setting =findViewById(R.id.ll_credit_Setting);


        //////////////////////

        cancelled_count =  findViewById(R.id.cancelled_count);
        main_requesttab =  findViewById(R.id.main_requesttab);
       
        ll_reqtab_accepted = findViewById(R.id.ll_reqtab_accepted);
       

        ll_tab_cancelled = findViewById(R.id.ll_tab_cancelled);
        ll_reqtab_new = findViewById(R.id.ll_reqtab_new);

        ll_reqtab_new.setOnClickListener(this);
        ll_credit_Setting.setOnClickListener(this);
        ll_reqtab_accepted.setOnClickListener(this);
        main_requesttab.setOnClickListener(this);
        ll_tab_cancelled.setOnClickListener(this);

        tv_reqnew = findViewById(R.id.tv_reqnew);
        new_count = findViewById(R.id.new_count);
        accepted_count = findViewById(R.id.accepted_count);
        tv_cancelled = findViewById(R.id.tv_cancelled);
        tv_accepted = findViewById(R.id.tv_accepted);

        tabLayout = (TabLayout) findViewById(R.id.tabscreditrequest);
        allrequestviewPager = (ViewPager) findViewById(R.id.allrequestviewPager);

        tabLayout.addTab(tabLayout.newTab().setText("New"));
        tabLayout.addTab(tabLayout.newTab().setText("Accepted"));
        tabLayout.addTab(tabLayout.newTab().setText("Cancelled"));



//getChildFragmentManager
        requestTabsAdapter = new CreditRequestTabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), AllCreditRequestActivity.this);
        allrequestviewPager.setAdapter(requestTabsAdapter);

        //allrequestviewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        allrequestviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {

                    tv_reqnew.setTextColor(Color.parseColor("#000000"));
                    tv_cancelled.setTextColor(Color.parseColor("#ffffff"));
                    tv_accepted.setTextColor(Color.parseColor("#ffffff"));
                    ll_reqtab_accepted.setBackgroundResource(0);
                    ll_reqtab_new.setBackgroundResource(R.drawable.tab_background_selected);
                    ll_tab_cancelled.setBackgroundResource(0);
                } else if (position == 1) {
                    ll_reqtab_accepted.setBackgroundResource(R.drawable.tab_background_selected);
                    tv_accepted.setTextColor(Color.parseColor("#000000"));
                    tv_reqnew.setTextColor(Color.parseColor("#ffffff"));
                    tv_cancelled.setTextColor(Color.parseColor("#ffffff"));
                    ll_reqtab_new.setBackgroundResource(0);
                    ll_tab_cancelled.setBackgroundResource(0);
                } else if (position == 2) {
                    tv_cancelled.setTextColor(Color.parseColor("#000000"));
                    tv_accepted.setTextColor(Color.parseColor("#ffffff"));
                    tv_reqnew.setTextColor(Color.parseColor("#ffffff"));
                    ll_reqtab_accepted.setBackgroundResource(0);
                    ll_reqtab_new.setBackgroundResource(0);
                    ll_tab_cancelled.setBackgroundResource(R.drawable.tab_background_selected);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                allrequestviewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
/////////////////
    }

    @Override
    public void onClick(View v) {
        if(v== allcredit_back){
            finish();
        }
        else if(v == ll_credit_Setting){
            Utils.I(AllCreditRequestActivity.this,CreditSettingActivity.class,null);
        }

        else if (v == ll_reqtab_new) {
            allrequestviewPager.setCurrentItem(0);
            tv_reqnew.setTextColor(Color.parseColor("#000000"));
            tv_cancelled.setTextColor(Color.parseColor("#ffffff"));
            tv_accepted.setTextColor(Color.parseColor("#ffffff"));
            ll_reqtab_accepted.setBackgroundResource(0);
            ll_reqtab_new.setBackgroundResource(R.drawable.tab_background_selected);
            ll_tab_cancelled.setBackgroundResource(0);
        }
        else if (v == ll_reqtab_accepted) {
            allrequestviewPager.setCurrentItem(1);
            //   Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_newFragment);
            ll_reqtab_accepted.setBackgroundResource(R.drawable.tab_background_selected);
            tv_accepted.setTextColor(Color.parseColor("#000000"));
            tv_reqnew.setTextColor(Color.parseColor("#ffffff"));
            tv_cancelled.setTextColor(Color.parseColor("#ffffff"));
            ll_reqtab_new.setBackgroundResource(0);
            ll_tab_cancelled.setBackgroundResource(0);
        }
        else if (v == ll_tab_cancelled) {
            allrequestviewPager.setCurrentItem(2);
            tv_cancelled.setTextColor(Color.parseColor("#000000"));
            tv_accepted.setTextColor(Color.parseColor("#ffffff"));
            tv_reqnew.setTextColor(Color.parseColor("#ffffff"));
            ll_reqtab_accepted.setBackgroundResource(0);
            ll_reqtab_new.setBackgroundResource(0);
            ll_tab_cancelled.setBackgroundResource(R.drawable.tab_background_selected);
        }
    }
}