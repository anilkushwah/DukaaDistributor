package com.dollop.retailer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import com.dollop.distributor.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {


    Context context;
    List<String> stringList;

    public ViewPagerAdapter(Context context,List<String> stringList) {

        this.context = context;
        this.stringList = stringList;

    }


    @Override
    public int getCount() {
        return stringList.size();
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View myImageLayout = LayoutInflater.from(context).inflate(R.layout.item_image_preview, container, false);
        ImageView image_preview = myImageLayout.findViewById(R.id.image_preview);
        if (stringList.get(position).equals("profile_pic")) {
// image_preview.setImageResource(R.drawable.);
        } else {
            Picasso.get().load(stringList.get(position)).into(image_preview);
        }

        container.addView(myImageLayout);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }

}