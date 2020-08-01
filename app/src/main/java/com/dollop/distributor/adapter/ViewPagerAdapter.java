package com.dollop.distributor.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<View> mResources;

    public ViewPagerAdapter(Context mContext, List<View> list) {
        this.mContext = mContext;
        this.mResources = list;
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        final View V = mResources.get(position);
/*
        if(model.getImage_name()!=null && model.getImage_name().trim().length()>0){
            Picasso.with(mContext)
                    .load(AppConst.salonurl+model.getImage_name())
                    .into(tv);
        }
*/

        container.addView(V);

        return V;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mResources.get(position));
    }
}