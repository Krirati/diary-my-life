package com.kstudio.diarymylife.ui.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.kstudio.diarymylife.R

open class BaseActivity : AppCompatActivity() {

    fun navigateToActivity(activity: Class<*>?) {
        val intent = Intent(this, activity)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
    }

    fun handleBackPass() {
        onBackPressedDispatcher.onBackPressed()
    }
}