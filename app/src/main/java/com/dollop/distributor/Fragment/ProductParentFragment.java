package com.dollop.distributor.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.MyReviewViewPagerAdapter;
import com.dollop.distributor.model.CategoryModel;
import com.google.android.material.tabs.TabLayout;


public class ProductParentFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyReviewViewPagerAdapter adapter;
    static public int selectedPostion = 0;
    int selectedTabPosition;
    ImageView back_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_parent, container, false);
        back_back = view.findViewById(R.id.back_back);
        back_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        getIDs(view);
        setEvents();
        return view;
    }

    private void getIDs(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.my_viewpager);
        tabLayout = view.findViewById(R.id.mytablayoutId);
        adapter = new MyReviewViewPagerAdapter(getFragmentManager(), getActivity(), viewPager, tabLayout);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());
    }


    private void setEvents() {
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                selectedPostion = tab.getPosition();
                Utils.E("check PositionTest::" + selectedPostion);
                viewPager.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
                Utils.E("check PositionTestReselected::" + tab.getPosition());
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // ProductChildFragment fragment = (ProductChildFragment) adapter.getRegisteredFragment(position);

                // fragment.GetAllReviewTask(ViewMyReViewStatusActivity.reviewTitleModels.get(position).reviewTitle);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void addPage(CategoryModel categoryModel) {
        Bundle bundle = new Bundle();
        bundle.putString("name", categoryModel.getName());
        ProductChildFragment fragmentChild = new ProductChildFragment(categoryModel);

        Utils.E("checkChildName:1:" + categoryModel.getName());
        adapter.addFrag(fragmentChild, categoryModel.getName());
        adapter.notifyDataSetChanged();
        if (adapter.getCount() >= 0)
            tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(0);

        setupTabLayout();
    }

    public void clearAllPage(int position) {
        viewPager.removeAllViews();

    }

    public void clearAllTabse() {
        tabLayout.removeAllTabs();
    }

    public void setupTabLayout() {
        selectedTabPosition = viewPager.getCurrentItem();

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(adapter.getTabView(i));
        }
        tabLayout.getTabAt(0).select();

    }

}