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
import com.dollop.distributor.model.NewOderlist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class NewFragment extends Fragment {

    public NewFragment() {
        // Required empty public constructor
    }


    RecyclerView rv_neworder;
    ArrayList<NewOderlist> newOderList;

    NewOrderAdapter newOrderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
             View root = inflater.inflate(R.layout.fragment_new, container, false);
             rv_neworder = root.findViewById(R.id.rv_new_order);

        rv_neworder.setLayoutManager(new LinearLayoutManager(getActivity()));
///////////////////
        newOderList = new ArrayList<>();

        NewOderlist model = new NewOderlist();
        model.setItemCount("5 ");
        model.setGen_order_id("#1234098765");
        model.setAgencyname("Aysha Agencyes");
        model.setTotal_amount("400.00");

        newOderList.add(model);

        NewOderlist model1 = new NewOderlist();
        model1.setItemCount("2 ");
        model1.setGen_order_id("#9287458765");
        model1.setTotal_amount("700.00");
        model1.setAgencyname("Aysha Agencyes");

        newOderList.add(model1);


        NewOderlist model2 = new NewOderlist();
        model2.setItemCount("7 ");
        model2.setGen_order_id("#8254328765");
        model2.setTotal_amount("500.00");
        model2.setAgencyname("Aysha Agencyes");
        newOderList.add(model2);


        NewOderlist model3 = new NewOderlist();
        model3.setItemCount("1 ");
        model3.setGen_order_id("1234328765");
        model3.setTotal_amount("100.00");
        model3.setAgencyname("Aysha Agencyes");
        newOderList.add(model3);

        newOrderAdapter = new NewOrderAdapter(getActivity(),newOderList);
        rv_neworder.setAdapter(newOrderAdapter);
        /////////////////


       // newList();
        return root;
    }

    private void newList() {
        {
            final Dialog dialog = Utils.initProgressDialog(getActivity());
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.view_orders, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();

                    Log.e("NewOrder:", "view_orders:--" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        Toast.makeText(getActivity(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        if (jsonObject.getInt("status")==200) {

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            newOderList = new ArrayList<>();
                            for(int i= 0;i<jsonArray.length();i++){

                                NewOderlist model = new NewOderlist();

                                JSONObject object = jsonArray.getJSONObject(i);

                                if(object.isNull("gen_order_id")){
                                    model.setGen_order_id(object.getString("0").toString());
                                }
                                else{
                                    model.setGen_order_id(object.getString("gen_order_id").toString());
                                }

                                 if(!object.isNull("total_amount")){
                                    model.setTotal_amount(object.getString("total_amount").toString());
                                }
                                 if(!object.isNull("itemCount")){
                                    model.setItemCount(object.getString("itemCount").toString());
                                }


                                newOderList.add(model);

                                rv_neworder.setLayoutManager(new LinearLayoutManager(getActivity()));
                                newOrderAdapter = new NewOrderAdapter(getActivity(),newOderList);
                                rv_neworder.setAdapter(newOrderAdapter);
                            }
                        }

                    } catch (JSONException e) {
                        dialog.dismiss();

                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();

                    NetworkResponse networkResponse = error.networkResponse;

                    String errorMessage = "Unknown error";
                    if (networkResponse == null) {
                        if (error.getClass().equals(TimeoutError.class)) {
                            errorMessage = "Request timeout";
                            Utils.T(getActivity(), errorMessage + "please check Internet connection");
                        } else if (error.getClass().equals(NoConnectionError.class)) {
                            errorMessage = "Failed to connect server";
                        }
                    } else {
                        String result = new String(networkResponse.data);
                        try {
                            JSONObject response = new JSONObject(result);
                            String status = response.getString("status");
                            String message = response.getString("message");
                            Log.e("Error Status", status);
                            Log.e("Error Message", message);
                            if (networkResponse.statusCode == 404) {
                                errorMessage = "Resource not found";
                            } else if (networkResponse.statusCode == 401) {
                                errorMessage = message + " Please login again";
                            } else if (networkResponse.statusCode == 400) {
                                errorMessage = message + " Check your inputs";
                            } else if (networkResponse.statusCode == 500) {
                                errorMessage = message + " Something is getting wrong";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> paramNeworder = new HashMap<>();
                    paramNeworder.put("distributor_id","1");
                    paramNeworder.put("status","new");
                    Log.e("Neworder:","Neworder:-" + paramNeworder);
                    return paramNeworder;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    25000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
        }
    }
}
