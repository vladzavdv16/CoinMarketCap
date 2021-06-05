package com.light.loftcoin.ui.rates;

import androidx.annotation.NonNull;

import com.light.loftcoin.data.Coin;

import java.util.List;

public interface RatesView {

    void showCoins(@NonNull List<? extends Coin> coins);

    void showError(@NonNull String error);
}
