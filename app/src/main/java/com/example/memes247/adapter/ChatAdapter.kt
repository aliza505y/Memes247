package com.example.memes247.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.core.R
import androidx.recyclerview.widget.RecyclerView
import com.example.memes247.adapter.MemesAdapter.ViewHolder
import com.example.memes247.dataclass.ChatDataClass

class ChatAdapter (private val list : ArrayList<ChatDataClass>):
        RecyclerView.Adapter<ChatAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(com.example.memes247.R.layout.rv_chat_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val data = list[position]
        holder.userName.text=data.name
        holder.userText.text=data.text
        holder.time.text=data.time
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val userName: TextView = itemView.findViewById(com.example.memes247.R.id.userName)
        val userText: TextView = itemView.findViewById(com.example.memes247.R.id.userText)
        val time: TextView = itemView.findViewById(com.example.memes247.R.id.textTime)

    }
        }