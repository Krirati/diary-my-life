package com.kstudio.diarymylife.ui.base

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.ui.journal.JournalDetailActivity

open class BaseFragment : Fragment() {
    protected var _binding: ViewBinding? = null

    fun navigateToActivity(activity: Class<*>?) {
        val intent = Intent(context, activity)
        startActivity(intent)
        getActivity()?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}