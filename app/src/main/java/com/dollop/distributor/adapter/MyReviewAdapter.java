package com.dollop.distributor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.Utility;
import com.dollop.distributor.model.MyReviewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyReviewModel> myReviewModels;

    public MyReviewAdapter(Context context, ArrayList<MyReviewModel> myReviewModels) {
        this.context = context;
        this.myReviewModels = myReviewModels;
    }

    @NonNull
    @Override
    public MyReviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.my_reviewlist, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyReviewAdapter.MyViewHolder holder, int position) {
        MyReviewModel myReviewModel = myReviewModels.get(position);
        if (myReviewModel.retailerImage != null) {
            Picasso.get().load(Const.URL.HOST_URL + myReviewModel.retailerImage).into(holder.user_Image);
        }
        holder.User_name.setText(myReviewModel.retailerName);
        holder.rating.setText(myReviewModel.rating + " Rating");
        holder.review_time.setText(Utility.strToDate(myReviewModel.createDate));
        holder.my_rating_ratebar.setRating(Float.parseFloat(myReviewModel.rating));
        if (!myReviewModel.reviewTitle.equals("")) {
            holder.review.setVisibility(View.VISIBLE);
            holder.review.setText(myReviewModel.reviewTitle);
        }

    }

    @Override
    public int getItemCount() {
        return myReviewModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView user_Image;
        TextView User_name, review, rating, review_time;
        RatingBar my_rating_ratebar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            user_Image = itemView.findViewById(R.id.user_profile_pic_riv);
            User_name = itemView.findViewById(R.id.user_name_tv);

            rating = itemView.findViewById(R.id.rating_by_user);
            review = itemView.findViewById(R.id.review_by_user_tv);
            review_time = itemView.findViewById(R.id.review_time_tv);
            my_rating_ratebar = itemView.findViewById(R.id.my_rating_ratebar);


        }
    }
}

