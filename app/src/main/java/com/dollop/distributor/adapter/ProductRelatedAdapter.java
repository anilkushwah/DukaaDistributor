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
import com.dollop.distributor.model.ProductRelatedModelList;

import java.util.ArrayList;
import java.util.List;

public class ProductRelatedAdapter extends RecyclerView.Adapter<ProductRelatedAdapter.MyViewHolder> {
    Context context;
    List<ProductRelatedModelList> productRelatedModelLists = new ArrayList<>();

    public ProductRelatedAdapter(Context context, List<ProductRelatedModelList> productRelatedModelLists) {
        this.context = context;
        this.productRelatedModelLists = productRelatedModelLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_product_related_list,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductRelatedModelList homeViewModel = productRelatedModelLists.get(position);
        holder.tv_productRelatedProduct.setText(homeViewModel.getProductRelatedProductVariety());
        holder.tv_productRelatedProductName.setText(homeViewModel.getProductRelatedProductName());
        holder.tv_ProductRealtedActualPrice.setText(homeViewModel.getProductRelatedPrice()+" /-");
        holder.tv_ProductRealtedPriceOff.setText(homeViewModel.getProductRelatedPriceCutoff()+" /-");
        holder.iv_productRelatedImage.setImageResource(homeViewModel.getProductRealtedImage());

    }

    @Override
    public int getItemCount() {
        return productRelatedModelLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_productRelatedProduct,tv_productRelatedProductName,tv_ProductRealtedActualPrice,tv_ProductRealtedPriceOff;
        ImageView iv_productRelatedImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_productRelatedProduct = itemView.findViewById(R.id.tv_productRelatedProduct);
            tv_productRelatedProductName = itemView.findViewById(R.id.tv_productRelatedProductName);
            iv_productRelatedImage = itemView.findViewById(R.id.iv_productRelatedImage);
            tv_ProductRealtedActualPrice = itemView.findViewById(R.id.tv_ProductRealtedActualPrice);
            tv_ProductRealtedPriceOff = itemView.findViewById(R.id.tv_ProductRealtedPriceOff);

        }
    }
}
