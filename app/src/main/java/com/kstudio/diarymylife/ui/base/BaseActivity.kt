package com.kstudio.diarymylife.ui.base

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.utils.Keys

open class BaseActivity : AppCompatActivity() {

    fun navigateToActivity(activity: Class<*>?) {
        val intent = Intent(this, activity)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    protected fun <T : Activity> navigateToActivityWithMoodId(
        activity: Class<T>?,
        journalId: Long? = null,
        transitionIn: Int = R.anim.slide_in_right,
        transitionOut: Int = R.anim.slide_out_left
    ) {
        val intent = Intent(this, activity)
        intent.putExtra(Keys.MOOD_ID, journalId)
        startActivity(intent)
        overridePendingTransition(transitionIn, transitionOut)
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

    fun hideKeyboard() {
        val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        // Find the currently focused view, so we can grab the correct window token from it.
        var view = this.currentFocus
        // If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
