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
import com.dollop.distributor.model.CompleteOderlist;
import com.dollop.distributor.model.NewOderlist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CompleteOrderFragment extends Fragment {

    public CompleteOrderFragment() {
        // Required empty public constructor
    }

    RecyclerView rv_completeorder;
    ArrayList<CompleteOderlist> completOderList;
    CompleteOrderAdapter completOrderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
             View root = inflater.inflate(R.layout.fragment_completorder, container, false);
        rv_completeorder = root.findViewById(R.id.rv_completeorder);

        Completlist();
        return root;
    }

    private void Completlist() {
        {
            final Dialog dialog = Utils.initProgressDialog(getActivity());
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.view_orders, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();

                    Log.e("CompleteOderlist:", "CompleteOderlist:--" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        Toast.makeText(getActivity(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        if (jsonObject.getInt("status")==200) {

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            completOderList = new ArrayList<>();
                            for(int i= 0;i<jsonArray.length();i++){

                                CompleteOderlist model = new CompleteOderlist();

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

                                completOderList.add(model);
                                rv_completeorder.setLayoutManager(new LinearLayoutManager(getActivity()));
                                completOrderAdapter = new CompleteOrderAdapter(getActivity(),completOderList);
                                rv_completeorder.setAdapter(completOrderAdapter);
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
                    paramNeworder.put("status","completed");
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
