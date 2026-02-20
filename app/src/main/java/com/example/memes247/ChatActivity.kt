package com.example.memes247

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.memes247.adapter.ChatAdapter
import com.example.memes247.databinding.ActivityChatBinding
import com.example.memes247.dataclass.ChatDataClass

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatAdapter
    private var list = ArrayList<ChatDataClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.backBtnChat.setOnClickListener {
            finish()
        }

        recyclerView=findViewById<RecyclerView>(R.id.rvChat)
        adapter= ChatAdapter(list)
        recyclerView.adapter=adapter
        list.add(ChatDataClass("~jw","Hi, I'm probably sleeping later...","about one year ago"))
        list.add(ChatDataClass("~jw","Hi, I'm probably sleeping later...","about one year ago"))
        list.add(ChatDataClass("~jw","Hi, I'm probably sleeping later...","about one year ago"))
        list.add(ChatDataClass("~jw","Hi, I'm probably sleeping later...","about one year ago"))
        list.add(ChatDataClass("~jw","Hi, I'm probably sleeping later...","about one year ago"))
        list.add(ChatDataClass("~jw","Hi, I'm probably sleeping later...","about one year ago"))
        list.add(ChatDataClass("~jw","Hi, I'm probably sleeping later...","about one year ago"))
        list.add(ChatDataClass("~jw","Hi, I'm probably sleeping later...","about one year ago"))
        list.add(ChatDataClass("~jw","Hi, I'm probably sleeping later...","about one year ago"))
        list.add(ChatDataClass("~jw","Hi, I'm probably sleeping later...","about one year ago"))


    }
}