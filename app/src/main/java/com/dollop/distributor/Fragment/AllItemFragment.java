package com.dollop.distributor.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

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
import com.dollop.distributor.UtilityTools.NetworkUtil;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.AddProduct_model;
import com.dollop.distributor.model.CategoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllItemFragment extends Fragment {


    ViewPager allitemsviewPager;


    String Arr[] = {"one", "two", "three", "four", "five", "six", "saven"};
    private ProductParentFragment fragmentParent;
    private ArrayList<CategoryModel> categotyNameList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_all_item, container, false);

        boolean status = NetworkUtil.getConnectivityStatus(getActivity());

        Categoty();

        return root;
    }

    private void Categoty() {
        final Dialog dialog = Utils.initProgressDialog(getActivity());
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.category, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                Log.e("distributer Category:-", "category:--" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                   // Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {

                        Utils.E("Category Response:-" + response);

                        JSONArray dataArray = jsonObject.getJSONArray("allCategory");
                        Utils.E("dataArray :-" + dataArray);

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject dataobject = dataArray.getJSONObject(i);

                            AddProduct_model addProduct_model = new AddProduct_model();
                            CategoryModel model = new CategoryModel();

                            if (!dataobject.isNull("category_id")) {
                                addProduct_model.setId(dataobject.getString("category_id"));
                                model.setId(dataobject.getString("category_id"));
                            }

                            if (!dataobject.isNull("name")) {
                                addProduct_model.setName(dataobject.getString("name"));
                                model.setName(dataobject.getString("name"));

                            }

                            if (!dataobject.isNull("image")) {
                                addProduct_model.setImage(dataobject.getString("image"));
                            }

                            if (!dataobject.isNull("is_active")) {
                                addProduct_model.setIs_active(dataobject.getString("is_active"));
                            }
                            if (!dataobject.isNull("is_delete")) {
                                addProduct_model.setIs_delete(dataobject.getString("is_delete"));
                            }
                            if (!dataobject.isNull("create_date")) {
                                addProduct_model.setCreate_date(dataobject.getString("create_date"));
                            }

                            Utils.E("category_list" + categotyNameList);

                            categotyNameList.add(model);

                        }

                        fragmentParent = (ProductParentFragment) getChildFragmentManager().findFragmentById(R.id.fragmentParent);
                        for (int i = 0; i < categotyNameList.size(); i++) {
                            Utils.E("categoryNameList::"+categotyNameList.get(i).getName());

                        fragmentParent.addPage(categotyNameList.get(i));
                        }
                    } else {
                        Utils.T(getActivity(), jsonObject.getString("message"));
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());

                //  stringStringHashMap.put("mobile",et_login_mobile_number.getText().toString());
                //  stringStringHashMap.put("type","distributer");
                Log.e("LoginParameter::", "paramLogin:--" + stringStringHashMap);
                return stringStringHashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

}
