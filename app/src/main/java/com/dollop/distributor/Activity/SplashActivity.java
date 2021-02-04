package com.dollop.distributor.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.SessionManager;
import com.dollop.distributor.UtilityTools.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends AppCompatActivity {
SessionManager sessionManager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessionManager = new SessionManager(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.I(SplashActivity.this, LoginActivity.class,null);
                finish();
            }
        }, 5000);


        FirebaseApp.initializeApp(SplashActivity.this);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("MSG", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Utils.E("Token::" + token);
                        // Log and toast
                        sessionManager.setTokenFCM(token);

                        Utils.E("Token11 11:" + sessionManager.getTokenFCM());
                        //  Toast.makeText(SplashScreen.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }
}