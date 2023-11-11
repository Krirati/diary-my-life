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
import com.kstudio.diarymylife.utils.Keys.Companion.MOOD_ID

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
        journalId: Long? = null,
        transitionIn: Int = R.anim.slide_in_right,
        transitionOut: Int = R.anim.slide_out_left
    ) {
        val intent = Intent(context, activity)
        intent.putExtra(MOOD_ID, journalId)
        startActivity(intent)
        requireActivity().overridePendingTransition(transitionIn, transitionOut)
    }

    open fun onBackPressedOrFinish(
        transitionIn: Int = R.anim.slide_in_left,
        transitionOut: Int = R.anim.slide_out_right
    ) {
        requireActivity().finishAfterTransition()
        requireActivity().overridePendingTransition(
            transitionIn,
            transitionOut
        )
    }

    abstract fun bindingView()
    abstract fun handleOnBackPress()
}
