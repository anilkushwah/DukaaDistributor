package com.dollop.distributor.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.dollop.distributor.R;
import com.dollop.distributor.adapter.OrderTabsAdapter;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment implements View.OnClickListener{

    TabLayout tabLayout;
    ViewPager orderviewPager;
    OrderTabsAdapter ordertabAdapter;

    LinearLayout  ll_tab_processing,ll_tab_completed,ll_tab_new,main_tab;
    TextView tv_new,tv_completed,tv_processing;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        main_tab =  root.findViewById(R.id.main_tab); main_tab.setOnClickListener(this);
        ll_tab_processing =  root.findViewById(R.id.ll_tab_processing); ll_tab_processing.setOnClickListener(this);
        ll_tab_completed =  root.findViewById(R.id.ll_tab_completed);ll_tab_completed.setOnClickListener(this);
        ll_tab_new = root.findViewById(R.id.ll_tab_new);ll_tab_new.setOnClickListener(this);

        tv_new =   root.findViewById(R.id.tv_new);
        tv_completed =   root.findViewById(R.id.tv_completed);
        tv_processing =   root.findViewById(R.id.tv_processing);

        tabLayout = (TabLayout) root.findViewById(R.id.tabsorders);
        orderviewPager = (ViewPager) root.findViewById(R.id.vp_orders);

        tabLayout.addTab(tabLayout.newTab().setText("New"));
        tabLayout.addTab(tabLayout.newTab().setText("Processing"));
        tabLayout.addTab(tabLayout.newTab().setText("Ready For Pickup"));
//getChildFragmentManager
        ordertabAdapter = new OrderTabsAdapter(getChildFragmentManager(),tabLayout.getTabCount(),getActivity());
        orderviewPager.setAdapter(ordertabAdapter);

        //orderviewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        orderviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == 0){

                    tv_new.setTextColor(Color.parseColor("#000000"));
                    tv_completed.setTextColor(Color.parseColor("#ffffff"));
                    tv_processing.setTextColor(Color.parseColor("#ffffff"));
                    ll_tab_processing.setBackgroundResource(0);
                    ll_tab_new.setBackgroundResource(R.drawable.tab_background_selected);
                    ll_tab_completed.setBackgroundResource(0);
                }

                else if(position ==1){
                    ll_tab_processing.setBackgroundResource(R.drawable.tab_background_selected);
                    tv_processing.setTextColor(Color.parseColor("#000000"));
                    tv_new.setTextColor(Color.parseColor("#ffffff"));
                    tv_completed.setTextColor(Color.parseColor("#ffffff"));
                    ll_tab_new.setBackgroundResource(0);
                    ll_tab_completed.setBackgroundResource(0);
                }
                else if(position ==2){
                    tv_completed.setTextColor(Color.parseColor("#000000"));
                    tv_processing.setTextColor(Color.parseColor("#ffffff"));
                    tv_new.setTextColor(Color.parseColor("#ffffff"));
                    ll_tab_processing.setBackgroundResource(0);
                    ll_tab_new.setBackgroundResource(0);
                    ll_tab_completed.setBackgroundResource(R.drawable.tab_background_selected);
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
            public void onTabSelected( TabLayout.Tab tab) {
                orderviewPager.setCurrentItem(tab.getPosition());
         /*       if(tab.getPosition() == 0){
                    tv_new.setTextColor(Color.parseColor("#000000"));
                    tv_completed.setTextColor(Color.parseColor("#ffffff"));
                    tv_processing.setTextColor(Color.parseColor("#ffffff"));

                    ll_tab_processing.setBackgroundResource(0);
                    ll_tab_new.setBackgroundResource(R.drawable.tab_background_selected);
                    ll_tab_completed.setBackgroundResource(0);
                }

                else if (tab.getPosition() ==1){
                    ll_tab_processing.setBackgroundResource(R.drawable.tab_background_selected);
                    tv_processing.setTextColor(Color.parseColor("#000000"));
                    tv_new.setTextColor(Color.parseColor("#ffffff"));
                    tv_completed.setTextColor(Color.parseColor("#ffffff"));
                    ll_tab_new.setBackgroundResource(0);
                    ll_tab_completed.setBackgroundResource(0);
                }
                else if (tab.getPosition() ==2){
                    tv_completed.setTextColor(Color.parseColor("#000000"));
                    tv_processing.setTextColor(Color.parseColor("#ffffff"));
                    tv_new.setTextColor(Color.parseColor("#ffffff"));
                    ll_tab_processing.setBackgroundResource(0);
                    ll_tab_new.setBackgroundResource(0);
                    ll_tab_completed.setBackgroundResource(R.drawable.tab_background_selected);
                }*/
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        return root;
    }

    @Override
    public void onClick(View v) {

        if(v == ll_tab_new){
            orderviewPager.setCurrentItem(0);
            tv_new.setTextColor(Color.parseColor("#000000"));
            tv_completed.setTextColor(Color.parseColor("#ffffff"));
            tv_processing.setTextColor(Color.parseColor("#ffffff"));
            ll_tab_processing.setBackgroundResource(0);
            ll_tab_new.setBackgroundResource(R.drawable.tab_background_selected);
            ll_tab_completed.setBackgroundResource(0);
        }
       else if(v == ll_tab_processing){
            orderviewPager.setCurrentItem(1);
         //   Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_newFragment);
            ll_tab_processing.setBackgroundResource(R.drawable.tab_background_selected);
            tv_processing.setTextColor(Color.parseColor("#000000"));
            tv_new.setTextColor(Color.parseColor("#ffffff"));
            tv_completed.setTextColor(Color.parseColor("#ffffff"));
            ll_tab_new.setBackgroundResource(0);
            ll_tab_completed.setBackgroundResource(0);
        }

        else if(v == ll_tab_completed){
            orderviewPager.setCurrentItem(2);
            tv_completed.setTextColor(Color.parseColor("#000000"));
            tv_processing.setTextColor(Color.parseColor("#ffffff"));
            tv_new.setTextColor(Color.parseColor("#ffffff"));
            ll_tab_processing.setBackgroundResource(0);
            ll_tab_new.setBackgroundResource(0);
            ll_tab_completed.setBackgroundResource(R.drawable.tab_background_selected);
        }
    }
}
