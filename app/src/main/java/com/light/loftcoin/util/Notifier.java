package com.light.loftcoin.util;

import androidx.annotation.NonNull;

import io.reactivex.Completable;

public interface Notifier {

    @NonNull
    Completable sendMessage(String title, String message, Class<?> receiver);
}
