package com.dollop.distributor.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.NetworkUtil;
import com.dollop.distributor.UtilityTools.SavedData;
import com.dollop.distributor.UtilityTools.SessionManager;
import com.dollop.distributor.UtilityTools.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    public SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(this);
        //getSupportActionBar().setElevation(0);
        //hideSystemUI();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_allItem, R.id.navigation_cart, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        navView.setOnNavigationItemSelectedListener(this);

        boolean status = NetworkUtil.getConnectivityStatus(HomeActivity.this);
        if (status == true) {

        } else {
            Utils.T(HomeActivity.this, "No Internet Connection available. Please try again");
        }

        if (SavedData.get_AccessType().equals("Partial Access")) {
            navView.getMenu().findItem(R.id.navigation_dashboard).setVisible(false);
            navView.getMenu().findItem(R.id.nav_earning).setVisible(false);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            // action with ID action_settings was selected
            case R.id.action_settings:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }

        return true;
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        // remove the following flag for version < API 19
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            // action with ID action_settings was selected
            case R.id.navigation_home:
                Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment).navigate(R.id.navigation_home);

                break;

            case R.id.nav_earning:

                Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_earning);
                break;

            case R.id.navigation_items:
                Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment).navigate(R.id.navigation_allItem);
                break;

            case R.id.navigation_profile:
                Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment).navigate(R.id.navigation_profile);
                break;
            case R.id.navigation_dashboard:
                Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment).navigate(R.id.navigation_dashboard);
                break;

            default:
                break;
        }

        return true;

    }
}
