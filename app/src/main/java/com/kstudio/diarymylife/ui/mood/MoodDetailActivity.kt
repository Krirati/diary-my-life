package com.kstudio.diarymylife.ui.mood

import android.os.Bundle
import androidx.navigation.findNavController
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ActivityJournalDetailBinding
import com.kstudio.diarymylife.ui.base.BaseActivity

class MoodDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityJournalDetailBinding

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJournalDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController.setGraph(R.navigation.journal_detail_nav_graph, intent.extras)
    }
}