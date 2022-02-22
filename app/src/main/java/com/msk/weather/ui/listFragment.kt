package com.msk.weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.msk.weather.Adapter.RecycleAdapter

import com.msk.weather.R
import com.msk.weather.databinding.ActivityMainBinding
import com.msk.weather.databinding.FragmentListBinding
import kotlinx.coroutines.*
import okhttp3.internal.wait
import timber.log.Timber
import java.util.ArrayList


class listFragment : Fragment(R.layout.fragment_list) {

    private lateinit var binding: FragmentListBinding
     private lateinit var viewModel:ViewModel
     private lateinit var RecycleAdapter: RecycleAdapter

     private var isSearching=false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentListBinding.bind(view)
        viewModel=(activity as MainActivity ).viewModel

        SetupRecyclerAdapter()
        RecycleAdapter.SetOnItemClickListener {
            viewModel.saveCityToDatabse(it)
        }

        viewModel.getAllWeatherInfo()
        viewModel.allweatherResponce.observe(viewLifecycleOwner) {
                RecycleAdapter.differ.submitList(it)
        }



        binding.editTextTextPersonName.addTextChangedListener {
            if (it.toString().isNotEmpty())
            {
            searching(it.toString())}
            else{
                viewModel.getAllWeatherInfo()
                Timber.d(viewModel.allweatherResponce.value?.size.toString())
                Timber.d("1111111111111111111111")
            }
        }


    }

    private fun searching(text:String) {
        var job:Job?=null
        job?.let { it.cancel() }
        job= Job()
        MainScope().launch(job+Dispatchers.Unconfined) {
            delay(500L)
            viewModel.GetWeatherInfo(text)
        }


    }

    fun SetupRecyclerAdapter() {
        RecycleAdapter= RecycleAdapter()
        binding.ListRecycler.apply {
            this.adapter=RecycleAdapter
            layoutManager=LinearLayoutManager(activity)

        }
    }


}