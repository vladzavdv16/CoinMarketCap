package com.light.loftcoin.ui.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.light.loftcoin.R;
import com.light.loftcoin.ui.welcome.WelcomeActivity;
import com.light.loftcoin.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private final Handler handler = new Handler();
    private Runnable goNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (preferences.getBoolean(WelcomeActivity.KEY_SHOW_WELCOME, true)) {
            goNext = () -> {
                startActivity(new Intent(this, WelcomeActivity.class));
            };
        } else {
            goNext = () -> {
                 startActivity(new Intent(this, MainActivity.class));
            };
        }
        handler.postDelayed(goNext, 2000);
    }

    @Override
    protected void onStop() {
        handler.removeCallbacks(goNext);
        super.onStop();
    }
}
