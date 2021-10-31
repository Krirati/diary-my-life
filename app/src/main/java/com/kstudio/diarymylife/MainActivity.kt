package com.kstudio.diarymylife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.kstudio.diarymylife.databinding.ActivityMainBinding
import com.kstudio.diarymylife.ui.adapter.ViewPagerAdapter
import com.kstudio.diarymylife.ui.home.HomeFragment
import com.kstudio.diarymylife.ui.todo.ToDoFragment
import com.kstudio.diarymylife.ui.write.WriteFragment

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
            WriteFragment(),
            ToDoFragment()
        )

        binding.viewPager.adapter = ViewPagerAdapter(fragment, this)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bottomNavigation.menu.getItem(position).isChecked = true
            }
        })
    }

    private fun setupNavigationBar() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_calendar -> {
                    binding.viewPager.currentItem = 0
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_write -> {
                    binding.viewPager.currentItem = 1
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_todo -> {
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