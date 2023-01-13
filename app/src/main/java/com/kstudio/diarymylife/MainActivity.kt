package com.kstudio.diarymylife

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kstudio.diarymylife.databinding.ActivityMainBinding
import com.kstudio.diarymylife.ui.adapter.ViewPagerAdapter
import com.kstudio.diarymylife.ui.create.CreateJournalActivity
import com.kstudio.diarymylife.ui.home.HomeFragment
import com.kstudio.diarymylife.ui.list.ListJournalFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewPager()
        setupNavigationBar()
    }

    private fun setupViewPager() {
        val fragment: ArrayList<Fragment> = arrayListOf(
            HomeFragment(),
            ListJournalFragment(),
        )

        binding.viewPager.apply {
            isUserInputEnabled = false
            adapter = ViewPagerAdapter(fragment, this@MainActivity)
            offscreenPageLimit = 4  // make sure left/right item is rendered
        }
    }

    private fun setupNavigationBar() {
        binding.bottomNavigation.apply {
            background = null
        }

        binding.fab.setOnClickListener {
            val intent = Intent(this, CreateJournalActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_chart -> {
                    binding.viewPager.currentItem = 2
                    return@setOnItemSelectedListener true
                }
                else -> {
                    binding.viewPager.currentItem = 0
                    return@setOnItemSelectedListener true
                }
            }
        }
    }
}