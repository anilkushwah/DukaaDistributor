package com.dollop.distributor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.Activity.OrderDetailsActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.model.CompleteOderlist;
import com.dollop.distributor.model.NewOderlist;

import java.util.List;

public class CompleteOrderAdapter extends RecyclerView.Adapter<CompleteOrderAdapter.MyViewHolder> {
    Context context;
    List<CompleteOderlist>  completelists ;

    public CompleteOrderAdapter(Context context, List<CompleteOderlist> completelists) {
        this.context = context;
        this.completelists = completelists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.complete_itemlist,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CompleteOderlist  current  = completelists.get(position);

        holder.o_amount.setText(current.getTotal_amount());
        holder.gen_order_id.setText(current.getGen_order_id());
        holder.o_item.setText(current.getItemCount()+" Items");

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.I(context, OrderDetailsActivity.class,null);
            }
        });

    }

    @Override
    public int getItemCount() {
        return completelists.size();
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
