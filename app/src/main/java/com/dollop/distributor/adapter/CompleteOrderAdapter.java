package com.dollop.distributor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        holder.or_datee.setText(current.getCreate_date());


        String status = current.getOrder_status().toString();

        if(status.equals("Scheduled")){
            //  holder.ll_card_back.setBackgroundColor(R.color.colorPrimary);
            holder.ll_complete_back.setBackgroundResource(R.drawable.schedule_back);
        }
        else if(status.equals("Delivery")){
            // holder.ll_card_back.setBackgroundColor(R.color.colorRed);
            holder.ll_complete_back.setBackgroundResource(R.drawable.deliverry_back);
            // holder.ll_card_back.setBackgroundColor(Color.parseColor("#ec1515"));
        }
        else if(status.equals("Pickup")){
            holder.ll_complete_back.setBackgroundResource(R.drawable.pickup_back);
            //  holder.ll_card_back.setBackgroundColor(R.color.orange);
            // holder.ll_card_back.setBackgroundColor(Color.parseColor("#F5C639"));
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
       private   TextView gen_order_id,o_item,o_amount,tv_agencyname,or_datee;
        private LinearLayout ll_complete_back;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            or_datee = itemView.findViewById(R.id.or_datee);
            gen_order_id = itemView.findViewById(R.id.gen_order_id);
            o_item = itemView.findViewById(R.id.or_items);
            o_amount = itemView.findViewById(R.id.order_amount);
            ll_complete_back = itemView.findViewById(R.id.ll_complete_back);
            tv_agencyname = itemView.findViewById(R.id.tv_agencyname);
        }
    }
}
