package com.yumel.rehber;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ENabizPharmacyApiInterface {
    @GET("Account/NobetciEczaneList")
    Call<List<ENabizPharmacy>> getNobetciEczaneler(
            @Query("ilKodu") int ilKodu,
            @Query("ilceKodu") String ilceKodu,
            @Query("nobetGunu") int nobetGunu);


    //getEczaneList(@Query("ilKodu") int ilKodu, @Query("ilceKodu") String ilceKodu, @Query("nobetGunu") int nobetGunu);

    /*@GET("Account/NobetciEczaneList")
    /*Call<List<ENabizPharmacy>> getEczaneList(@Query("ilKodu") int ilKodu, @Query("ilceKodu") String ilceKodu, @Query("nobetGunu") int nobetGunu);*/

    /*Call<List<ENabizPharmacy>> getEczaneList();*/




    /*@GET("/Account/NobetciEczaneList?ilKodu=6&ilceKodu=Tumu&nobetGunu=0")
    Call<ENabizPharmacy> getposts();*/
/*    @GET("/Account/NobetciEczaneList")
    Call<List<ENabizGetPharmacy>> getPharmacies(@Query("ilKodu") int cityCode,
                                       @Query("ilceKodu") String districtCode,
                                       @Query("nobetGunu") int day);*/

/*    static ENabizPharmacyApiInterface getInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://enabiz.gov.tr")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ENabizPharmacyApiInterface.class);
    }*/
}