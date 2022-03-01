package com.msk.weather.Repository

import com.msk.weather.Util.Resource
import com.msk.weather.responce.LocalData.DB_Entity
import kotlinx.coroutines.flow.Flow

interface IRepository {

    suspend fun getCityRepository(city:String): Flow<Resource<DB_Entity>>

    suspend fun getAllCityRepository():Flow<Resource<List<DB_Entity>>>

    suspend fun saveCityToDatabase(dbEntity: DB_Entity):Flow<Resource<DB_Entity>>

    suspend fun deleteCityInDatabase(dbEntity: DB_Entity)
}