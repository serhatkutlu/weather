package com.msk.weather.Util

import android.os.Build
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

 object DateConverter {

    fun ConvertDate(date:String):String{

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val day= LocalDate.parse(date,firstApiFormat).dayOfWeek.toString()
            return ConvertDay(day)
        }else{
        val firstApiFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date).day
            return ConvertDay(firstApiFormat.toString())
    }
     }
     fun ConvertDay(day:String):String {
         Timber.d(day)
        when (day) {
            "MONDAY" -> return "Pazartesi"
            "TUESDAY" -> return "Salı"
            "WEDNESDAY" -> return "Carsamba"
            "THURSDAY" -> return "Persembe"
            "FRIDAY" -> return "Cuma"
            "SATURDAY" -> return "Cumartesi"
            "SUNDAY" -> return "Pazar"
            else->return "gun"
        }
    }
}