package com.dollop.distributor.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dollop.distributor.Fragment.CompleteOrderFragment;
import com.dollop.distributor.Fragment.NewFragment;
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
                 NewFragment newFragment = new NewFragment();
                return newFragment;

            case 1:
                ProcessingFragment processeFragment1 = new ProcessingFragment();
                return processeFragment1;

            case 2:
                CompleteOrderFragment completefragmet = new CompleteOrderFragment();
                return completefragmet;

            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}




