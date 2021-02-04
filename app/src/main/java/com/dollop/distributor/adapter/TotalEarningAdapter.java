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

import com.dollop.distributor.Activity.OrderDetailsActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.Utility;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.model.Datum;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TotalEarningAdapter extends RecyclerView.Adapter<TotalEarningAdapter.MyViewHolder> {
    Context context;
    List<Datum>  totalEarningmodels  = new ArrayList<>();

    public TotalEarningAdapter(Context context, List<Datum> totalEarningmodels) {
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
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {
        Datum mDatum  = totalEarningmodels.get(position);

        holder.name.setText(mDatum.retailerName);
        holder.date.setText(""+ Utility.strToDate(mDatum.createDate));
        holder.amount.setText(mDatum.totalAmount);
        holder.amount_type.setText(mDatum.transactionMode);
        holder.address.setText(mDatum.retailerAddress);

        holder.tv_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("orderData", totalEarningmodels.get(position));
                bundle.putSerializable("type", "earning");
                Utils.I(context, OrderDetailsActivity.class, bundle);

            }
        });

        if (mDatum.shopImage!=null) {
            Picasso.get().load(Const.URL.HOST_URL + mDatum.shopImage).into(holder.image);

        }

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
