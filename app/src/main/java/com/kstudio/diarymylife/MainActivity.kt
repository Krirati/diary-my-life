package com.kstudio.diarymylife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kstudio.diarymylife.databinding.ActivityMainBinding
import com.kstudio.diarymylife.ui.adapter.ViewPagerAdapter
import com.kstudio.diarymylife.ui.home.HomeFragment
import com.kstudio.diarymylife.ui.setting.SettingFragment
import com.kstudio.diarymylife.ui.write.WriteActivity

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
            SettingFragment()
        )

        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = ViewPagerAdapter(fragment, this)
    }

    private fun setupNavigationBar() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_write -> {
//                    binding.viewPager.currentItem = 1
                    val intent = Intent(this, WriteActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_todo -> {
                    binding.viewPager.currentItem = 1
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