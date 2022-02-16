package com.msk.weather.DataBase

import androidx.room.*

import com.msk.weather.responce.LocalData.DB_Entity


@Dao
interface dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCity(City:DB_Entity)

    @Query("Select * From weather_DB WHERE city In (:name)")
    suspend fun getCity(name:String):DB_Entity

    @Delete
    suspend fun delete(City:DB_Entity)

}