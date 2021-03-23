package com.example.demojobintentservice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import android.os.SystemClock
import android.util.Log
import androidx.core.app.JobIntentService

class DemoService: JobIntentService() {

    companion object{
        private const val JOB_ID = 1007

        fun enqueueService(context: Context, intent: Intent){
            enqueueWork(context, DemoService::class.java, JOB_ID, intent)
        }
    }

    override fun onHandleWork(intent: Intent) {
        if (intent.action != null) {
            when (intent.action) {
                AppConstant.ACTION_PROGRESS -> {
                    val receiver = intent.getParcelableExtra(AppConstant.RECEIVER_CALLBACK) as? ResultReceiver
                    for (count in 1..AppConstant.MAX_VALUE) {
                        Log.i(AppConstant.DEMO_SERVICE_TAG, "Running service $count time ${SystemClock.elapsedRealtime()}")
                        try {
                            Thread.sleep(100) //100ms
                        } catch (e: InterruptedException) {
                        }
                        val bundle = Bundle()
                        bundle.putString("data", count.toString())
                        receiver?.send(AppConstant.CALLBACK_RESULT_CODE, bundle)
                    }
                }
            }
        }
        Log.i(AppConstant.DEMO_SERVICE_TAG, "Service completed by  ${SystemClock.elapsedRealtime()}")
    }



}