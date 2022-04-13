package com.kstudio.diarymylife.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.utils.Keys.Companion.JOURNAL_ID

abstract class BaseFragment<VB : ViewBinding>(
    private val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateMethod.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    protected fun <T : Activity> navigateToActivity(
        activity: Class<T>?,
        journalId: Long?,
    ) {
        val intent = Intent(context, activity)
        intent.putExtra(JOURNAL_ID, journalId)
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    abstract fun bindingView()

    open fun onBackPressedOrFinish() {
        requireActivity().finishAfterTransition()
        requireActivity().overridePendingTransition(
            R.anim.slide_in_top,
            R.anim.slide_out_bottom
        )
    }

    abstract fun handleOnBackPress()

}