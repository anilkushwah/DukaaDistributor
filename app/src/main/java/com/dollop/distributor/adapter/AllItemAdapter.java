package com.dollop.distributor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.R;
import com.dollop.distributor.model.Product;

import java.util.ArrayList;
import java.util.List;

public class AllItemAdapter extends RecyclerView.Adapter<AllItemAdapter.MyViewHolder> {
    Context context;
    List<Product> productList;
    // List<ReviewModelList> allRatingslists ;

    public AllItemAdapter(Context context, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_product, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvProductId.setText(product.productName);
        holder.tvUnitId.setText(product.packing);
        holder.tvTotalId.setText(product.totalAmount);
        holder.tvQuantityId.setText(product.productQty);


    }

    @Override
    public int getItemCount() {
        return productList.size();
        // return 3;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuantityId,tvTotalId, tvUnitId, tvProductId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductId = itemView.findViewById(R.id.tvProductId);
            tvUnitId = itemView.findViewById(R.id.tvUnitId);
            tvQuantityId = itemView.findViewById(R.id.tvQuantityId);
            tvTotalId = itemView.findViewById(R.id.tvTotalId);


        }
    }
}
