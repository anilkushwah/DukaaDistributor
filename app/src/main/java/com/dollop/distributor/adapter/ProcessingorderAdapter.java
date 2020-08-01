package com.dollop.distributor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.R;
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
        holder.o_item.setText(current.getItemCount());

    }

    @Override
    public int getItemCount() {
        return Processinglist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       private   TextView gen_order_id,o_item,o_amount;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            gen_order_id = itemView.findViewById(R.id.gen_order_id);
            o_item = itemView.findViewById(R.id.or_items);
            o_amount = itemView.findViewById(R.id.order_amount);
        }
    }
}
