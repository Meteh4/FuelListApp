package com.metoly.roomtest.Api

import com.metoly.roomtest.Model.Gasoline.Gasoline
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GasolinePriceService {
    @GET("gasPrice/turkeyGasoline")
    fun getGasolinePrice(
        @Query("district") district: String,
        @Query("city") city: String,
        @Header("content-type") contentType: String = "application/json",
        @Header("authorization") authorization: String
    ): Call<Gasoline>
}
