package com.light.loftcoin;

import android.app.Application;
import android.os.StrictMode;

import com.google.firebase.messaging.FirebaseMessaging;
import com.light.loftcoin.ui.main.MainActivity;
import com.light.loftcoin.util.DebugTree;

import timber.log.Timber;

public class LoftApp extends Application {

    private BaseComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults();
            Timber.plant(new DebugTree());
        }

        component = DaggerAppComponent.builder()
                .application(this)
                .build();

        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(instanceIdResult -> {
            Timber.d("fcm: %s", instanceIdResult);
        });

    }

    public BaseComponent getComponent() {
        return component;
    }
}
