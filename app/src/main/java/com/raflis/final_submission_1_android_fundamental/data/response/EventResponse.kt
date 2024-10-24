package com.raflis.final_submission_1_android_fundamental.data.response

import com.google.gson.annotations.SerializedName
import com.raflis.final_submission_1_android_fundamental.data.entity.Event

data class EventResponse(

	@field:SerializedName("listEvents")
	val listEvents: List<Event?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

