package com.mixlr.panos.stopwatchdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mixlr.panos.stopwatchdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isStarted: Boolean = false

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
    }

    private fun startOrStop() {
        if (isStarted) {
            stop()
        } else {
            start()
        }
    }
    private fun start() {
        binding.apply {
            btnStart.text = "Stop"
            isStarted = true
        }
    }

    private fun stop() {
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
}