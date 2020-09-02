package com.dollop.distributor.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dollop.distributor.Activity.CutomerDetailsAcceptActivity;
import com.dollop.distributor.Activity.CutomerDetailsActivity;
import com.dollop.distributor.Activity.CutomerDetailsCancelActivity;
import com.dollop.distributor.Fragment.BreakfastDaFragment;
import com.dollop.distributor.Fragment.CompleteOrderFragment;
import com.dollop.distributor.Fragment.NewCreditRequestFragment;
import com.dollop.distributor.Fragment.NewOrderFragment;
import com.dollop.distributor.Fragment.ProcessingFragment;
import com.dollop.distributor.UtilityTools.Utils;


public class CreditRequestTabsAdapter extends FragmentPagerAdapter {

    int totalTabs;
    Context context;

    public CreditRequestTabsAdapter(@NonNull FragmentManager fm, int totalTabs, Context context) {
        super(fm);
        this.totalTabs = totalTabs;
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                return new NewCreditRequestFragment();

            case 1:

                return new NewCreditRequestFragment();
           //     Utils.I(context, CutomerDetailsAcceptActivity.class, null);

            case 2: return new NewCreditRequestFragment();

              //  Utils.I(context, CutomerDetailsCancelActivity.class, null);

            default:
                return null;
         }


    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}




