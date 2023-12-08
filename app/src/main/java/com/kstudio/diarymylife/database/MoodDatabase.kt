package com.kstudio.diarymylife.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kstudio.diarymylife.database.dao.ActivityEventDao
import com.kstudio.diarymylife.database.dao.MoodDao
import com.kstudio.diarymylife.database.dao.ProfileDao
import com.kstudio.diarymylife.database.model.ActivityEvent
import com.kstudio.diarymylife.database.model.Mood
import com.kstudio.diarymylife.database.model.MoodActivityEventCrossRef
import com.kstudio.diarymylife.database.model.Profile
import com.kstudio.diarymylife.database.util.Converter
import java.util.concurrent.Executors

@Database(
    entities = [
        Mood::class,
        ActivityEvent::class,
        MoodActivityEventCrossRef::class,
        Profile::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(value = [Converter::class])
abstract class MoodDatabase : RoomDatabase() {
    abstract fun moodDao(): MoodDao
    abstract fun activityEventDao(): ActivityEventDao
    abstract fun profileDao(): ProfileDao

    companion object {
        @Volatile
        private var INSTANT: MoodDatabase? = null

        fun getDatabase(context: Context): MoodDatabase {
            return INSTANT ?: synchronized(this) {
                val instant = Room
                    .databaseBuilder(
                        context.applicationContext,
                        MoodDatabase::class.java,
                        "mood_database"
                    )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Executors.newSingleThreadScheduledExecutor()
                                .execute { INSTANT?.activityEventDao()?.insertAll(*populateData()) }
                        }
                    })
                    .build()
                INSTANT = instant
                instant
            }
        }

        fun populateData() = arrayOf(
            ActivityEvent(eventId = 0, activityName = "Book", activityImage = "ic_book"),
            ActivityEvent(eventId = 1, activityName = "Dating", activityImage = "ic_dating"),
            ActivityEvent(eventId = 2, activityName = "Exercise", activityImage = "ic_exercise"),
            ActivityEvent(eventId = 3, activityName = "Food", activityImage = "ic_food"),
            ActivityEvent(eventId = 4, activityName = "Game", activityImage = "ic_game"),
            ActivityEvent(eventId = 5, activityName = "Health", activityImage = "ic_health"),
            ActivityEvent(eventId = 6, activityName = "Gift", activityImage = "ic_gift"),
            ActivityEvent(eventId = 7, activityName = "Travel", activityImage = "ic_map"),
            ActivityEvent(eventId = 8, activityName = "Music", activityImage = "ic_music"),
            ActivityEvent(eventId = 9, activityName = "Relax", activityImage = "ic_relax"),
            ActivityEvent(eventId = 10, activityName = "Music", activityImage = "ic_music"),
            ActivityEvent(eventId = 11, activityName = "Shopping", activityImage = "ic_shopping"),
            ActivityEvent(eventId = 12, activityName = "Weather", activityImage = "ic_weather"),
        )
    }
}
