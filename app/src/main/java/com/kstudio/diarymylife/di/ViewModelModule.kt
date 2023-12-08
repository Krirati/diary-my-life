package com.kstudio.diarymylife.di

import com.kstudio.diarymylife.ui.base.BaseMoodViewModel
import com.kstudio.diarymylife.ui.create.CreateMoodViewModel
import com.kstudio.diarymylife.ui.create.CreateNewMoodViewModel
import com.kstudio.diarymylife.ui.detail.MoodDetailViewModel
import com.kstudio.diarymylife.ui.detail.moodLanding.MoodDetailLandingViewModel
import com.kstudio.diarymylife.ui.home.HomeViewModel
import com.kstudio.diarymylife.ui.list.ListViewModel
import com.kstudio.diarymylife.ui.onboarding.OnboardingViewModel
import com.kstudio.diarymylife.ui.setting.notification.NotificationViewModel
import com.kstudio.diarymylife.ui.setting.profile.ProfileViewModel
import com.kstudio.diarymylife.ui.splash.SplashViewModel
import com.kstudio.diarymylife.ui.summary.SummaryMoodViewModel
import com.kstudio.diarymylife.widgets.select_date_bottomsheet.SelectDateBottomSheetViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { MoodDetailViewModel() }
    viewModel { MoodDetailLandingViewModel(get()) }
    viewModel { CreateNewMoodViewModel(get()) }
    viewModel { CreateMoodViewModel() }
    viewModel { SummaryMoodViewModel(get()) }
    viewModel { SelectDateBottomSheetViewModel() }
    viewModel { NotificationViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { BaseMoodViewModel() }
    viewModel { ListViewModel(get(), get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { OnboardingViewModel(get(), get()) }
}