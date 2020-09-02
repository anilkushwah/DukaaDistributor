package com.dollop.distributor.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dollop.distributor.Fragment.CompleteOrderFragment;
import com.dollop.distributor.Fragment.NewOrderFragment;
import com.dollop.distributor.Fragment.ProcessingFragment;


public class OrderTabsAdapter extends FragmentPagerAdapter {

    int totalTabs;
    Context context;

    public OrderTabsAdapter(@NonNull FragmentManager fm, int totalTabs, Context context) {
        super(fm);
        this.totalTabs = totalTabs;
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                return new NewOrderFragment();

            case 1:
                return new ProcessingFragment();

            case 2:
                return new CompleteOrderFragment();

            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}




