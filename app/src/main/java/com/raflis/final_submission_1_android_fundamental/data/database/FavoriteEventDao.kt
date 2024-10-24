package com.raflis.final_submission_1_android_fundamental.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.raflis.final_submission_1_android_fundamental.data.entity.FavoriteEvent

@Dao
interface FavoriteEventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: FavoriteEvent)

    @Update
    fun update(note: FavoriteEvent)

    @Delete
    fun delete(note: FavoriteEvent)

    @Query("SELECT * from favoriteEvent ORDER BY id ASC")
    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>>
}