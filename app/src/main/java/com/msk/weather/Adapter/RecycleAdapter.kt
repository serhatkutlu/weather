package com.msk.weather.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.msk.weather.databinding.FragmentListBinding
import com.msk.weather.databinding.ListFragmentRowBinding
import com.msk.weather.responce.LocalData.DB_Entity
import com.msk.weather.ui.listFragment
import timber.log.Timber

class RecycleAdapter :RecyclerView.Adapter<RecycleAdapter.Holder>() {
    inner class Holder(val binding: ListFragmentRowBinding):RecyclerView.ViewHolder(binding.root)  {

    }

    private val differCallback=object: DiffUtil.ItemCallback<DB_Entity>(){
        override fun areItemsTheSame(oldItem: DB_Entity, newItem: DB_Entity): Boolean {

            return  oldItem.city==newItem.city
        }

        override fun areContentsTheSame(oldItem: DB_Entity, newItem: DB_Entity): Boolean {

            return oldItem==newItem
        }

    }
    val differ= AsyncListDiffer(this,differCallback)
    var pos=0
    var isSerarching=false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val binding=ListFragmentRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val list=differ.currentList.get(position)
        holder.binding.apply {
            CityName.text=list.city
            temperature.text=list.currDay.temp_c.toString()+"°"
            info.text=list.currDay.info
            if (isSerarching){
                deleteButton.visibility=View.INVISIBLE
            }
            else{
                deleteButton.visibility=View.VISIBLE
            }
            holder.binding.deleteButton.setOnClickListener{view->
                deleteItemClickListener?.let {
                    it(list)
                }
            }
            holder.itemView.setOnClickListener {
                    onItemClickListener?.let {
                        pos=position
                        it(list)
                    }
            }

        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }



    private var onItemClickListener:((DB_Entity)->Unit)?=null


    fun SetOnItemClickListener(listener:(DB_Entity)->Unit){
       onItemClickListener=listener
    }


    private var deleteItemClickListener:((DB_Entity)->Unit)?=null

    fun SetDeleteButtonListener(listener:(DB_Entity)->Unit){
        deleteItemClickListener=listener

    }



}