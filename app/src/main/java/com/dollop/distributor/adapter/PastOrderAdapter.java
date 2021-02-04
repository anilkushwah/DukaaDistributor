package com.dollop.distributor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.Activity.PastorderDetailsActivity;
import com.dollop.distributor.Activity.TotalEarningDetailsActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.model.TotalEarningmodel;
import com.dollop.distributor.model.pastOrderModel;

import java.util.ArrayList;
import java.util.List;

public class PastOrderAdapter extends RecyclerView.Adapter<PastOrderAdapter.MyViewHolder> {
    Context context;
    List<pastOrderModel>   pastOrderModels  ;

    public PastOrderAdapter(Context context, List<pastOrderModel> pastOrderModels) {
        this.context = context;
        this.pastOrderModels = pastOrderModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pastlist_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
     //   pastOrderModel  current  = pastOrderModels.get(position);

//        holder.name.setText(current.getName());
//        holder.date.setText(current.getDate());
//        holder.amount.setText(current.getAmount() +" (15Kg)");
//        holder.amount_type.setText(current.getAmount_type());
//        holder.address.setText(current.getAddress());
        holder.tv_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Bundle bundle=new Bundle();
//                bundle.putString("order_status","complete");
                Utils.I(context, PastorderDetailsActivity.class,null);

            }
        });

    }

    @Override
    public int getItemCount() {
      //  return pastOrderModels.size();
        return 5;
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
