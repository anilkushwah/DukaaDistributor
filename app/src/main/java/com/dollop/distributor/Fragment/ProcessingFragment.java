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
import com.dollop.distributor.adapter.NewOrderAdapter;
import com.dollop.distributor.adapter.ProcessingorderAdapter;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.AllOrderDTO;
import com.dollop.distributor.model.NewOderlist;
import com.dollop.distributor.model.Processinglist;
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


public class ProcessingFragment extends Fragment {

    private ArrayList<AllOrderDTO> mAllOrderDTOArrayList = new ArrayList<>();

    public ProcessingFragment() {
        // Required empty public constructor
    }
    RecyclerView rv_processorder;
    ProcessingorderAdapter processingAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_processorder, container, false);
        rv_processorder = root.findViewById(R.id.rv_processorder);

        rv_processorder.setLayoutManager(new LinearLayoutManager(getActivity()));
        processingAdapter = new ProcessingorderAdapter(getActivity(), mAllOrderDTOArrayList);
        rv_processorder.setAdapter(processingAdapter);
        processingAdapter.notifyDataSetChanged();

       // allOrderMethod();
        return root;
    }



    private void allOrderMethod() {

        final Dialog dialog = Utils.initProgressDialog(getActivity());
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hm = new HashMap<>();
        hm.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
        hm.put("status", "processing");
        Call<NewOderlist> call = apiService.newOderlist(hm);
        call.enqueue(new Callback<NewOderlist>() {
            @Override
            public void onResponse(Call<NewOderlist> call, retrofit2.Response<NewOderlist> response) {
                dialog.dismiss();
                try {

                    NewOderlist body = response.body();

                    if (body.getStatus() == 200) {
                        mAllOrderDTOArrayList = body.getData();
                        rv_processorder.setLayoutManager(new LinearLayoutManager(getActivity()));
                        processingAdapter = new ProcessingorderAdapter(getActivity(), mAllOrderDTOArrayList);
                        rv_processorder.setAdapter(processingAdapter);
                        processingAdapter.notifyDataSetChanged();

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
