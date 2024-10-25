package com.raflis.final_submission_1_android_fundamental.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raflis.final_submission_1_android_fundamental.data.local.entity.FavoriteEvent
import com.raflis.final_submission_1_android_fundamental.data.local.repository.FavoriteEventRepository

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mFavoriteEventRepository: FavoriteEventRepository =
        FavoriteEventRepository(application)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _favoriteEvents = MutableLiveData<List<FavoriteEvent>>()
    val favoriteEvents: LiveData<List<FavoriteEvent>> = _favoriteEvents

    fun loadFavoriteEvents() {
        _isLoading.value = true
        mFavoriteEventRepository.getAllFavoriteEvents().observeForever { favoriteList ->
            _favoriteEvents.value = favoriteList ?: emptyList()
            _isLoading.value = false
        }
    }
}