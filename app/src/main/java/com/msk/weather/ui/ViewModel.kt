package com.msk.weather.ui
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.weather.Repository
import com.msk.weather.Util.Resource
import com.msk.weather.responce.LocalData.DB_Entity
import com.msk.weather.responce.data.weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ViewModel  @Inject constructor( val repository:Repository):ViewModel() {


    private val _weatherResponce =MutableLiveData<DB_Entity>()
    val weatherResponce:LiveData<DB_Entity> =_weatherResponce


    fun GetWeatherInfo(){

        viewModelScope.launch(){
                val a=repository.getPokemonRepository()
                when (a){
                    is Resource.Success->{
                        a.data?.let {
                            _weatherResponce.value=a.data!!
                            Timber.d(a.data!!.days.get(1).info)
                        }}
                    is Resource.Error-> Timber.d("Error")
                }
        }
    }
}