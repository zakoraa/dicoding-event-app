package com.raflis.final_submission_1_android_fundamental.data.repository.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.raflis.final_submission_1_android_fundamental.data.database.FavoriteEventDao
import com.raflis.final_submission_1_android_fundamental.data.database.FavoriteEventRoomDatabase
import com.raflis.final_submission_1_android_fundamental.data.entity.FavoriteEvent
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteEventRepository(application: Application) {
    private val mFavoriteEventsDao: FavoriteEventDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteEventRoomDatabase.getDatabase(application)
        mFavoriteEventsDao = db.favoriteEventDao()
    }

    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>> =
        mFavoriteEventsDao.getAllFavoriteEvents()

    fun insert(favoriteEvent: FavoriteEvent) {
        executorService.execute { mFavoriteEventsDao.insert(favoriteEvent) }
    }

    fun delete(favoriteEvent: FavoriteEvent) {
        executorService.execute { mFavoriteEventsDao.delete(favoriteEvent) }
    }

    fun update(favoriteEvent: FavoriteEvent) {
        executorService.execute { mFavoriteEventsDao.update(favoriteEvent) }
    }
}