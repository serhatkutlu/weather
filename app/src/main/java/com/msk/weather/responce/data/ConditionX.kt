package com.msk.weather.responce.data


import com.google.gson.annotations.SerializedName

data class ConditionX(
    @SerializedName("icon")
    val icon: String, // //cdn.weatherapi.com/weather/64x64/day/113.png
    @SerializedName("text")
    val text: String // Sunny
)