package com.msk.weather.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.msk.weather.databinding.FragmentListBinding
import com.msk.weather.databinding.ListFragmentRowBinding
import com.msk.weather.responce.LocalData.DB_Entity
import com.msk.weather.ui.listFragment

class RecycleAdapter :RecyclerView.Adapter<RecycleAdapter.Holder>() {
    inner class Holder(val binding: ListFragmentRowBinding):RecyclerView.ViewHolder(binding.root)  {

    }

    private val differCallback=object: DiffUtil.ItemCallback<DB_Entity>(){
        override fun areItemsTheSame(oldItem: DB_Entity, newItem: DB_Entity): Boolean {

            return  oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: DB_Entity, newItem: DB_Entity): Boolean {

            return oldItem==newItem
        }

    }
    val differ= AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val binding=ListFragmentRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val list=differ.currentList.get(position)
        holder.binding.apply {
            CityName.text=list.city
            temperature.text=list.currDay.temp_c.toString()
            info.text=list.currDay.info
            holder.itemView.setOnClickListener {
                    onItemClickListener?.let {
                        it(list)
                    }
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun deleteItem(pos:Int):DB_Entity{
        return differ.currentList.get(pos)

    }

    private var onItemClickListener:((DB_Entity)->Unit)?=null


    fun SetOnItemClickListener(listener:(DB_Entity)->Unit){
       onItemClickListener=listener
    }



}