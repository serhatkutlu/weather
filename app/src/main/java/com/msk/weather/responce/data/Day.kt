package com.msk.weather.responce.data


import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("avgtemp_c")
    val avgtempC: Double, // 0.7
     @SerializedName("condition")
    val condition: ConditionX,

)