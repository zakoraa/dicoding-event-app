package com.raflis.final_submission_1_android_fundamental.ui.finished

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raflis.final_submission_1_android_fundamental.data.ResultStatus
import com.raflis.final_submission_1_android_fundamental.data.local.entity.Event
import com.raflis.final_submission_1_android_fundamental.data.remote.repository.EventRepository

class FinishedViewModel(private val eventRepository: EventRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    private val _finishedEventList = MutableLiveData<List<Event>>()
    val finishedEventList: LiveData<List<Event>> = _finishedEventList

    init {
        getFinishedEvents()
    }

    private fun getFinishedEvents() {
        if (_finishedEventList.value != null && _finishedEventList.value!!.isNotEmpty()) {
            _isLoading.value = false
            return
        }
        _isLoading.value = true

        eventRepository.getFinishedEvents().observeForever { result ->
            when (result) {
                is ResultStatus.Loading -> _isLoading.value = true
                is ResultStatus.Success -> {
                    _finishedEventList.value = result.data
                    _isLoading.value = false
                }
                is ResultStatus.Error -> {
                    _finishedEventList.value = emptyList()
                    _toastMessage.value = result.error
                    _isLoading.value = false
                }
            }
        }
    }
}
