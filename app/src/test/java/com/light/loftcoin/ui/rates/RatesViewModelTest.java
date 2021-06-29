package com.light.loftcoin.ui.rates;


import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.light.loftcoin.data.Coin;
import com.light.loftcoin.data.CoinsRepo;
import com.light.loftcoin.data.FakeCoin;
import com.light.loftcoin.data.TestCoinsRepo;
import com.light.loftcoin.data.TestCurrencyRepo;
import com.light.loftcoin.util.TestRxSchedulers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import io.reactivex.observers.TestObserver;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class RatesViewModelTest {

    private TestCurrencyRepo currencyRepo;

    private TestCoinsRepo coinsRepo;

    private RatesViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        currencyRepo = new TestCurrencyRepo(ApplicationProvider.getApplicationContext());
        coinsRepo = new TestCoinsRepo();
        viewModel = new RatesViewModel(coinsRepo, currencyRepo, new TestRxSchedulers());
    }

    @Test
    public void coins() {
        final TestObserver<List<Coin>> coinsTest = viewModel.coins().test();

        viewModel.isRefreshing().test().assertValue(true);
        final List<Coin> coins = Arrays.asList(new FakeCoin(), new FakeCoin());
        coinsRepo.listings.onNext(coins);
        viewModel.isRefreshing().test().assertValue(false);
        coinsTest.assertValue(coins);

        CoinsRepo.Query query = coinsRepo.lastListingsQuery;
        assertThat(query).isNotNull();
        assertThat(query.forceUpdate()).isTrue();

        viewModel.switchSortingOrder();

        viewModel.isRefreshing().test().assertValue(false);
        coinsRepo.listings.onNext(coins);
        viewModel.isRefreshing().test().assertValue(false);

        query = coinsRepo.lastListingsQuery;
        assertThat(query).isNotNull();
        assertThat(query.forceUpdate()).isFalse();
    }
}