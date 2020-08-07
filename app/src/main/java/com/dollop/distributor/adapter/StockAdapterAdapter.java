package com.dollop.distributor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.R;
import com.dollop.distributor.model.StockQuantity_model;
import com.dollop.distributor.model.TotalEarningmodel;

import java.util.ArrayList;
import java.util.List;

public class StockAdapterAdapter extends RecyclerView.Adapter<StockAdapterAdapter.MyViewHolder> {
    Context context;
    List<StockQuantity_model>   stockQuantity_models  ;

    public StockAdapterAdapter(Context context, List<StockQuantity_model> stockQuantity_models) {
        this.context = context;
        this.stockQuantity_models = stockQuantity_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stock_adapter_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        StockQuantity_model  current  = stockQuantity_models.get(position);

        holder.name.setText(current.getName());
        holder.quantity.setText(current.getQuantity()+" left");
        holder.image.setImageResource(R.drawable.backeryy);

    }

    @Override
    public int getItemCount() {
        return stockQuantity_models.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
       private   TextView name,quantity;
        private ImageView image;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.si_name);
            quantity = itemView.findViewById(R.id.si_qty);
            image = itemView.findViewById(R.id.si_image);
        }
    }
}
