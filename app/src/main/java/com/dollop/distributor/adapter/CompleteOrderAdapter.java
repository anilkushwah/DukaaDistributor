package com.dollop.distributor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.Activity.NewOrderActivity;
import com.dollop.distributor.Activity.OrderDetailsActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.model.AllOrderDTO;
import com.dollop.distributor.model.CompleteOderlist;
import com.dollop.distributor.model.NewOderlist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CompleteOrderAdapter extends RecyclerView.Adapter<CompleteOrderAdapter.MyViewHolder> {
    Context context;
    List<AllOrderDTO>  completelists ;

    public CompleteOrderAdapter(Context context, List<AllOrderDTO> completelists) {
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
       /* AllOrderDTO mOrderDTO = completelists.get(position);

        holder.o_amount.setText(mOrderDTO.getTotalAmount(), TextView.BufferType.SPANNABLE);
        holder.gen_order_id.setText(mOrderDTO.getGenOrderId());
        holder.o_item.setText(mOrderDTO.getItemCount() + "  Items");
        holder.tv_agencyname.setText(mOrderDTO.getName());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = format.parse(mOrderDTO.getCreateDate());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
            String Date = dateFormat.format(date);
            holder.or_date.setText(Date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String status = mOrderDTO.getOrderStatusData();
        Utils.E("status::"+status);
        if (status.equals("pending")) {
            //  holder.ll_card_back.setBackgroundColor(R.color.colorPrimary);
            holder.ll_complete_back.setBackgroundResource(R.drawable.schedule_back);
        } else if (status.equals("canceled")) {
            holder.ll_complete_back.setBackgroundResource(R.drawable.deliverry_back);
        } else if (status.equals("Pickup")) {
            holder.ll_complete_back.setBackgroundResource(R.drawable.pickup_back);
            //  holder.ll_card_back.setBackgroundColor(R.color.orange);
            // holder.ll_card_back.setBackgroundColor(Color.parseColor("#F5C639"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.I(context, OrderDetailsActivity.class,null);
            }
        });*/


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.I(context, OrderDetailsActivity.class,null);
            }
        });

    }

    @Override
    public int getItemCount() {
       // return completelists.size();
        return 5;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
       private   TextView gen_order_id,o_item,o_amount,tv_agencyname,or_date;
        private LinearLayout ll_complete_back;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            or_date = itemView.findViewById(R.id.or_datee);
            gen_order_id = itemView.findViewById(R.id.gen_order_id);
            o_item = itemView.findViewById(R.id.or_items);
            o_amount = itemView.findViewById(R.id.order_amount);
            ll_complete_back = itemView.findViewById(R.id.ll_complete_back);
            tv_agencyname = itemView.findViewById(R.id.tv_agencyname);
        }
    }
}
