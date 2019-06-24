package com.example.ktomoya.rusuapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.example.ktomoya.rusuapp.R
import com.example.ktomoya.rusuapp.helper.HttpGetTask

class KeyActivity : AppCompatActivity() {

    var isOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key)

        findViewById<Button>(R.id.btn_open_key).setOnClickListener {
            isOpen = !isOpen
            val task = HttpGetTask(isOpen)
            task.execute()
            (it as Button).text = if (isOpen) "閉じる" else "開ける"
        }
    }
}