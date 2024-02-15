package com.metoly.roomtest.Api

import com.metoly.roomtest.Model.Diesel.Diesel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DieselPriceService {
    @GET("gasPrice/turkeyDiesel")
    fun getDieselPrice(
        @Query("district") district: String,
        @Query("city") city: String,
        @Header("content-type") contentType: String = "application/json",
        @Header("authorization") authorization: String
    ): Call<Diesel>
}