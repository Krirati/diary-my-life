package com.kstudio.diarymylife.di

import com.kstudio.diarymylife.domain.GetMoodsAndActivitiesWithLimitUseCase
import com.kstudio.diarymylife.domain.GetMoodsAndActivityUseCase
import com.kstudio.diarymylife.domain.ProfileUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetMoodsAndActivitiesWithLimitUseCase(get()) }
    single { GetMoodsAndActivityUseCase(get()) }
    single { ProfileUseCase(get()) }
}