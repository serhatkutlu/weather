package com.msk.weather.responce.api

import com.msk.weather.Util.Constants.API_KEY
import com.msk.weather.responce.data.weather
import retrofit2.http.GET
import retrofit2.http.Query

interface weatherApi {

    @GET("forecast.json")
    suspend fun GetWeatherdata(
        @Query("key") key:String=API_KEY,
        @Query("q") q:String="London",
        @Query("days") days:String="7"
    ):weather
}