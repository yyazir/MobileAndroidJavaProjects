package com.yumel.rehber;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface EventInterface {
    @GET("EventData/GetLast5Events")
    @Headers("Content-Type: text/xml")
    Call<List<EarthquakeAFAD>> getEvents();
}
