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

import com.dollop.distributor.Activity.CutomerDetailsActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.model.NewCreditReq_Model;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewCreditReqAdapter extends RecyclerView.Adapter<NewCreditReqAdapter.MyViewHolder> {
    Context context;
    List<NewCreditReq_Model> newCreditReq_models;
    Activity activity;

    public NewCreditReqAdapter(Context context, List<NewCreditReq_Model> newCreditReq_models) {
        this.context = context;
        this.newCreditReq_models = newCreditReq_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newcreditrequest_itemlist, null);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final NewCreditReq_Model reqModel = newCreditReq_models.get(position);

        holder.credit_status.setText(reqModel.getStatus());
        holder.name.setText(reqModel.getRetailerName());
        holder.mobile.setText(reqModel.getRetailerMobile());
        holder.store_name.setText(reqModel.getRetailerShopName());
        holder.tv_reqamount.setText("$ "+reqModel.getAmount());

        if (reqModel.getRetailerImage()!=null) {
            Picasso.get().load(Const.URL.HOST_URL +reqModel.getRetailerImage()).error(R.drawable.ic_user_blue).into(holder.retailer_image);

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("modelData",reqModel);
                Utils.I(context, CutomerDetailsActivity.class, bundle);
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
