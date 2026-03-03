package com.example.memes247

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memes247.adapter.MemesAdapter
import com.example.memes247.dataclass.MemesDataClass
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MemeFragment : Fragment() {
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MemesAdapter
    private val list = ArrayList<MemesDataClass>()
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_meme, container, false)

        database = FirebaseDatabase.getInstance()
        recyclerView = view.findViewById(R.id.memes_recycleView)
        
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        
        adapter = MemesAdapter(list)
        recyclerView.adapter = adapter

        fetchMemes()

        return view
    }

    private fun fetchMemes() {
        database.getReference("AllMemes")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val imageUrl = snap.getValue(String::class.java)
                            if (imageUrl != null) {
                                list.add(MemesDataClass(imageUrl = imageUrl, likesCount = 0, isLiked = false))
                            }
                        }
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(requireContext(), "No memes found", Toast.LENGTH_SHORT).show()
                    }
                }
                
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Failed to fetch memes", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
