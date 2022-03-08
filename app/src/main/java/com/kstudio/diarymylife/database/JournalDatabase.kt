package com.kstudio.diarymylife.database

import android.content.Context
import androidx.room.*
import com.kstudio.diarymylife.data.Journal
import com.kstudio.diarymylife.dao.JournalDao

@Database(entities = [Journal::class], version = 1, exportSchema = false)
@TypeConverters(value = [Converter::class])
abstract class JournalDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDao

    companion object {
        @Volatile
        private var INSTANT : JournalDatabase? = null

        fun getDatabase(context: Context): JournalDatabase {
            return INSTANT ?: synchronized(this) {
                val instant = Room.databaseBuilder(
                    context.applicationContext,
                    JournalDatabase::class.java,
                    "journal_database"
                ).build()
                INSTANT = instant
                instant
            }
        }
    }
}