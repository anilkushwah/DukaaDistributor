package com.dollop.distributor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.R;
import com.dollop.distributor.model.RatingModel;

import java.util.ArrayList;
import java.util.List;

public class AllRatingAdapter extends RecyclerView.Adapter<AllRatingAdapter.MyViewHolder> {
    Context context;
    List<RatingModel> allRatingslists;
    // List<ReviewModelList> allRatingslists ;

    public AllRatingAdapter(Context context, ArrayList<RatingModel> allRatingslists) {
        this.context = context;
        this.allRatingslists = allRatingslists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_rating_item_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RatingModel allRatingslist = allRatingslists.get(position);
        holder.name.setText(allRatingslist.retailer_name);
        holder.time.setText(allRatingslist.create_date);
        holder.rating_num.setRating(Float.parseFloat(allRatingslist.rating));
        holder.rating_text.setText(allRatingslist.review_description);


    }

    @Override
    public int getItemCount() {
        return allRatingslists.size();
        // return 3;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView rating_text, name, time;
        ImageView iv_ProductImage;
        RatingBar rating_num;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rating_text = itemView.findViewById(R.id.rating_text);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            rating_num = itemView.findViewById(R.id.rating_num);

        }
    }
}
