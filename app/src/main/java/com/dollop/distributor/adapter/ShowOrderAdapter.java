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
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.model.ShoworderModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShowOrderAdapter extends RecyclerView.Adapter<ShowOrderAdapter.MyHolder> {


    Context context;
    List<ShoworderModel> showorderModels;

    public ShowOrderAdapter(Context context, List<ShoworderModel> showorderModels) {
        this.context = context;
        this.showorderModels = showorderModels;
    }

    @NonNull
    @Override
    public ShowOrderAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_order_item_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowOrderAdapter.MyHolder holder, int position) {

        ShoworderModel showorderModel = showorderModels.get(position);

        holder.TextView_Item_NameId.setText(showorderModel.product_name);
        holder.TextView_Item_QuantityId.setText(showorderModel.pack_size);
        holder.TextView_Item_PriceId.setText(showorderModel.product_discounted_price);
        Picasso.get().load(Const.URL.HOST_URL + showorderModel.product_image).error(R.drawable.no_product).into(holder.ImageView_Item_ImageId);
        holder.order_price.setText(showorderModel.product_amount);
        holder.order_qty.setText(showorderModel.product_qty);

    }

    @Override
    public int getItemCount() {
       return showorderModels.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        TextView TextView_Item_NameId, TextView_Item_QuantityId, TextView_Item_PriceId, order_qty, order_price;
        ImageView ImageView_Item_ImageId;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            order_price = itemView.findViewById(R.id.order_price);
            order_qty = itemView.findViewById(R.id.order_qty);
            TextView_Item_NameId = itemView.findViewById(R.id.TextView_Item_NameId);
            TextView_Item_QuantityId = itemView.findViewById(R.id.TextView_Item_QuantityId);
            TextView_Item_PriceId = itemView.findViewById(R.id.TextView_Item_amount);
            ImageView_Item_ImageId = itemView.findViewById(R.id.ImageView_Item_ImageId);
        }
    }
}
