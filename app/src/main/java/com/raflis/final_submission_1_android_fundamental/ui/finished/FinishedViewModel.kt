package com.raflis.final_submission_1_android_fundamental.ui.finished

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raflis.final_submission_1_android_fundamental.data.entity.Event
import com.raflis.final_submission_1_android_fundamental.data.repository.remote.EventConfig
import com.raflis.final_submission_1_android_fundamental.data.response.EventResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    private val _finishedEventList = MutableLiveData<List<Event?>?>()
    val finishedEventList: LiveData<List<Event?>?> =_finishedEventList


    init {
        getFinishedEvents()
    }

    private  fun getFinishedEvents() {
        if (_finishedEventList.value != null && _finishedEventList.value!!.isNotEmpty()) {
            _isLoading.value = false
            return
        }
        _isLoading.value = true

        val finishedEventListData = EventConfig.getApiService().getFinishedEvents()
        finishedEventListData.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    _finishedEventList.value = response.body()?.listEvents?: emptyList()
                } else {
                    _finishedEventList.value = emptyList()
                    _toastMessage.value = "Failed to get finished events"
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<EventResponse>, response: Throwable) {
                _finishedEventList.value = emptyList()
                _isLoading.value = false
                _toastMessage.value = "Failed to get finished events"
            }

        })
    }
}