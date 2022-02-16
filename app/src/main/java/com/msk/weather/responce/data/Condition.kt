package com.msk.weather.responce.data


import com.google.gson.annotations.SerializedName

data class Condition(
    @SerializedName("code")
    val code: Int, // 1000
    @SerializedName("icon")
    val icon: String, // //cdn.weatherapi.com/weather/64x64/day/113.png
    @SerializedName("text")
    val text: String // Sunny
)