package com.raflis.final_submission_1_android_fundamental.ui.common.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raflis.final_submission_1_android_fundamental.ui.event_details.FavoriteEventAddDeleteViewModel
import com.raflis.final_submission_1_android_fundamental.ui.favorite.FavoriteViewModel

class FavoriteEventViewModelFactory private constructor(
    private val mApplication: Application,
) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: FavoriteEventViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavoriteEventViewModelFactory {
            if (INSTANCE == null) {
                synchronized(FavoriteEventViewModelFactory::class.java) {
                    INSTANCE = FavoriteEventViewModelFactory(application)
                }
            }
            return INSTANCE as FavoriteEventViewModelFactory
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