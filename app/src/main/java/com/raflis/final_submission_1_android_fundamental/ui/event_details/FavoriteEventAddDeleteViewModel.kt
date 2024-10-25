package com.raflis.final_submission_1_android_fundamental.ui.event_details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raflis.final_submission_1_android_fundamental.data.local.entity.FavoriteEvent
import com.raflis.final_submission_1_android_fundamental.data.local.repository.FavoriteEventRepository
import kotlinx.coroutines.launch

class FavoriteEventAddDeleteViewModel(application: Application) : ViewModel() {
    private val mFavoriteEventRepository: FavoriteEventRepository =
        FavoriteEventRepository(application)

    private val _isFavorite = MediatorLiveData<Boolean>()

    fun isFavoriteEvent(eventId: Int?): LiveData<Boolean> {
        val favoriteEventLiveData = mFavoriteEventRepository.getFavoriteEventById(eventId)
        _isFavorite.addSource(favoriteEventLiveData) { favoriteEvent ->
            _isFavorite.value = favoriteEvent != null
        }
        return _isFavorite
    }

    fun toggleFavoriteEvent(favoriteEvent: FavoriteEvent) {
        viewModelScope.launch {
            val isFavorite = _isFavorite.value ?: false
            if (isFavorite) {
                mFavoriteEventRepository.delete(favoriteEvent)
            } else {
                mFavoriteEventRepository.insert(favoriteEvent)
            }
            _isFavorite.value = !isFavorite
        }
    }

}