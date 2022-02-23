package com.msk.weather.Util

import android.os.Build
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DateConverter {

    fun ConvertDate(date:String):String{

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val day= LocalDate.parse(date,firstApiFormat).dayOfWeek.toString()
            return ConvertDay(day)
        }else{
        val firstApiFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date).day
            return ConvertDay(firstApiFormat.toString())
    }}
    fun ConvertDay(day:String):String {
        when (day) {
            "Monday" -> return "Mon"
            "Tuesday" -> return "Tue"
            "Wednesday" -> return "Wed"
            "Thursday" -> return "Thu"
            "Friday" -> return "Fri"
            "Saturday" -> return "Sat"
            "Sunday" -> return "Sun"
            "1" -> return "Mon"
            "2" -> return "Tue"
            "3" -> return "Wed"
            "4" -> return "Thu"
            "5" -> return "Fri"
            "6" -> return "Sat"
            "7" -> return "Sun"
            else->return "day"
        }
    }
}