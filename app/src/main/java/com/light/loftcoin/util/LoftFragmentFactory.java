package com.light.loftcoin.util;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import timber.log.Timber;

public class LoftFragmentFactory extends FragmentFactory {

    private final Map<Class<?>, Provider<Fragment>> provider;

    @Inject
    LoftFragmentFactory(Map<Class<?>, Provider<Fragment>> provider){
        this.provider = provider;
    }

    @NonNull
    @Override
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
        try {
            final Class<?> classKey = Class.forName(className);
            final Provider<Fragment> providers = provider.get(classKey);
            if (providers != null){
                return providers.get();
            }
        } catch (ClassNotFoundException e) {
            Timber.e(e);
        }
        return super.instantiate(classLoader, className);
    }
}
