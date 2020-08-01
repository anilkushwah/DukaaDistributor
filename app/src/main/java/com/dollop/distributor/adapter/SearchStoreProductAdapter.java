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
import com.dollop.distributor.model.SearchStoreModelList;

import java.util.ArrayList;
import java.util.List;

public class SearchStoreProductAdapter extends RecyclerView.Adapter<SearchStoreProductAdapter.MyViewHolder> {
    Context context;
    List<SearchStoreModelList> searchStoreModelLists = new ArrayList<>();

    public SearchStoreProductAdapter(Context context, List<SearchStoreModelList> searchStoreModelLists) {
        this.context = context;
        this.searchStoreModelLists = searchStoreModelLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_searchstore_product,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SearchStoreModelList searchStoreModelList = searchStoreModelLists.get(position);
        holder.tv_SearchStoreName_ProductName.setText(searchStoreModelList.getSearchStoreName());
        holder.tv_VarityProductName.setText(searchStoreModelList.getSearchStoreVarietyName());
        holder.tv_SearchStoreName_description.setText(searchStoreModelList.getSearchStoreProductOff());
        if (searchStoreModelList.getSearchStoreOffPrice() !=null){
            holder.tv_VarityProductPrice.setVisibility(View.VISIBLE);
            holder.tv_VarityProductPrice.setText(searchStoreModelList.getSearchStoreOffPrice()+" /-");
        }else {
            holder.tv_VarityProductPrice.setVisibility(View.GONE);
        }

        holder.iv_SearchStoreProductImage.setImageResource(searchStoreModelList.getSearchStoreImage());

    }

    @Override
    public int getItemCount() {
        return searchStoreModelLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_SearchStoreName_ProductName,tv_VarityProductName,tv_SearchStoreName_description,tv_VarityProductPrice;
        ImageView iv_SearchStoreProductImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_SearchStoreName_ProductName = itemView.findViewById(R.id.tv_SearchStoreName_ProductName);
            tv_VarityProductPrice = itemView.findViewById(R.id.tv_VarityProductPrice);
            tv_VarityProductName = itemView.findViewById(R.id.tv_VarityProductName);
            iv_SearchStoreProductImage = itemView.findViewById(R.id.iv_SearchStoreProductImage);
            tv_SearchStoreName_description = itemView.findViewById(R.id.tv_SearchStoreName_description);
        }
    }
}
