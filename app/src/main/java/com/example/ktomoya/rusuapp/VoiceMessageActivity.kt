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


class VoiceMessageActivity : AppCompatActivity() {

    var mediaRecorder: MediaRecorder? = null
    var mediaPlayer : MediaPlayer? = null

    var isPlaying : Boolean = false
    var isRecording : Boolean = false

    var filename : String? = null

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
    }

    fun getFileNameWithTimestamp() =
            Environment.getExternalStorageDirectory().absolutePath + "/voice_%d.acc".format(System.currentTimeMillis() / 1000)


    fun startRecord() {
        filename = getFileNameWithTimestamp()

        var wavFile: File? = File(filename)
        if (wavFile!!.exists()) {
            wavFile!!.delete()
        }

        try {
            mediaRecorder = MediaRecorder()
            mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
            mediaRecorder!!.setOutputFile(filename)
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
        if (filename != null) {
            try {
                mediaPlayer = MediaPlayer()
                mediaPlayer!!.setDataSource(filename)
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
}