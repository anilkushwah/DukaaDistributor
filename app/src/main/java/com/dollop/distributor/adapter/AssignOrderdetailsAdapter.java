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
import com.dollop.distributor.model.ShoworderModel;

import java.util.ArrayList;
import java.util.List;

public class AssignOrderdetailsAdapter extends RecyclerView.Adapter<AssignOrderdetailsAdapter.MyHolder>  {


    Context context;
    List<ShoworderModel> showorderModels = new ArrayList<>();

    public AssignOrderdetailsAdapter(Context context, List<ShoworderModel> showorderModels) {
        this.context = context;
        this.showorderModels = showorderModels;
    }

    @NonNull
    @Override
    public AssignOrderdetailsAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_details_assign_driver_item_layout,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignOrderdetailsAdapter.MyHolder holder, int position) {

        ShoworderModel showorderModel = showorderModels.get(position);

     /*   holder.TextView_Item_NameId.setText(showorderModel.getName());
        holder.TextView_Item_QuantityId.setText(showorderModel.getItemquantity());
        holder.TextView_Item_PriceId.setText(showorderModel.getAmount());
        holder.order_price.setText(showorderModel.getOrderprice());
        holder.order_qty.setText(showorderModel.getOrderqty());*/

    }

    @Override
    public int getItemCount() {
        return showorderModels.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        TextView TextView_Item_NameId, TextView_Item_QuantityId, TextView_Item_PriceId,order_qty,order_price;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            order_price = itemView.findViewById(R.id.order_price);
            order_qty = itemView.findViewById(R.id.order_qty);
            TextView_Item_NameId = itemView.findViewById(R.id.TextView_Item_NameId);
            TextView_Item_QuantityId = itemView.findViewById(R.id.TextView_Item_QuantityId);
            TextView_Item_PriceId = itemView.findViewById(R.id.TextView_Item_amount);
        }
    }
}
