package com.dollop.distributor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.R;
import com.dollop.distributor.model.Popular_search_Model;

import java.util.ArrayList;

public class Popular_serachAdapter extends RecyclerView.Adapter<Popular_serachAdapter.MyViewHolder> {
    Context context;
    ArrayList<Popular_search_Model> popular_search_models ;

    public Popular_serachAdapter(Context context, ArrayList<Popular_search_Model> popular_search_models) {
        this.context = context;
        this.popular_search_models = popular_search_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.popularsearch_itemlist,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Popular_search_Model current  = popular_search_models.get(position);

        holder.tv_name.setText(current.getName());
    }

    @Override
    public int getItemCount() {
        return popular_search_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       private   TextView tv_name;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);

        }
    }
}
