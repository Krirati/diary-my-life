package com.kstudio.diarymylife.domain

import com.kstudio.diarymylife.data.mood.MoodRepository
import com.kstudio.diarymylife.domain.model.MoodAndActivityUI
import com.kstudio.diarymylife.domain.model.MoodViewType
import com.kstudio.diarymylife.ui.adapter.ItemCardSwipeAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMoodsAndActivityUseCase constructor(
    private val moodRepository: MoodRepository
) {
    operator fun invoke(): Flow<List<MoodViewType>> {
        return moodRepository.getMoodsAndActivities().map { moods ->
            if (moods.isEmpty()) listOf(
                MoodViewType(
                    viewType = ItemCardSwipeAdapter.VIEW_ADD,
                    data = null
                )
            ) else {
                moods.map { moodItem ->
                    val mood = moodItem.mood
                    MoodViewType(
                        viewType = ItemCardSwipeAdapter.VIEW_ITEM,
                        data = MoodAndActivityUI(
                            moodId = mood.moodId,
                            mood = mood.mood,
                            title = mood.title,
                            desc = mood.description,
                            imageUri = mood.imageUri.orEmpty(),
                            timestamp = mood.timestamp,
                            createTime = mood.createTime,
                            fileName = mood.fileName
                        )
                    )
                }
            }
        }
    }
}