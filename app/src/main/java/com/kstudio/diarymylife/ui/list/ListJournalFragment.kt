package com.kstudio.diarymylife.ui.list

import android.os.Bundle
import android.view.View
import com.kstudio.diarymylife.databinding.FragmentListJournalBinding
import com.kstudio.diarymylife.ui.base.BaseFragment

class ListJournalFragment : BaseFragment<FragmentListJournalBinding>(
    FragmentListJournalBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingView()
    }
    override fun bindingView() = with(binding) {
    }

    override fun handleOnBackPress() {
        TODO("Not yet implemented")
    }
}