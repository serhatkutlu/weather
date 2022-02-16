package com.msk.weather.responce.data


import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.msk.weather.responce.LocalData.DB_Entity
import com.msk.weather.responce.LocalData.LocalDay


data class weather(
    @SerializedName("current")
    val current: Current,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("location")
    val location: Location
){
    fun toEntity():DB_Entity{
        val days= arrayListOf<LocalDay>()
        for (i in forecast.forecastday){
            val localday=LocalDay(i.date,i.day.avgtempC,i.day.condition.icon,i.day.condition.text)
            days.add(localday)
        }
        val currDay:LocalDay
        current.apply {
            currDay=LocalDay( temp_c = this.tempC, icon_url = this.condition.icon, info =this.condition.text )
        }

        return DB_Entity(city = location.name , currDay =currDay,days = days, )
    }
}
