package com.dollop.distributor.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.Activity.CutomerDetailsAcceptActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.model.AcceptCreditReq_Model;

import java.util.List;

public class AcceptCreditReqAdapter extends RecyclerView.Adapter<AcceptCreditReqAdapter.MyViewHolder> {
    Context context;
    List<AcceptCreditReq_Model> newCreditReq_models;
    Activity activity;

    public AcceptCreditReqAdapter(Context context, List<AcceptCreditReq_Model> newCreditReq_models) {
        this.context = context;
        this.newCreditReq_models = newCreditReq_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.acceptcreditrequest_itemlist, null);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final AcceptCreditReq_Model reqModel = newCreditReq_models.get(position);

        holder.credit_status.setText(reqModel.getStatus());
        holder.name.setText(reqModel.getName());
        holder.mobile.setText(reqModel.getMobile());
        holder.store_name.setText(reqModel.getStorename());
        holder.tv_reqamount.setText("$ "+reqModel.getTv_reqamount()+(" (19Kg)"));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("name",reqModel.getName().toString());
                bundle.putString("address",reqModel.getAddress().toString());
                bundle.putString("dis",reqModel.getDescription().toString());
                bundle.putString("storename",reqModel.getStorename().toString());
                bundle.putString("account",reqModel.getAcc_no().toString());
                bundle.putString("amount",reqModel.getTv_reqamount().toString());
                bundle.putString("request_id",reqModel.getRequest_id().toString());
                Utils.I(context, CutomerDetailsAcceptActivity.class, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newCreditReq_models.size();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView name, mobile, credit_status, store_name,tv_reqamount;
        ImageView retailer_image;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_reqamount = itemView.findViewById(R.id.tv_reqamount);
            name = itemView.findViewById(R.id.name);
            mobile = itemView.findViewById(R.id.mobile);
            credit_status = itemView.findViewById(R.id.credit_status);
            store_name = itemView.findViewById(R.id.store_name);
            retailer_image = itemView.findViewById(R.id.retailer_image);

        }
    }
}
