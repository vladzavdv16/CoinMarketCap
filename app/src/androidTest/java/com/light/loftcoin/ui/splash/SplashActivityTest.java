package com.light.loftcoin.ui.splash;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SplashActivityTest  {

    private SharedPreferences sPrefs;

    @Before
    public void setUp() throws Exception {
        final Context context = ApplicationProvider.getApplicationContext();
        sPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Test
    public void open_welcome_activity_if_first_run() {


    }
}