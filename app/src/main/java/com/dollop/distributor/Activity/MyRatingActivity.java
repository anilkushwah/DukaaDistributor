package com.dollop.distributor.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.NetworkUtil;
import com.dollop.distributor.UtilityTools.SessionManager;
import com.dollop.distributor.UtilityTools.Utility;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.MyReviewAdapter;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.MyReviewModel;
import com.dollop.distributor.model.RatingDTO;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class MyRatingActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView my_rating_list_rec;
    ImageView back_btn_review, no_data_image;

    MyReviewAdapter myReviewAdapter;
    ArrayList<MyReviewModel> myReviewModels;
    TextView total_rating_tv, total_review_TV;
    LinearLayout rating_LL;
    SessionManager sessionManager;
    String id = "", str_type ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_my_rating);

        sessionManager = new SessionManager(this);
        id = getIntent().getStringExtra("id");
        str_type = getIntent().getStringExtra("type");

        initialization();



    }

    private void initialization() {

        myReviewModels = new ArrayList<>();
        back_btn_review = findViewById(R.id.back_btn_review);
        my_rating_list_rec = findViewById(R.id.my_rating_list_rec);
        no_data_image = findViewById(R.id.no_data_image);
        total_rating_tv = findViewById(R.id.total_rating_tv);
        total_review_TV = findViewById(R.id.total_review_TV);
        rating_LL = findViewById(R.id.rating_LL);
        back_btn_review.setOnClickListener(this);
            RatingMethod();
    }

    @Override
    public void onClick(View v) {
        if (v == back_btn_review) {
            onBackPressed();
        }
    }


    private void RatingMethod() {
        final Dialog dialog1 = Utils.initProgressDialog(this);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hm = new HashMap<>();

        hm.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());

        Call<RatingDTO> call = apiService.get_review_rating(hm);
        call.enqueue(new Callback<RatingDTO>() {
            @Override
            public void onResponse(Call<RatingDTO> call, retrofit2.Response<RatingDTO> response) {
                dialog1.dismiss();
                try {

                    RatingDTO body = response.body();

                    if (body.status== 200) {

                        myReviewModels = body.data;

                        if (myReviewModels == null) {
                            no_data_image.setVisibility(View.VISIBLE);
                        }

                        total_rating_tv.setText("" + body.ratingCount);
                     total_review_TV.setText(body.ratingCount + "Ratings " + body.reviewCount + "Reviews");

                        myReviewAdapter = new MyReviewAdapter(MyRatingActivity.this, myReviewModels);
                        my_rating_list_rec.setLayoutManager(new LinearLayoutManager(MyRatingActivity.this, RecyclerView.VERTICAL, false));
                        my_rating_list_rec.setAdapter(myReviewAdapter);
                        myReviewAdapter.notifyDataSetChanged();

                    } else {
                        no_data_image.setVisibility(View.VISIBLE);
                        rating_LL.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<RatingDTO> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                dialog1.dismiss();

            }
        });


    }


}