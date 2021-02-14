package com.dollop.distributor.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.NetworkUtil;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.CompleteOrderAdapter;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.AllOrderDTO;
import com.dollop.distributor.model.NewOderlist;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;


public class CompleteOrderFragment extends Fragment {

    public CompleteOrderFragment() {
        // Required empty public constructor
    }

    RecyclerView rv_completeorder;
    ArrayList<AllOrderDTO> completOderList;
    CompleteOrderAdapter completOrderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_completorder, container, false);
        rv_completeorder = root.findViewById(R.id.rv_completeorder);

        //  completOderList = body.getData();
        rv_completeorder.setLayoutManager(new LinearLayoutManager(getActivity()));
        completOrderAdapter = new CompleteOrderAdapter(getActivity(), completOderList);
        rv_completeorder.setAdapter(completOrderAdapter);
        completOrderAdapter.notifyDataSetChanged();

        boolean status = NetworkUtil.getConnectivityStatus(getActivity());
        if (status == true) {

        } else {
            Utils.T(getActivity(), "No Internet Connection available. Please try again");
        }
        // allOrderMethod();

        return root;
    }

    private void allOrderMethod() {

        final Dialog dialog = Utils.initProgressDialog(getActivity());
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
//status:completed
        HashMap<String, String> hm = new HashMap<>();
        hm.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
        hm.put("status", "completed");
        Call<NewOderlist> call = apiService.newOderlist(hm);
        call.enqueue(new Callback<NewOderlist>() {
            @Override
            public void onResponse(Call<NewOderlist> call, retrofit2.Response<NewOderlist> response) {
                dialog.dismiss();
                try {

                    NewOderlist body = response.body();

                    if (body.getStatus() == 200) {
                        completOderList = body.getData();
                        rv_completeorder.setLayoutManager(new LinearLayoutManager(getActivity()));
                        completOrderAdapter = new CompleteOrderAdapter(getActivity(), completOderList);
                        rv_completeorder.setAdapter(completOrderAdapter);
                        completOrderAdapter.notifyDataSetChanged();

                    } else {
                        Utils.T(getActivity(), body.getMessage());


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
}
