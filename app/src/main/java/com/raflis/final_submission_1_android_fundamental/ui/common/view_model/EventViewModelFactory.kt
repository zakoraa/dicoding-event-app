package com.raflis.final_submission_1_android_fundamental.ui.common.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raflis.final_submission_1_android_fundamental.ui.event_details.FavoriteEventAddDeleteViewModel
import com.raflis.final_submission_1_android_fundamental.ui.favorite.FavoriteViewModel

class EventViewModelFactory private constructor(
    private val mApplication: Application,
) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: EventViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): EventViewModelFactory {
            if (INSTANCE == null) {
                synchronized(EventViewModelFactory::class.java) {
                    INSTANCE = EventViewModelFactory(application)
                }
            }
            return INSTANCE as EventViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteEventAddDeleteViewModel::class.java)) {
            return FavoriteEventAddDeleteViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}