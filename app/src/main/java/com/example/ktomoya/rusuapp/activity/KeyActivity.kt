package com.example.ktomoya.rusuapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import com.example.ktomoya.rusuapp.R

class KeyActivity : AppCompatActivity() {

    val TAG = "KEY ACTIVITY"
    var isOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key)

        findViewById<Button>(R.id.btn_open_key).setOnClickListener {
            // open
            isOpen = !isOpen
        }
    }
}