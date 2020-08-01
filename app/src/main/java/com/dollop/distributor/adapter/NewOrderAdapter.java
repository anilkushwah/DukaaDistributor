package com.dollop.distributor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.R;
import com.dollop.distributor.model.NewOderlist;

import java.util.ArrayList;
import java.util.List;

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.MyViewHolder> {
    Context context;
    List<NewOderlist>  newOderlists ;

    public NewOrderAdapter(Context context, List<NewOderlist> newOderlists) {
        this.context = context;
        this.newOderlists = newOderlists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.neworder_itemlist,null);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NewOderlist  current  = newOderlists.get(position);

        holder.o_amount.setText(current.getTotal_amount());
        holder.gen_order_id.setText(current.getGen_order_id());
        holder.o_item.setText(current.getItemCount());

        String Status = current.getOrder_status().toString();

        if(Status.equals("Completed")){
            holder.tv_order_staus.setText(current.getOrder_status());
            holder.tv_order_staus.setTextColor(Color.parseColor("#14a809"));
            holder.img_semicircle.setImageResource(R.drawable.right_semicircle);
        }
        else{
            holder.tv_order_staus.setText(current.getOrder_status());
            holder.tv_order_staus.setTextColor(Color.parseColor("#ec1515"));
            holder.img_semicircle.setImageResource(R.drawable.red_right_semicircle);
        }

        holder.tv_agencyname.setText(current.getAgencyname().toString());



    }

    @Override
    public int getItemCount() {
        return newOderlists.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
       private   TextView gen_order_id,o_item,o_amount,tv_agencyname,tv_order_staus;
        private ImageView img_semicircle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            gen_order_id = itemView.findViewById(R.id.gen_order_id);
            o_item = itemView.findViewById(R.id.or_items);
            o_amount = itemView.findViewById(R.id.order_amount);
            img_semicircle = itemView.findViewById(R.id.img_semicircle);
            tv_agencyname = itemView.findViewById(R.id.tv_agencyname);
            tv_order_staus = itemView.findViewById(R.id.tv_order_staus);
        }
    }
}
