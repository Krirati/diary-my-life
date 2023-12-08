package com.kstudio.diarymylife.ui.setting.profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.data.profile.Profile
import com.kstudio.diarymylife.databinding.ActivityProfileBinding
import com.kstudio.diarymylife.ui.base.BaseActivity
import com.kstudio.diarymylife.utils.Gender
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime

class ProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileBinding

    private val currentDate = LocalDateTime.now()

    private val datePickerDialog by lazy {
        DatePickerDialog(
            this@ProfileActivity,
            { _, year, month, dayOfMonth ->
                viewModel.setBirthDate(year, month, dayOfMonth)
            }, currentDate.year, currentDate.monthValue, currentDate.dayOfMonth
        )
    }
    private val viewModel by viewModel<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingView()
        observer()
    }

    private fun bindingView() = with(binding) {
        back.setOnClickListener { handleBackPass() }
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
            setOnKeyListener { hideKeyboard(activity = this@ProfileActivity) }
            setOnTextChange(onTextChanged = ::onTextChange)
        }
        buttonSave.setOnClickListener { viewModel.updateProfile() }
    }

    private fun onTextChange(s: CharSequence?, start: Int, before: Int, count: Int) {
        viewModel.setNickname(s.toString())
    }

    private fun observer() {
        viewModel.isProfileChanged.observe(this@ProfileActivity) {
            binding.buttonSave.isEnabled = it
        }

        viewModel.brithDate.observe(this) {
            binding.birthdate.setDefaultTextValue(it.toString())
        }

        viewModel.oldProfile.observe(this) {
            prefillOldProfile(it)
        }

        viewModel.event.observe(this) {
            if (it) {
                Toast.makeText(this, "Update profile success.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Update profile failed.", Toast.LENGTH_LONG).show()
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