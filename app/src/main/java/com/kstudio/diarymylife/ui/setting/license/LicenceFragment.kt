package com.kstudio.diarymylife.ui.setting.license

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.kstudio.diarymylife.databinding.FragmentLicenceBinding
import com.kstudio.diarymylife.ui.adapter.LicenceAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.setting.SettingNavigate
import com.kstudio.diarymylife.ui.setting.SettingViewModel
import com.kstudio.diarymylife.utils.External

class LicenceFragment :
    BaseFragment<FragmentLicenceBinding>(FragmentLicenceBinding::inflate) {

    private val activityViewModel by activityViewModels<SettingViewModel>()
    private val adapterLicence by lazy {
        LicenceAdapter {
            External.openExternal(requireContext(), it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingView()
        initData()
    }

    override fun bindingView() = with(binding) {
        back.setOnClickListener { handleOnBackPress() }
        recyclerview.apply {
            adapter = adapterLicence
        }
        return@with
    }

    override fun handleOnBackPress() {
        activityViewModel.emitSettingNavigate(SettingNavigate.Setting)
    }

    private fun initData() {
        adapterLicence.updateLicenceList(listLicence)
    }
}