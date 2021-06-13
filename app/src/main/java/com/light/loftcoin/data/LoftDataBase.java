package com.light.loftcoin.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {
        RoomCoin.class
}, version = 1)
abstract class LoftDataBase extends RoomDatabase {
    abstract CoinsDao coins();
}
