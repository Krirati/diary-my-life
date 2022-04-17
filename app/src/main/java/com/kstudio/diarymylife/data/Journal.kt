package com.kstudio.diarymylife.data

import androidx.room.*
import java.time.LocalDateTime

@Entity(tableName = "user_mood")
data class Journal(
    @PrimaryKey(autoGenerate = true) val moodId: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "mood") val mood: String?,
    @ColumnInfo(name = "activity") val activity: ArrayList<Int>? = arrayListOf(),
    @ColumnInfo(name = "image_name") val imageName: String?,
    @ColumnInfo(name = "timestamp") val timestamp: LocalDateTime,
    @ColumnInfo(name = "create_time") val createTime: LocalDateTime,
) {

}

@Entity(primaryKeys = ["moodId", "eventId"])
data class JournalActivityEventCrossRef(
    val moodId: Long,
    val eventId: Int
)

data class MoodWithActivity(
    @Embedded val journal: Journal,
    @Relation(
        entity = ActivityEvent::class,
        parentColumn = "moodId",
        entityColumn = "eventId",
        associateBy = Junction(JournalActivityEventCrossRef::class)
    )
    val activities: List<ActivityEvent>
)

//@Entity
//data class Playlist(
//    @PrimaryKey val playlistId: Long,
//    val playlistName: String
//)
//
//@Entity
//data class Song(
//    @PrimaryKey val songId: Long,
//    val songName: String,
//    val artist: String
//)
//
//@Entity(primaryKeys = ["playlistId", "songId"])
//data class PlaylistSongCrossRef(
//    val playlistId: Long,
//    val songId: Long
//)
//
//data class PlaylistWithSongs(
//    @Embedded val playlist: Playlist,
//    @Relation(
//        parentColumn = "playlistId",
//        entityColumn = "songId",
//        associateBy = Junction(PlaylistSongCrossRef::class)
//    )
//    val songs: List<Song>
//)