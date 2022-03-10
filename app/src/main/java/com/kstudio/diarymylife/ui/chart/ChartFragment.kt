package com.kstudio.diarymylife.ui.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kstudio.diarymylife.databinding.FragmentChartBinding
import com.kstudio.diarymylife.ui.base.BaseFragment

class ChartFragment: BaseFragment() {
    private val binding get() = _binding as FragmentChartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChartBinding.inflate(layoutInflater)
        return binding.root
    }
}