package com.light.loftcoin;

import android.content.Context;

import com.light.loftcoin.data.CoinsRepo;
import com.light.loftcoin.data.CurrencyRepo;

public interface BaseComponent {
    abstract Context context();
    abstract CoinsRepo coinsRepo();
    abstract CurrencyRepo currencyRepo();
}
