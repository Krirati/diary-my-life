package com.kstudio.diarymylife.ui.moods

import android.os.Bundle
import androidx.navigation.findNavController
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ActivityJournalWriteBinding
import com.kstudio.diarymylife.ui.base.BaseActivity
import com.kstudio.diarymylife.utils.Keys.Companion.MOOD_ID

class MoodActivity : BaseActivity() {

    private lateinit var binding: ActivityJournalWriteBinding
    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJournalWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val startDestination = if (intent.extras?.getLong(MOOD_ID) != null) {
            R.id.moodDetailLandingFragment
        } else {
            R.id.writeFragment
        }

        val navGraph = navController.navInflater.inflate(R.navigation.mood_create_nav_graph)
        navGraph.setStartDestination(startDestination)
        navController.setGraph(navGraph, intent.extras)
    }
}
