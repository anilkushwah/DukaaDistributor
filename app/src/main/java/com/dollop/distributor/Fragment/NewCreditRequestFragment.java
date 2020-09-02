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
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.NewCreditReqAdapter;
import com.dollop.distributor.adapter.NewOrderAdapter;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.AllOrderDTO;
import com.dollop.distributor.model.NewCreditReq_Model;
import com.dollop.distributor.model.NewOderlist;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;
import com.dollop.distributor.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;


public class NewCreditRequestFragment extends Fragment {

    RecyclerView rv_new_request;
    ArrayList<NewCreditReq_Model> newCreditReq_models;
    NewCreditReqAdapter newCreditReqAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_newcredit, container, false);
        rv_new_request = root.findViewById(R.id.rv_new_request);

        newCreditReq_models = new ArrayList<>();


      //  mAllOrderDTOArrayList = body.getData();
        //HomeFragment.NewOr//derCount = newCreditReq_models.size();
        rv_new_request.setLayoutManager(new LinearLayoutManager(getActivity()));
        newCreditReqAdapter = new NewCreditReqAdapter(getActivity(),newCreditReq_models);
        rv_new_request.setAdapter(newCreditReqAdapter);
        newCreditReqAdapter.notifyDataSetChanged();

       /// allOrderMethod();
        return root;
    }


}
