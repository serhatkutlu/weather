package com.msk.weather.ui

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import com.msk.weather.Service
import com.msk.weather.Util.CheckInternet
import com.msk.weather.Util.makeToast
import com.msk.weather.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var  binding:ActivityMainBinding
    val viewModel:ViewModel by viewModels()
    val mHandler = Handler()
    lateinit var runnable:Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startService(Intent(this,Service::class.java))

    }

    override fun onStart() {
        super.onStart()
        runnable=Runnable {
            val isInternet=CheckInternet.Start(application)

            if (!isInternet){
                applicationContext.makeToast("Veriler Güncellenemedi")
            }
        }
        mHandler.postDelayed(runnable,1000)


    }
}