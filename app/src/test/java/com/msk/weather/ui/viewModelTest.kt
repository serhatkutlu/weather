package com.msk.weather.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.msk.weather.FakeRepository.FakeRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.msk.weather.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class viewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

     lateinit var viewmodel:ViewModel
     lateinit var fakeRepository: FakeRepository

    @Before
    fun first(){
        fakeRepository=FakeRepository()
        viewmodel= ViewModel(fakeRepository)
    }

    @Test
    fun `get all data ,return null`(){


        val City=viewmodel.allweatherResponce.value
        assertThat(City).isEqualTo(null)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Get Weather Info and save to database and delete ,return data size`(){

        viewmodel.GetWeatherInfo("london")
        val data= viewmodel.allweatherResponce.value?.get(0)
        data?.let{
            viewmodel.saveCityToDatabse(data)
        }
        viewmodel.GetWeatherInfo("paris")
        val data2= viewmodel.allweatherResponce.value?.get(0)
        data2?.let {
            viewmodel.saveCityToDatabse(data2)
        }

        assertThat(fakeRepository.fakeDatabase.size).isEqualTo(2)

        data2?.let {
            viewmodel.deleteCityInDatabase(data2)
        }
        assertThat(fakeRepository.fakeDatabase.size).isEqualTo(1)



    }








}