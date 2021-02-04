package com.dollop.distributor.Activity;

import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.dollop.distributor.DirectionHelper.FetchURL;
import com.dollop.distributor.DirectionHelper.TaskLoadedCallback;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.SavedData;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.firebase.Config;
import com.dollop.distributor.model.Datum;
import com.dollop.distributor.model.OrderModel;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

import static com.dollop.distributor.DirectionHelper.PointsParser.jObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private GoogleMap mMap;
    private String driver_id = "";
    private Datum datum;

    private double aDriveDoubleLatitute = 0.0;
    private double aDriverDoubleLongitute = 0.0;
    private double aDistributorLatitude = 0.0;
    private double aDistributorLongitude = 0.0;

    private LatLng BOUND1;
    private LatLng BOUND2;
    private Polyline currentPolyline;
    TextView driver_name_tv, location_txt, driver_mobile_tv, vehicle_number_tv;
    TextView retailer_name_tv, retailer_number_tv;

    CircleImageView driver_image;
    ImageView vehicle_type_image;

    Handler handler = new Handler();
    int lunchingTime = 0;
    Context mContext;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        datum = (Datum) getIntent().getSerializableExtra("orderDetail");
        mContext = MapsActivity.this;
        dialog = new Dialog(MapsActivity.this, R.style.dialogstyle);

        retailer_name_tv = findViewById(R.id.retailer_name_tv);
        retailer_number_tv = findViewById(R.id.retailer_number_tv);

        driver_image = findViewById(R.id.driver_image);
        vehicle_number_tv = findViewById(R.id.vehicle_number_tv);
        driver_name_tv = findViewById(R.id.driver_name_tv);
        driver_mobile_tv = findViewById(R.id.driver_mobile_tv);
        vehicle_type_image = findViewById(R.id.vehicle_type_image);
        location_txt = findViewById(R.id.location_txt);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        LocalBroadcastManager.getInstance(MapsActivity.this).registerReceiver(mMessageReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        if (datum.driverId == null) {
            setTimerMethod();

        } else {

            runnable.run();

            getProfile();

            vehicle_number_tv.setText(datum.vehicleType);
            if (datum.vehicleType.equals("Van")) {
                vehicle_type_image.setBackgroundResource(R.drawable.ic_pickup_truck_home);

            } else if (datum.vehicleType.equals("Bike")) {
                vehicle_type_image.setBackgroundResource(R.drawable.bike);

            } else if (datum.vehicleType.equals("Truck")) {
                vehicle_type_image.setBackgroundResource(R.drawable.truck);
            }
        }


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

    private Runnable runnable = new Runnable() {
        public void run() {
            if (!((Activity) mContext).isFinishing()) {
                get_driver_lat_long();
            }

            handler.postDelayed(this, 10000);
        }
    };

    private void get_driver_lat_long() {
        lunchingTime = 1;
        final Dialog dialog = Utils.initProgressDialog(MapsActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.get_driver_lat_long, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Utils.E("get_driver_lat_long::" + response);
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 200) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        String id = jsonObject1.getString("id");
                        String driver_lat = jsonObject1.getString("driver_lat");
                        String driver_long = jsonObject1.getString("driver_long");
                        String driver_name = jsonObject1.getString("full_name");
                        String mobile_number = jsonObject1.getString("mobile");
                        //   String profile_img = jsonObject1.getString("profile_img");

                        aDriveDoubleLatitute = Double.parseDouble(driver_lat);
                        aDriverDoubleLongitute = Double.parseDouble(driver_long);


                        driver_name_tv.setText(driver_name);
                        driver_mobile_tv.setText(mobile_number);

                        if (!jsonObject1.isNull("profile_img")) {
                            Picasso.get().load(Const.URL.HOST_URL + jsonObject1.getString("profile_img")).error(R.drawable.ic_user)
                                    .into(driver_image);
                        }

                        LatLng distributorLatLong = new LatLng(aDistributorLatitude, aDistributorLongitude);
                        Utils.E("distributorLatLong::" + distributorLatLong);
                        mMap.addMarker(new MarkerOptions().position(distributorLatLong).title(datum.shopName));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(distributorLatLong));
                        LatLng driverLatLong = new LatLng(aDriveDoubleLatitute, aDriverDoubleLongitute);
                        mMap.addMarker(new MarkerOptions().position(driverLatLong).title("Partner"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(driverLatLong));
                        Utils.E("driverLatLong::" + driverLatLong);

                        new FetchURL(MapsActivity.this).execute(getUrl(distributorLatLong, driverLatLong, "driving"), "driving");
                        Utils.E("getUrl::" + getUrl(distributorLatLong, driverLatLong, "driving"));
                        BOUND1 = distributorLatLong;
                        BOUND2 = driverLatLong;
                        moveCameraToWantedArea();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                if (error.getClass().equals(TimeoutError.class)) {
                    String errorMessage = "Request timeout";
                    Utils.T(MapsActivity.this, errorMessage + " No Internet Connection available. Please try again");
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

                stringStringHashMap.put("driver_id", datum.driverId);
                //  stringStringHashMap.put("distributor_id","1");
                Utils.E("get_driver_lat_long:params:" + stringStringHashMap);
                return stringStringHashMap;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(MapsActivity.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

    }

    private void moveCameraToWantedArea() {
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                // Set up the bounds coordinates for the area we want the user's viewpoint to be.
                LatLngBounds bounds = new LatLngBounds.Builder()
                        .include(BOUND1)
                        .include(BOUND2)
                        .build();
                // Move the camera now.
                if (lunchingTime == 1) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
                }

            }
        });
        distanceFrom_in_Km();
    }

    private void distanceFrom_in_Km() {
        JSONArray jRoutes;
        JSONArray jLegs;
        JSONObject distance;
        try {
            jRoutes = jObject.getJSONArray("routes");
            for (int i = 0; i < jRoutes.length(); i++) {
                jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                for (int j = 0; j < jLegs.length(); j++) {
                    distance = ((JSONObject) jLegs.get(i)).getJSONObject("distance");
                    String Km = distance.getString("text");
                    Utils.E("km" + Km);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" +
                getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }


    private void getOrderDetail(final Dialog dialogMain) {
        final Dialog dialog1 = Utils.initProgressDialog(MapsActivity.this);


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
        stringStringHashMap.put("order_id", datum.id);

        Call<OrderModel> call = apiService.getAllOrder(stringStringHashMap);
        call.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, retrofit2.Response<OrderModel> response) {
                dialog1.dismiss();
                try {
                    OrderModel body = response.body();

                    if (body.status == 200) {


                        datum = body.data.get(0);
                        Utils.E("UtilsData ArrayList:getOrderDetail:" + datum.driverId);
                        if (datum.driverId != null) {
                            runnable.run();
                            dialogMain.dismiss();

                            if (datum.vehicleType.equals("Van")) {
                                vehicle_type_image.setBackgroundResource(R.drawable.ic_pickup_truck_home);

                            } else if (datum.vehicleType.equals("Bike")) {
                                vehicle_type_image.setBackgroundResource(R.drawable.bike);

                            } else if (datum.vehicleType.equals("Truck")) {
                                vehicle_type_image.setBackgroundResource(R.drawable.truck);
                            }

                            if (datum.orderStatus.equals("3")) {
                                aDistributorLatitude = Double.parseDouble(datum.retailerLat);
                                aDistributorLongitude = Double.parseDouble(datum.retailerLong);
                                location_txt.setText("Drop Location");

                            } else if (datum.orderStatus.equals("4")) {
                                aDistributorLatitude = Double.parseDouble(datum.retailerLat);
                                aDistributorLongitude = Double.parseDouble(datum.retailerLong);
                                location_txt.setText("Drop Location");

                            } else {
                                aDistributorLatitude = Double.parseDouble(datum.storeLat);
                                aDistributorLongitude = Double.parseDouble(datum.storeLong);
                                location_txt.setText("Pickup Location");

                            }


                        } else {
                            onBackPressed();
                        }


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                dialog1.dismiss();

            }
        });

    }

    private void setTimerMethod() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_dialog_for_accept_request);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setCanceledOnTouchOutside(false);


        dialog.setCancelable(false);
        dialog.show();

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (!((Activity) mContext).isFinishing()) {
                    getOrderDetail(dialog);
                    getProfile();
                }


            }
        };

        handler.postDelayed(runnable, 30000);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Utils.E("Order Details:Order Details");

            String message = intent.getStringExtra("action");
            Utils.E("Order Details:Order Details");
            if (message.equals("order_accepted")) {

                if (!((Activity) mContext).isFinishing()) {

                    dialog.dismiss();
                    getOrderDetail(dialog);

                }


            } else if (message.equals("order_deliverd")) {
                if (!((Activity) mContext).isFinishing()) {
                    Utils.I(MapsActivity.this, HomeActivity.class, null);
                    finish();
                }
            } else if (message.equals("driver_picked_order")) {
                if (!((Activity) mContext).isFinishing()) {
                    Utils.T(MapsActivity.this, "Order Assign to driver");
                    MapsActivity.this.setResult(RESULT_OK);

                    finish();
                }
            }


            Utils.E("message:Order Details" + message);

        }
    };


    private void getProfile() {
        final Dialog dialog = Utils.initProgressDialog(MapsActivity.this);
        RequestQueue queue = Volley.newRequestQueue(MapsActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.get_distributor, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                Log.e("getProfile:", "profile:--" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    // Toast.makeText(PersonalDeatilsActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {

                        JSONArray jsonArray = jsonObject.getJSONArray("distributor");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            if (!object.isNull("name")) {


                            }

                            if (!object.isNull("mobile")) {
                                retailer_number_tv.setText(object.getString("mobile"));
                            }
                            if (!object.isNull("store_address")) {
                                retailer_name_tv.setText(object.getString("name"));
                            }


                        }
                    } else {
                        Utils.T(MapsActivity.this, jsonObject.getString("message"));
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
                        Utils.T(MapsActivity.this, errorMessage + "please check Internet connection");
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
                HashMap<String, String> ProfileParam = new HashMap<>();
                //   ProfileParam.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId().toString());

                ProfileParam.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
                Log.e("ProfileParam::", "param:--" + ProfileParam);
                return ProfileParam;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }


}