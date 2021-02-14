package com.dollop.distributor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.R;
import com.dollop.distributor.model.SearchHistory_Model;

import java.util.ArrayList;

public class SerachHistoryAdapter extends RecyclerView.Adapter<SerachHistoryAdapter.MyViewHolder> {
    Context context;
    ArrayList<SearchHistory_Model> searchHistory_models ;

    public SerachHistoryAdapter(Context context, ArrayList<SearchHistory_Model> searchHistory_models) {
        this.context = context;
        this.searchHistory_models = searchHistory_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searchhistory_itemlist,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SearchHistory_Model current  = searchHistory_models.get(position);

        holder.tv_searchname.setText(current.getName());
    }

    @Override
    public int getItemCount() {
        return searchHistory_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       private   TextView tv_searchname;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_searchname = itemView.findViewById(R.id.tv_searchname);

        }
    }
}
