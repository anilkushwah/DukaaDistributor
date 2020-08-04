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
import com.dollop.distributor.model.NewOderlist;
import com.dollop.distributor.model.Processinglist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ProcessingFragment extends Fragment {

    public ProcessingFragment() {
        // Required empty public constructor
    }

    RecyclerView rv_processorder;
    ArrayList<Processinglist> processingList;
    ProcessingorderAdapter  processingAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
             View root = inflater.inflate(R.layout.fragment_processorder, container, false);
        rv_processorder = root.findViewById(R.id.rv_processorder);

        processingList = new ArrayList<>();

        Processinglist model = new Processinglist();
        model.setGen_order_id("#1232423");
        model.setTotal_amount("3450");
        model.setItemCount("4");
        model.setAgencyname("Ram Agencyes");
        processingList.add(model);


        Processinglist model1 = new Processinglist();
        model1.setGen_order_id("#1232423");
        model1.setTotal_amount("3450");
        model1.setItemCount("4");
        model1.setAgencyname("Ram Agencyes");
        processingList.add(model1);

        Processinglist model2 = new Processinglist();
        model2.setGen_order_id("#1232423");
        model2.setTotal_amount("3450");
        model2.setItemCount("4");
        model2.setAgencyname("Ram Agencyes");
        processingList.add(model2);

        Processinglist model3 = new Processinglist();
        model3.setGen_order_id("#1232423");
        model3.setTotal_amount("3450");
        model3.setItemCount("4");
        model3.setAgencyname("Ram Agencyes");
        processingList.add(model3);

        Processinglist model4 = new Processinglist();
        model4.setGen_order_id("#1232423");
        model4.setTotal_amount("3450");
        model4.setItemCount("4");
        model4.setAgencyname("Ram Agencyes");
        processingList.add(model4);

        rv_processorder.setLayoutManager(new LinearLayoutManager(getActivity()));
        processingAdapter = new ProcessingorderAdapter(getActivity(),processingList);
        rv_processorder.setAdapter(processingAdapter);



        /// processingList();
        return root;
    }

    private void processingList() {
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
                            processingList = new ArrayList<>();
                            for(int i= 0;i<jsonArray.length();i++){

                                Processinglist model = new Processinglist();

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


                                processingList.add(model);
                                rv_processorder.setLayoutManager(new LinearLayoutManager(getActivity()));
                                processingAdapter = new ProcessingorderAdapter(getActivity(),processingList);
                                rv_processorder.setAdapter(processingAdapter);
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
                    paramNeworder.put("status","processing");
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
