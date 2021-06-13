package com.light.loftcoin.data;


public interface Coin {

    int id();

    String name();

    String symbol();

    int rank();

    double price();

    double change24h();

}
