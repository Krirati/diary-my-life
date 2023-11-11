package com.kstudio.diarymylife.di

import com.kstudio.diarymylife.domain.GetMoodsAndActivitiesWithLimitUseCase
import com.kstudio.diarymylife.domain.GetMoodsAndActivityUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetMoodsAndActivitiesWithLimitUseCase(get()) }
    single { GetMoodsAndActivityUseCase(get()) }
}
