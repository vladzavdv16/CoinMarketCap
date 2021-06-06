package com.light.loftcoin.data;


import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Currency {

    static Currency create(String symbol, String code, String title){
        return new AutoValue_Currency(symbol, code, title);
    }

    public abstract String symbol();

    public abstract String code();

    public abstract String title();
}
