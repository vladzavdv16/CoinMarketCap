package com.light.loftcoin.data;


import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Observable;

@Dao
abstract class CoinsDao {

    @Query("SELECT * FROM RoomCoin")
    abstract LiveData<List<RoomCoin>> fetchAll();

    @Query("SELECT * FROM RoomCoin ORDER by price DESC")
    abstract Observable<List<RoomCoin>> fetchAllSortByPrice();

    @Query("SELECT * FROM RoomCoin ORDER by rank ASC")
    abstract Observable<List<RoomCoin>> fetchAllSortByRank();

    @WorkerThread
    @Query("SELECT COUNT(id) FROM(RoomCoin)")
    abstract int coinsCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insert(List<RoomCoin> coins);
}
