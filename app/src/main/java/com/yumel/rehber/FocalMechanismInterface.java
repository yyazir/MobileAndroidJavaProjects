package com.yumel.rehber;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FocalMechanismInterface {
    @GET("sismo/focalmechanism/odakmekanizmasiyeni.xml")
    Call<AutomaticMomentTensors>  getFocalMechanism();
}
