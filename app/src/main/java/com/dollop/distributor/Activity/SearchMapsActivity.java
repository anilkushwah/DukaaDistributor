package com.dollop.distributor.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import com.dollop.distributor.DirectionHelper.GoogleApisHandle;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.SavedData;
import com.dollop.distributor.UtilityTools.SessionManager;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.database.UserModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchMapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener {

    Activity activity = SearchMapsActivity.this;
    private static final int REQUEST_CODE_AUTOCOMPLETEPICK = 1;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled, check_request_ride;
    private boolean canGetLocation = false;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    // integer for permissions results request
    private static final int ALL_PERMISSIONS_RESULT = 1011;
    private Location location;
    private GoogleApiClient googleApiClient;
    double Currentlatitude, Currentlongitude;
    ImageView iv_clear_editText_text;
    TextView et_searchLocation, tv_current_location, tv_mark;
    LinearLayout ll_search;
    Button btn_save_location;
    List<Place.Field> fields;
    private FusedLocationProviderClient fusedLocationClient;
    boolean flag = true;
    private LatLng Pick_up = null;
    LatLng CurrentlatLng;
    Marker PickUpMarker;
    String Address;
    public GoogleApisHandle googleApisHandle;
    int count = 0;
    //    double Currentlatitude = 0.00;
//    double Currentlongitude = 0.00;
    String type = "";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_maps);

        sessionManager = new SessionManager(this);
        type = getIntent().getStringExtra("type");

        initializationView();


    }


    private void googleMethods() {

        fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionsToRequest = permissionsToRequest(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(
                        new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        googleApiClient = new GoogleApiClient.Builder(activity).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();

        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        if (!Places.isInitialized()) {
            Places.initialize(activity, "\n" + "AIzaSyANub5r9vSq0BTwMQprVi8_Xoe7i8_Ao0I");
        }

        googleApisHandle = GoogleApisHandle.getInstance(activity);


    }


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wantedPermissions) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == ALL_PERMISSIONS_RESULT) {
            for (String perm : permissionsToRequest) {
                if (!hasPermission(perm)) {
                    permissionsRejected.add(perm);
                }
            }

            if (permissionsRejected.size() > 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                        new androidx.appcompat.app.AlertDialog.Builder(this).
                                setMessage("These permissions are mandatory to get your location. You need to allow them.").
                                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(permissionsRejected.
                                                    toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                        }
                                    }
                                }).setNegativeButton("Cancel", null).create().show();

                        return;
                    }
                }
            } else {
                if (googleApiClient != null) {
                    googleApiClient.connect();
                }
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

        ////////////////canboy
        /*if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {*/

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Currentlatitude, Currentlongitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            }
        });
        getCurrentLocation();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            Log.e("Dollop::", "lat change::" + location.getLatitude());
            Log.e("Dollop::", "long change::" + location.getLongitude());
            Currentlatitude = location.getLatitude();
            Currentlongitude = location.getLongitude();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.e("Dollop::", "lat after change::" + location.getLatitude());
            Log.e("Dollop::", "long after change::" + location.getLongitude());
            Currentlatitude = location.getLatitude();
            Currentlongitude = location.getLongitude();

            LatLng CurrentlatLng = new LatLng(Currentlatitude, Currentlongitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(CurrentlatLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
    }

    private void setCurrentMarker(double latitude, double longitude) {

        CurrentlatLng = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CurrentlatLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        getAddress(latitude, longitude);
    }

    public String getAddress(double lat, double lang) {
        try {
            Geocoder geocoder;
            List<android.location.Address> addresses;
            geocoder = new Geocoder(SearchMapsActivity.this);
            if (lat != 0 || lang != 0) {
                addresses = geocoder.getFromLocation(lat, lang, 1);
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getAddressLine(1);
                String country = addresses.get(0).getAddressLine(2);
                String state = addresses.get(0).getSubLocality();

                Utils.E("Qmy  address" + addresses);
                Utils.E("Qaddress" + address);
                Utils.E("Qcity" + city);
                Utils.E("Qcountry" + country);
                Utils.E("Qstate" + state);

                et_searchLocation.setText(address);
                tv_current_location.setText(address);
                tv_mark.setText(state);

                Address = et_searchLocation.toString();
                return city + "," + country + "-" + state;

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void getCurrentLocation() {
        // location = googleApisHandle.getLastKnownLocation(SearchMapsActivity.this);
        location = googleApisHandle.getLastKnownLocation(this);
        if (location != null) {
            setCurrentMarker(location.getLatitude(), location.getLongitude());
            Currentlatitude = location.getLatitude();
            Currentlongitude = location.getLongitude();
        } else {
            if (count < 5) {
                count++;
                getCurrentLocation();

            } else {
                //  Toast.makeText(getApplicationContext(), "please_refresh_your_location", Toast.LENGTH_SHORT);
                ShowRefereshDialog();

            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btn_save_location) {

            SaveLocation();

        } else if (v == iv_clear_editText_text) {
            et_searchLocation.setText("");
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = this.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(this);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        } else if (v == ll_search) {

            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,
                    fields).build(SearchMapsActivity.this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETEPICK);
        }
    }


    private void SaveLocation() {
        final Dialog dialog = Utils.initProgressDialog(this);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.add_address, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                Log.e("save_location", "save_distributor_location:--" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("jsonObject:", "jsonObject:--" + jsonObject);

                    //   Toast.makeText(SearchMapsActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    if (jsonObject.getInt("status") == 200) {

                        JSONObject data = jsonObject.getJSONObject("data");

                        UserModel userModel = UserDataHelper.getInstance().getList().get(0);
                        userModel.setStore_lat(data.getString("store_lat").toString());
                        userModel.setStore_long(data.getString("store_long").toString());
                        userModel.setLocation(data.getString("store_address").toString());
                        UserDataHelper.getInstance().insertData(userModel);
                        SavedData.saveSetlocation("Active");

                        if (type.equals("otpPage")) {
                            Utils.I_clear(SearchMapsActivity.this, LoginActivity.class, null);
                            finish();
                        } else {
                            sessionManager.setLoginSession(true);
                            Utils.I_clear(SearchMapsActivity.this, HomeActivity.class, null);
                            finish();
                        }

                    } else {
                        Utils.T(SearchMapsActivity.this, jsonObject.getString("message"));
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
                        Utils.T(SearchMapsActivity.this, errorMessage + "please check Internet connection");
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

                stringStringHashMap.put("add_lat", "" + Currentlatitude);
                stringStringHashMap.put("add_long", "" + Currentlongitude);
                stringStringHashMap.put("address", et_searchLocation.getText().toString());
                stringStringHashMap.put("type_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
                stringStringHashMap.put("address_type", "Store");
                stringStringHashMap.put("type", "distributor");

                Utils.E("saveAddress::" + stringStringHashMap);
                return stringStringHashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETEPICK) {
            flag = false;
            Place place = Autocomplete.getPlaceFromIntent(data);

            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude(place.getLatLng().latitude);
            location.setLongitude(place.getLatLng().longitude);

            Currentlatitude = place.getLatLng().latitude;
            Currentlongitude = place.getLatLng().longitude;


            if (place.getAddress().contains(place.getName())) {
                et_searchLocation.setText(place.getAddress());
                tv_current_location.setText(place.getAddress());
                tv_mark.setText(place.getName());

            } else {
                et_searchLocation.setText(place.getAddress());
                tv_current_location.setText(place.getAddress());
                tv_mark.setText(place.getName());
            }

            Drawable circleDrawable = getResources().getDrawable(R.drawable.ic_greypin);
            BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable);
            Pick_up = place.getLatLng();
            if (PickUpMarker != null) {
                PickUpMarker.remove();
            }
            PickUpMarker = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).icon(markerIcon));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(Pick_up));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        }
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }

        //check_request_ride = getuserDetails.getBoolean("request_ride",false);

    }

    @Override
    public void onPause() {
        super.onPause();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    public void onDestroy() {

        super.onDestroy();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    private void ShowRefereshDialog() {
        androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(SearchMapsActivity.this)
                .setMessage("please_refresh_your_location")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Utils.I(this, AmountDoneNotificationActivity.class, null);
                        dialog.dismiss();
                    }
                }).show();

    }

    private void ShowEnableDialog() {
        androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(SearchMapsActivity.this)
                .setMessage("You need to enable permissions to display location !")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Utils.I(this, AmountDoneNotificationActivity.class, null);
                        dialog.dismiss();
                    }
                }).show();

    }

    private void initializationView() {
        tv_current_location = findViewById(R.id.tv_current_location);
        tv_mark = findViewById(R.id.tv_mark);
        btn_save_location = findViewById(R.id.btn_save_location);
        ll_search = findViewById(R.id.ll_search);

        iv_clear_editText_text = findViewById(R.id.iv_clear_editText_text);
        et_searchLocation = findViewById(R.id.et_searchLocation);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        if (!isGooglePlayServicesAvailable()) {
            finish();
        }

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled) {
            // no network provider is enabled
            showSettingsAlert();

        } else {
            this.canGetLocation = true;
        }
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionsToRequest = permissionsToRequest(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(
                        new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }

        // we build google api client
        googleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        googleMethods();


        btn_save_location.setOnClickListener(this);
        iv_clear_editText_text.setOnClickListener(this);
        ll_search.setOnClickListener(this);

    }

}
