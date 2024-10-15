package com.raflis.final_submission_1_android_fundamental.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raflis.final_submission_1_android_fundamental.data.model.EventModel
import com.raflis.final_submission_1_android_fundamental.data.repository.remote.EventConfig
import com.raflis.final_submission_1_android_fundamental.data.response.EventResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    private val _upcomingEventList = MutableLiveData<List<EventModel?>?>()
    val upcomingEventList: LiveData<List<EventModel?>?> = _upcomingEventList


    init {
        getUpcomingEvents()
    }

    private fun getUpcomingEvents() {
        if (_upcomingEventList.value != null && _upcomingEventList.value!!.isNotEmpty()) {
            _isLoading.value = false
            return
        }
        _isLoading.value = true

        val upcomingEventListData = EventConfig.getApiService().getUpcomingEvents()
        upcomingEventListData.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    _upcomingEventList.value = response.body()?.listEvents ?: emptyList()
                } else {
                    _upcomingEventList.value = emptyList()
                    _toastMessage.value = "Failed to get upcoming events"
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<EventResponse>, response: Throwable) {
                _upcomingEventList.value = emptyList()
                _isLoading.value = false
                _toastMessage.value = "Failed to get upcoming events"
            }

        })
    }

}