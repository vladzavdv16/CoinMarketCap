package com.light.loftcoin.ui.currency;


import androidx.lifecycle.ViewModelProvider;

import com.light.loftcoin.BaseComponent;
import com.light.loftcoin.util.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        CurrencyModule.class,
        ViewModelModule.class
}, dependencies = {
        BaseComponent.class
})

abstract class CurrencyComponent {

    abstract ViewModelProvider.Factory viewModelFactory();


}
