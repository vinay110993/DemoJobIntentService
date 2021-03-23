package com.example.demojobintentservice

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demojobintentservice.databinding.MainActivityLayoutBinding

class MainActivity : AppCompatActivity() , CallBackReceiver.Receiver {
    private var binding: MainActivityLayoutBinding?= null

    private val callBackReceiver: CallBackReceiver by lazy {
        CallBackReceiver(Handler())
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.progressBar?.max = 100

        binding?.btnService?.setOnClickListener {
            val intent = Intent()
            intent.putExtra(AppConstant.RECEIVER_CALLBACK, callBackReceiver)
            intent.action = AppConstant.ACTION_PROGRESS
            DemoService.enqueueService(this, intent)
        }
        callBackReceiver.setReceiver(this)
    }

    @SuppressLint("SetTextI18n")
    override fun setResult(resultCode: Int, resultData: Bundle) {
        if(resultCode == AppConstant.CALLBACK_RESULT_CODE){
            val data = resultData.getString("data")
            binding?.tvPercentageCompleted?.text = "$data % completed"
            binding?.progressBar?.setProgressCompat((data.getIntValue()*100)/AppConstant.MAX_VALUE, true) //calculate percentage
        }
    }
}
