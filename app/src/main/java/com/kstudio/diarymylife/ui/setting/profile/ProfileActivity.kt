package com.kstudio.diarymylife.ui.setting.profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import com.kstudio.diarymylife.databinding.ActivityProfileBinding
import com.kstudio.diarymylife.ui.base.BaseActivity
import java.time.LocalDateTime

class ProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileBinding

    private val currentDate = LocalDateTime.now()

    private val datePickerDialog by lazy {
        DatePickerDialog(
            this@ProfileActivity,
            { _, year, month, dayOfMonth ->
                Log.d("test", "year $year mouth $month day $dayOfMonth")
            }, currentDate.year, currentDate.monthValue, currentDate.dayOfMonth
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingView()
    }

    private fun bindingView() = with(binding) {
        back.setOnClickListener { handleBackPass() }
        nickname.setOnKeyListener { hideKeyboard(activity = this@ProfileActivity) }
        birthdate.apply {
            setOnClickListener { datePickerDialog.show() }
            setOnClickWidget { datePickerDialog.show() }
        }
    }
}