package com.dollop.distributor.ui.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.Activity.AllCreditRequestActivity;
import com.dollop.distributor.Activity.HomeActivity;
import com.dollop.distributor.Activity.OrderDetailsActivity;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.RecyclerItemClickListener;
import com.dollop.distributor.UtilityTools.SavedData;

import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.NewOrderAdapter;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.firebase.Config;
import com.dollop.distributor.firebase.MyFirebaseMessagingService;
import com.dollop.distributor.model.CreditRequestResponse;
import com.dollop.distributor.model.Datum;
import com.dollop.distributor.model.NewCreditReq_Model;
import com.dollop.distributor.model.OrderModel;
import com.dollop.distributor.model.TokanResponse;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment implements View.OnClickListener {

  private static final int REQUESTCODE = 301;
  TabLayout tabLayout;
  LinearLayout ll_tab_processing, ll_tab_completed, ll_tab_new, main_tab, fragment_container, ll_tab_readyfor;
  TextView tvDispatchId, tv_new, tv_completed, tv_readyfor;
  public static TextView new_count, processing_count;
  ArrayList<NewCreditReq_Model> newCreditReq_models;
  public static int NewOrderCount = 0, ProcessingOrderCount = 0;
  RelativeLayout rl_creditreq;
  private RecyclerView rvOrderId;
  private TextView tvReadyForPickupCountId;
  private TextView tvDispatchCountId, new_credit_request_tv;
  private TextView tvCompleteCountId;
  private ArrayList<Datum> datumArrayList = new ArrayList<>();
  LinearLayout request_credit_LL, new_count_LL, ready_For_Pickup_LL, dispatch_LL, CompleteCount_LL;
  View root;
  Context mContext;
  Activity mActivity;
  Fragment currentFragment;

  boolean visiblePage = false;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {

    root = inflater.inflate(R.layout.fragment_home, container, false);
    mActivity = getActivity();

    initializationView(root);
    updateMethod();

    newCreditReq_models = new ArrayList<>();
    NewOrder("1");
    get_credit_request("Pending");

    MyFirebaseMessagingService.GenerateToken(getActivity());

    rvOrderId.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rvOrderId,
      new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
          Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
          Bundle bundle = new Bundle();
          bundle.putSerializable("orderData", datumArrayList.get(position));
          bundle.putSerializable("type", "home");
          intent.putExtras(bundle);
          startActivityForResult(intent, REQUESTCODE);

        }

        @Override
        public void onLongItemClick(View view, int position) {

        }
      }));


    return root;
  }

  private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {

      //  Utils.E("broadcast:Home Fragment");

      String message = intent.getStringExtra("action");
      newCreditReq_models = new ArrayList<>();

      if (message.equals("order_accepted")) {

        if (visiblePage) {
          tab_readyforMethod();
          Unceletct_newMethod();
          Unselect_DispatchMethod();
          Unselect_completed();
          Utils.E("instanceof: instanceof");
        }


      } else if (message.equals("order_placed")) {
        if (visiblePage) {

          tab_newMethod();
          Unselect_readyforMethod();
          Unselect_DispatchMethod();
          Unselect_completed();

        }
      } else if (message.equals("order_packed")) {
        if (visiblePage) {

          tab_readyforMethod();

          Unceletct_newMethod();
          Unselect_DispatchMethod();
          Unselect_completed();
        }
      } else if (message.equals("order_dispatch")) {
        if (visiblePage) {
          DispatchMethod();
          Unceletct_newMethod();
          Unselect_readyforMethod();
          Unselect_completed();
        }
      } else if (message.equals("order_deliverd")) {
        if (visiblePage) {

          completed();
          Unceletct_newMethod();
          Unselect_DispatchMethod();
          Unselect_readyforMethod();

        }

      }

      Utils.E("message:Home Fragment" + message);

    }
  };

  @Override
  public void onResume() {
    super.onResume();
    Utils.E("onResume:onResume onResume");
    visiblePage = true;
  }

  @Override
  public void onPause() {
    super.onPause();
    visiblePage = false;
    Utils.E("onPause:onPause onPause");
  }

  private void initializationView(View root) {

    currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);

    LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
      new IntentFilter(Config.PUSH_NOTIFICATION));
    mContext = getActivity();
    fragment_container = root.findViewById(R.id.fragment_container);
    rl_creditreq = root.findViewById(R.id.rl_creditreq);
    rvOrderId = root.findViewById(R.id.rvOrderId);
    tv_readyfor = root.findViewById(R.id.tv_readyfor);
    main_tab = root.findViewById(R.id.main_tab);
    ll_tab_processing = root.findViewById(R.id.ll_tab_processing);
    ll_tab_new = root.findViewById(R.id.ll_tab_new);
    ll_tab_completed = root.findViewById(R.id.ll_tab_completed);
    ll_tab_readyfor = root.findViewById(R.id.ll_tab_readyfor);
    new_credit_request_tv = root.findViewById(R.id.new_credit_request_tv);
    request_credit_LL = root.findViewById(R.id.request_credit_LL);
    tv_new = root.findViewById(R.id.tv_new);
    new_count = root.findViewById(R.id.new_count);
    tvReadyForPickupCountId = root.findViewById(R.id.tvReadyForPickupCountId);
    tvDispatchCountId = root.findViewById(R.id.tvDispatchCountId);
    tvCompleteCountId = root.findViewById(R.id.tvCompleteCountId);
    tv_completed = root.findViewById(R.id.tv_completed);
    tvDispatchId = root.findViewById(R.id.tvDispatchId);
    new_count_LL = root.findViewById(R.id.new_count_LL);
    ready_For_Pickup_LL = root.findViewById(R.id.ready_For_Pickup_LL);
    dispatch_LL = root.findViewById(R.id.dispatch_LL);
    CompleteCount_LL = root.findViewById(R.id.CompleteCount_LL);

    tab_newMethod();

    if (SavedData.get_AccessType().equals("Partial Access")) {
      request_credit_LL.setVisibility(View.GONE);
    }

    rl_creditreq.setOnClickListener(this);
    main_tab.setOnClickListener(this);
    ll_tab_readyfor.setOnClickListener(this);
    ll_tab_completed.setOnClickListener(this);
    ll_tab_processing.setOnClickListener(this);
    ll_tab_new.setOnClickListener(this);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    if (requestCode == REQUESTCODE && resultCode == RESULT_OK) {

      tab_readyforMethod();

      Unceletct_newMethod();
      Unselect_DispatchMethod();
      Unselect_completed();

    } else if (requestCode == REQUESTCODE && resultCode == 103) {
      DispatchMethod();
      Unceletct_newMethod();
      Unselect_readyforMethod();
      Unselect_completed();

    }
  }


  private void NewOrder(final String status) {
    Utils.E("Result::::::NewOrder");
    final Dialog dialog = Utils.initProgressDialog(getActivity());

    ApiInterface apiService =
      ApiClient.getClient().create(ApiInterface.class);
    HashMap<String, String> stringStringHashMap = new HashMap<>();

    if (SavedData.get_AccessType().equals("Distributor")) {
      stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
    } else {
      stringStringHashMap.put("distributor_id", SavedData.get_MainMemberId());
    }

    stringStringHashMap.put("status", status);
    Utils.E("checkNewOrder::" + stringStringHashMap);

    Call<OrderModel> call = apiService.getAllOrder(stringStringHashMap);
    call.enqueue(new Callback<OrderModel>() {
      @Override
      public void onResponse(Call<OrderModel> call, retrofit2.Response<OrderModel> response) {
        dialog.dismiss();
        try {
          OrderModel body = response.body();
          datumArrayList.clear();
          if (body.status == 200) {

            datumArrayList.addAll(body.data);
            //Accept=1,packed=2,dispatch=3,picked=4, Delivered=5,6=canceled
          }
          new_count.setText("" + body.acceptCount);
          tvReadyForPickupCountId.setText("" + body.packedCount);
          tvDispatchCountId.setText("" + body.dispatchCount);
          tvCompleteCountId.setText("" + body.deliveredCount);

          NewOrderAdapter orderDetailsAdapter = new NewOrderAdapter(getActivity(), datumArrayList);
          rvOrderId.setLayoutManager(new LinearLayoutManager(getActivity()));
          rvOrderId.setAdapter(orderDetailsAdapter);


        } catch (Exception e) {
          e.printStackTrace();
        }

      }

      @Override
      public void onFailure(Call<OrderModel> call, Throwable t) {
        call.cancel();
        t.printStackTrace();
        dialog.dismiss();

      }
    });

  }

  public void tab_newMethod() {

    ll_tab_new.setBackgroundResource(R.drawable.tab_background_selected);
    tv_new.setTextColor(getResources().getColor(R.color.colorBlue));
    new_count_LL.setBackgroundResource(R.drawable.ic_tab_blue_circle);
    new_count.setTextColor(getResources().getColor(R.color.white));
    NewOrder("1");
  }

  public void Unceletct_newMethod() {
    ll_tab_new.setBackgroundResource(R.drawable.ordertabselected_back);
    tv_new.setTextColor(getResources().getColor(R.color.white));
    new_count_LL.setBackgroundResource(R.drawable.ic_tab_circle);
    new_count.setTextColor(getResources().getColor(R.color.colorBlue));


  }

  public void tab_readyforMethod() {

    ll_tab_readyfor.setBackgroundResource(R.drawable.tab_background_selected);
    tv_readyfor.setTextColor(getResources().getColor(R.color.colorBlue));
    ready_For_Pickup_LL.setBackgroundResource(R.drawable.ic_tab_blue_circle);
    tvReadyForPickupCountId.setTextColor(getResources().getColor(R.color.white));
    NewOrder("2");
  }

  public void Unselect_readyforMethod() {

    ll_tab_readyfor.setBackgroundResource(R.drawable.ordertabselected_back);
    tv_readyfor.setTextColor(getResources().getColor(R.color.white));
    ready_For_Pickup_LL.setBackgroundResource(R.drawable.ic_tab_circle);
    tvReadyForPickupCountId.setTextColor(getResources().getColor(R.color.colorBlue));


  }

  public void DispatchMethod() {
    ll_tab_processing.setBackgroundResource(R.drawable.tab_background_selected);
    tvDispatchId.setTextColor(getResources().getColor(R.color.colorBlue));
    dispatch_LL.setBackgroundResource(R.drawable.ic_tab_blue_circle);
    tvDispatchCountId.setTextColor(getResources().getColor(R.color.white));
    NewOrder("3");

  }

  public void Unselect_DispatchMethod() {

    ll_tab_processing.setBackgroundResource(R.drawable.ordertabselected_back);
    tvDispatchId.setTextColor(getResources().getColor(R.color.white));
    dispatch_LL.setBackgroundResource(R.drawable.ic_tab_circle);
    tvDispatchCountId.setTextColor(getResources().getColor(R.color.colorBlue));

  }

  public void completed() {

    NewOrder("5");

    ll_tab_completed.setBackgroundResource(R.drawable.tab_background_selected);
    tv_completed.setTextColor(getResources().getColor(R.color.colorBlue));
    CompleteCount_LL.setBackgroundResource(R.drawable.ic_tab_blue_circle);
    tvCompleteCountId.setTextColor(getResources().getColor(R.color.white));
  }

  public void Unselect_completed() {

    ll_tab_completed.setBackgroundResource(R.drawable.ordertabselected_back);
    tv_completed.setTextColor(getResources().getColor(R.color.white));
    CompleteCount_LL.setBackgroundResource(R.drawable.ic_tab_circle);
    tvCompleteCountId.setTextColor(getResources().getColor(R.color.colorBlue));


  }

  @Override
  public void onClick(View v) {

    if (v == ll_tab_new) {

      tab_newMethod();

      Unselect_readyforMethod();
      Unselect_DispatchMethod();
      Unselect_completed();
    } else if (v == ll_tab_processing) {
      DispatchMethod();

      Unceletct_newMethod();
      Unselect_readyforMethod();
      Unselect_completed();
    } else if (v == ll_tab_readyfor) {

      tab_readyforMethod();

      Unceletct_newMethod();
      Unselect_DispatchMethod();
      Unselect_completed();
    } else if (v == ll_tab_completed) {

      completed();

      Unceletct_newMethod();
      Unselect_DispatchMethod();
      Unselect_readyforMethod();

    } else if (v == rl_creditreq) {
      Utils.I(getActivity(), AllCreditRequestActivity.class, null);
    }
  }

  private void get_credit_request(final String status) {
    final Dialog dialog = Utils.initProgressDialog(getActivity());

    ApiInterface apiService =
      ApiClient.getClient().create(ApiInterface.class);
    HashMap<String, String> stringStringHashMap = new HashMap<>();

    if (SavedData.get_AccessType().equals("Distributor")) {
      stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
    } else {
      stringStringHashMap.put("distributor_id", SavedData.get_MainMemberId());
    }
    stringStringHashMap.put("status", status);


    Call<CreditRequestResponse> call = apiService.get_credit_request(stringStringHashMap);
    call.enqueue(new Callback<CreditRequestResponse>() {
      @Override
      public void onResponse(Call<CreditRequestResponse> call, retrofit2.Response<CreditRequestResponse> response) {
        dialog.dismiss();
        try {
          CreditRequestResponse body = response.body();
          newCreditReq_models.clear();

          if (body.getStatus() == 200) {

            newCreditReq_models = body.getData();
            new_credit_request_tv.setText("" + newCreditReq_models.size());
          }


        } catch (Exception e) {
          e.printStackTrace();
        }

      }

      @Override
      public void onFailure(Call<CreditRequestResponse> call, Throwable t) {
        call.cancel();
        t.printStackTrace();
        dialog.dismiss();

      }
    });

  }


  private void updateMethod() {

    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    HashMap<String, String> hm = new HashMap<>();
    hm.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
    hm.put("token", ((HomeActivity) getActivity()).sessionManager.getTokenFCM());

    Call<TokanResponse> call = apiService.distributor_update_token(hm);
    call.enqueue(new Callback<TokanResponse>() {
      @Override
      public void onResponse(Call<TokanResponse> call, Response<TokanResponse> response) {

        try {


        } catch (
          Exception e) {
          e.printStackTrace();
        }

      }

      @Override
      public void onFailure(Call<TokanResponse> call, Throwable t) {
        call.cancel();
        t.printStackTrace();

      }
    });
  }


}
