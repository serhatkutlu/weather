package com.msk.weather.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.msk.weather.Converters.Converter
import com.msk.weather.responce.LocalData.DB_Entity


@Database(
    entities = [DB_Entity::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class WeatherDatabase:RoomDatabase() {
    abstract fun Dao():dao
}