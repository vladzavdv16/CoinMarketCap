package com.light.loftcoin;

import android.app.Application;
import android.content.Context;

import com.light.loftcoin.data.CoinsRepo;
import com.light.loftcoin.data.DataModule;
import com.light.loftcoin.widget.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(
        modules = {
                AppModule.class,
                DataModule.class
        }
)

public abstract class AppComponent {

    abstract Context context();
    abstract CoinsRepo coinsRepo();

    @Component.Builder
    static abstract class Builder {
        @BindsInstance
        abstract Builder application(Application app);

        abstract AppComponent build();
    }
}
