package com.msk.weather.Repository

import com.msk.weather.DataBase.dao
import com.msk.weather.Util.Resource
import com.msk.weather.responce.LocalData.DB_Entity
import com.msk.weather.responce.api.weatherApi
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.lang.Exception

@ActivityScoped
class Repository (val api:weatherApi,val dao:dao): IRepository {

    override suspend fun getCityRepository(city:String): Flow<Resource<DB_Entity>> =flow {
        emit(Resource.Loading())
        try {
            val Api_Responce = api.GetWeatherdata(q = city).toEntity()
            emit(Resource.Success(Api_Responce))
        } catch (e: Exception) {

            emit(Resource.Error("data could not be updated"))

        }

    }

    override suspend fun getAllCityRepository():Flow<Resource<List<DB_Entity>>>{
        return flow {

            emit(Resource.Loading())

            try {
                var AllCitysdao=dao.getAllCity()

                try {
                    AllCitysdao.map {
                        val responce=api.GetWeatherdata(q=it.city).toEntity()
                        responce.city=it.city
                        dao.update(responce)
                }
                    AllCitysdao=dao.getAllCity()
                    emit(Resource.Success(AllCitysdao))
                }catch (e:Exception){
                    emit(Resource.Success(AllCitysdao))
                }
                emit(Resource.Success(AllCitysdao))
            }catch (e:Exception){

               emit(Resource.Error("database is empty"))
            }
        }
    }

    override suspend fun saveCityToDatabase(dbEntity: DB_Entity):Flow<Resource<DB_Entity>>{
        return flow {
            emit(Resource.Loading())

            try {
                dao.addCity(dbEntity)
                val data=dao.getCity(dbEntity.city)


                emit(Resource.Success(data))
            }catch (e:Exception){
                emit(Resource.Error("data could not be saved"))
            }
        }

    }
    override suspend fun deleteCityInDatabase(dbEntity: DB_Entity){
        try {
            dao.delete(dbEntity)
        }catch (e:Exception){
            Timber.d(e)
        }
    }
}