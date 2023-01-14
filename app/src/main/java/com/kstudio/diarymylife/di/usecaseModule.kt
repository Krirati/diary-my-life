package com.kstudio.diarymylife.di

import com.kstudio.diarymylife.domain.GetMoodsAndActivitiesWithLimitUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetMoodsAndActivitiesWithLimitUseCase(get()) }
}