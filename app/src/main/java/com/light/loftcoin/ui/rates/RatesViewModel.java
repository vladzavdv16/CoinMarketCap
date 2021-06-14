package com.light.loftcoin.ui.rates;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;


import com.light.loftcoin.data.Coin;
import com.light.loftcoin.data.CoinsRepo;
import com.light.loftcoin.data.CurrencyRepo;
import com.light.loftcoin.data.SortBy;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class RatesViewModel extends ViewModel {

    private final Observable<List<Coin>> coins;

    private final Subject<Boolean> isRefreshing = BehaviorSubject.create();

    private final Subject<Class<?>> pullToRefresh = BehaviorSubject.createDefault(Void.TYPE);

    private final Subject<SortBy> sortBy = BehaviorSubject.createDefault(SortBy.RANK);

    private final AtomicBoolean forceUpdate = new AtomicBoolean();

    private int sortIndex = 1;

    @Inject
    public RatesViewModel(CoinsRepo coinsRepo, CurrencyRepo currencyRepo) {

        this.coins = pullToRefresh
                .map((ptr) -> CoinsRepo.Query.builder())
                .switchMap((qb) -> currencyRepo.currency()
                        .map((c) -> qb.currency(c.code())))
                .doOnNext((qb) -> isRefreshing.onNext(true))
                .doOnNext((qb) -> forceUpdate.set(true))
                .switchMap((qb) -> sortBy
                        .map(qb::sortBy))
                .map((qb) -> qb.forceUpdate(forceUpdate.getAndSet(false)))
                .map(CoinsRepo.Query.Builder::build)
                .switchMap(coinsRepo::listings)
                .doOnEach((ntf) -> isRefreshing.onNext(false));

    }

    @NonNull
    Observable<List<Coin>> coins() {
        return coins.observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    Observable<Boolean> isRefreshing() {
        return isRefreshing.observeOn(AndroidSchedulers.mainThread());
    }

    final void refresh() {
        pullToRefresh.onNext(Void.TYPE);
    }

    void switchSortingOrder() {
        sortBy.onNext(SortBy.values()[sortIndex++ % SortBy.values().length]);
    }

}
