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
import com.dollop.distributor.UtilityTools.SavedData;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.NewCreditReqAdapter;
import com.dollop.distributor.database.UserDataHelper;

import com.dollop.distributor.model.CreditRequestResponse;
import com.dollop.distributor.model.NewCreditReq_Model;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;


import retrofit2.Call;
import retrofit2.Callback;


public class AcceptCreditRequestFragment extends Fragment {

    RecyclerView rv_new_request;
    ArrayList<NewCreditReq_Model> newCreditReq_models = new ArrayList<>();
    NewCreditReqAdapter newCreditReqAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_acceptcredit, container, false);
        rv_new_request = root.findViewById(R.id.rv_new_request);

        newCreditReq_models = new ArrayList<>();

        get_credit_request("Approved");

        return root;
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

                        rv_new_request.setLayoutManager(new LinearLayoutManager(getActivity()));
                        newCreditReqAdapter = new NewCreditReqAdapter(getActivity(), newCreditReq_models);
                        rv_new_request.setAdapter(newCreditReqAdapter);
                        newCreditReqAdapter.notifyDataSetChanged();

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

}
