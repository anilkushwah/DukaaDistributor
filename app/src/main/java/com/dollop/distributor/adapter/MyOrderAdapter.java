package com.dollop.distributor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.R;
import com.dollop.distributor.model.MyOderlist;

import java.util.ArrayList;
import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyViewHolder> {
    Context context;
    List<MyOderlist>  myOderlists = new ArrayList<>();

    public MyOrderAdapter(Context context, List<MyOderlist> myOderlists) {
        this.context = context;
        this.myOderlists = myOderlists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.myorder_itemlist,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyOderlist  current  = myOderlists.get(position);

        holder.order_amount.setText(current.getOder_amount());
        holder.tv_market.setText(current.getMarket_name());
        holder.tv_order_items.setText(current.getOrder_item());
        holder.order_time.setText(current.getOrder_time());
        holder.tv_order_titile.setText(current.getOrder_title());

    }

    @Override
    public int getItemCount() {
        return myOderlists.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
       private   TextView tv_market,tv_order_items,order_time,order_amount,tv_order_titile;
        private  RadioButton saved_radio;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            order_amount = itemView.findViewById(R.id.order_amount);
            tv_market = itemView.findViewById(R.id.tv_market);
            tv_order_items = itemView.findViewById(R.id.tv_order_items);
            order_time = itemView.findViewById(R.id.order_time);
            tv_order_titile = itemView.findViewById(R.id.tv_order_titile);


        }
    }
}
