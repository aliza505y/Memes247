package com.example.memes247.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.memes247.R
import com.example.memes247.dataclass.CommentDataClass

class CommentAdapter (private val list: ArrayList<CommentDataClass>) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>( ){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comments_rv_design, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val data= list[position]
        holder.user.text = data.user_id
        holder.comments.text = data.user_comment
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val user: TextView = itemView.findViewById(R.id.user_name_comments)
        val comments: TextView = itemView.findViewById(R.id.user_comment)
    }

    }