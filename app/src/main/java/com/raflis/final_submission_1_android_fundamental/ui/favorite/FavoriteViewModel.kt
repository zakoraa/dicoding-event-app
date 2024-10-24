package com.raflis.final_submission_1_android_fundamental.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.raflis.final_submission_1_android_fundamental.data.local.entity.FavoriteEvent
import com.raflis.final_submission_1_android_fundamental.data.local.repository.FavoriteEventRepository

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mFavoriteEventRepository: FavoriteEventRepository =
        FavoriteEventRepository(application)

    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>> =
        mFavoriteEventRepository.getAllFavoriteEvents()
}