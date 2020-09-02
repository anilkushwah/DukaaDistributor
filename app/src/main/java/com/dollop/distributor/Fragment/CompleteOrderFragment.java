package com.dollop.distributor.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.CompleteOrderAdapter;
import com.dollop.distributor.adapter.NewOrderAdapter;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.AllOrderDTO;
import com.dollop.distributor.model.CompleteOderlist;
import com.dollop.distributor.model.NewOderlist;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;
import com.dollop.distributor.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;


public class CompleteOrderFragment extends Fragment {

    public CompleteOrderFragment() {
        // Required empty public constructor
    }

    RecyclerView rv_completeorder;
    ArrayList<AllOrderDTO> completOderList;
    CompleteOrderAdapter completOrderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_completorder, container, false);
        rv_completeorder = root.findViewById(R.id.rv_completeorder);

      //  completOderList = body.getData();
        rv_completeorder.setLayoutManager(new LinearLayoutManager(getActivity()));
        completOrderAdapter = new CompleteOrderAdapter(getActivity(), completOderList);
        rv_completeorder.setAdapter(completOrderAdapter);
        completOrderAdapter.notifyDataSetChanged();

       // allOrderMethod();

        return root;
    }

    private void allOrderMethod() {

        final Dialog dialog = Utils.initProgressDialog(getActivity());
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
//status:completed
        HashMap<String, String> hm = new HashMap<>();
        hm.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
        hm.put("status", "completed");
        Call<NewOderlist> call = apiService.newOderlist(hm);
        call.enqueue(new Callback<NewOderlist>() {
            @Override
            public void onResponse(Call<NewOderlist> call, retrofit2.Response<NewOderlist> response) {
                dialog.dismiss();
                try {

                    NewOderlist body = response.body();

                    if (body.getStatus() == 200) {
                        completOderList = body.getData();
                        rv_completeorder.setLayoutManager(new LinearLayoutManager(getActivity()));
                        completOrderAdapter = new CompleteOrderAdapter(getActivity(), completOderList);
                        rv_completeorder.setAdapter(completOrderAdapter);
                        completOrderAdapter.notifyDataSetChanged();

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
