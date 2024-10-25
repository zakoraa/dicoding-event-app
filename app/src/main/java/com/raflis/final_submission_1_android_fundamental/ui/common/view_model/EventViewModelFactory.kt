package com.raflis.final_submission_1_android_fundamental.ui.common.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raflis.final_submission_1_android_fundamental.data.di.Injection
import com.raflis.final_submission_1_android_fundamental.data.remote.repository.EventRepository
import com.raflis.final_submission_1_android_fundamental.ui.finished.FinishedViewModel
import com.raflis.final_submission_1_android_fundamental.ui.home.HomeViewModel
import com.raflis.final_submission_1_android_fundamental.ui.upcoming.UpcomingViewModel

class EventViewModelFactory private constructor(private val eventRepository: EventRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(eventRepository) as T
        } else if (modelClass.isAssignableFrom(FinishedViewModel::class.java)) {
            return FinishedViewModel(eventRepository) as T
        } else if (modelClass.isAssignableFrom(UpcomingViewModel::class.java)) {
            return UpcomingViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: EventViewModelFactory? = null
        fun getInstance(context: Context): EventViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: EventViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}