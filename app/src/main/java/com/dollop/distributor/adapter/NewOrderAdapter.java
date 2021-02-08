package com.dollop.distributor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.Activity.OrderDetailsActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utility;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.model.Datum;

import java.util.ArrayList;
import java.util.List;

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.MyViewHolder> {
    Context context;
    //List<AllOrderDTO> mAllOrderDTOArrayList;
    List<Datum> mAllOrderDTOArrayList;

    public NewOrderAdapter(Context context, ArrayList<Datum> mList) {
        this.context = context;
        this.mAllOrderDTOArrayList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.neworder_itemlist, null);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Datum mOrderDTO = mAllOrderDTOArrayList.get(position);

        holder.o_amount.setText(context.getString(R.string.currency_sign) + " " + mOrderDTO.paidAmount, TextView.BufferType.SPANNABLE);
        holder.gen_order_id.setText("#000"+mOrderDTO.id);
        holder.o_item.setText("" + mOrderDTO.totalWeight);
        holder.tv_agencyname.setText(  mOrderDTO.shopName);
        holder.or_date.setText(Utility.strToDate(mOrderDTO.createDate));
       /* holder.tv_retailer_name.setText("Retailer Name- " + mOrderDTO.retailerName);*/
        Utils.E("check TranscationRespoinse::"+mOrderDTO.tansactionResponse);

        if (mOrderDTO.deliveryType.equals("Delivery")) {
            holder.ll_card_back.setBackground(context.getResources().getDrawable(R.drawable.card_blue));

        } else if (mOrderDTO.deliveryType.equals("Self PickUp")) {
            holder.ll_card_back.setBackground(context.getResources().getDrawable(R.drawable.card_orange));
        }


        String status = mOrderDTO.orderStatus;
        Utils.E("status::" + status);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("orderData", mAllOrderDTOArrayList.get(position));
                bundle.putSerializable("type", "order");
                Utils.I(context, OrderDetailsActivity.class, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAllOrderDTOArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView gen_order_id, o_item, o_amount, tv_agencyname, or_date, tv_retailer_name;
        private LinearLayout ll_card_back;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            gen_order_id = itemView.findViewById(R.id.gen_order_id);
            o_item = itemView.findViewById(R.id.or_items);
            o_amount = itemView.findViewById(R.id.order_amount);
            tv_agencyname = itemView.findViewById(R.id.tv_agencyname);
            or_date = itemView.findViewById(R.id.or_date);
            ll_card_back = itemView.findViewById(R.id.ll_card_back);
            tv_retailer_name = itemView.findViewById(R.id.tv_retailer_name);
        }
    }
}
