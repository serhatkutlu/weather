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

import com.msk.weather.databinding.FragmentPrimaryBinding
import com.msk.weather.responce.LocalData.DB_Entity

class primaryFragment : Fragment(R.layout.fragment_primary) {

    lateinit var binding: FragmentPrimaryBinding
    lateinit var navController:NavController
    lateinit var viewModel: ViewModel
    lateinit var viewPagerAdapter: ViewPagerAdapter
    var currPage:Int=0
    val mHandler =Handler()
    lateinit var runnable:Runnable
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentPrimaryBinding.bind(view)
        navController= NavController(requireContext())
        viewModel=(activity as MainActivity).viewModel
        viewModel.getAllWeatherInfo()



        //this function opens the selected city and update differ list
        viewModel.allweatherResponce.observe(viewLifecycleOwner){
            it?.let {data->
                setViewPagerAdapter(it)
                viewPagerListener()
                currPage=arguments?.getInt("page")!!
                runnable= Runnable {
                    binding.viewPager2.setCurrentItem(currPage,true)
                }
                mHandler.postDelayed(runnable,50)


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
                    }}



        })
    }


}