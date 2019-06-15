package com.example.ktomoya.rusuapp.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
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
        findViewById<Button>(R.id.btn_goto_gallery).setOnClickListener(View.OnClickListener {it ->
            val intent = Intent(this, GarallyActivity::class.java)
            startActivity(intent)
        })


        requestPermissions()
    }

    fun requestPermissions() {
        val requiredPermissions = listOf<String>(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val requests = mutableListOf<String>()
        for (required in requiredPermissions) {
            if (this.checkCallingOrSelfPermission(required) != PackageManager.PERMISSION_GRANTED) {
                requests.add (required)
            }
        }

        if (!requests.isEmpty()) {
            requestPermissions(requests.toTypedArray(), 101);
        }
    }
}