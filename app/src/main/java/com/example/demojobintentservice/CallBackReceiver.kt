package com.example.demojobintentservice

import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver

open class CallBackReceiver(handler: Handler) : ResultReceiver(handler) {

    private var receiver: Receiver?= null

    fun setReceiver(receiver: Receiver){
        this.receiver = receiver
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        super.onReceiveResult(resultCode, resultData)
        if (resultData!= null) receiver?.setResult(resultCode = resultCode, resultData = resultData)
    }

    interface Receiver{
        fun setResult(resultCode: Int, resultData: Bundle )
    }
}