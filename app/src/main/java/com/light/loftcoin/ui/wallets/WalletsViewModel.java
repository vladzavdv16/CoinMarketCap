package com.light.loftcoin.ui.wallets;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.light.loftcoin.data.CurrencyRepo;
import com.light.loftcoin.data.Transaction;
import com.light.loftcoin.data.Wallet;
import com.light.loftcoin.data.WalletsRepo;
import com.light.loftcoin.util.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

class WalletsViewModel extends ViewModel {

    private final Subject<Integer> walletPosition = BehaviorSubject.createDefault(0);

    private final Observable<List<Wallet>> wallets;

    private final RxSchedulers schedulers;

    @Inject
    WalletsViewModel(WalletsRepo walletsRepo, CurrencyRepo currencyRepo, RxSchedulers schedulers) {
        wallets = currencyRepo.currency().switchMap(walletsRepo::wallets)
        .doOnNext((wallets) -> Timber.d("%d", wallets.size()));
        this.schedulers = schedulers;
    }

    @NonNull
    Observable<List<Wallet>> wallets() {
        return wallets.observeOn(schedulers.main());
    }

    @NonNull
    Observable<List<Transaction>> transactions() {
        return Observable.empty();
    }

    @NonNull
    Completable addWallet() {
        return Completable.fromAction(() -> Timber.d("~"));
    }

}