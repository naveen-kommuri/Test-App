package com.test.coinstracker;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;
    private Context mContext;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//                    .certificatePinner(certificatePinner)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
//                    .protocols(Arrays.asList(Protocol.HTTP_1_1))
//                    .sslSocketFactory(getSslSocketFactory(context))  // Enable for SSL Pinning
                    .build();


            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.coinmarketcap.com/v1/ticker/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}