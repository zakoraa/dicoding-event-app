package com.raflis.final_submission_1_android_fundamental.data.di

import android.content.Context
import com.raflis.final_submission_1_android_fundamental.data.remote.repository.EventRepository
import com.raflis.final_submission_1_android_fundamental.data.remote.retrofit.EventConfig

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val eventService = EventConfig.getApiService()
        return EventRepository.getInstance(eventService)
    }
}