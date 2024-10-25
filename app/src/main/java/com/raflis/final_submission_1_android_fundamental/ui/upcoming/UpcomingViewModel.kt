package com.raflis.final_submission_1_android_fundamental.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raflis.final_submission_1_android_fundamental.data.ResultStatus
import com.raflis.final_submission_1_android_fundamental.data.local.entity.Event
import com.raflis.final_submission_1_android_fundamental.data.remote.repository.EventRepository

class UpcomingViewModel(private val eventRepository: EventRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    private val _upcomingEventList = MutableLiveData<List<Event>>()
    val upcomingEventList: LiveData<List<Event>> = _upcomingEventList

    init {
        getUpcomingEvents()
    }

    private fun getUpcomingEvents() {
        if (_upcomingEventList.value != null && _upcomingEventList.value!!.isNotEmpty()) {
            _isLoading.value = false
            return
        }
        _isLoading.value = true

        eventRepository.getUpcomingEvents().observeForever { result ->
            when (result) {
                is ResultStatus.Loading -> _isLoading.value = true
                is ResultStatus.Success -> {
                    _upcomingEventList.value = result.data
                    _isLoading.value = false
                }

                is ResultStatus.Error -> {
                    _upcomingEventList.value = emptyList()
                    _toastMessage.value = result.error
                    _isLoading.value = false
                }
            }
        }
    }
}
