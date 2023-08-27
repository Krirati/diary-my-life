package com.kstudio.diarymylife.ui.setting.profile

import android.app.DatePickerDialog
import android.os.Bundle
import com.kstudio.diarymylife.R
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
    }

    private fun onTextChange(s: CharSequence?, start: Int, before: Int, count: Int) {
        viewModel.setNickname(s.toString())
    }

    private fun observer() {
        viewModel.isProfileChanged.observe(this@ProfileActivity) {
            binding.buttonSave.isEnabled = it
        }

        viewModel.brithDate.observe(this) {
            binding.birthdate.setDefaultTextValue(it)
        }
    }
}