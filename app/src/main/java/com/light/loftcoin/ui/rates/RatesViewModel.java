package com.light.loftcoin.ui.rates;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.light.loftcoin.data.Coin;
import com.light.loftcoin.data.CoinsRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

public class RatesViewModel extends ViewModel {

    private final MutableLiveData<List<Coin>> coins = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isRefreshing = new MutableLiveData<>();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final CoinsRepo repo;

    private Future<?> future;

    @Inject
    public RatesViewModel(CoinsRepo repo) {
       this.repo = repo;
        refresh();
    }

    @NonNull
    LiveData<List<Coin>> coins() {
        return coins;
    }

    @NonNull
    LiveData<Boolean> isRefreshing() {
        return isRefreshing;
    }

    final void refresh() {
        isRefreshing.postValue(true);
        future = executor.submit(() -> {
            try {
                coins.postValue(new ArrayList<>(repo.listings("USD")));
                isRefreshing.postValue(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onCleared() {
        if (future != null) {
            future.cancel(true);
        }
    }


}
