package com.raflis.final_submission_1_android_fundamental.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raflis.final_submission_1_android_fundamental.data.ResultStatus
import com.raflis.final_submission_1_android_fundamental.data.local.entity.Event
import com.raflis.final_submission_1_android_fundamental.data.remote.repository.EventRepository

class HomeViewModel(private val eventRepository: EventRepository) : ViewModel() {

    private val _isGettingUpcomingEvent = MutableLiveData<Boolean>().apply { value = true }
    val isGettingUpcomingEvent: LiveData<Boolean> = _isGettingUpcomingEvent

    private val _isGettingFinishedEvent = MutableLiveData<Boolean>().apply { value = true }
    val isGettingFinishedEvent: LiveData<Boolean> = _isGettingFinishedEvent

    private val _isSearchingEvents = MutableLiveData<Boolean>().apply { value = false }
    val isSearchingEvents: LiveData<Boolean> = _isSearchingEvents

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    private val _upcomingEventList = MutableLiveData<List<Event>>()
    val upcomingEventList: LiveData<List<Event>> = _upcomingEventList

    private val _finishedEventList = MutableLiveData<List<Event>>()
    val finishedEventList: LiveData<List<Event>> = _finishedEventList

    private val _searchResultEventList = MutableLiveData<List<Event>>()
    val searchResultEventList: LiveData<List<Event>> = _searchResultEventList

    init {
        getUpcomingEvents()
        getFinishedEvents()
    }

    private fun getUpcomingEvents() {
        if (_upcomingEventList.value != null && _upcomingEventList.value!!.isNotEmpty()) {
            _isGettingUpcomingEvent.value = false
            return
        }

        eventRepository.getUpcomingEvents().observeForever { result ->
            when (result) {
                is ResultStatus.Loading -> _isGettingUpcomingEvent.value = true
                is ResultStatus.Success -> {
                    _upcomingEventList.value = result.data.take(5)
                    _isGettingUpcomingEvent.value = false
                }

                is ResultStatus.Error -> {
                    _upcomingEventList.value = emptyList()
                    _toastMessage.value = result.error
                    _isGettingUpcomingEvent.value = false
                }
            }
        }
    }

    private fun getFinishedEvents() {
        if (_finishedEventList.value != null && _finishedEventList.value!!.isNotEmpty()) {
            _isGettingFinishedEvent.value = false
            return
        }

        eventRepository.getFinishedEvents().observeForever { result ->
            when (result) {
                is ResultStatus.Loading -> _isGettingFinishedEvent.value = true
                is ResultStatus.Success -> {
                    _finishedEventList.value = result.data.take(5)
                    _isGettingFinishedEvent.value = false
                }

                is ResultStatus.Error -> {
                    _finishedEventList.value = emptyList()
                    _toastMessage.value = result.error
                    _isGettingFinishedEvent.value = false
                }
            }
        }
    }

    fun searchEvents(keyword: String) {
        if (keyword.isEmpty()) {
            _searchResultEventList.value = emptyList()
            return
        }
        _isSearchingEvents.value = true

        eventRepository.searchEvents(keyword).observeForever { result ->
            _isSearchingEvents.value = false
            when (result) {
                is ResultStatus.Loading -> {}
                is ResultStatus.Success -> {
                    _searchResultEventList.value = result.data
                }

                is ResultStatus.Error -> {
                    _searchResultEventList.value = emptyList()
                    _toastMessage.value = result.error
                }
            }
        }
    }


}