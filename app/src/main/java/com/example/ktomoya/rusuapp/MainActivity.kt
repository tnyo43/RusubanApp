package com.example.ktomoya.rusubanapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.ktomoya.rusuapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_goto_voice_message).setOnClickListener(View.OnClickListener {it ->
            val intent = Intent(this, VoiceMessageActivity::class.java)
            startActivity(intent)
        })
    }
}