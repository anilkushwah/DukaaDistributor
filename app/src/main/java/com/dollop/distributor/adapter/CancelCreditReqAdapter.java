package com.dollop.distributor.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.Activity.CutomerDetailsCancelActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.model.NewCreditReq_Model;

import java.util.List;

public class CancelCreditReqAdapter extends RecyclerView.Adapter<CancelCreditReqAdapter.MyViewHolder> {
    Context context;
    List<NewCreditReq_Model> newCreditReq_models;
    Activity activity;

    public CancelCreditReqAdapter(Context context, List<NewCreditReq_Model> newCreditReq_models) {
        this.context = context;
        this.newCreditReq_models = newCreditReq_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cancelcreditrequest_itemlist, null);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
       /* AllOrderDTO mOrderDTO = mAllOrderDTOArrayList.get(position);

        holder.o_amount.setText(mOrderDTO.getTotalAmount(), TextView.BufferType.SPANNABLE);
        holder.gen_order_id.setText(mOrderDTO.getGenOrderId());
        holder.o_item.setText(mOrderDTO.getItemCount() + "  Items");
        holder.tv_agencyname.setText(mOrderDTO.getName());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = format.parse(mOrderDTO.getCreateDate());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
            String Date = dateFormat.format(date);
            holder.or_date.setText(Date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String status = mOrderDTO.getOrderStatusData();
        Utils.E("status::"+status);
        if (status.equals("pending")) {
            //  holder.ll_card_back.setBackgroundColor(R.color.colorPrimary);
            holder.ll_card_back.setBackgroundResource(R.drawable.schedule_back);
        } else if (status.equals("canceled")) {
            holder.ll_card_back.setBackgroundResource(R.drawable.deliverry_back);
        } else if (status.equals("Pickup")) {
            holder.ll_card_back.setBackgroundResource(R.drawable.pickup_back);
            //  holder.ll_card_back.setBackgroundColor(R.color.orange);
            // holder.ll_card_back.setBackgroundColor(Color.parseColor("#F5C639"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("order_status",mAllOrderDTOArrayList.get(position).getOrderStatusData());
                bundle.putSerializable("Order_id", mAllOrderDTOArrayList.get(position).getId());
                Utils.I(context, NewOrderActivity.class, bundle);
            }
        });*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Bundle bundle = new Bundle();
                bundle.putString("order_status",mAllOrderDTOArrayList.get(position).getOrderStatusData());
                bundle.putSerializable("Order_id", mAllOrderDTOArrayList.get(position).getId());
                Utils.I(context, NewOrderActivity.class, bundle);*/
                Utils.I(context, CutomerDetailsCancelActivity.class, null);
            }
        });




    }

    @Override
    public int getItemCount() {
       // return mAllOrderDTOArrayList.size();
        return 7;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView gen_order_id, o_item, o_amount, tv_agencyname, or_date;
        private LinearLayout ll_card_back;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            gen_order_id = itemView.findViewById(R.id.gen_order_id);
            o_item = itemView.findViewById(R.id.or_items);
            o_amount = itemView.findViewById(R.id.order_amount);
            tv_agencyname = itemView.findViewById(R.id.tv_agencyname);
            or_date = itemView.findViewById(R.id.or_date);
            ll_card_back = itemView.findViewById(R.id.ll_card_back);

        }
    }
}
