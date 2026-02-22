package com.example.memes247

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.memes247.adapter.ChatAdapter
import com.example.memes247.databinding.ActivityChatBinding
import com.example.memes247.dataclass.ChatDataClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatAdapter
    private var list = ArrayList<ChatDataClass>()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val auth= FirebaseAuth.getInstance()


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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        binding.backBtnChat.setOnClickListener {
            finish()
        }

        binding.btnSend.setOnClickListener {
            sendMessage()
        }

        recyclerView=findViewById(R.id.rvChat)
        adapter= ChatAdapter(list)
        recyclerView.adapter=adapter

        readMessages()
    }

    /*private fun sendMessage() {
        val currentUser = auth.currentUser ?: return
        val messageText = binding.etMessage.text.toString().trim()
        if (messageText.isEmpty()) {
            Toast.makeText(this, "Enter message", Toast.LENGTH_SHORT).show()
            return
        }
        val senderName = currentUser.displayName ?: "Unknown"
        val timestamp = System.currentTimeMillis()
        val data = ChatDataClass(messageText, senderName, timestamp)
        val chatRoomId = currentUser.uid
        database.reference.child("chats").child(chatRoomId).push().setValue(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Message sent successfully", Toast.LENGTH_SHORT).show()
                binding.etMessage.text.clear()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show()
            }
    }*/

    private fun sendMessage() {
        val currentUser = auth.currentUser ?: return
        val messageText = binding.etMessage.text.toString().trim()
        if (messageText.isEmpty()) {
            Toast.makeText(this, "Enter message", Toast.LENGTH_SHORT).show()
            return
        }

        val senderName = currentUser.displayName ?: "Unknown"

        // Get current time as formatted string
        val timestamp = java.text.SimpleDateFormat(
            "hh:mm a, dd MMM yyyy",
            java.util.Locale.getDefault()
        ).format(java.util.Date())

        val data = ChatDataClass(messageText, senderName, timestamp)

        val chatRoomId = currentUser.uid

        database.reference
            .child("chats")
            .child(chatRoomId)
            .push()
            .setValue(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Message sent successfully", Toast.LENGTH_SHORT).show()
                binding.etMessage.text.clear()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show()
            }
    }
    private fun readMessages(){
        val currentUser = auth.currentUser ?: return
        val senderId = currentUser.uid
        val chatRoomId = senderId
        database.reference.child("chats").child(chatRoomId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (dataSnapshot in snapshot.children){
                    val data = dataSnapshot.getValue(ChatDataClass::class.java)
                    list.add(data!!)
                    adapter.notifyDataSetChanged()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ChatActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }


}