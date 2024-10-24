package com.raflis.final_submission_1_android_fundamental.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtil {

    @SuppressLint("ConstantLocale")
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
    val outputTimeFormat = SimpleDateFormat("HH:mm 'WIB'", Locale("id", "ID"))

    fun formatEventTime(beginTime: String, endTime: String): String {
        val beginDate = inputFormat.parse(beginTime)
        val endDate = inputFormat.parse(endTime)

        if (beginDate != null && endDate != null) {
            val beginDateString = outputDateFormat.format(beginDate)
            val beginTimeString = outputTimeFormat.format(beginDate)
            val endTimeString = outputTimeFormat.format(endDate)

            return if (isSameDay(beginDate, endDate)) {
                "$beginDateString\n($beginTimeString - $endTimeString)"
            } else {
                "$beginDateString $beginTimeString - ${outputDateFormat.format(endDate)} $endTimeString"
            }
        }
        return "Invalid date"
    }

    private fun isSameDay(date1: Date, date2: Date): Boolean {
        val calendar1 = Calendar.getInstance().apply { time = date1 }
        val calendar2 = Calendar.getInstance().apply { time = date2 }
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
    }

    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}
