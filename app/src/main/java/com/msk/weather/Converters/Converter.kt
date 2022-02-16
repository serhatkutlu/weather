package com.msk.weather.Converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.msk.weather.responce.LocalData.LocalDay


@ProvidedTypeConverter
class Converter(private val jsonParser: jsonParser) {

    @TypeConverter
    fun fromLocalDayJson(json:String):List<LocalDay>{
        return jsonParser.fromJson<ArrayList<LocalDay>>(json,object :TypeToken<ArrayList<LocalDay>>(){}.type) ?: emptyList()
    }

    @TypeConverter
    fun toLocalDayJson(days:List<LocalDay>):String{
        return jsonParser.toJson(days,
            object :TypeToken<ArrayList<LocalDay>>(){}.type) ?: "[]"
    }
}