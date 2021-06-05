package com.light.loftcoin.data;

import android.app.ListActivity;

import androidx.annotation.NonNull;

import com.light.loftcoin.BuildConfig;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class CmcCoinsRepo implements CoinsRepo {

    private CmcApi api;

    public CmcCoinsRepo() {
        api = createRetrofit(createHttpClient()).create(CmcApi.class);
    }

    @NonNull
    @Override
    public List<? extends Coin> listings(@NonNull String currency) throws IOException {
        Response<Listings> response = api.listings(currency).execute();
        if (response.isSuccessful()) {
            final Listings listings = response.body();
            if (listings != null) {
                return listings.data();
            }
        } else {
            ResponseBody body = response.errorBody();
            if (body != null){
                throw new IOException(response.toString());
            }
        }
        return Collections.emptyList();
    }


    private OkHttpClient createHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(chain -> {
            Request request = chain.request();
            return chain.proceed(request.newBuilder().
                    addHeader(CmcApi.API_KEY, BuildConfig.API_KEY).build());
        });
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            interceptor.redactHeader(CmcApi.API_KEY);
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }

    private Retrofit createRetrofit(OkHttpClient httpClient) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(httpClient);
        builder.baseUrl(BuildConfig.API_ENDPOINT);
        Moshi moshi = new Moshi.Builder().build();
        builder.addConverterFactory(MoshiConverterFactory.create(
                moshi.newBuilder()
                        .add(Coin.class, moshi.adapter(AutoValue_Coin.class))
                        .add(Listings.class, moshi.adapter(AutoValue_Listings.class))
                        .build()
        ));
        return builder.build();
    }


}
