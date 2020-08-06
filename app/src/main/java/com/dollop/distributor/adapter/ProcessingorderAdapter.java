package com.dollop.distributor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.Activity.OrderDetailsActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.model.NewOderlist;
import com.dollop.distributor.model.Processinglist;

import java.util.List;

public class ProcessingorderAdapter extends RecyclerView.Adapter<ProcessingorderAdapter.MyViewHolder> {
    Context context;
    List<Processinglist>  Processinglist ;

    public ProcessingorderAdapter(Context context, List<com.dollop.distributor.model.Processinglist> processinglist) {
        this.context = context;
        Processinglist = processinglist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.processingorder_itemlist,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Processinglist  current  = Processinglist.get(position);

        holder.o_amount.setText(current.getTotal_amount());
        holder.gen_order_id.setText(current.getGen_order_id());
        holder.o_item.setText(current.getItemCount() +" Items");
        holder.tv_agencyname.setText(current.getAgencyname());
        holder.or_date.setText(current.getCreate_date());


        String status = current.getOrder_status().toString();

        if(status.equals("Scheduled")){
            //  holder.ll_card_back.setBackgroundColor(R.color.colorPrimary);
            holder.ll_process_back.setBackgroundResource(R.drawable.schedule_back);
        }
        else if(status.equals("Delivery")){
            // holder.ll_card_back.setBackgroundColor(R.color.colorRed);
            holder.ll_process_back.setBackgroundResource(R.drawable.deliverry_back);
            // holder.ll_card_back.setBackgroundColor(Color.parseColor("#ec1515"));
        }
        else if(status.equals("Pickup")){
            holder.ll_process_back.setBackgroundResource(R.drawable.pickup_back);
            //  holder.ll_card_back.setBackgroundColor(R.color.orange);
            // holder.ll_card_back.setBackgroundColor(Color.parseColor("#F5C639"));
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.I(context, OrderDetailsActivity.class,null);
            }
        });

    }

    @Override
    public int getItemCount() {
        return Processinglist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       private   TextView gen_order_id,o_item,o_amount,tv_agencyname,or_date;
       LinearLayout ll_process_back;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            gen_order_id = itemView.findViewById(R.id.gen_order_id);
            o_item = itemView.findViewById(R.id.or_items);
            o_amount = itemView.findViewById(R.id.order_amount);
            tv_agencyname = itemView.findViewById(R.id.tv_agencyname);
            or_date = itemView.findViewById(R.id.or_date);
            ll_process_back = itemView.findViewById(R.id.ll_process_back);
        }
    }
}
