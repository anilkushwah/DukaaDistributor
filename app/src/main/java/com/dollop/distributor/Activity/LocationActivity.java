package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;


import com.dollop.distributor.DirectionHelper.GoogleApisHandle;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utils;

public class LocationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int ALL_PERMISSIONS_RESULT =1011 ;
    LinearLayout btn_use_Location_Id,add_location_manually;
    String Currentlatitude, Currentlongitude;
    Activity activity =  LocationActivity.this;
    private Location location;
    public GoogleApisHandle googleApisHandle;
    int count = 0;
    LocationManager locationManager;
    private boolean isGPSEnabled;
    private boolean canGetLocation = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        add_location_manually = findViewById(R.id.add_location_manually);
        btn_use_Location_Id = findViewById(R.id.btn_use_Location_Id);
        btn_use_Location_Id.setOnClickListener(this);
        add_location_manually.setOnClickListener(this);

        googleApisHandle = GoogleApisHandle.getInstance(LocationActivity.this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);



     //   permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
      //  permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

     //   permissionsToRequest = permissionsToRequest(permissions);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (permissionsToRequest.size() > 0) {
//                requestPermissions(permissionsToRequest.toArray(
//                        new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
//            }
//        }

    }
//    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {
//        ArrayList<String> result = new ArrayList<>();
//
//        for (String perm : wantedPermissions) {
//            if (!hasPermission(perm)) {
//                result.add(perm);
//            }
//        }
//        return result;
//    }

    @Override
    public void onClick(View v) {
          if (v == btn_use_Location_Id){
              if (!isGPSEnabled) {
                  // no network provider is enabled
                  showSettingsAlert();
              } else {
                  this.canGetLocation = true;
                  getCurrentLocation();
              }
              //
        }
          else if(v == add_location_manually){
              Intent intent = new Intent(LocationActivity.this, SearchMapsActivity.class);
              startActivity(intent);
              finish();
          }
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

    private void getCurrentLocation() {

        location = googleApisHandle.getLastKnownLocation(this);
        if (location != null) {
            Currentlatitude = String.valueOf(location.getLatitude());
            Currentlongitude = String.valueOf(location.getLongitude());

            Utils.E("Currentlatitude"+Currentlatitude);
            Utils.E("Currentlongitude"+Currentlongitude);


        } else {
            if (count < 5) {
                count++;
                getCurrentLocation();

            } else {
              //  Toast.makeText(getApplicationContext(), "please_refresh_your_location", Toast.LENGTH_SHORT);

                    Utils.T(LocationActivity.this, "Please Refresh Your Location");

            }
        }
    }

}
