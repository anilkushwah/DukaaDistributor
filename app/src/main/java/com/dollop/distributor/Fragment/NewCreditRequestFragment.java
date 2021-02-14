package com.dollop.distributor.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.SavedData;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.NewCreditReqAdapter;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.CreditRequestResponse;
import com.dollop.distributor.model.NewCreditReq_Model;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;


public class NewCreditRequestFragment extends Fragment {

    RecyclerView rv_new_request;
    ArrayList<NewCreditReq_Model> newCreditReq_models;
    NewCreditReqAdapter newCreditReqAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_newcredit, container, false);

        rv_new_request = root.findViewById(R.id.rv_new_request);
        newCreditReq_models = new ArrayList<>();
        get_credit_request("Pending");
        return root;


    }


   /* private void get_credit_request(final String status) {
        final Dialog dialog = Utils.initProgressDialog(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.get_credit_request, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Utils.E("get_credit_request::" + response);
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Utils.T(getActivity(), jsonObject.getString("message").toString());

                    if (jsonObject.getInt("status") == 200) {
                        jsonObject.getString("pending_count");
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        newCreditReq_models = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            NewCreditReq_Model model = new NewCreditReq_Model();
                            //{"status":1,"message":"No Data Found!",
                            // "pending_count":"0",
                            // "approved_count":"0",
                            // "cancel_count":"0","data":{"distributor_id":"10"}}
                            if (!object.isNull("distributor_id")) {
                                model.setDistributor_id(object.getString("distributor_id"));
                            }
                            if (!object.isNull("id")) {
                                model.setRequest_id(object.getString("id"));
                            }

                            if (!object.isNull("retailer_id")) {
                                model.setRetailer_id(object.getString("retailer_id"));

                            }
                            if (!object.isNull("amount")) {
                                model.setTv_reqamount(object.getString("amount"));
                            }
                            if (!object.isNull("acc_no")) {
                                model.setAcc_no(object.getString("acc_no"));

                            }
                            if (!object.isNull("description")) {
                                model.setDescription(object.getString("description"));
                            }
                            if (!object.isNull("status")) {
                                model.setStatus(object.getString("status"));
                            }
                            if (!object.isNull("retailer_name")) {
                                Utils.E("name>>>" + object.getString("retailer_name"));
                                model.setName(object.getString("retailer_name"));
                            }
                            if (!object.isNull("retailer_mobile")) {
                                Utils.E("mobile>>>" + object.getString("retailer_mobile"));
                                model.setMobile(object.getString("retailer_mobile"));
                            }
                            if (!object.isNull("retailer_address")) {
                                model.setAddress(object.getString("retailer_address"));
                            }

                            if (!object.isNull("retailer_image")) {
                                model.setRetailer_image(object.getString("retailer_image"));
                            }

                            if (!object.isNull("retailer_shop_name")) {
                                model.setStorename(object.getString("retailer_shop_name"));
                            }
                            newCreditReq_models.add(model);

                        }
                        rv_new_request.setLayoutManager(new LinearLayoutManager(getActivity()));
                        newCreditReqAdapter = new NewCreditReqAdapter(getActivity(), newCreditReq_models);
                        rv_new_request.setAdapter(newCreditReqAdapter);
                        newCreditReqAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Utils.E("get_credit_request:Exception:" + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                if (error.getClass().equals(TimeoutError.class)) {
                    String errorMessage = "Request timeout";
                    Utils.T(getActivity(), errorMessage + " No Internet Connection available. Please try again");
                }
            }
        }) {
            @Override
            public String getCacheKey() {
                return super.getCacheKey();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> stringStringHashMap = new HashMap<>();

                stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
                stringStringHashMap.put("status", status);
                Utils.E("get_credit_request:params:" + stringStringHashMap);
                return stringStringHashMap;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);


    }*/

    private void get_credit_request(final String status) {
        final Dialog dialog = Utils.initProgressDialog(getActivity());
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> stringStringHashMap = new HashMap<>();

        if (SavedData.get_AccessType().equals("Distributor")) {
            stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
        } else {
            stringStringHashMap.put("distributor_id", SavedData.get_MainMemberId());
        }

        stringStringHashMap.put("status", status);
        Call<CreditRequestResponse> call = apiService.get_credit_request(stringStringHashMap);
        call.enqueue(new Callback<CreditRequestResponse>() {
            @Override
            public void onResponse(Call<CreditRequestResponse> call, retrofit2.Response<CreditRequestResponse> response) {
                dialog.dismiss();
                try {
                    CreditRequestResponse body = response.body();
                    newCreditReq_models.clear();

                    if (body.getStatus() == 200) {

                        newCreditReq_models = body.getData();

                        rv_new_request.setLayoutManager(new LinearLayoutManager(getActivity()));
                        newCreditReqAdapter = new NewCreditReqAdapter(getActivity(), newCreditReq_models);
                        rv_new_request.setAdapter(newCreditReqAdapter);
                        newCreditReqAdapter.notifyDataSetChanged();

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<CreditRequestResponse> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                dialog.dismiss();

            }
        });

    }


}
