package com.msk.weather.responce.LocalData

import java.util.*

data class LocalDay(
    val date:String,
    val temp_c:Double,
    val icon_url:String,
    val info:String
) {
}