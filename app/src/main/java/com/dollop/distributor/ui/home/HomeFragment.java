package com.dollop.distributor.ui.home;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.dollop.distributor.Activity.AllCreditRequestActivity;
import com.dollop.distributor.Activity.CreditSettingActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.NewOrderAdapter;
import com.dollop.distributor.adapter.OrderTabsAdapter;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.AllOrderDTO;
import com.dollop.distributor.model.NewOderlist;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeFragment extends Fragment implements View.OnClickListener {

    TabLayout tabLayout;
    ViewPager orderviewPager;
    OrderTabsAdapter ordertabAdapter;
    LinearLayout ll_tab_processing, ll_tab_completed, ll_tab_new, main_tab;
    TextView tv_new, tv_completed, tv_processing, new_count, processing_count;
    public static int NewOrderCount = 0, ProcessingOrderCount = 0;
    RelativeLayout   rl_creditreq;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        rl_creditreq = root.findViewById(R.id.rl_creditreq);
        main_tab = root.findViewById(R.id.main_tab);
        main_tab.setOnClickListener(this);
        ll_tab_processing = root.findViewById(R.id.ll_tab_processing);
        ll_tab_processing.setOnClickListener(this);
        ll_tab_completed = root.findViewById(R.id.ll_tab_completed);
        ll_tab_completed.setOnClickListener(this);
        ll_tab_new = root.findViewById(R.id.ll_tab_new);
        ll_tab_new.setOnClickListener(this);

        tv_new = root.findViewById(R.id.tv_new);
        new_count = root.findViewById(R.id.new_count);
        processing_count = root.findViewById(R.id.processing_count);
        tv_completed = root.findViewById(R.id.tv_completed);
        tv_processing = root.findViewById(R.id.tv_processing);

        tabLayout = (TabLayout) root.findViewById(R.id.tabsorders);
        orderviewPager = (ViewPager) root.findViewById(R.id.vp_orders);

        tabLayout.addTab(tabLayout.newTab().setText("New"));
        tabLayout.addTab(tabLayout.newTab().setText("Processing"));
        tabLayout.addTab(tabLayout.newTab().setText("Ready For Pickup"));

        rl_creditreq.setOnClickListener(this);


        processing_count.setText("" + ProcessingOrderCount);
//getChildFragmentManager
        ordertabAdapter = new OrderTabsAdapter(getChildFragmentManager(), tabLayout.getTabCount(), getActivity());
        orderviewPager.setAdapter(ordertabAdapter);

        //orderviewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        orderviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {

                    tv_new.setTextColor(Color.parseColor("#000000"));
                    tv_completed.setTextColor(Color.parseColor("#ffffff"));
                    tv_processing.setTextColor(Color.parseColor("#ffffff"));
                    ll_tab_processing.setBackgroundResource(0);
                    ll_tab_new.setBackgroundResource(R.drawable.tab_background_selected);
                    ll_tab_completed.setBackgroundResource(0);
                } else if (position == 1) {
                    ll_tab_processing.setBackgroundResource(R.drawable.tab_background_selected);
                    tv_processing.setTextColor(Color.parseColor("#000000"));
                    tv_new.setTextColor(Color.parseColor("#ffffff"));
                    tv_completed.setTextColor(Color.parseColor("#ffffff"));
                    ll_tab_new.setBackgroundResource(0);
                    ll_tab_completed.setBackgroundResource(0);
                } else if (position == 2) {
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
            public void onTabSelected(TabLayout.Tab tab) {
                orderviewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
     //   allOrderMethod();
        return root;
    }

    ArrayList<AllOrderDTO> mAllOrderDTOArrayList = new ArrayList<>();

    private void allOrderMethod() {

        final Dialog dialog = Utils.initProgressDialog(getActivity());
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
/*distributor_id:1
status:new
*/
        HashMap<String, String> hm = new HashMap<>();
        hm.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
        hm.put("status", "new");

        Call<NewOderlist> call = apiService.newOderlist(hm);
        call.enqueue(new Callback<NewOderlist>() {
            @Override
            public void onResponse(Call<NewOderlist> call, retrofit2.Response<NewOderlist> response) {
                dialog.dismiss();
                try {

                    NewOderlist body = response.body();

                    if (body.getStatus() == 200) {
                        mAllOrderDTOArrayList = body.getData();
                        new_count.setText("" + mAllOrderDTOArrayList.size());
                    } else {


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<NewOderlist> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {

        if (v == ll_tab_new) {
            orderviewPager.setCurrentItem(0);
            tv_new.setTextColor(Color.parseColor("#000000"));
            tv_completed.setTextColor(Color.parseColor("#ffffff"));
            tv_processing.setTextColor(Color.parseColor("#ffffff"));
            ll_tab_processing.setBackgroundResource(0);
            ll_tab_new.setBackgroundResource(R.drawable.tab_background_selected);
            ll_tab_completed.setBackgroundResource(0);
        }
        else if (v == ll_tab_processing) {
            orderviewPager.setCurrentItem(1);
            //   Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_newFragment);
            ll_tab_processing.setBackgroundResource(R.drawable.tab_background_selected);
            tv_processing.setTextColor(Color.parseColor("#000000"));
            tv_new.setTextColor(Color.parseColor("#ffffff"));
            tv_completed.setTextColor(Color.parseColor("#ffffff"));
            ll_tab_new.setBackgroundResource(0);
            ll_tab_completed.setBackgroundResource(0);
        }
        else if (v == ll_tab_completed) {
            orderviewPager.setCurrentItem(2);
            tv_completed.setTextColor(Color.parseColor("#000000"));
            tv_processing.setTextColor(Color.parseColor("#ffffff"));
            tv_new.setTextColor(Color.parseColor("#ffffff"));
            ll_tab_processing.setBackgroundResource(0);
            ll_tab_new.setBackgroundResource(0);
            ll_tab_completed.setBackgroundResource(R.drawable.tab_background_selected);
        }
        else if(v == rl_creditreq){
            Utils.I(getActivity(), AllCreditRequestActivity.class,null);
        }
    }
}
