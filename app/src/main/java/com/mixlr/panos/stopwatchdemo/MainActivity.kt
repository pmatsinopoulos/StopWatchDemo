package com.mixlr.panos.stopwatchdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mixlr.panos.stopwatchdemo.databinding.ActivityMainBinding
import kotlin.math.roundToInt

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
            time = 0.0
            tvTime.text = getFormattedTime(time)
        }
    }

    private val updateTime: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            time = intent.getDoubleExtra(StopWatchService.CURRENT_TIME, 0.0)
            binding.apply {
                tvTime.text = getFormattedTime(time)
            }
        }
    }

    private fun getFormattedTime(time: Double): String {
        val timeInt = time.roundToInt()
        val SECONDS_PER_MINUTE = 60
        val SECONDS_PER_HOUR = 3_600
        val hours = timeInt / SECONDS_PER_HOUR
        val minutes = timeInt % SECONDS_PER_HOUR / SECONDS_PER_MINUTE
        val seconds = timeInt % SECONDS_PER_HOUR % SECONDS_PER_MINUTE
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}