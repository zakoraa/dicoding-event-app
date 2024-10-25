package com.raflis.final_submission_1_android_fundamental.data.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.raflis.final_submission_1_android_fundamental.data.local.database.FavoriteEventDao
import com.raflis.final_submission_1_android_fundamental.data.local.database.FavoriteEventRoomDatabase
import com.raflis.final_submission_1_android_fundamental.data.local.entity.FavoriteEvent
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

    fun getFavoriteEventById(id: Int?): LiveData<FavoriteEvent> =
        mFavoriteEventsDao.getFavoriteEventById(id)

    fun insert(favoriteEvent: FavoriteEvent) {
        executorService.execute { mFavoriteEventsDao.insert(favoriteEvent) }
    }

    fun delete(favoriteEvent: FavoriteEvent) {
        executorService.execute { mFavoriteEventsDao.delete(favoriteEvent) }
    }

}