package com.dollop.distributor.Fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.NetworkUtil;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.NewOrderAdapter;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.NewOderlist;
import com.dollop.distributor.model.NewOrderModel;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;
import com.dollop.distributor.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;


public class NewOrderFragment extends Fragment {
    RecyclerView rv_neworder;
    // ArrayList<AllOrderDTO> mAllOrderDTOArrayList;
    ArrayList<NewOrderModel> mAllOrderDTOArrayList;
    NewOrderAdapter newOrderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new, container, false);
        rv_neworder = root.findViewById(R.id.rv_new_order);

        boolean status = NetworkUtil.getConnectivityStatus(getActivity());
        if (status == true) {

        } else {
            Utils.T(getActivity(), "No Internet Connection available. Please try again");
        }

        /*mAllOrderDTOArrayList = body.getData();
        HomeFragment.NewOrderCount = mAllOrderDTOArrayList.size();
        rv_neworder.setLayoutManager(new LinearLayoutManager(getActivity()));
        newOrderAdapter = new NewOrderAdapter(getActivity(), mAllOrderDTOArrayList);
        rv_neworder.setAdapter(newOrderAdapter);
        newOrderAdapter.notifyDataSetChanged();*/

        //allOrderMethod();

        NewOrder();
        return root;
    }

/*
    private void allOrderMethod() {

        final Dialog dialog = Utils.initProgressDialog(getActivity());
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hm = new HashMap<>();
        hm.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
      //  hm.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
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
                        HomeFragment.NewOrderCount = mAllOrderDTOArrayList.size();
                        rv_neworder.setLayoutManager(new LinearLayoutManager(getActivity()));
                        newOrderAdapter = new NewOrderAdapter(getActivity(), mAllOrderDTOArrayList);
                        rv_neworder.setAdapter(newOrderAdapter);
                        newOrderAdapter.notifyDataSetChanged();

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
*/

    private void NewOrder() {
        final Dialog dialog = Utils.initProgressDialog(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.view_orders, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Utils.E("getNewOrder::" + response);
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);


                    String msg = jsonObject.getString("message");
                    JSONArray jsonArray = jsonObject.getJSONArray("AllOrder");

                    if (jsonObject.getInt("status") == 200) {
                        mAllOrderDTOArrayList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            NewOrderModel model = new NewOrderModel();

                            if (!object.isNull("id")) {
                                model.setOrderId(object.getString("id"));
                                Utils.E("id" + object.getString("id"));
                            }

                            if (!object.isNull("gen_order_id")) {
                                model.setGen_orderId(object.getString("gen_order_id"));
                                Utils.E("gen_order_id" + object.getString("gen_order_id"));
                            }
                            if (!object.isNull("total_amount")) {
                                model.setTotalAMount(object.getString("total_amount"));
                                Utils.E("total_amount" + object.getString("total_amount"));
                            }
                            if (!object.isNull("create_date")) {
                                model.setDate(object.getString("create_date"));
                                Utils.E("create_date" + object.getString("create_date"));
                            }
                            if (!object.isNull("itemCount")) {
                                model.setUnitcount(object.getString("itemCount"));
                                Utils.E("itemCount" + object.getString("itemCount"));
                            }
                            if (!object.isNull("order_status_data")) {
                                model.setOrdrStatus(object.getString("order_status_data"));
                                Utils.E("order_status_data" + object.getString("order_status_data"));
                            }
                            if (!object.isNull("retailer_name")) {
                                model.setRetailerName(object.getString("retailer_name"));
                                Utils.E("retailer_name" + object.getString("retailer_name"));
                            }
                            mAllOrderDTOArrayList.add(model);
                            // ((HomeFragment)getActivity()).yourPublicMethod();
                        }

                       /* rv_neworder.setLayoutManager(new LinearLayoutManager(getActivity()));
                        newOrderAdapter = new NewOrderAdapter(getActivity(), mAllOrderDTOArrayList);
                        rv_neworder.setAdapter(newOrderAdapter);
                        newOrderAdapter.notifyDataSetChanged();
                        HomeFragment.new_count.setText("" + mAllOrderDTOArrayList.size());*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                if (error.getClass().equals(TimeoutError.class)) {
                    String errorMessage = "Request timeout";
                    Utils.T(getActivity(), errorMessage + " No Internet Connection available. Please try again");
                }
            }
        }) {
            @Override
            public String getCacheKey() {
                return super.getCacheKey();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("status", "pending");
                stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
                //  stringStringHashMap.put("distributor_id","1");
                Utils.E("GetNewOrder:params:" + stringStringHashMap);
                return stringStringHashMap;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);


    }

}
