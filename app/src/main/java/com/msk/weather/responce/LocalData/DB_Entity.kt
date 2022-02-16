package com.msk.weather.responce.LocalData

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_DB")
data class DB_Entity(
    @PrimaryKey(autoGenerate = true)
    val  id:Int=0,
    val city:String,
    val days:List<LocalDay>,
    @Embedded
    val currDay:LocalDay
) {



}