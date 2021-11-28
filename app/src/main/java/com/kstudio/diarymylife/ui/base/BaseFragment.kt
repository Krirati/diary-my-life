package com.kstudio.diarymylife.ui.base

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.utils.Keys

open class BaseFragment : Fragment() {
    protected var _binding: ViewBinding? = null

    fun navigateToActivity(activity: Class<*>?, journalId: Int?) {
        val intent = Intent(context, activity)
        startActivity(intent)
        intent.putExtra(Keys.JOURNAL_ID, journalId)
        requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    fun finish(
        enterAnimation: Int = R.anim.slide_in_left,
        exitAnimation: Int = R.anim.slide_out_right
    ) {
        requireActivity().run {
            finish()
            overridePendingTransition(enterAnimation, exitAnimation)
        }
    }
}