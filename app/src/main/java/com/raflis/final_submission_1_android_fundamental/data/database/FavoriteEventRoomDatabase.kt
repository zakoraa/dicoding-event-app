package com.raflis.final_submission_1_android_fundamental.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raflis.final_submission_1_android_fundamental.data.entity.FavoriteEvent

@Database(entities = [FavoriteEvent::class], version = 1)
abstract class FavoriteEventRoomDatabase : RoomDatabase() {
    abstract fun favoriteEventDao(): FavoriteEventDao
    companion object {
        @Volatile
        private var INSTANCE: FavoriteEventRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FavoriteEventRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteEventRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteEventRoomDatabase::class.java, "favorite_event_database")
                        .build()
                }
            }
            return INSTANCE as FavoriteEventRoomDatabase
        }
    }
}