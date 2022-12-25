package com.kstudio.diarymylife.di

import com.kstudio.diarymylife.ui.create.CreateMoodViewModel
import com.kstudio.diarymylife.ui.create.select_activity.SelectActivityViewModel
import com.kstudio.diarymylife.ui.create.select_mood.SelectMoodViewModel
import com.kstudio.diarymylife.ui.home.HomeViewModel
import com.kstudio.diarymylife.ui.list.ListMoodViewModel
import com.kstudio.diarymylife.ui.mood.MoodDetailViewModel
import com.kstudio.diarymylife.ui.mood.moodLanding.MoodLandingViewModel
import com.kstudio.diarymylife.ui.setting.notification.NotificationViewModel
import com.kstudio.diarymylife.widgets.select_date_bottomsheet.SelectDateBottomSheetViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { MoodDetailViewModel() }
    viewModel { MoodLandingViewModel(get()) }
    viewModel { SelectMoodViewModel(get()) }
    viewModel { SelectActivityViewModel(get()) }
    viewModel { CreateMoodViewModel() }
    viewModel { ListMoodViewModel(get()) }
    viewModel { SelectDateBottomSheetViewModel() }
    viewModel { NotificationViewModel() }
}