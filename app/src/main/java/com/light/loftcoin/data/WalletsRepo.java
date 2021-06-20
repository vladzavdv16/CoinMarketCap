package com.light.loftcoin.data;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;

public interface WalletsRepo {

    @NonNull
    Observable<List<Wallet>> wallets(Currency currency);

    @NonNull
    Observable<List<Transaction>> transactions(@NonNull Wallet wallet);
}
