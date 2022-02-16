package com.msk.weather.Util

import java.lang.reflect.Type


interface jsonParser {

    fun <T> fromJson(json:String,type: Type): T?

    fun <T> toJson(obj:T,type:Type):String?
}