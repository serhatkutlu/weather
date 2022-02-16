package com.msk.weather.responce.LocalData

import java.util.*

data class LocalDay(
    val date:String?=null,
    val temp_c:Double,
    val icon_url:String,
    val info:String
) {
}