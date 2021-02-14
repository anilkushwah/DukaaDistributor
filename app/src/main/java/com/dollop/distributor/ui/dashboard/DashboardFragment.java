package com.dollop.distributor.ui.dashboard;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.dollop.distributor.Activity.StockQuantityActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.SavedData;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.database.UserDataHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;*/


public class DashboardFragment extends Fragment {


    TextView tv_view_all;
    ImageView back_back;
    private View root;
    private TextView order_accept;
    private TextView tvWeeklyId, tvWeeklyId_total_Sold;
    private TextView tv_monthly, tv_monthly_total_sold;
    private TextView tv_yearly, tv_yearly_total_sold;
    BarChart barchart, barchart_total_sold;
    LinearLayout week_chart_LL;
    RelativeLayout stack_managment_RL;
    Legend l;
    Legend l_sale;
    private String dates="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        tv_view_all = root.findViewById(R.id.tv_view_all);
        tvWeeklyId = root.findViewById(R.id.tvWeeklyId);
        tv_monthly = root.findViewById(R.id.tv_monthly);
        tv_yearly = root.findViewById(R.id.tv_yearly);
        barchart = root.findViewById(R.id.barchart);
        back_back = root.findViewById(R.id.back_back);
        tvWeeklyId_total_Sold = root.findViewById(R.id.tvWeeklyId_total_Sold);
        tv_monthly_total_sold = root.findViewById(R.id.tv_monthly_total_sold);
        tv_yearly_total_sold = root.findViewById(R.id.tv_yearly_total_sold);
        barchart_total_sold = root.findViewById(R.id.barchart_total_sold);
        week_chart_LL = root.findViewById(R.id.week_chart_LL);
        stack_managment_RL = root.findViewById(R.id.stack_managment_RL);

        OnClickListenerMethod();

        tvWeeklyId.setBackgroundResource(R.drawable.button_back);
        tvWeeklyId.setTextColor(Color.WHITE);

        tv_monthly.setBackgroundResource(R.drawable.btn_unselected);
        tv_monthly.setTextColor(Color.BLACK);

        tv_yearly.setBackgroundResource(R.drawable.btn_unselected);
        tv_yearly.setTextColor(Color.BLACK);

        getProductSold("Weekly");


        tvWeeklyId_total_Sold.setBackgroundResource(R.drawable.button_back);
        tvWeeklyId_total_Sold.setTextColor(Color.WHITE);

        tv_monthly_total_sold.setBackgroundResource(R.drawable.btn_unselected);
        tv_monthly_total_sold.setTextColor(Color.BLACK);

        tv_yearly_total_sold.setBackgroundResource(R.drawable.btn_unselected);
        tv_yearly_total_sold.setTextColor(Color.BLACK);

        getDashBoardDetail("Weekly");


        return root;
    }

    private void OnClickListenerMethod() {

        back_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        tvWeeklyId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvWeeklyId.setBackgroundResource(R.drawable.button_back);
                tvWeeklyId.setTextColor(Color.WHITE);

                tv_monthly.setBackgroundResource(R.drawable.btn_unselected);
                tv_monthly.setTextColor(Color.BLACK);

                tv_yearly.setBackgroundResource(R.drawable.btn_unselected);
                tv_yearly.setTextColor(Color.BLACK);
                resetChart();

                getProductSold("Weekly");

            }
        });


        tv_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_monthly.setBackgroundResource(R.drawable.button_back);
                tv_monthly.setTextColor(Color.WHITE);

                tvWeeklyId.setBackgroundResource(R.drawable.btn_unselected);
                tvWeeklyId.setTextColor(Color.BLACK);

                tv_yearly.setBackgroundResource(R.drawable.btn_unselected);
                tv_yearly.setTextColor(Color.BLACK);

                resetChart();
                getProductSold("Monthly");
            }
        });
        tv_yearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_yearly.setBackgroundResource(R.drawable.button_back);
                tv_yearly.setTextColor(Color.WHITE);

                tvWeeklyId.setBackgroundResource(R.drawable.btn_unselected);
                tvWeeklyId.setTextColor(Color.BLACK);

                tv_monthly.setBackgroundResource(R.drawable.btn_unselected);
                tv_monthly.setTextColor(Color.BLACK);
                resetChart();
                getProductSold("Yearly");
            }
        });

        tv_view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.I(getContext(), StockQuantityActivity.class, null);
            }
        });


        tvWeeklyId_total_Sold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvWeeklyId_total_Sold.setBackgroundResource(R.drawable.button_back);
                tvWeeklyId_total_Sold.setTextColor(Color.WHITE);

                tv_monthly_total_sold.setBackgroundResource(R.drawable.btn_unselected);
                tv_monthly_total_sold.setTextColor(Color.BLACK);

                tv_yearly_total_sold.setBackgroundResource(R.drawable.btn_unselected);
                tv_yearly_total_sold.setTextColor(Color.BLACK);
                resetChartSale();

                getDashBoardDetail("Weekly");

            }
        });


        tv_monthly_total_sold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_monthly_total_sold.setBackgroundResource(R.drawable.button_back);
                tv_monthly_total_sold.setTextColor(Color.WHITE);

                tvWeeklyId_total_Sold.setBackgroundResource(R.drawable.btn_unselected);
                tvWeeklyId_total_Sold.setTextColor(Color.BLACK);

                tv_yearly_total_sold.setBackgroundResource(R.drawable.btn_unselected);
                tv_yearly_total_sold.setTextColor(Color.BLACK);

                resetChartSale();
                getDashBoardDetail("Monthly");
            }
        });
        tv_yearly_total_sold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_yearly_total_sold.setBackgroundResource(R.drawable.button_back);
                tv_yearly_total_sold.setTextColor(Color.WHITE);

                tvWeeklyId_total_Sold.setBackgroundResource(R.drawable.btn_unselected);
                tvWeeklyId_total_Sold.setTextColor(Color.BLACK);

                tv_monthly_total_sold.setBackgroundResource(R.drawable.btn_unselected);
                tv_monthly_total_sold.setTextColor(Color.BLACK);
                resetChartSale();
                getDashBoardDetail("Yearly");
            }
        });
        stack_managment_RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), StockQuantityActivity.class));
            }
        });
    }

    private void resetChart() {
        barchart.notifyDataSetChanged();
        barchart.invalidate();
        barchart.getXAxis().setValueFormatter(null);
        barchart.getBarData().clearValues();
        barchart.clear();
    }

    private void resetChartSale() {
        barchart_total_sold.notifyDataSetChanged();
        barchart_total_sold.invalidate();
        barchart_total_sold.getXAxis().setValueFormatter(null);
        barchart_total_sold.getBarData().clearValues();
        barchart_total_sold.clear();
    }


    private void getDashBoardDetail(final String status) {

        XAxis xAxis = barchart_total_sold.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAvoidFirstLastClipping(false);
        xAxis.setLabelsToSkip(0);
        xAxis.setTextSize(20f);

        YAxis yAxisRight = barchart_total_sold.getAxisRight();
        yAxisRight.setEnabled(false);
        YAxis yAxisLeft = barchart_total_sold.getAxisLeft();
        yAxisLeft.setGranularity(31f);
        yAxisLeft.setTextSize(20f);
        yAxisLeft.setGranularity(30f);
        barchart_total_sold.setPinchZoom(true);
        l_sale = barchart_total_sold.getLegend();
        l_sale.setFormSize(15f);


        final Dialog dialog = Utils.initProgressDialog(getActivity());
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.statics_distributor_earning, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Utils.E("getDashBoardDetail:response:" + response);
              /*  {"status":200,"message":"success","earning":{"x":["2020-10-20 15:54:12","2020-10-20 18:06:22","2020-10-20
                    19:43:55","2020-10-21 11:33:54","2020-10-21 11:49:25"],"y":["2200","1100","1200","26000","26000"]}}

*/
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();


                    if (jsonObject.getInt("status") == 200) {
                        JSONObject earning = jsonObject.getJSONObject("earning");
                        JSONArray jsonArrayX = earning.getJSONArray("x");
                        JSONArray jsonArrayY = earning.getJSONArray("y");
                        ArrayList NoOfEmp = new ArrayList();
                        ArrayList year = new ArrayList();

                        for (int i = 0; i < jsonArrayX.length(); i++) {
                            if (status.equals("Monthly")) {
                                String[] onlyDate = jsonArrayX.getString(i).split("-");
                                dates = onlyDate[onlyDate.length - 1];
                            } else {
                                dates = jsonArrayX.getString(i);
                            }
                            //data.add(new ValueDataEntry(dates, Double.parseDouble(jsonArrayY.getString(i))));
                            NoOfEmp.add(new BarEntry(Float.parseFloat(jsonArrayY.getString(i)), i));
                            year.add(dates);
                        }
                        Utils.E("NoOfEmp::" + NoOfEmp);
                        BarDataSet bardataset = new BarDataSet(NoOfEmp, "");
                        barchart_total_sold.animateY(5000);
                        BarData data = new BarData(year, bardataset);
                        bardataset.setColors(Collections.singletonList(R.color.colorBlue));
                        barchart_total_sold.setData(data);

                        if (status.equals("Monthly")) {
                            // barchart.setVisibleXRangeMaximum(10);
                            //   data.setGroupSpace(0f);
                            //  barchart.getXAxis().setAxisMaxValue(10);
                            //  barchart.setVerticalScrollbarPosition(10);
                            barchart_total_sold.setVisibleXRange(1, 10);

                        } else if (status.equals("Weekly")) {
                            data.setGroupSpace(0);
                            barchart_total_sold.getXAxis().setAxisMaxValue(7);
                            barchart_total_sold.setVerticalScrollbarPosition(7);
                            barchart_total_sold.setVisibleXRange(1, 7);
                            barchart_total_sold.setVisibleXRangeMaximum(7);

                        } else if (status.equals("Yearly")) {
                            data.setGroupSpace(0);
                            barchart_total_sold.getXAxis().setAxisMaxValue(10);
                            barchart_total_sold.setVerticalScrollbarPosition(10);
                            barchart_total_sold.setVisibleXRange(1, 7);
                            barchart_total_sold.setVisibleXRangeMaximum(7);

                        }


                    } else {
                        Utils.T(getActivity(), jsonObject.getString("message"));
                    }
                } catch (Exception e) {
                    Utils.E("getDashBoardDetail:Exception:" + response);
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
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                //stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
                if (SavedData.get_AccessType().equals("Distributor")) {
                    stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());

                } else {
                    stringStringHashMap.put("distributor_id", SavedData.get_MainMemberId());

                }

                stringStringHashMap.put("type", status);
                Utils.E("getDashBoardDetail:Params:" + stringStringHashMap);
                return stringStringHashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }


    private void getProductSold(final String status) {

        XAxis xAxis = barchart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAvoidFirstLastClipping(false);
        xAxis.setLabelsToSkip(0);
        xAxis.setTextSize(20f);

        YAxis yAxisRight = barchart.getAxisRight();
        yAxisRight.setEnabled(false);
        YAxis yAxisLeft = barchart.getAxisLeft();
        yAxisLeft.setGranularity(31f);
        yAxisLeft.setTextSize(20f);
        yAxisLeft.setGranularity(30f);
        barchart.setPinchZoom(true);
        l = barchart.getLegend();
        l.setFormSize(15f);


        week_chart_LL.setVisibility(View.VISIBLE);

        final Dialog dialog = Utils.initProgressDialog(getActivity());
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.statics_distributor_product, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Utils.E("getProductSold:response:" + response);
              /*  {"status":200,"message":"success","earning":{"x":["2020-10-20 15:54:12","2020-10-20 18:06:22","2020-10-20
                    19:43:55","2020-10-21 11:33:54","2020-10-21 11:49:25"],"y":["2200","1100","1200","26000","26000"]}}

*/
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();


                    if (jsonObject.getInt("status") == 200) {
                        JSONObject earning = jsonObject.getJSONObject("earning");
                        JSONArray jsonArrayX = earning.getJSONArray("x");
                        JSONArray jsonArrayY = earning.getJSONArray("y");
                        ArrayList NoOfEmp = new ArrayList();
                        ArrayList year = new ArrayList();

                        for (int i = 0; i < jsonArrayX.length(); i++) {
                            String dates = "";
                            if (status.equals("Monthly")) {
                                String[] onlyDate = jsonArrayX.getString(i).split("-");
                                dates = onlyDate[onlyDate.length - 1];
                            } else {
                                dates = jsonArrayX.getString(i);
                            }

                            //data.add(new ValueDataEntry(dates, Double.parseDouble(jsonArrayY.getString(i))));
                            NoOfEmp.add(new BarEntry(Float.parseFloat(jsonArrayY.getString(i)), i));
                            year.add(dates);
                        }

                        BarDataSet bardataset = new BarDataSet(NoOfEmp, "");
                        barchart.animateY(5000);
                        BarData data = new BarData(year, bardataset);
                        bardataset.setColors(Collections.singletonList(R.color.colorBlue));
                        barchart.setData(data);

                        if (status.equals("Monthly")) {
                            // barchart.setVisibleXRangeMaximum(10);
                            //   data.setGroupSpace(0f);
                            //  barchart.getXAxis().setAxisMaxValue(10);
                            //  barchart.setVerticalScrollbarPosition(10);
                            barchart.setVisibleXRange(1, 10);

                        } else if (status.equals("Weekly")) {
                            data.setGroupSpace(0);
                            barchart.getXAxis().setAxisMaxValue(7);
                            barchart.setVerticalScrollbarPosition(7);
                            barchart.setVisibleXRange(1, 7);
                            barchart.setVisibleXRangeMaximum(7);

                        } else if (status.equals("Yearly")) {
                            data.setGroupSpace(0);
                            barchart.getXAxis().setAxisMaxValue(10);
                            barchart.setVerticalScrollbarPosition(10);
                            barchart.setVisibleXRange(1, 7);
                            barchart.setVisibleXRangeMaximum(7);

                        }


                    } else {
                        Utils.T(getActivity(), jsonObject.getString("message"));
                    }
                } catch (Exception e) {
                    Utils.E("getDashBoardDetail:Exception:" + response);
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
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                //stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
                if (SavedData.get_AccessType().equals("Distributor")) {
                    stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());

                } else {
                    stringStringHashMap.put("distributor_id", SavedData.get_MainMemberId());

                }

                stringStringHashMap.put("type", status);
                Utils.E("getProductSold:Params:" + stringStringHashMap);
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
