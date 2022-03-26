package com.kstudio.diarymylife.ui.create.select_activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kstudio.diarymylife.databinding.FragmentSelectActivityBinding
import com.kstudio.diarymylife.ui.adapter.ActivityListAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.create.CreateJournalViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectActivityFragment : BaseFragment<FragmentSelectActivityBinding>
    (FragmentSelectActivityBinding::inflate) {

    private val adapterActivity by lazy { ActivityListAdapter(requireContext()) }
    private val viewModel by viewModel<SelectActivityViewModel>()
    private val shearViewModel: CreateJournalViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnBackPress()
        bindingView()
        observeLiveDate()
        viewModel.updateListActivity()
    }

    override fun bindingView() = with(binding) {
        recyclerviewActivity.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
            adapter = adapterActivity
        }

        back.setOnClickListener { onBackPressed() }
        buttonSave.setOnClickListener { navigateToNextScreen() }
    }

    override fun onBackPressed() {
        findNavController().navigateUp()
    }

    override fun handleOnBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            })
    }

    private fun observeLiveDate() {
        viewModel.listActivity.observe(viewLifecycleOwner) {
            adapterActivity.updateActivityItems(it)
        }
        shearViewModel.selects.observe(viewLifecycleOwner) {
            Log.d("test", "test dddd: " +it)
        }
    }

    @SuppressLint("ResourceType")
    private fun navigateToNextScreen() {
        val direction =
            SelectActivityFragmentDirections.actionSelectActivityFragmentToResultJournalFragment()
        findNavController().navigate(direction)
    }
}