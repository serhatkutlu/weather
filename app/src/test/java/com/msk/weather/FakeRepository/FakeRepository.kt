package com.msk.weather.FakeRepository

import com.msk.weather.Repository.IRepository
import com.msk.weather.Util.Resource
import com.msk.weather.responce.LocalData.DB_Entity
import com.msk.weather.responce.LocalData.LocalDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class FakeRepository : IRepository {

    val fakeDatabase= mutableListOf<DB_Entity>()



    //fake request
    override suspend fun getCityRepository(city: String): Flow<Resource<DB_Entity>> {
        return flow{

            val day=LocalDay("Monday",10.4,"https","info")

            val _db_entity=DB_Entity(city, listOf(day),day)

            emit(Resource.Success(_db_entity))

        }


    }

    override suspend fun getAllCityRepository(): Flow<Resource<List<DB_Entity>>> {
        return flow {
            val allCity=fakeDatabase
            emit(Resource.Success(allCity))

        }
    }

    override suspend fun saveCityToDatabase(dbEntity: DB_Entity): Flow<Resource<DB_Entity>> {
        return flow {
            fakeDatabase.add(dbEntity)

            fakeDatabase.forEach {
                if (it==dbEntity){
                    emit(Resource.Success(it))
                    return@forEach
                }else{
                    emit(Resource.Error("data could not be saved"))

                }
            }

        }
    }

    override suspend fun deleteCityInDatabase(dbEntity: DB_Entity) {
        fakeDatabase.remove(dbEntity)
    }
}