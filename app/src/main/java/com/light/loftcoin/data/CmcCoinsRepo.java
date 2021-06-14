package com.light.loftcoin.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;
import timber.log.Timber;

@Singleton
class CmcCoinsRepo implements CoinsRepo {

    private final CmcApi api;

    private final LoftDataBase db;


    @Inject
    public CmcCoinsRepo(CmcApi api, LoftDataBase db) {
        this.api = api;
        this.db = db;

    }

    @NotNull
    @Override
    public Observable<List<Coin>> listings(@NonNull Query query) {
        return Observable
                .fromCallable(() -> query.forceUpdate() || db.coins().coinsCount() == 0)
                .switchMap((f) -> f ? api.listings(query.currency()) : Observable.empty())
                .map((listings) -> mapToRoomCoins(query, listings.data()))
                .doOnNext((coins) -> db.coins().insert(coins))
                .switchMap((coins) -> fetchFromDb(query))
                .switchIfEmpty(fetchFromDb(query))
                .map(ArrayList::new)
                .subscribeOn(Schedulers.io())
                ;

    }

    private Observable<List<RoomCoin>> fetchFromDb(Query query) {
        if (query.sortBy() == SortBy.PRICE) {
            return db.coins().fetchAllSortByPrice();
        } else {
            return db.coins().fetchAllSortByRank();
        }

    }

    private Observable<List<RoomCoin>> mapToRoomCoins(Query query, List<? extends Coin> data) {
        List<RoomCoin> roomCoins = new ArrayList<>(data.size());
        for (Coin coin : data) {
            roomCoins.add(RoomCoin.create(
                    coin.name(),
                    coin.symbol(),
                    coin.rank(),
                    coin.price(),
                    coin.change24h(),
                    query.currency(),
                    coin.id()
            ));
        }
        return (Observable<List<RoomCoin>>) roomCoins;
    }

}
