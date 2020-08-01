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
import com.dollop.distributor.model.NotificationModelList;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    Context context;
    List<NotificationModelList> notificationModelLists = new ArrayList<>();

    public NotificationAdapter(Context context, List<NotificationModelList> notificationModelLists) {
        this.context = context;
        this.notificationModelLists = notificationModelLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_notification,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NotificationModelList homeViewModel = notificationModelLists.get(position);
        holder.tv_notificationTime.setText(homeViewModel.getNotifcationTime());
        holder.tv_notificaiton_Description.setText(homeViewModel.getNotificationDescription());

    }

    @Override
    public int getItemCount() {
        return notificationModelLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_notificationTime,tv_notificaiton_Description;
        ImageView iv_ProductImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_notificationTime = itemView.findViewById(R.id.tv_notificationTime);
            tv_notificaiton_Description = itemView.findViewById(R.id.tv_notificaiton_Description);

        }
    }
}
