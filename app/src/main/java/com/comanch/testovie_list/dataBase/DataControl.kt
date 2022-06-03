package com.comanch.testovie_list.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StarTrackData::class], version = 1, exportSchema = false)
abstract class DataControl : RoomDatabase() {

    abstract val starTrackDao: StarTrackDao
    companion object {

        @Volatile
        private var INSTANCE: DataControl? = null

        fun getInstance(context: Context): DataControl {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            DataControl::class.java,
                            "starTrack_data_base")
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}