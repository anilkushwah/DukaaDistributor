package com.dollop.distributor.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.SavedData;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.OfferAdapter;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.OfferDTO;
import com.dollop.distributor.model.OfferResponse;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class OfferActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout add_offer_LL;
    ImageView memberback;
    RecyclerView rv_memberList;
    ArrayList<OfferResponse> mOfferResponseArrayList = new ArrayList<>();
    Activity activity = OfferActivity.this;
    OfferAdapter mOfferAdapter;
    static Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_offer);

        initializationView();

    }

    private void initializationView() {
        dialog = new Dialog(OfferActivity.this, R.style.dialogstyle);
        add_offer_LL = findViewById(R.id.add_offer_LL);
        memberback = findViewById(R.id.memberback);
        rv_memberList = findViewById(R.id.rv_memberList);
        mOfferResponseArrayList = new ArrayList<>();

        getOfferMethod();

        add_offer_LL.setOnClickListener(this);
        memberback.setOnClickListener(this);
    }

    private void getOfferMethod() {
        final Dialog dialog = Utils.initProgressDialog(OfferActivity.this);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> stringStringHashMap = new HashMap<>();

        if (SavedData.get_AccessType().equals("Distributor")) {
            stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());

        } else {
            stringStringHashMap.put("distributor_id", SavedData.get_MainMemberId());

        }


        Utils.E("checkNewOrder::" + stringStringHashMap);

        Call<OfferDTO> call = apiService.get_offer(stringStringHashMap);
        call.enqueue(new Callback<OfferDTO>() {
            @Override
            public void onResponse(Call<OfferDTO> call, retrofit2.Response<OfferDTO> response) {
                dialog.dismiss();
                try {
                    OfferDTO body = response.body();
                    mOfferResponseArrayList.clear();

                    if (body.getStatus() == 200) {


                        mOfferResponseArrayList = body.getData();

                        rv_memberList.setLayoutManager(new LinearLayoutManager(OfferActivity.this));
                        mOfferAdapter = new OfferAdapter(OfferActivity.this, mOfferResponseArrayList, "offer", dialog);
                        rv_memberList.setAdapter(mOfferAdapter);
                        mOfferAdapter.notifyDataSetChanged();

                    } else {
                       Utils.T(OfferActivity.this, body.getMessage());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<OfferDTO> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                dialog.dismiss();

            }
        });

    }


    @Override
    public void onClick(View v) {
        if (v == add_offer_LL) {
            Utils.I(OfferActivity.this, CreateOfferActivity.class, null);
        }
        if (v == memberback) {
            Utils.I_clear(OfferActivity.this, HomeActivity.class, null);

        }
    }




}