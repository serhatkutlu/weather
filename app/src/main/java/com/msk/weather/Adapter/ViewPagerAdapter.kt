package com.msk.weather.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msk.weather.databinding.PrimaryFragmentRowBinding
import com.msk.weather.responce.LocalData.DB_Entity
import com.msk.weather.responce.LocalData.LocalDay

class ViewPagerAdapter(var list: List<DB_Entity>):RecyclerView.Adapter<ViewPagerAdapter.Page2ViewHolder>(){
    inner class Page2ViewHolder(val binding:PrimaryFragmentRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Page2ViewHolder {
        val binding= PrimaryFragmentRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Page2ViewHolder(binding)
        //return Page2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.primary_fragment_row,parent,false))
    }

    override fun onBindViewHolder(holder: Page2ViewHolder, position: Int) {
        val pos=list.get(position)
        holder.binding.apply {
            CityName.text=pos.city
            CityDegree.text=pos.currDay.temp_c.toString()
        }
    }

    override fun getItemCount(): Int {
        return list.size    }
}