package com.raflis.final_submission_1_android_fundamental.data.remote.retrofit

import com.raflis.final_submission_1_android_fundamental.data.remote.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EventService {
    @GET("events?active=1")
    fun getUpcomingEvents(): Call<EventResponse>

    @GET("events?active=0")
    fun getFinishedEvents(): Call<EventResponse>

    @GET("events?active=-1&limit=1")
    fun getLatestEvent(): Call<EventResponse>

    @GET("events?active=-1")
    fun searchEvents(@Query("q") keyword: String): Call<EventResponse>
}