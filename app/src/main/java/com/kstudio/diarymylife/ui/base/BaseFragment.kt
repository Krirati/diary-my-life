package com.kstudio.diarymylife.ui.base

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.utils.Keys.Companion.JOURNAL_ID

open class BaseFragment : Fragment() {
    protected var _binding: ViewBinding? = null

    protected fun <T : Activity> navigateToActivity(
        activity: Class<T>?,
        journalId: Long?,
    ) {
        val intent = Intent(context, activity)
        intent.putExtra(JOURNAL_ID, journalId)
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

}