package com.light.loftcoin.ui.welcome;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.light.loftcoin.databinding.ActivityWelcomeBinding;
import com.light.loftcoin.ui.main.MainActivity;
import com.light.loftcoin.widget.CircleIndicator;

public class WelcomeActivity extends AppCompatActivity {

    public static final String KEY_SHOW_WELCOME = "show_welcome";
    private SnapHelper helper;
    private ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        //add circleIndicator
        binding.recycler.addItemDecoration(new CircleIndicator(this));
        binding.recycler.setAdapter(new WelcomePageAdapter());

        helper = new PagerSnapHelper();
        helper.attachToRecyclerView(binding.recycler);

        binding.btnStart.setOnClickListener(view -> {
            PreferenceManager.getDefaultSharedPreferences(this).edit().
                    putBoolean(KEY_SHOW_WELCOME, false).apply();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        helper.attachToRecyclerView(null);
        binding.recycler.setAdapter(null);
        super.onDestroy();
    }
}
