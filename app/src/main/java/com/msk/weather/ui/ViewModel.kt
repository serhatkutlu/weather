package com.msk.weather.ui
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.msk.weather.Repository.IRepository
import com.msk.weather.Util.Resource
import com.msk.weather.responce.LocalData.DB_Entity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ViewModel  @Inject constructor( val repository: IRepository):ViewModel() {




    private var _allweatherResponce = MutableLiveData<List<DB_Entity>>()
    val allweatherResponce:LiveData<List<DB_Entity>> = _allweatherResponce


    fun deleteCityInDatabase(dbEntity: DB_Entity){
        viewModelScope.launch {
            repository.deleteCityInDatabase(dbEntity)
        }
    }
    fun saveCityToDatabse(dbEntity: DB_Entity){
        viewModelScope.launch() {
            repository.saveCityToDatabase(dbEntity).onEach {
                if (it is Resource.Error){
                    Timber.d(it.message)
                }
            }.launchIn(this)
        }
    }
    fun GetWeatherInfo(City:String){
        viewModelScope.launch(){

                repository.getCityRepository(City).onEach {
                    when (it){
                    is Resource.Success->{
                        it.data?.let {data->
                            _allweatherResponce.postValue(listOf())
                            _allweatherResponce.postValue(listOf(it.data))

                        }
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
                        Timber.d(it.data.size.toString())
                    }
                    is Resource.Error->  Timber.d(it.message)
                }

            }.launchIn(this)
        }
    }
}