package com.msk.weather.Util

import android.content.Context
import android.content.SharedPreferences

import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import timber.log.Timber

fun ImageView.loadUrl(url:String){
    Glide.with(context)
        .load(url)
        .into(this)
}
fun Context.makeToast(text:String="Toast"){
    Toast.makeText(this,text,Toast.LENGTH_LONG).show()
}

fun Context.setGetPrefs(value:Int?=null):Int{
     val APP_PREF_INT_EXAMPLE = "WeatherPref"

     val preferences: SharedPreferences = this.getSharedPreferences(APP_PREF_INT_EXAMPLE,Context.MODE_PRIVATE)
    value?.let {
        preferences.edit().putInt(APP_PREF_INT_EXAMPLE, value).apply()

    }
    return  preferences.getInt(APP_PREF_INT_EXAMPLE, 0)
}

fun Context.CheckFirsTimePrefs():Boolean{
    val APP_PREF_INT_EXAMPLE = "FirsTime"

    val preferences: SharedPreferences = this.getSharedPreferences(APP_PREF_INT_EXAMPLE,Context.MODE_PRIVATE)

    if (preferences.getInt(APP_PREF_INT_EXAMPLE, 0)==0){
        preferences.edit().putInt(APP_PREF_INT_EXAMPLE, 1).apply()
        return true
    }
    else return false


}

