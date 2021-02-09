package com.dollop.distributor.adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.dollop.distributor.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * Created by sohel on 1/10/2018.
 */

public class MyReviewViewPagerAdapter extends FragmentStatePagerAdapter {
  private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
  private final ArrayList<String> mFragmentTitleList = new ArrayList<>();
  SparseArray<Fragment> registeredFragments = new SparseArray<>();

  Context context;
  ViewPager viewPager;
  TabLayout tabLayout;

  public MyReviewViewPagerAdapter(FragmentManager manager, Context context, ViewPager viewPager,
                                  TabLayout tabLayout) {
    super(manager);
    this.context = context;
    this.viewPager = viewPager;
    this.tabLayout = tabLayout;
  }

  @Override
  public Fragment getItem(int position) {
    return mFragmentList.get(position);
  }

  @Override
  public int getCount() {
    return mFragmentList.size();
  }

  public void addFrag(Fragment fragment, String title) {
    mFragmentList.add(fragment);
    mFragmentTitleList.add(title);

  }

  public void removeFrag(int position) {
    removeTab(position);
    Fragment fragment = mFragmentList.get(position);
    mFragmentList.remove(fragment);
    mFragmentTitleList.remove(position);
    destroyFragmentView(viewPager, position, fragment);
    notifyDataSetChanged();
  }

  public View getTabView(final int position) {
    View view = LayoutInflater.from(context).inflate(R.layout.my_team_custom_tab_item, null);
    TextView tabItemName = (TextView) view.findViewById(R.id.tvCustomTabId);
    tabItemName.setText(mFragmentTitleList.get(position));
    return view;
  }

  private void destroyFragmentView(ViewGroup container, int position, Object object) {
    FragmentManager manager = ((Fragment) object).getFragmentManager();
    FragmentTransaction trans = manager.beginTransaction();
    trans.remove((Fragment) object);
    trans.commit();
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    Fragment fragment = (Fragment) super.instantiateItem(container, position);
    registeredFragments.put(position, fragment);
    return fragment;
  }

  private void removeTab(int position) {
    if (tabLayout.getChildCount() > 0) {
      tabLayout.removeTabAt(position);
    }
  }

  public Fragment getRegisteredFragment(int position) {
    return registeredFragments.get(position);
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    registeredFragments.remove(position);
    super.destroyItem(container, position, object);
  }

  @Override
  public int getItemPosition(Object object) {
    return POSITION_NONE;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return mFragmentTitleList.get(position);
  }
}
