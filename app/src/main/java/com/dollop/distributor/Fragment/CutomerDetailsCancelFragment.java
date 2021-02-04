package com.dollop.distributor.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.NetworkUtil;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.PastOrderListAdapter;
import com.dollop.distributor.adapter.TotalEarningAdapter;
import com.dollop.distributor.model.PastOrder_Model;
import com.dollop.distributor.model.TotalEarningmodel;

import java.util.ArrayList;


public class CutomerDetailsCancelFragment extends Fragment implements View.OnClickListener {

    LinearLayout ll_pastorder,show_past_order;
    RecyclerView rv_pastorder;
    PastOrderListAdapter pastOrderListAdapter;
    ArrayList<PastOrder_Model> pastOrder_models = new ArrayList<>();
    ImageView credit_setback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.activity_cutomer_details_cancel, container, false);

        initializationView(root);
        return root;

    }

    private void initializationView(View root) {
        credit_setback = root.findViewById(R.id.credit_setback);
        ll_pastorder = root.findViewById(R.id.ll_pastorder);
        show_past_order = root.findViewById(R.id.show_past_order);
        rv_pastorder = root.findViewById(R.id.rv_pastorder);

        ll_pastorder.setOnClickListener(this);

        boolean status = NetworkUtil.getConnectivityStatus(getActivity());
        if(status == true) {

        }else{
            Utils.T(getActivity(),"No Internet Connection available. Please try again");
        }

    }

    @Override
    public void onClick(View v) {
        if(v == ll_pastorder){
            show_past_order.setVisibility(View.VISIBLE);
        }
        else  if(v == credit_setback){
            getActivity().finish();
        }
    }
}
