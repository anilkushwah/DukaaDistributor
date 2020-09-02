package com.dollop.distributor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
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

import com.dollop.distributor.Activity.NewOrderActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.model.ManageMemberModel;
import com.dollop.distributor.model.TotalEarningmodel;

import java.util.ArrayList;
import java.util.List;

public class ManageMemberAdapter extends RecyclerView.Adapter<ManageMemberAdapter.MyViewHolder> {
    Context context;
    List<ManageMemberModel>   manageMemberModels;

    public ManageMemberAdapter(Context context, List<ManageMemberModel> manageMemberModels) {
        this.context = context;
        this.manageMemberModels = manageMemberModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.managemeber_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ManageMemberModel  current  = manageMemberModels.get(position);

        holder.name.setText(current.getName().toString());
        holder.role.setText(current.getRole().toString());

        String access = current.getAccess().toString();

        if(access.equals("Full Access")){
            holder.ll_full_access.setVisibility(View.VISIBLE);
            holder.ll_partial_access.setVisibility(View.GONE);
        }
        else {
            holder.ll_full_access.setVisibility(View.GONE);
            holder.ll_partial_access.setVisibility(View.VISIBLE);
        }


        holder.ll_member_menu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                popup.getMenuInflater().inflate(R.menu.bf_menu_popup, popup.getMenu());
                //popup.show();

                @SuppressLint("RestrictedApi") MenuPopupHelper menuHelper = new MenuPopupHelper(context, (MenuBuilder) popup.getMenu(), v);
                menuHelper.setForceShowIcon(true);
                menuHelper.setGravity(Gravity.END);
                menuHelper.show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return manageMemberModels.size();
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
