package com.msk.weather.responce.LocalData

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_DB")
data class DB_Entity(
    val  id:Int=0,
    @PrimaryKey(autoGenerate = false)
    val city:String,
    val days:List<LocalDay>,
    @Embedded
    val currDay:LocalDay
) {



}