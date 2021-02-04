package com.dollop.distributor.adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.Activity.LoginActivity;
import com.dollop.distributor.Activity.MyRatingActivity;
import com.dollop.distributor.Activity.StockQuantityActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.RatingDTO;
import com.dollop.distributor.model.StockQuantity_model;
import com.dollop.distributor.model.TotalEarningmodel;
import com.dollop.distributor.model.UpdateStock;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class StockAdapterAdapter extends RecyclerView.Adapter<StockAdapterAdapter.MyViewHolder> {
    Context context;
    List<StockQuantity_model> stockQuantity_models;
    String category_id, sub_category_id;

    public StockAdapterAdapter(Context context, List<StockQuantity_model> stockQuantity_models, String category, String sub_category) {
        this.context = context;
        this.stockQuantity_models = stockQuantity_models;
        this.category_id = category;
        this.sub_category_id = sub_category;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stock_adapter_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final StockQuantity_model current = stockQuantity_models.get(position);

        holder.category_tv.setText(current.product_name);
        //  Log.e("(current.sub_", current.sub_category_name);
        holder.sub_category_tv.setText(current.total_unit + "X" + current.unit_per_packing_weight + " " + current.unit);
      /*  if (!current.sub_category_name.equals("null")) {

            holder.sub_category_tv.setVisibility(View.VISIBLE);
        } else {
            holder.sub_category_tv.setVisibility(View.GONE);
        }*/
         holder.total_quantity_text_tv.setText("Total " + current.packing + " Quantity :");
        holder.stock_tv.setText(current.stock_quantity + " left");
        if (current.product_image != null) {
            Picasso.get().load(Const.URL.HOST_URL + current.product_image)
                    .error(R.drawable.no_product).into(holder.image);

        }


        holder.tv_editstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog1 = new Dialog(context, R.style.dialogstyle); // Context, this, etc.
                dialog1.setContentView(R.layout.update_quantity_layout);
                dialog1.setCanceledOnTouchOutside(true);
                dialog1.setCancelable(true);
                TextView tv_savestock = dialog1.findViewById(R.id.tv_savestock);
                final EditText edt_stock_quantity = dialog1.findViewById(R.id.edt_stock_quantity);
                edt_stock_quantity.setText(current.stock_quantity);
                tv_savestock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog1.dismiss();
                        updateQuantityMethod(edt_stock_quantity.getText().toString(), current.Id);

                    }
                });
                dialog1.show();

            }
        });


        holder.tv_savestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ll_editstock.setVisibility(View.VISIBLE);
                holder.ll_updatestock.setVisibility(View.GONE);
            }
        });


    }

    private void updateQuantityMethod(String stockQ, String id) {
        final Dialog dialog1 = Utils.initProgressDialog(context);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> hm = new HashMap<>();

        hm.put("product_id", id);
        hm.put("stock_quantity", stockQ);

        Call<UpdateStock> call = apiService.update_product_stock_quantity(hm);
        call.enqueue(new Callback<UpdateStock>() {
            @Override
            public void onResponse(Call<UpdateStock> call, retrofit2.Response<UpdateStock> response) {
                dialog1.dismiss();
                try {

                    UpdateStock body = response.body();

                    if (body.status == 200) {
                        ((StockQuantityActivity) context).getProduct(category_id, sub_category_id);
                        Utils.E("success::"+response);
                    }

                } catch (Exception e) {
                    Utils.E("exception::"+e.getMessage());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<UpdateStock> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                Utils.E("fail::"+t.getMessage());
                dialog1.dismiss();

            }
        });
    }

    @Override
    public int getItemCount() {
        return stockQuantity_models.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView category_tv, sub_category_tv, stock_tv, tv_editstock, tv_savestock, total_quantity_text_tv;
        private ImageView image;
        LinearLayout ll_updatestock, ll_editstock;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            category_tv = itemView.findViewById(R.id.category_tv);
            sub_category_tv = itemView.findViewById(R.id.sub_category_tv);
            stock_tv = itemView.findViewById(R.id.stock_tv);
            total_quantity_text_tv = itemView.findViewById(R.id.total_quantity_text_tv);

            image = itemView.findViewById(R.id.si_image);
            ll_updatestock = itemView.findViewById(R.id.ll_updatestock);
            ll_editstock = itemView.findViewById(R.id.ll_editstock);
            tv_savestock = itemView.findViewById(R.id.tv_savestock);
            tv_editstock = itemView.findViewById(R.id.tv_editstock);
        }
    }


}
