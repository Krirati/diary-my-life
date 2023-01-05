package com.kstudio.diarymylife.ui.create.select_activity

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kstudio.diarymylife.data.ActivityDetail
import com.kstudio.diarymylife.databinding.FragmentSelectActivityBinding
import com.kstudio.diarymylife.ui.adapter.ActivityListAdapter
import com.kstudio.diarymylife.ui.adapter.ActivityListAdapter.Companion.SELECT
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.create.CreateMoodViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectActivityFragment : BaseFragment<FragmentSelectActivityBinding>
    (FragmentSelectActivityBinding::inflate) {

    private val adapterActivity by lazy {
        ActivityListAdapter(
            requireContext(),
            ::updateSelectActivity
        )
    }
    private val viewModel by viewModel<SelectActivityViewModel>()
    private val shearViewModel: CreateMoodViewModel by activityViewModels()

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

        back.setOnClickListener { onBackPressedOrFinish() }
    }

    override fun onBackPressedOrFinish() {
        findNavController().navigateUp()
    }

    override fun handleOnBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressedOrFinish()
                }
            })
    }

    private fun observeLiveDate() {
        viewModel.listActivity.observe(viewLifecycleOwner) {
            adapterActivity.updateActivityItems(it)
        }
        shearViewModel.selectsActivity.observe(viewLifecycleOwner) {
            it?.let { data -> adapterActivity.updateFirstTimeSelectActivity(data) }
        }
    }

    private fun updateSelectActivity(data: ActivityDetail, state: String) {
        if (state == SELECT) {
            shearViewModel.addSelectActivity(data)
        } else {
            shearViewModel.removeSelectActivity(data)
        }
    }
}