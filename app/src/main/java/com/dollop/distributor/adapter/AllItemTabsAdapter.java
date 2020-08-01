package com.dollop.distributor.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dollop.distributor.Fragment.BreakfastDaFragment;


public class AllItemTabsAdapter extends FragmentPagerAdapter {

    int totalTabs;
    Context context;

    public AllItemTabsAdapter(@NonNull FragmentManager fm, int totalTabs, Context context) {
        super(fm);
        this.totalTabs = totalTabs;
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                 BreakfastDaFragment bkfragment = new BreakfastDaFragment();
                return bkfragment;

            case 1:
                BreakfastDaFragment bkfragment1 = new BreakfastDaFragment();
                return bkfragment1;

            case 2:
                BreakfastDaFragment bkfragment2 = new BreakfastDaFragment();
                return bkfragment2;

            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}




