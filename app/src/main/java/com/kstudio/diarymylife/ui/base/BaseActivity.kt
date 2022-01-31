package com.kstudio.diarymylife.ui.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.ui.journal.JournalDetailActivity

open class BaseActivity : AppCompatActivity() {

    fun navigateToActivity(activity: Class<*>?) {
        val intent = Intent(this, activity)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.not_slide)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
    }
}