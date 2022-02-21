package com.msk.weather.ui
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.msk.weather.Repository
import com.msk.weather.Util.Resource
import com.msk.weather.responce.LocalData.DB_Entity
import com.msk.weather.responce.data.weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ViewModel  @Inject constructor( val repository:Repository):ViewModel() {


    private val _weatherResponce =MutableLiveData<DB_Entity>()
    val weatherResponce:LiveData<DB_Entity> =_weatherResponce

    private val _allweatherResponce = MutableLiveData<List<DB_Entity>>()
    val allweatherResponce:LiveData<List<DB_Entity>> = _allweatherResponce



    fun GetWeatherInfo(City:String){
        viewModelScope.launch(){

                repository.getCityRepository(City).onEach {
                    when (it){
                    is Resource.Success->{
                        _weatherResponce.value=it.data!!

                    }
                    is Resource.Error-> Timber.d(it.message)
                    is Resource.Loading->Timber.d("ww")
                }
                }.launchIn(this)

        }
    }

    fun getAllWeatherInfo(){
        viewModelScope.launch() {
            repository.getAllCityRepository().onEach {
                when(it){
                    is Resource.Success->{

                        _allweatherResponce.value=it.data!!
                    }
                    is Resource.Error->  Timber.d(it.message)
                }

            }.launchIn(this)
        }
    }
}