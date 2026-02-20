package com.example.memes247

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.memes247.adapter.SortedAdapter
import com.example.memes247.dataclass.SortedDataClass
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SortedMemeFragment : Fragment() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter: SortedAdapter
    private val list = ArrayList<SortedDataClass>()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_sorted_meme, container, false)




        fetchCategories()
        recyclerView = view.findViewById<RecyclerView>(R.id.sortedMemes_recyclerView)
        adapter= SortedAdapter(list)
        recyclerView.adapter=adapter
        return view
    }

    private fun fetchCategories(){
        database.getReference("Categories").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (snap in snapshot.children){
                    val data = snap.getValue(SortedDataClass::class.java)
                    list.add(data!!)
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to fetch", Toast.LENGTH_SHORT).show()
            }

        })

    }

}