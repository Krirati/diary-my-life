package com.kstudio.diarymylife.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kstudio.diarymylife.databinding.FragmentSettingBinding
import com.kstudio.diarymylife.ui.base.BaseFragment

class SettingFragment: BaseFragment() {
    private lateinit var viewModel: SettingViewModel

    private val binding get() = _binding as FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(SettingViewModel::class.java)

        _binding = FragmentSettingBinding.inflate(layoutInflater)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}