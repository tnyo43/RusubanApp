package com.example.ktomoya.rusubanapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ktomoya.rusuapp.R
import android.media.MediaRecorder
import java.io.File
import java.io.IOException
import android.media.MediaPlayer
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.ktomoya.rusuapp.helper.DBOpenHelper
import com.example.ktomoya.rusuapp.model.VoiceMessage


class VoiceMessageActivity : AppCompatActivity() {

    var mediaRecorder: MediaRecorder? = null
    var mediaPlayer : MediaPlayer? = null

    var isPlaying : Boolean = false
    var isRecording : Boolean = false
    var vm : VoiceMessage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_message)
        findViewById<Button>(R.id.btn_record).setOnClickListener(View.OnClickListener {
            var text = ""
            if (isRecording) {
                stopRecord()
                text = "録音"
            } else {
                startRecord()
                text = "録音終了"
            }
            findViewById<Button>(R.id.btn_record).setText(text)
        })
        findViewById<Button>(R.id.btn_play).setOnClickListener(android.view.View.OnClickListener {
            var text = ""
            if (isPlaying) {
                stopPlay()
                text = "再生"
            } else {
                startPlay()
                text = "停止"
            }
            findViewById<Button>(R.id.btn_record).setText(text)
        })
        findViewById<Button>(R.id.btn_send_voice).setOnClickListener(View.OnClickListener {
            if (!isRecording && !isPlaying && vm != null) {
                Toast.makeText(this, "送信しています", Toast.LENGTH_LONG).show()
                val dbHandler = DBOpenHelper(this, null)
                dbHandler.addMessage(vm!!)
                updateList()
            }
        })
    }

    fun startRecord() {
        vm = VoiceMessage.createNewMessage()

        var file: File? = File(vm!!.filename)
        if (file!!.exists()) {
            file!!.delete()
        }

        try {
            mediaRecorder = MediaRecorder()
            mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
            mediaRecorder!!.setOutputFile(vm!!.filename)
            mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mediaRecorder!!.prepare()
            mediaRecorder!!.start()
            this.isRecording = true
            this.isPlaying = false
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stopRecord() {
        try {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            mediaRecorder = null
            this.isRecording = false
            this.isPlaying = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun startPlay() {
        if (vm != null) {
            try {
                mediaPlayer = MediaPlayer()
                mediaPlayer!!.setDataSource(vm!!.filename)
                mediaPlayer!!.prepare()
                mediaPlayer!!.setOnCompletionListener(MediaPlayer.OnCompletionListener {
                    this.isRecording = false
                    this.isPlaying = false
                })
                mediaPlayer!!.start()
                this.isRecording = false
                this.isPlaying = true
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun stopPlay() {
        try {
            mediaPlayer!!.stop()
            mediaPlayer!!.prepare()
            mediaPlayer!!.release()
            this.isRecording = false
            this.isPlaying = false
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun updateList() {
        val dbHandler = DBOpenHelper(this, null)
        val cursor = dbHandler.getAllVoiceMessage()
        cursor!!.moveToFirst()
        var count = 0
        do {
            val text = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_FILENAME))
            count++
        } while (cursor.moveToNext())
        Toast.makeText(this, count.toString(), Toast.LENGTH_LONG).show()
    }
}