package com.yumel.rehber;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitIns {
    private static Retrofit retrofit;
    //    private static final String BASE_URL = "https://api.collectapi.com/";
    private static final String BASE_URL = "https://enabiz.gov.tr";

/*    public static Retrofit getPharcyRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }*/

    public static Retrofit getPharmacyRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
