package com.yumel.rehber;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PharmacyService {

        @GET("Account/NobetciEczaneList?ilKodu=6&ilceKodu=Tumu&nobetGunu=0")
        Call<List<ENabizPharmacy>> getPharmacies();

}
