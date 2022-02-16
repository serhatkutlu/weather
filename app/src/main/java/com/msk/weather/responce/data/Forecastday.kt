package com.msk.weather.responce.data


import com.google.gson.annotations.SerializedName

data class Forecastday(
    @SerializedName("date")
    val date: String, // 2022-02-14
    @SerializedName("day")
    val day: Day,

)