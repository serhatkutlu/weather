package com.msk.weather.responce.api

import com.msk.weather.Util.Constants.API_KEY
import com.msk.weather.responce.data.weather
import retrofit2.http.GET
import retrofit2.http.Query

interface weatherApi {

    @GET("forecast.json")
    suspend fun GetWeatherdata(
        @Query("key") key:String=API_KEY,
        @Query("q") q:String,
        @Query("days") days:Int=3,
        @Query("lang") lang:String="tr"
    ):weather
}