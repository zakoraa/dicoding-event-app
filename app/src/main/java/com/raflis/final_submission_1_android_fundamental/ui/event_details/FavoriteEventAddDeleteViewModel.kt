package com.raflis.final_submission_1_android_fundamental.ui.event_details

import android.app.Application
import androidx.lifecycle.ViewModel
import com.raflis.final_submission_1_android_fundamental.data.entity.FavoriteEvent
import com.raflis.final_submission_1_android_fundamental.data.repository.local.FavoriteEventRepository

class FavoriteEventAddDeleteViewModel(application: Application) : ViewModel() {
    private val mFavoriteEventRepository: FavoriteEventRepository =
        FavoriteEventRepository(application)

    fun insert(favoriteEvent: FavoriteEvent) {
        mFavoriteEventRepository.insert(favoriteEvent)
    }

    fun update(favoriteEvent: FavoriteEvent) {
        mFavoriteEventRepository.update(favoriteEvent)
    }

    fun delete(favoriteEvent: FavoriteEvent) {
        mFavoriteEventRepository.delete(favoriteEvent)
    }
}