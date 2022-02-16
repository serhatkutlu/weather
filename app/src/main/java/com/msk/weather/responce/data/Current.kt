package com.msk.weather.responce.data


import com.google.gson.annotations.SerializedName

data class Current(

    @SerializedName("condition")
    val condition: Condition,
    @SerializedName("feelslike_c")
    val feelslikeC: Double, // 2.1
    @SerializedName("is_day")
    val isDay: Int, // 1
    @SerializedName("temp_c")
    val tempC: Double, // 2.5

)