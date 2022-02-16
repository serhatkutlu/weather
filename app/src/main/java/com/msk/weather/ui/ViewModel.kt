package com.msk.weather.ui
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.msk.weather.Repository
import com.msk.weather.Util.Resource
import com.msk.weather.responce.LocalData.DB_Entity
import com.msk.weather.responce.data.weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ViewModel  @Inject constructor( val repository:Repository):ViewModel() {


    private val _weatherResponce =MutableLiveData<DB_Entity>()
    val weatherResponce:LiveData<DB_Entity> =_weatherResponce




    fun GetWeatherInfo(City:String){

        viewModelScope.launch(){
                val a = repository.getCityRepository(City).onEach {
                    when (it){
                    is Resource.Success->{
                        _weatherResponce.value=it.data!!
                        Timber.d(weatherResponce.toString())
                    }
                    is Resource.Error-> Timber.d(it.message)

                }
                }

        }
    }
}