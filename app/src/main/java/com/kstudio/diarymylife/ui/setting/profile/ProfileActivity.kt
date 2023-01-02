package com.kstudio.diarymylife.ui.setting.profile

import android.os.Bundle
import com.kstudio.diarymylife.databinding.ActivityProfileBinding
import com.kstudio.diarymylife.ui.base.BaseActivity

class ProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingView()
    }

    private fun bindingView() = with(binding) {
        back.setOnClickListener { handleBackPass() }
    }
}