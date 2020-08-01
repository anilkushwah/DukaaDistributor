package com.dollop.distributor.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.R;
import com.dollop.distributor.model.WishListProductsModelList;

import java.util.ArrayList;
import java.util.List;

public class WishListProductAdapter extends RecyclerView.Adapter<WishListProductAdapter.MyViewHolder> {
    Context context;
    List<WishListProductsModelList> wishListProductsModelLists = new ArrayList<>();
    int delete=0;

    public WishListProductAdapter(Context context, List<WishListProductsModelList> wishListProductsModelLists) {
        this.context = context;
        this.wishListProductsModelLists = wishListProductsModelLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_wishlist_product,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WishListProductsModelList wishListProductsModelList = wishListProductsModelLists.get(position);
        holder.tv_WishList_CategoryProductName.setText(wishListProductsModelList.getCategoryTypeProduct());
        holder.tv_WishList_ProductName.setText(wishListProductsModelList.getWishlistProductName());
        holder.iv_WishListProductImage.setImageResource(wishListProductsModelList.getWishListProductImage());
        holder.tv_ProductActualPrice.setText(wishListProductsModelList.getWishlistProductActualPrice()+" /-");
        holder.tv_CutPrice_Show.setText(wishListProductsModelList.getWishlistproductPriceOff()+" /-");
        holder.tv_CutPrice_Show.setPaintFlags(holder.tv_CutPrice_Show.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if (delete == 1){
            holder.lv_delete_product.setVisibility(View.VISIBLE);
        }else {
            holder.lv_delete_product.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return wishListProductsModelLists.size();
    }

    public void getDeletePosition(int i) {
        delete = i;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_WishList_CategoryProductName,tv_WishList_ProductName,tv_CutPrice_Show,tv_ProductActualPrice;
        ImageView iv_WishListProductImage;
        LinearLayout lv_wishlist_price_cut,lv_delete_product;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_WishList_CategoryProductName = itemView.findViewById(R.id.tv_WishList_CategoryProductName);
            tv_ProductActualPrice = itemView.findViewById(R.id.tv_ProductActualPrice);
            tv_WishList_ProductName = itemView.findViewById(R.id.tv_WishList_ProductName);
            iv_WishListProductImage = itemView.findViewById(R.id.iv_WishListProductImage);
            lv_delete_product = itemView.findViewById(R.id.lv_delete_product);
            lv_wishlist_price_cut = itemView.findViewById(R.id.lv_wishlist_price_cut);
            tv_CutPrice_Show = itemView.findViewById(R.id.tv_CutPrice_Show);
        }
    }
}
