package com.comanch.testovie_list.dataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "starTrack_data_base")
data class StarTrackData(

    @PrimaryKey(autoGenerate = true)
    var starTrackId: Long = 0L,

    @ColumnInfo(name = "imageTitle")
    var imageTitle: String = "",

    @ColumnInfo(name = "imageUrl")
    var imageUrl: String = "",

    @ColumnInfo(name = "explanation")
    var explanation: String = "",

    @ColumnInfo(name = "isSealed")
    var isSealed: Boolean = false,

    @ColumnInfo(name = "timeInMills")
    var timeInMills: Long = 0
)


