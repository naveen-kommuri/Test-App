package com.test.coinstracker;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("{idName}")
    Call<ArrayList<Coin>> getCoin(@Path("idName") String id, @Query("convert") String convertType);
}