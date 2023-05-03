package com.yumel.rehber;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CityInterface {
    @GET("Account/GetIller?ne=true")
    Call<String> getCities();
}
