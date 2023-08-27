package com.kstudio.diarymylife.domain

import com.kstudio.diarymylife.data.mood.MoodRepository
import com.kstudio.diarymylife.domain.model.MoodAndActivityUI
import com.kstudio.diarymylife.domain.model.MoodViewType
import com.kstudio.diarymylife.ui.adapter.ItemCardSwipeAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMoodsAndActivitiesWithLimitUseCase constructor(
    private val moodRepository: MoodRepository
) {
    operator fun invoke(): Flow<List<MoodViewType>> {
        return moodRepository.getMoodsAndActivitiesWithLimit().map { moods ->
            if (moods.isEmpty()) listOf(
                MoodViewType(
                    viewType = ItemCardSwipeAdapter.VIEW_ADD,
                    data = null
                )
            ) else {
                moods.map { mood ->
                    MoodViewType(
                        viewType = ItemCardSwipeAdapter.VIEW_ITEM,
                        data = MoodAndActivityUI(
                            moodId = mood.mood.moodId,
                            mood = mood.mood.mood,
                            desc = mood.mood.description,
                            imageUri = mood.mood.imageUri.orEmpty(),
                            timestamp = mood.mood.timestamp,
                            createTime = mood.mood.createTime,
                            fileName = mood.mood.fileName
                        )
                    )
                }
            }
        }
    }
}