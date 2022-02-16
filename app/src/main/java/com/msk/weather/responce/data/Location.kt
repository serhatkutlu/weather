package com.msk.weather.responce.data


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("country")
    val country: String, // Turkey
    @SerializedName("localtime")
    val localtime: String, // 2022-02-14 13:50
    @SerializedName("name")
    val name: String, // Igdir
)