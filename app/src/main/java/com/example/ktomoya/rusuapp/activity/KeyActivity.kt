package com.example.ktomoya.rusuapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import com.example.ktomoya.rusuapp.R

class KeyActivity : AppCompatActivity() {

    val TAG = "KEY ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key)

        findViewById<Button>(R.id.btn_open_key).setOnClickListener {
            // open
            Log.d(TAG, "open")
        }
        findViewById<Button>(R.id.btn_close_key).setOnClickListener {
            // close
            Log.d(TAG, "close")
        }
    }
}