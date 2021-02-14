package com.dollop.distributor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utility;
import com.dollop.distributor.model.TotalEarningmodel;

import java.util.List;

public class PastOrderListAdapter extends RecyclerView.Adapter<PastOrderListAdapter.MyViewHolder> {
    Context context;
    List<TotalEarningmodel> pastOrder_models;

    public PastOrderListAdapter(Context context, List<TotalEarningmodel> pastOrder_models) {
        this.context = context;
        this.pastOrder_models = pastOrder_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pastorder_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.TextView_Item_NameId.setText(Utility.strToDate(pastOrder_models.get(position).createDate));
        holder.order_qty.setText(pastOrder_models.get(position).itemCount);
        holder.TextView_Item_amount.setText(pastOrder_models.get(position).totalAmount);

    }

    @Override
    public int getItemCount() {
        return pastOrder_models.size();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView TextView_Item_NameId, order_qty, TextView_Item_amount;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TextView_Item_NameId = itemView.findViewById(R.id.TextView_Item_NameId);
            order_qty = itemView.findViewById(R.id.order_qty);
            TextView_Item_amount = itemView.findViewById(R.id.TextView_Item_amount);

        }
    }
}
