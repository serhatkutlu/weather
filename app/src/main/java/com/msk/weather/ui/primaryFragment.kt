package com.msk.weather.ui

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat.getSystemService

import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.msk.weather.Adapter.ViewPagerAdapter
import com.msk.weather.R
import com.msk.weather.Service
import com.msk.weather.Util.CheckFirsTimePrefs
import com.msk.weather.Util.CheckInternet
import com.msk.weather.Util.setGetPrefs

import com.msk.weather.databinding.FragmentPrimaryBinding
import com.msk.weather.responce.LocalData.DB_Entity
import timber.log.Timber

class primaryFragment : Fragment(R.layout.fragment_primary) {

    lateinit var binding: FragmentPrimaryBinding
    lateinit var navController:NavController
    lateinit var viewModel: ViewModel
    lateinit var viewPagerAdapter: ViewPagerAdapter

    private var CurrPage=0
    private var checkViewPager=0



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentPrimaryBinding.bind(view)
        navController= NavController(requireContext())
        viewModel=(activity as MainActivity).viewModel

        //if the app is installed for the first time it makes the first call
        if(requireContext().CheckFirsTimePrefs()) {
            viewModel.GetWeatherInfo("london")
            viewModel.allweatherResponce.value?.get(0)?.let {
                viewModel.saveCityToDatabse(it)
            }
        }
        viewModel.getAllWeatherInfo()
        CurrPage=requireContext().setGetPrefs()

        //this function opens the selected city and update differ list
        viewModel.allweatherResponce.observe(viewLifecycleOwner){

            it?.let {data->
                setViewPagerAdapter(it)
                viewPagerListener()
                checkViewPager++

                if (checkViewPager>1) {
                    binding.viewPager2.setCurrentItem(CurrPage,true)
                    checkViewPager=0
                }

            }
        }


        binding.floatingActionButton.setOnClickListener {
            val action=primaryFragmentDirections.actionPrimaryFragmentToListFragment()
            findNavController().navigate(action)

        }


    }



    private fun setViewPagerAdapter(list: List<DB_Entity>) {
        viewPagerAdapter= ViewPagerAdapter(list)
        binding.viewPager2.adapter=viewPagerAdapter
        binding.viewPager2.orientation=ViewPager2.ORIENTATION_HORIZONTAL
        binding.circleIndicator3.setViewPager(binding.viewPager2)

    }

    //this function  gets the name of the selected city
    private fun viewPagerListener() {
        binding.viewPager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.allweatherResponce.value?.let {
                    val city= it.get(binding.viewPager2.currentItem)
                    Service.city.postValue(city.city)
                    requireContext().setGetPrefs(position)

                    }}



        })
    }


}