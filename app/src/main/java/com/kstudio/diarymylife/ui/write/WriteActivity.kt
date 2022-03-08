package com.kstudio.diarymylife.ui.write

import android.os.Bundle
import androidx.navigation.findNavController
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ActivityJournalWriteBinding
import com.kstudio.diarymylife.ui.base.BaseActivity

class WriteActivity : BaseActivity() {

    private lateinit var binding: ActivityJournalWriteBinding

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJournalWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController.setGraph(R.navigation.journal_create_nav_graph)
    }
}