package com.msk.weather.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

import com.msk.weather.R
import com.msk.weather.databinding.ActivityMainBinding
import com.msk.weather.databinding.FragmentListBinding


class listFragment : Fragment(R.layout.fragment_list) {

    lateinit var binding: FragmentListBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentListBinding.inflate(layoutInflater)

    }


}