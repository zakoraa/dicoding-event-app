package com.raflis.final_submission_1_android_fundamental.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.SyncHttpClient
import com.raflis.final_submission_1_android_fundamental.R
import org.json.JSONObject

class WorkerUtil(
    context: Context,
    workerParams: WorkerParameters,
) : Worker(context, workerParams) {

    companion object {
        private val TAG = WorkerUtil::class.java.simpleName
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel_01"
        const val CHANNEL_NAME = "dicoding channel"
    }

    private var resultStatus: Result? = null

    override fun doWork(): Result {
        return getCurrentEvent()
    }

    private fun getCurrentEvent(): Result {
        Looper.prepare()
        val client = SyncHttpClient()
        val url = "https://event-api.dicoding.dev/events?active=-1&limit=1"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out cz.msebera.android.httpclient.Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val responseObject = JSONObject(result)

                    if (responseObject.getBoolean("error")) {
                        showNotification(
                            "Error fetching events",
                            responseObject.getString("message")
                        )
                        resultStatus = Result.failure()
                        return
                    }

                    val listEvents = responseObject.getJSONArray("listEvents")
                    if (listEvents.length() > 0) {
                        val eventObject = listEvents.getJSONObject(0)
                        val eventName = eventObject.getString("name")
                        val eventSummary = eventObject.getString("summary")
                        val eventDescription = eventObject.getString("description")
                        val eventCity = eventObject.getString("cityName")
                        val beginTime = eventObject.getString("beginTime")
                        val endTime = eventObject.getString("endTime")
                        val eventLink = eventObject.getString("link")

                        val title = "Event: $eventName in $eventCity"
                        val message =
                            "$eventSummary\nDescription: $eventDescription\nStart: $beginTime\nEnd: $endTime\nLink: $eventLink"
                        showNotification(title, message)
                        resultStatus = Result.success()
                    } else {
                        showNotification("No events found", "The event list is empty.")
                        resultStatus = Result.failure()
                    }
                } catch (e: Exception) {
                    showNotification("Error parsing event data", e.message)
                    resultStatus = Result.failure()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out cz.msebera.android.httpclient.Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                Log.d(TAG, "onFailure: Failed to fetch events")
                showNotification("Get Current Event Failed", error.message)
                resultStatus = Result.failure()
            }
        })
        return resultStatus as Result
    }


    private fun showNotification(title: String, description: String?) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.dicoding_logo)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notification.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }


}