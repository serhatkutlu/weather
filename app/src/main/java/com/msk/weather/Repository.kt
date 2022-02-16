package com.msk.weather

import com.msk.weather.DataBase.dao
import com.msk.weather.Util.Resource
import com.msk.weather.responce.LocalData.DB_Entity
import com.msk.weather.responce.api.weatherApi
import com.msk.weather.responce.data.weather
import dagger.hilt.android.scopes.ActivityScoped
import timber.log.Timber
import java.lang.Exception

@ActivityScoped
class Repository (val api:weatherApi,val dao:dao) {

         suspend fun getPokemonRepository():Resource<DB_Entity>{

             val data=api.GetWeatherdata().toEntity()

             dao.addCity(data)
             //Timber.d(dao.getCity("London").toString())
            val responce= try {
                dao.getCity("London")

            }catch (e:Exception){
                Timber.d(e.toString())
                return Resource.Error("Error")

            }

            return Resource.Success(responce)
        }

}