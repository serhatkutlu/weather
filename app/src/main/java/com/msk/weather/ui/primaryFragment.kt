package com.msk.weather.ui

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast

import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.msk.weather.Adapter.ViewPagerAdapter
import com.msk.weather.R
import com.msk.weather.Util.DateConverter
import com.msk.weather.databinding.FragmentPrimaryBinding
import timber.log.Timber


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

        viewModel.allweatherResponce.observe(viewLifecycleOwner){
            it?.let {data->
                viewPagerAdapter= ViewPagerAdapter(data)
                binding.viewPager2.adapter=viewPagerAdapter
                binding.viewPager2.orientation=ViewPager2.ORIENTATION_HORIZONTAL
                binding.circleIndicator3.setViewPager(binding.viewPager2)
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


}