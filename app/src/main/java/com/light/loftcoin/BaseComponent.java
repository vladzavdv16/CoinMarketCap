package com.light.loftcoin;

import android.content.Context;

import com.light.loftcoin.data.CoinsRepo;
import com.light.loftcoin.data.CurrencyRepo;
import com.light.loftcoin.util.ImageLoader;
import com.light.loftcoin.util.RxSchedulers;

public interface BaseComponent {
    Context context();

    CoinsRepo coinsRepo();

    CurrencyRepo currencyRepo();

    ImageLoader imageLoader();

    RxSchedulers schedulers();


}
