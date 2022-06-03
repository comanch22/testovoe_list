package com.comanch.testovie_list.dataBase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StarTrackDao {

    @Insert
    suspend fun insert(starTrack: StarTrackData)

    @Update
    suspend fun update(starTrack: StarTrackData)

    @Delete
    suspend fun delete(starTrack: StarTrackData)

    @Transaction
    suspend fun insertItemGetId(item: StarTrackData): Long? {
        insert(item)
        return getItem()?.starTrackId
    }

    @Query("SELECT * from starTrack_data_base WHERE starTrackId = :key")
    suspend fun get(key: Long): StarTrackData?

    @Query("SELECT * from starTrack_data_base WHERE isSealed = :b ORDER BY starTrackId DESC LIMIT 1")
    suspend fun getNotSealed(b: Boolean = false): StarTrackData?

    @Query("DELETE FROM starTrack_data_base")
    suspend fun clear()

    @Query("SELECT * FROM starTrack_data_base ORDER BY starTrackId DESC LIMIT 1")
    suspend fun getItem(): StarTrackData?

    @Query("SELECT * FROM starTrack_data_base ORDER BY starTrackId DESC")
    fun getAllItems(): LiveData<List<StarTrackData>>

    @Query("SELECT * FROM starTrack_data_base ORDER BY timeInMills DESC LIMIT 1")
    suspend fun getItemWithMaxTime(): StarTrackData?

    @Query("SELECT * FROM starTrack_data_base ORDER BY starTrackId DESC")
    suspend fun getListItems(): List<StarTrackData>?

    @Query("SELECT COUNT(*) FROM starTrack_data_base")
    suspend fun getCount(): Int?
}