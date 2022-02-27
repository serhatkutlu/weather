package com.msk.weather

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.os.Handler
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.msk.weather.Repository.Repository
import com.msk.weather.Util.Constants
import com.msk.weather.Util.Resource
import com.msk.weather.responce.LocalData.DB_Entity
import com.msk.weather.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.internal.wait
import timber.log.Timber
import java.lang.Exception
import java.lang.Runnable
import javax.inject.Inject
import kotlin.system.measureTimeMillis


@AndroidEntryPoint
class Service:LifecycleService() {


    companion object{
        val city:MutableLiveData<String?> =MutableLiveData(null)
    }

    @Inject
    lateinit var Repository: Repository
    lateinit var runnable: Runnable
    private val handler= Handler()
    private  var  serviceJob=Job()
    private  var  serviceScope= CoroutineScope(serviceJob+Dispatchers.IO)
    private   var  job:Job?=null
    private  var responce: DB_Entity? =null
    private  var icon: Bitmap?=null



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        city.observe(this) {
            it.let {
                setupNotification(it)
            }
        }

            return super.onStartCommand(intent, flags, startId)
        }



    fun setupNotification(city:String?=null){

        job?.let {
            it.cancel()
        }
        city?.let {
            runnable = Runnable {
               job= CoroutineScope(Job()).launch{
                 getData(city)

                   Timber.d("44")
                   handler.postDelayed(runnable,3600000)

               }


            }
            handler.postDelayed(runnable, 1000)
        }
    }


    private fun startNotification() {
        val data=responce?.currDay!!
        val intent=Intent(this,MainActivity::class.java)
        val pendingIntent=PendingIntent.getActivity(this,0,intent,0)


        val notification=NotificationCompat.Builder(this, Constants.CHANNEL_ID)
            .setContentTitle("${responce?.city?.uppercase()}")
            .setLargeIcon(icon)
            .setSmallIcon(R.drawable.ic_outline_air_24)
            .setContentText("${data.temp_c}°      ${data.info.uppercase()}")
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1,notification)

    }


    private fun setupicon() {
        serviceScope.launch{
            try {
                Timber.d(responce?.currDay?.icon_url)
                icon = Glide.with(this@Service).asBitmap().load("https:"+responce?.currDay?.icon_url).submit().get()


                startNotification()
            } catch (e: Exception) {
                Timber.d(e)
            }
        }
    }

    private  fun getData(city:String) {


        serviceScope.launch{
                Repository.getCityRepository(city).onEach {
                    when (it) {
                        is Resource.Success -> {
                            it.data?.let { data ->
                                responce=data
                                setupicon()
                            }
                        }
                        is Resource.Error -> Timber.d(it.message)
                        is Resource.Loading -> Timber.d("ww")
                    }
                }.launchIn(this)

            }


    }


 }