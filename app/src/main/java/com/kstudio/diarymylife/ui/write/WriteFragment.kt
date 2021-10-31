package com.kstudio.diarymylife.ui.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.kstudio.diarymylife.databinding.FragmentWriteBinding
import com.kstudio.diarymylife.ui.base.BaseFragment

class WriteFragment: BaseFragment() {

    private lateinit var writeViewModel: WriteViewModel

    private val binding get() = _binding as FragmentWriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        writeViewModel = ViewModelProvider(this).get(WriteViewModel::class.java)

        _binding = FragmentWriteBinding.inflate(layoutInflater)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        writeViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }
}