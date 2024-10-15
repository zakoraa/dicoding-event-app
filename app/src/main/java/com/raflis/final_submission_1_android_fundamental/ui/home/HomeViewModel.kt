package com.raflis.final_submission_1_android_fundamental.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raflis.final_submission_1_android_fundamental.data.model.EventModel
import com.raflis.final_submission_1_android_fundamental.data.repository.remote.EventConfig
import com.raflis.final_submission_1_android_fundamental.data.response.EventResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _isGettingUpcomingEvent = MutableLiveData<Boolean>().apply { value = true }
    val isGettingUpcomingEvent: LiveData<Boolean> = _isGettingUpcomingEvent

    private val _isGettingFinishedEvent = MutableLiveData<Boolean>().apply { value = true }
    val isGettingFinishedEvent: LiveData<Boolean> = _isGettingFinishedEvent

    private val _isSearchingEvents = MutableLiveData<Boolean>().apply { value = false }
    val isSearchingEvents: LiveData<Boolean> = _isSearchingEvents

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    private val _upcomingEventList = MutableLiveData<List<EventModel?>?>()
    val upcomingEventList: LiveData<List<EventModel?>?> = _upcomingEventList

    private val _finishedEventList = MutableLiveData<List<EventModel?>?>()
    val finishedEventList: LiveData<List<EventModel?>?> = _finishedEventList

    private val _searchResultEventList = MutableLiveData<List<EventModel?>?>()
    val searchResultEventList: LiveData<List<EventModel?>?> = _searchResultEventList

    init {
        getUpcomingEvents()
        getFinishedEvents()
    }

    private fun getUpcomingEvents() {
        if (_upcomingEventList.value != null && _upcomingEventList.value!!.isNotEmpty()) {
            _isGettingUpcomingEvent.value = false
            return
        }

        val upcomingEventListData = EventConfig.getApiService().getUpcomingEvents()
        upcomingEventListData.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {

                if (response.isSuccessful) {
                    _upcomingEventList.value =
                        response.body()?.listEvents?.take(5) ?: emptyList()
                } else {
                    _upcomingEventList.value = emptyList()
                    _toastMessage.value = "Failed to get upcoming events"
                }
                _isGettingUpcomingEvent.value = false
            }

            override fun onFailure(call: Call<EventResponse>, response: Throwable) {
                _upcomingEventList.value = emptyList()
                _toastMessage.value = "Failed to get upcoming events"
                _isGettingUpcomingEvent.value = false
            }

        })

    }

    private fun getFinishedEvents() {
        if (_finishedEventList.value != null && _finishedEventList.value!!.isNotEmpty()) {
            _isGettingFinishedEvent.value = false
            return
        }

        val finishedEventListData = EventConfig.getApiService().getFinishedEvents()
        finishedEventListData.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {

                if (response.isSuccessful) {
                    _finishedEventList.value =
                        response.body()?.listEvents?.take(5) ?: emptyList()
                } else {
                    _finishedEventList.value = emptyList()
                    _toastMessage.value = "Failed to get finished events"
                }
                _isGettingFinishedEvent.value = false
            }

            override fun onFailure(call: Call<EventResponse>, response: Throwable) {
                _finishedEventList.value = emptyList()
                _isGettingFinishedEvent.value = false
                _toastMessage.value = "Failed to get finished events"
            }

        })
    }

    fun searchEvents(keyword: String) {
        if (keyword.isEmpty() || keyword == "") {
            _searchResultEventList.value = emptyList()
            return
        }
        _isSearchingEvents.value = true

        val searchResultEventListData = EventConfig.getApiService().searchEvents(keyword)
        searchResultEventListData.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isSearchingEvents.value = false

                if (response.isSuccessful) {
                    _searchResultEventList.value = response.body()?.listEvents ?: emptyList()
                } else {
                    _toastMessage.value = "Failed to get events"
                }
            }

            override fun onFailure(call: Call<EventResponse>, response: Throwable) {
                _isSearchingEvents.value = false
                _toastMessage.value = "Failed to get events"
            }

        })
    }

}