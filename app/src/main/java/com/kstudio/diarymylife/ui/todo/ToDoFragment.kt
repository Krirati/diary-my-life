package com.kstudio.diarymylife.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.kstudio.diarymylife.databinding.FragmentTodoBinding
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.home.HomeViewModel

class ToDoFragment: BaseFragment() {
    private lateinit var homeViewModel: ToDoViewModel

    private val binding get() = _binding as FragmentTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(ToDoViewModel::class.java)

        _binding = FragmentTodoBinding.inflate(layoutInflater)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}