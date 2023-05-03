package com.yumel.rehber;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DistrictsInterface {
    @GET("Account/GetNobetciEczaneIlceler")
    Call<String> getDistricts(
            @Query("IlKodu") int İlKodu,
            @Query("nobetGunu") int nobetGunu);
}
