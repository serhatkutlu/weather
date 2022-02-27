package com.msk.weather.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.msk.weather.Util.DateConverter
import com.msk.weather.Util.loadUrl
import com.msk.weather.databinding.PrimaryFragmentRowBinding
import com.msk.weather.responce.LocalData.DB_Entity
import com.msk.weather.responce.LocalData.LocalDay
import timber.log.Timber
import java.util.*

class ViewPagerAdapter(val list:List<DB_Entity>):RecyclerView.Adapter<ViewPagerAdapter.Page2ViewHolder>(){
    inner class Page2ViewHolder(val binding:PrimaryFragmentRowBinding):RecyclerView.ViewHolder(binding.root) {

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Page2ViewHolder {
        val binding= PrimaryFragmentRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Page2ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Page2ViewHolder, position: Int) {
        val pos=list.get(position)

        holder.binding.apply {
            CityName.text=pos.city
            mainDayName.text=DateConverter.ConvertDate(pos.days[0].date)
            mainimage.loadUrl("https:"+pos.currDay.icon_url)
            CityDegree.text=pos.currDay.temp_c.toString()+"°"
            mainInfo.text=pos.currDay.info
            this.dayDegree1.text=pos.days[0].temp_c.toString()+"°"
            this.dayDegree2.text=pos.days[1].temp_c.toString()+"°"
            this.dayDegree3.text=pos.days[2].temp_c.toString()+"°"

            this.dayImage1.loadUrl("https:"+pos.days[0].icon_url)
            this.dayImage2.loadUrl("https:"+pos.days[1].icon_url)
            this.dayImage3.loadUrl("https:"+pos.days[2].icon_url)

            this.dayName1.text=DateConverter.ConvertDate(pos.days[0].date)
            this.dayName2.text=DateConverter.ConvertDate(pos.days[1].date)
            this.dayName3.text=DateConverter.ConvertDate(pos.days[2].date)

            this.dayInfo1.text=pos.days[0].info
            this.dayInfo2.text=pos.days[1].info
            this.dayInfo3.text=pos.days[2].info

    }}

    override fun getItemCount(): Int {
        return list.size    }
}