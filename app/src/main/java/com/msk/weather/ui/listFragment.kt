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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msk.weather.Adapter.RecycleAdapter
import com.msk.weather.Adapter.SwipeToDelete

import com.msk.weather.R
import com.msk.weather.databinding.ActivityMainBinding
import com.msk.weather.databinding.FragmentListBinding
import kotlinx.coroutines.*
import okhttp3.internal.wait
import org.w3c.dom.Entity
import timber.log.Timber
import java.util.ArrayList


class listFragment : Fragment(R.layout.fragment_list) {

    private lateinit var binding: FragmentListBinding
     private lateinit var viewModel:ViewModel
     private lateinit var RecycleAdapter: RecycleAdapter
    private var job:Job?=null
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
                searching(it.toString())
            }
            else{
                job?.let { it.cancel() }
                viewModel.getAllWeatherInfo()
            }
        }
    }
    private fun searching(text:String) {

        job?.let { it.cancel() }
        job= Job()
        MainScope().launch(job as CompletableJob +Dispatchers.IO) {
            delay(500L)
            viewModel.GetWeatherInfo(text)
        }


    }

    private fun SetupRecyclerAdapter() {
        RecycleAdapter= RecycleAdapter()
        binding.ListRecycler.apply {
            this.adapter=RecycleAdapter
            layoutManager=LinearLayoutManager(activity)
            itemTouchFun()

        }
    }

    private fun itemTouchFun(){
        val item=object:SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                DeleteCity(viewHolder.adapterPosition)
            }
        }
        ItemTouchHelper(item).attachToRecyclerView(binding.ListRecycler)

    }
    private fun DeleteCity(adapterPos:Int){
        val entity=RecycleAdapter.deleteItem(adapterPos)
        viewModel.deleteCityInDatabase(entity)
        viewModel.getAllWeatherInfo()
    }

}