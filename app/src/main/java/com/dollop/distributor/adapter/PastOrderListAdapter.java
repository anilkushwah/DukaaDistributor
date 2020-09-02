package com.dollop.distributor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.R;
import com.dollop.distributor.model.ManageMemberModel;
import com.dollop.distributor.model.PastOrder_Model;

import java.util.List;

public class PastOrderListAdapter extends RecyclerView.Adapter<PastOrderListAdapter.MyViewHolder> {
    Context context;
    List<PastOrder_Model>   pastOrder_models;

    public PastOrderListAdapter(Context context, List<PastOrder_Model> pastOrder_models) {
        this.context = context;
        this.pastOrder_models = pastOrder_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pastorder_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return 3;
       // return pastOrder_models.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
       private   TextView name,role;
        private ImageView image;
        LinearLayout ll_member_menu,ll_full_access,ll_partial_access;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            role = itemView.findViewById(R.id.role);
            ll_partial_access = itemView.findViewById(R.id.ll_partial_access);
            ll_full_access = itemView.findViewById(R.id.ll_full_access);
            ll_member_menu = itemView.findViewById(R.id.ll_member_menu);
        }
    }
}
