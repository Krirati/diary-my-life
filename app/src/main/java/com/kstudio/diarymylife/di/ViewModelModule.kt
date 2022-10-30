package com.kstudio.diarymylife.di

import com.kstudio.diarymylife.ui.create.CreateJournalViewModel
import com.kstudio.diarymylife.ui.create.select_activity.SelectActivityViewModel
import com.kstudio.diarymylife.ui.create.select_mood.SelectMoodViewModel
import com.kstudio.diarymylife.ui.home.HomeViewModel
import com.kstudio.diarymylife.ui.journal.JournalDetailViewModel
import com.kstudio.diarymylife.ui.journal.journalEdit.JournalEditViewModel
import com.kstudio.diarymylife.ui.journal.journalLanding.JournalLandingViewModel
import com.kstudio.diarymylife.ui.list.ListMoodViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { JournalDetailViewModel() }
    viewModel { JournalLandingViewModel(get()) }
    viewModel { JournalEditViewModel(get()) }
    viewModel { SelectMoodViewModel() }
    viewModel { SelectActivityViewModel(get()) }
    viewModel { CreateJournalViewModel(get()) }
    viewModel { ListMoodViewModel(get())}
}