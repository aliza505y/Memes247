package com.example.memes247

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.memes247.adapter.CommentAdapter
import com.example.memes247.databinding.ActivityCommentsBinding
import com.example.memes247.dataclass.CommentDataClass

class CommentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommentsBinding
    private lateinit var adapter: CommentAdapter
    private lateinit var list : ArrayList<CommentDataClass>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        list = ArrayList()
        recyclerView = binding.commentsRecyclerView
        adapter = CommentAdapter(list)
        recyclerView.adapter = adapter
        binding.backBtnComment.setOnClickListener {
            finish()
        }
        list.add(CommentDataClass("~Monica","So true"))
        list.add(CommentDataClass("~jwa","So funny"))
        list.add(CommentDataClass("~bleda","Hey, Any body is here right now"))
        list.add(CommentDataClass("~peda","What's going on?"))
        list.add(CommentDataClass("~Monica","Well fun!"))
        list.add(CommentDataClass("~Monica","legacy"))
        list.add(CommentDataClass("~Monica","So true"))


    }
}