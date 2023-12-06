package com.kstudio.diarymylife.ui.onboarding.permission

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.data.ResultSelectDate
import com.kstudio.diarymylife.databinding.FragmentOnboardingPageBinding
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.onboarding.OnboardingStep
import com.kstudio.diarymylife.ui.onboarding.OnboardingViewModel
import com.kstudio.diarymylife.utils.Permissions
import com.kstudio.diarymylife.widgets.select_date_bottomsheet.SelectDateBottomSheet
import com.kstudio.diarymylife.widgets.select_date_bottomsheet.SelectDateHandle

class PermissionFragment :
    BaseFragment<FragmentOnboardingPageBinding>(FragmentOnboardingPageBinding::inflate),
    SelectDateHandle {

    private val activityViewModel: OnboardingViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnBackPress()
        bindingView()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(activity, "Accept for notification", Toast.LENGTH_LONG).show()
                activityViewModel.nextScreen(OnboardingStep.AcceptNotification)
            } else {
                Toast.makeText(
                    activity,
                    "Please allow permission for notification",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    override fun bindingView(): Unit = with(binding) {
        imagePage.setImageResource(R.drawable.crying)
        title.text = getString(R.string.permission)
        description.text = getString(R.string.permission_description)
        nextButton.setOnClickListener {
            Permissions.requirePermissionNotification(
                requireContext(),
                Manifest.permission.SCHEDULE_EXACT_ALARM,
                callBack = {
                    activityViewModel.nextScreen(OnboardingStep.AcceptNotification)
                },
                requireAccept = { requestPermissionLauncher.launch(Manifest.permission.SCHEDULE_EXACT_ALARM) })
        }
        switchEnableNotification.apply {
            visibility = View.VISIBLE
            setStateEnable(true)
            setStateCheck(true)
            setOnSwitchCheckChange { }
            setOnClickWidget { showBottomSheetSelectTime() }
        }
    }

    override fun handleOnBackPress() {
    }

    private fun showBottomSheetSelectTime() {
        val bottomSheetSelectTime = SelectDateBottomSheet(
            getContext = requireContext(),
            onClickDone = ::onClickDoneBottomSheet,
            onClose = ::onCloseBottomSheet,
            type = SelectDateBottomSheet.BottomSheetType.DISPLAY_TIME,
        )
        activity?.supportFragmentManager?.let {
            bottomSheetSelectTime.show(it, bottomSheetSelectTime.tag)
        }
    }

    override fun onClickDoneBottomSheet(date: ResultSelectDate) {
//        activityViewModel.setIsDailyChange(date.time)
//        activityViewModel.saveNotificationTime(date.time)
    }

    override fun onCloseBottomSheet() { /* Do nothing*/
    }
}