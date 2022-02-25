package com.msk.weather.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msk.weather.Adapter.RecycleAdapter
import com.msk.weather.Adapter.SwipeToDelete

import com.msk.weather.R
import com.msk.weather.databinding.ActivityMainBinding
import com.msk.weather.databinding.FragmentListBinding
import com.msk.weather.responce.LocalData.LocalDay
import kotlinx.coroutines.*
import okhttp3.internal.wait
import org.w3c.dom.Entity
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.time.Duration.Companion.days
import kotlin.time.days


class listFragment : Fragment(R.layout.fragment_list) {

    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel:ViewModel
    private lateinit var RecycleAdapter: RecycleAdapter
    private var job:Job?=null
    private var isSearching=false
    private var lastlistSize=0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentListBinding.bind(view)
        viewModel=(activity as MainActivity ).viewModel

        SetupRecyclerAdapter()



        RecycleAdapter.SetOnItemClickListener {
            var pos=0
            if (isSearching){
                viewModel.saveCityToDatabse(it)
                Timber.d(lastlistSize.toString())
                pos=lastlistSize
            }
            else {
                pos=RecycleAdapter.pos
            }
            val action=listFragmentDirections.actionListFragmentToPrimaryFragment(pos)
            findNavController().navigate(action)
        }

        viewModel.getAllWeatherInfo()
        viewModel.allweatherResponce.observe(viewLifecycleOwner) {
            RecycleAdapter.differ.submitList(it)
            if (it.size>1){
                lastlistSize=it.size
            }
        }


        binding.editTextTextPersonName.addTextChangedListener {
            if (it.toString().isNotEmpty())
            {isSearching=true
                searching(it.toString())
            }
            else{
                isSearching=false
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