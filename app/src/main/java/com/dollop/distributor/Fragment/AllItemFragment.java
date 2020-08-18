package com.dollop.distributor.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollop.distributor.R;
import com.dollop.distributor.adapter.AllItemTabsAdapter;
import com.google.android.material.tabs.TabLayout;

public class AllItemFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager allitemsviewPager;
    AllItemTabsAdapter alltabAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View root=  inflater.inflate(R.layout.fragment_all_item, container, false);

        tabLayout = (TabLayout) root.findViewById(R.id.tabsallitem);
        allitemsviewPager = (ViewPager) root.findViewById(R.id.vp_allitems);

        tabLayout.addTab(tabLayout.newTab().setText("Breakfast & Dairy"));
        tabLayout.addTab(tabLayout.newTab().setText("Vegitable & Friuts"));
        tabLayout.addTab(tabLayout.newTab().setText("Household"));
//getChildFragmentManager
        ///getSupportFragmentManager
        alltabAdapter = new AllItemTabsAdapter(getChildFragmentManager(),tabLayout.getTabCount(),getActivity());
        allitemsviewPager.setAdapter(alltabAdapter);
        allitemsviewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                allitemsviewPager.setCurrentItem(tab.getPosition());
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
}
