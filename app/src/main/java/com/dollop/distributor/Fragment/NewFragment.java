package com.dollop.distributor.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dollop.distributor.LoginActivity;
import com.dollop.distributor.OtpSendActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.NewOrderAdapter;
import com.dollop.distributor.model.AllOrderDTO;
import com.dollop.distributor.model.NewOderlist;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;


public class NewFragment extends Fragment {

    RecyclerView rv_neworder;
    ArrayList<AllOrderDTO> mAllOrderDTOArrayList;
    NewOrderAdapter newOrderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
             View root = inflater.inflate(R.layout.fragment_new, container, false);
             rv_neworder = root.findViewById(R.id.rv_new_order);

        mAllOrderDTOArrayList = new ArrayList<>();

       allOrderMethod();
        return root;
    }

    private void allOrderMethod() {

        final Dialog dialog = Utils.initProgressDialog(getActivity());
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            HashMap<String, String> hm = new HashMap<>();
            hm.put("distributor_id", "1");

            Call<NewOderlist> call = apiService.newOderlist(hm);
            call.enqueue(new Callback<NewOderlist>() {
                @Override
                public void onResponse(Call<NewOderlist> call, retrofit2.Response<NewOderlist> response) {
                    dialog.dismiss();
                    try {

                        NewOderlist body = response.body();

                        if (body.getStatus() == 200) {
                            mAllOrderDTOArrayList = body.getData();
                            rv_neworder.setLayoutManager(new LinearLayoutManager(getActivity()));
                            newOrderAdapter = new NewOrderAdapter(getActivity(),mAllOrderDTOArrayList);
                            rv_neworder.setAdapter(newOrderAdapter);
                            newOrderAdapter.notifyDataSetChanged();

                        } else {


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<NewOderlist> call, Throwable t) {
                    call.cancel();
                    t.printStackTrace();
                    dialog.dismiss();
                }
            });

    }

}
