package com.light.loftcoin.ui.main;

import android.content.SharedPreferences;

import com.light.loftcoin.BaseComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        MainModule.class
}, dependencies = {
        BaseComponent.class
})
public abstract class MainComponent {

    abstract void inject(MainActivity activity);

}
