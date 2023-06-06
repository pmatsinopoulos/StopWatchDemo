package com.mixlr.panos.stopwatchdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mixlr.panos.stopwatchdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isStarted: Boolean = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnStart.setOnClickListener {
                startOrStop()
            }

            btnReset.setOnClickListener {
                reset()
            }
        }
        serviceIntent = Intent(applicationContext, StopWatchService::class.java)
        registerReceiver(updateTime, IntentFilter(StopWatchService.UPDATED_TIME))
    }

    private fun startOrStop() {
        if (isStarted) {
            stop()
        } else {
            start()
        }
    }
    private fun start() {
        serviceIntent.putExtra(StopWatchService.CURRENT_TIME, time)
        startService(serviceIntent)
        binding.apply {
            btnStart.text = "Stop"
            isStarted = true
        }
    }

    private fun stop() {
        stopService(serviceIntent)
        binding.apply {
            btnStart.text = "Start"
            isStarted = false
        }
    }

    private fun reset() {
        binding.apply {
            stop()
        }
    }

    private val updateTime: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            time = intent.getDoubleExtra(StopWatchService.CURRENT_TIME, 0.0)
            binding.apply {
                tvTime.text = time.toString()
            }
        }

    }
}