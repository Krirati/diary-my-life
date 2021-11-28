package com.kstudio.diarymylife.ui.journal

import android.os.Bundle
import com.kstudio.diarymylife.databinding.ActivityJournalDetailBinding
import com.kstudio.diarymylife.ui.base.BaseActivity

class JournalDetailActivity: BaseActivity() {

    private lateinit var binding: ActivityJournalDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJournalDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}