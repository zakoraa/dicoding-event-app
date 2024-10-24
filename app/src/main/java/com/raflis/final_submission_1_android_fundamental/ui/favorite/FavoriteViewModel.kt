package com.raflis.final_submission_1_android_fundamental.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.raflis.final_submission_1_android_fundamental.data.entity.FavoriteEvent
import com.raflis.final_submission_1_android_fundamental.data.repository.local.FavoriteEventRepository

class MainViewModel(application: Application) : ViewModel() {

    private val mFavoriteEventRepository: FavoriteEventRepository =
        FavoriteEventRepository(application)

    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>> =
        mFavoriteEventRepository.getAllFavoriteEvents()
}