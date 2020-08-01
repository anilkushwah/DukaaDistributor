package com.dollop.distributor.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.R;
import com.dollop.distributor.adapter.TotalEarningAdapter;
import com.dollop.distributor.model.TotalEarningmodel;

import java.util.ArrayList;

public class EarningFragment extends Fragment {


    RecyclerView rv_total_earning;
    ArrayList<TotalEarningmodel> Earningmodel = new ArrayList<>();
    TotalEarningAdapter totalEarningAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View root=  inflater.inflate(R.layout.fragment_earning, container, false);

        rv_total_earning = root.findViewById(R.id.rv_total_earning);

        TotalEarningmodel model = new TotalEarningmodel();
        model.setName("Alex Carey");
        model.setAddress("Indore");
        model.setAmount("400.00");
        model.setAmount_type("Cash");
        model.setDate("28 Jun 10:20 AM");
        Earningmodel.add(model);

        TotalEarningmodel model1 = new TotalEarningmodel();
        model1.setName("John");
        model1.setAddress("Bhopal");
        model1.setAmount("900.00");
        model1.setAmount_type("Online");
        model1.setDate("29 Jun 11:20 PM");
        Earningmodel.add(model1);


        TotalEarningmodel model2 = new TotalEarningmodel();
        model2.setName("Rakesh Sharma");
        model2.setAddress("Ujjain");
        model2.setAmount("700.00");
        model2.setAmount_type("Cash");
        model2.setDate("15 Jun 1:20 AM");
        Earningmodel.add(model2);


        TotalEarningmodel model3 = new TotalEarningmodel();
        model3.setName("Ram ");
        model3.setAddress("Delhi");
        model3.setAmount("500.00");
        model3.setAmount_type("Wallet");
        model3.setDate("28 Jun 10:20 AM");
        Earningmodel.add(model3);


        rv_total_earning.setLayoutManager(new LinearLayoutManager(getActivity()));
        totalEarningAdapter = new TotalEarningAdapter(getActivity(),Earningmodel);
        rv_total_earning.setAdapter(totalEarningAdapter);

        return root;
    }
}
