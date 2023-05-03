package com.yumel.rehber;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PharmacyApiInterface {
    /*@Headers({"Content-Type: application/json",
            "Authorization: apikey 1Gggk1eQZAgZZpeHBXnmbf:1qaltJQ9y32q9WUWyXnaOb"})
    @GET("health/dutyPharmacy?il=ankara")
    Call<PostPharmacy> getposts();*/
            @GET("Account/NobetciEczaneList")
            Call<List<ENabizPharmacy>> getEczaneList(@Query("ilKodu") int ilKodu, @Query("ilceKodu") String ilceKodu, @Query("nobetGunu") int nobetGunu);


        }
