package com.msk.weather.Util

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

fun ImageView.loadUrl(url:String){
    Glide.with(context)
        .load(url)
        .into(this)
}
fun Context.makeToast(text:String="Toast"){
    Toast.makeText(this,text,Toast.LENGTH_LONG).show()
}