package com.example.memes247.adapter

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
import com.jackandphantom.androidlikebutton.AndroidLikeButton

class MemesAdapter(private val list: ArrayList<MemesDataClass>) :
    RecyclerView.Adapter<MemesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val memeImage: ImageView = itemView.findViewById(R.id.memeImage)
        val comments: TextView = itemView.findViewById(R.id.comments)
        val likeButton: AndroidLikeButton = itemView.findViewById(R.id.likeButton)
        val tvLikeCount: TextView = itemView.findViewById(R.id.tvLikeCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.memes_rv_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        
        // Load Image
        Glide.with(holder.itemView.context).load(data.imageUrl).into(holder.memeImage)

        // Set initial like count text
        holder.tvLikeCount.text = data.likesCount.toString()

        // Handle the Like Button Animation and Count
        holder.likeButton.setOnLikeEventListener(object : AndroidLikeButton.OnLikeEventListener {
            override fun onLikeClicked(androidLikeButton: AndroidLikeButton) {
                if (!data.isLiked) {
                    data.isLiked = true
                    data.likesCount += 1
                    holder.tvLikeCount.text = data.likesCount.toString()
                }
            }

            override fun onUnlikeClicked(androidLikeButton: AndroidLikeButton) {
                if (data.isLiked) {
                    data.isLiked = false
                    data.likesCount -= 1
                    holder.tvLikeCount.text = data.likesCount.toString()
                }
            }
        })

        // Comments click listener
        holder.comments.setOnClickListener {
            val intent = Intent(holder.itemView.context, CommentsActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
