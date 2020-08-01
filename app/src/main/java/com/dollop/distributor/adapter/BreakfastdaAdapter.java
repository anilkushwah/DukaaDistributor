package com.dollop.distributor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.R;
import com.dollop.distributor.model.breakfastdaModel;

import java.util.ArrayList;
import java.util.List;

public class BreakfastdaAdapter extends RecyclerView.Adapter<BreakfastdaAdapter.MyViewHolder> {
    Context context;
    List<breakfastdaModel> breakfastdaModels = new ArrayList<>();

    int allitemdata;
    String stock;


    public BreakfastdaAdapter(Context context, List<breakfastdaModel> breakfastdaModels) {
        this.context = context;
        this.breakfastdaModels = breakfastdaModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.breakfast_itemlist,null);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        breakfastdaModel  current  = breakfastdaModels.get(position);

        holder.bf_name.setText(current.getName());
        holder.bf_amount.setText(current.getAmount());


          stock = current.getStock().toString();

        if(stock.equals("Out of Stock")){
            holder.bf_stock.setText(current.getStock());
            holder.bf_stock.setTextColor(Color.parseColor("#ec1515"));
          //  holder.bf_stock.setTextColor(R.color.colorRed);
        }
        else{
            holder.bf_stock.setText(current.getStock());
            holder.bf_stock.setTextColor(Color.parseColor("#14a809"));
        }

      //  if (allitemdata == position){
       if (position == (getItemCount()-1)){

            holder.free_space_Add_items.setVisibility(View.VISIBLE);
        } else {
            holder.free_space_Add_items.setVisibility(View.GONE);
        }

        holder.bf_menu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {


                PopupMenu popup = new PopupMenu(context, v);
                popup.getMenuInflater().inflate(R.menu.bf_menu_popup, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem menu_item) {
                        switch (menu_item.getItemId()) {

                        }
                        return true;
                    }
                });

                MenuPopupHelper menuHelper = new MenuPopupHelper(context, (MenuBuilder) popup.getMenu(), v);
                menuHelper.setForceShowIcon(true);
                menuHelper.setGravity(Gravity.END);
                menuHelper.show();
                //popup.show();

            }


        });



    }

    @Override
    public int getItemCount() {

      //  allitemdata=(breakfastdaModels.size()-1);
        return breakfastdaModels.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
       private   TextView bf_name,bf_stock,bf_amount;
       ImageView bf_menu;
       LinearLayout free_space_Add_items;
       Switch breakfastswitch;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            free_space_Add_items = itemView.findViewById(R.id.free_space_Add_items);
            bf_name = itemView.findViewById(R.id.bf_name);
            bf_stock = itemView.findViewById(R.id.bf_stock);
            bf_amount = itemView.findViewById(R.id.bf_amount);
            bf_menu = itemView.findViewById(R.id.bf_menu);
        }
    }
}
