package com.dollop.distributor.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.Activity.ProductDetailsActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeProductAdapter extends RecyclerView.Adapter<HomeProductAdapter.MyViewHolder> {
    Context context;
    List<HomeViewModel> homeViewModels = new ArrayList<>();

    public HomeProductAdapter(Context context, List<HomeViewModel> homeViewModels) {
        this.context = context;
        this.homeViewModels = homeViewModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_home_product,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.e("size","si"+homeViewModels.size());
        HomeViewModel homeViewModel = homeViewModels.get(position);
        holder.tv_ProductHeading.setText(homeViewModel.getProductHeading());
        holder.tv_ProductDescription.setText(homeViewModel.getProductDescription());
        //holder.tv_ProductHomeVariety.setText(homeViewModel.getProductVarity());
        holder.iv_ProductImage.setImageResource(homeViewModel.getHomeProductImage());
        holder.lv_ProductDetailsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeViewModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ProductHeading,tv_ProductDescription,tv_ProductHomeVariety;
        ImageView iv_ProductImage;
        LinearLayout lv_ProductDetailsGo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ProductHeading = itemView.findViewById(R.id.tv_ProductHeading);
            tv_ProductDescription = itemView.findViewById(R.id.tv_ProductDescription);
            iv_ProductImage = itemView.findViewById(R.id.iv_ProductImage);
            //tv_ProductHomeVariety = itemView.findViewById(R.id.tv_ProductHomeVariety);
            lv_ProductDetailsGo = itemView.findViewById(R.id.lv_ProductDetailsGo);
        }
    }
}
