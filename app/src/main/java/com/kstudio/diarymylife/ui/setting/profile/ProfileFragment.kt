package com.kstudio.diarymylife.ui.setting.profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.data.profile.Profile
import com.kstudio.diarymylife.databinding.ActivityProfileBinding
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.setting.SettingNavigate
import com.kstudio.diarymylife.ui.setting.SettingViewModel
import com.kstudio.diarymylife.utils.Gender
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime

class ProfileFragment : BaseFragment<ActivityProfileBinding>(ActivityProfileBinding::inflate) {

    private val currentDate = LocalDateTime.now()

    private val datePickerDialog by lazy {
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                viewModel.setBirthDate(year, month, dayOfMonth)
            }, currentDate.year, currentDate.monthValue, currentDate.dayOfMonth
        )
    }
    private val viewModel by viewModel<ProfileViewModel>()
    private val activityViewModel by activityViewModels<SettingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingView()
        observer()
    }

    override fun bindingView() = with(binding) {
        back.setOnClickListener { handleOnBackPress() }
        birthdate.apply {
            setOnClickListener { datePickerDialog.show() }
            setOnClickWidget { datePickerDialog.show() }
        }

        radioGroupGender.setOnCheckedChangeListener { _, checkedId ->
            val gender = when (checkedId) {
                R.id.radioMan -> Gender.MEN
                R.id.radioWoman -> Gender.WOMEN
                R.id.radioOther -> Gender.OTHER
                else -> Gender.OTHER
            }
            viewModel.setGender(gender)
        }
        nickname.apply {
            setOnKeyListener { hideKeyboard() }
            setOnTextChange(onTextChanged = ::onTextChange)
        }
        buttonSave.setOnClickListener { viewModel.updateProfile() }
    }

    override fun handleOnBackPress() {
        activityViewModel.emitSettingNavigate(SettingNavigate.Setting)
    }

    private fun onTextChange(s: CharSequence?, start: Int, before: Int, count: Int) {
        viewModel.setNickname(s.toString())
    }

    private fun observer() {
        viewModel.isProfileChanged.observe(viewLifecycleOwner) {
            binding.buttonSave.isEnabled = it
        }

        viewModel.brithDate.observe(viewLifecycleOwner) {
            binding.birthdate.setDefaultTextValue(it.toString())
        }

        viewModel.oldProfile.observe(viewLifecycleOwner) {
            prefillOldProfile(it)
        }

        viewModel.event.observe(viewLifecycleOwner) {
            activity?.let { activity ->
                if (it) {
                    Toast.makeText(activity, "Update profile success.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(activity, "Update profile failed.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun prefillOldProfile(profile: Profile) = with(binding) {
        nickname.setDefaultTextValue(profile.nickname)
        profile.birthDate?.let { birthdate.setDefaultTextValue(it.toString()) }
        profile.gender.takeIf { it.isEmpty().not() }?.let {
            radioGroupGender.check(mappingGender(it))
        }
    }

    private fun mappingGender(gender: String): Int {
        return when (Gender.fromString(gender)) {
            Gender.MEN -> R.id.radioMan
            Gender.WOMEN -> R.id.radioWoman
            Gender.OTHER -> R.id.radioOther
        }
    }
}
