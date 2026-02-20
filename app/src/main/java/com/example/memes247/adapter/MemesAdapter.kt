package com.example.memes247.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.memes247.CommentsActivity
import com.example.memes247.R
import com.example.memes247.dataclass.MemesDataClass

class MemesAdapter (private val list: ArrayList<MemesDataClass>) :
    RecyclerView.Adapter<MemesAdapter.ViewHolder>( ){
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val memeImage: ImageView = itemView.findViewById(R.id.memeImage)
            val comments: TextView = itemView.findViewById(R.id.comments)


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MemesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.memes_rv_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemesAdapter.ViewHolder, position: Int) {
        val data = list[position]
        Glide.with(holder.itemView.context).load(data.imageUrl).into(holder.memeImage)
        holder.comments.setOnClickListener {
            val intent = Intent(holder.itemView.context, CommentsActivity::class.java)
            holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}