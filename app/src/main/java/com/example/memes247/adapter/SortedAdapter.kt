package com.example.memes247.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.memes247.R
import com.example.memes247.dataclass.SortedDataClass

class SortedAdapter (private val listed : ArrayList<SortedDataClass>):
        RecyclerView.Adapter<SortedAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sorted_memes_rv_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val data = listed[position]
        holder.categoryName.text = data.categoryName
    }

    override fun getItemCount(): Int {
        return listed.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
                val categoryName : TextView = itemView.findViewById(R.id.categoryName)

            }

        }