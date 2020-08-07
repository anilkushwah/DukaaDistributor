package com.dollop.distributor.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.Activity.NewOrderActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.model.TotalEarningmodel;

import java.util.ArrayList;
import java.util.List;

public class TotalEarningAdapter extends RecyclerView.Adapter<TotalEarningAdapter.MyViewHolder> {
    Context context;
    List<TotalEarningmodel>  totalEarningmodels  = new ArrayList<>();

    public TotalEarningAdapter(Context context, List<TotalEarningmodel> totalEarningmodels) {
        this.context = context;
        this.totalEarningmodels = totalEarningmodels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.total_earning_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TotalEarningmodel  current  = totalEarningmodels.get(position);

        holder.name.setText(current.getName());
        holder.date.setText(current.getDate());
        holder.amount.setText(current.getAmount());
        holder.amount_type.setText(current.getAmount_type());
        holder.address.setText(current.getAddress());
        holder.tv_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();
                bundle.putString("order_status","complete");
                Utils.I(context, NewOrderActivity.class,bundle);

            }
        });

    }

    @Override
    public int getItemCount() {
        return totalEarningmodels.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
       private   TextView name,address,date,amount_type,amount,tv_view_details;
        private ImageView image;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            date = itemView.findViewById(R.id.date);
            amount_type = itemView.findViewById(R.id.amount_type);
            amount = itemView.findViewById(R.id.amount);
            image = itemView.findViewById(R.id.image);
            tv_view_details = itemView.findViewById(R.id.tv_view_details);
        }
    }
}
