package com.msk.weather.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.msk.weather.R
import com.msk.weather.databinding.FragmentPrimaryBinding


class primaryFragment : Fragment(R.layout.fragment_primary) {

    lateinit var binding: FragmentPrimaryBinding
    lateinit var navController:NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding= FragmentPrimaryBinding.inflate(layoutInflater)
        super.onViewCreated(binding.root, savedInstanceState)

        navController= NavController(requireContext())
        binding.floatingActionButton.setOnClickListener {
            val action=primaryFragmentDirections.actionPrimaryFragmentToListFragment()
            findNavController().navigate(action)
        }


    }


}