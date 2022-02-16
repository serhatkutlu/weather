package com.msk.weather.Di

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.msk.weather.DataBase.WeatherDatabase
import com.msk.weather.DataBase.dao
import com.msk.weather.Repository
import com.msk.weather.Util.Constants.BASE_URL
import com.msk.weather.Util.Converter
import com.msk.weather.Util.gsonParser
import com.msk.weather.responce.api.weatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApi():weatherApi{
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(weatherApi::class.java)
    }
    @Singleton
    @Provides
    fun Providerepository(api:weatherApi,dao: dao)=Repository(api,dao)

    @Singleton
    @Provides
    fun ProvideDB(context: Application):dao{
        return Room.databaseBuilder(context,WeatherDatabase::class.java,"weatherDB")
            .addTypeConverter(Converter(gsonParser(Gson())))
            .build()
            .Dao()
    }
}