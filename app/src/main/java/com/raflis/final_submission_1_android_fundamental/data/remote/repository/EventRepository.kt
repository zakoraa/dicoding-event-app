package com.raflis.final_submission_1_android_fundamental.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.raflis.final_submission_1_android_fundamental.data.ResultStatus
import com.raflis.final_submission_1_android_fundamental.data.local.entity.Event
import com.raflis.final_submission_1_android_fundamental.data.remote.response.EventResponse
import com.raflis.final_submission_1_android_fundamental.data.remote.retrofit.EventService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventRepository private constructor(
    private val eventService: EventService
) {

    fun getUpcomingEvents(): LiveData<ResultStatus<List<Event>>> {
     val upcomingEventResult = MediatorLiveData<ResultStatus<List<Event>>>()
        upcomingEventResult.value = ResultStatus.Loading
        val client = eventService.getUpcomingEvents()
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents?.filterNotNull() ?: emptyList()
                    upcomingEventResult.value = ResultStatus.Success(events)
                } else {
                    upcomingEventResult.value = ResultStatus.Error("Failed to get upcoming events")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                upcomingEventResult.value = ResultStatus.Error(t.message.toString())
            }
        })

        return upcomingEventResult
    }

    fun getFinishedEvents(): LiveData<ResultStatus<List<Event>>> {
        val finishedEventResult = MediatorLiveData<ResultStatus<List<Event>>>()
        finishedEventResult.value = ResultStatus.Loading
        val client = eventService.getFinishedEvents()
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents?.filterNotNull() ?: emptyList()
                    finishedEventResult.value = ResultStatus.Success(events)
                } else {
                    finishedEventResult.value = ResultStatus.Error("Failed to get finished events")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                finishedEventResult.value = ResultStatus.Error(t.message.toString())
            }
        })

        return finishedEventResult
    }

    fun searchEvents(keyword: String): LiveData<ResultStatus<List<Event>>> {
        val searchResult = MediatorLiveData<ResultStatus<List<Event>>>()
        searchResult.value = ResultStatus.Loading

        val client = eventService.searchEvents(keyword)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents?.filterNotNull() ?: emptyList()
                    searchResult.value = ResultStatus.Success(events)
                } else {
                    searchResult.value = ResultStatus.Error("Failed to search events")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                searchResult.value = ResultStatus.Error(t.message.toString())
            }
        })

        return searchResult
    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            eventService: EventService,
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(eventService)
            }.also { instance = it }
    }
}
