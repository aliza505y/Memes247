package com.example.memes247

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.example.memes247.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var pagerr: ViewPager
    private lateinit var tab: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.chat.setOnClickListener {
            startActivity(Intent(this,ChatActivity::class.java))
        }

        pagerr = findViewById<ViewPager>(R.id.pager)
        tab = findViewById<TabLayout>(R.id.tab_layout)
        tab.tabIconTint= ContextCompat.getColorStateList(this,R.color.tab_icon_selector)


        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MemeFragment())
        adapter.addFragment(SortedMemeFragment())
        adapter.addFragment(ShuffleFragment())
        adapter.addFragment(MenuFragment())
        pagerr.adapter=adapter
        tab.setupWithViewPager(pagerr)
        tab.getTabAt(0)?.setIcon(R.drawable.burn)
        tab.getTabAt(1)?.setIcon(R.drawable.grid)
        tab.getTabAt(2)?.setIcon(R.drawable.shuffle)
        tab.getTabAt(3)?.setIcon(R.drawable.menu)


    }
}