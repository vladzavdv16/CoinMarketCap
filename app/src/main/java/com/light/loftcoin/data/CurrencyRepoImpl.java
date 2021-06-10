package com.light.loftcoin.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.light.loftcoin.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

class CurrencyRepoImpl implements CurrencyRepo{

    private final Context context;

    @Inject
    public CurrencyRepoImpl(@NonNull Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public LiveData<List<Currency>> availableCurrencies() {
        return new AllCurrenciesLiveData(context);
    }

    @NonNull
    @Override
    public LiveData<Currency> currency() {
        return null;
    }

    @Override
    public void updateCurrency(@NonNull Currency currency) {

    }

    private static class AllCurrenciesLiveData extends LiveData<List<Currency>>{

        private final Context context;

        private AllCurrenciesLiveData(Context context) {
            this.context = context;
        }

        @Override
        protected void onActive() {
            List<Currency> currencies = new ArrayList<>();
            currencies.add(Currency.create("$", "USD", context.getString(R.string.usd)));
            currencies.add(Currency.create("E", "EUR", context.getString(R.string.eur)));
            currencies.add(Currency.create("R", "RUB", context.getString(R.string.rub)));
            setValue(currencies);
        }

        @Override
        protected void onInactive() {
            super.onInactive();
        }
    }
}
