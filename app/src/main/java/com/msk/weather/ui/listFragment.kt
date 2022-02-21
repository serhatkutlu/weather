package com.msk.weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import timber.log.Timber


class listFragment : Fragment(R.layout.fragment_list) {

    lateinit var binding: FragmentListBinding
     lateinit var viewModel:ViewModel
     lateinit var RecycleAdapter: RecycleAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentListBinding.bind(view)
        viewModel=(activity as MainActivity ).viewModel
      SetupRecyclerAdapter()
        viewModel.getAllWeatherInfo()
        viewModel.allweatherResponce.observe(viewLifecycleOwner, Observer {
            RecycleAdapter.differ.submitList(it)
        })

        binding.editTextTextPersonName.addTextChangedListener {
            searching(it.toString())
        }


    }

    private fun searching(text:String?=null) {
        val job=Job()
        job.cancel()
        MainScope().launch(job+Dispatchers.Main) {
            delay(500L)
            text?.let {
                if(text.isNotEmpty()){
                    viewModel.GetWeatherInfo(text)
                }
            }
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