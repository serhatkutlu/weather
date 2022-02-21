package com.msk.weather

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
class Repository (val api:weatherApi,val dao:dao) {

          fun getCityRepository(city:String): Flow<Resource<DB_Entity>> =flow {

                    emit(Resource.Loading())
                    val database_Responce=dao.getCity(city)

                    emit(Resource.Loading(database_Responce))

                     try {
                         val Api_Responce = api.GetWeatherdata().toEntity()

                         dao.addCity(Api_Responce)

                    } catch (e: Exception) {

                        emit(Resource.Error("data could not be updated"))

                    }
                    val data=dao.getCity(city)
                    emit(Resource.Success(data))
                }
    suspend fun getAllCityRepository():Flow<Resource<List<DB_Entity>>>{
        return flow {
            Timber.d("database is empty")
            emit(Resource.Loading())

            try {
                val AllCitys=dao.getAllCity()
                Timber.d(AllCitys[0].city)
                emit(Resource.Success(AllCitys))
            }catch (e:Exception){

               emit(Resource.Error("database is empty"))
            }

        }


    }

}