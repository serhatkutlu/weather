package com.msk.weather.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.msk.weather.R
import com.msk.weather.databinding.FragmentPrimaryBinding
import timber.log.Timber


class primaryFragment : Fragment(R.layout.fragment_primary) {

    lateinit var binding: FragmentPrimaryBinding
    lateinit var navController:NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentPrimaryBinding.bind(view)
        navController= NavController(requireContext())



        binding.textView.text="sdadad"
        binding.floatingActionButton.setOnClickListener {
            val action=primaryFragmentDirections.actionPrimaryFragmentToListFragment()
            findNavController().navigate(action)

        }


    }


}