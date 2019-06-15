package com.example.ktomoya.rusubanapp

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ktomoya.rusuapp.R
import android.media.MediaRecorder
import java.io.File
import java.io.IOException
import android.media.MediaPlayer
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.example.ktomoya.rusuapp.helper.DBOpenHelper
import com.example.ktomoya.rusuapp.model.VoiceMessage
import com.example.ktomoya.rusuapp.view.VoiceMessageAdapter
import com.example.ktomoya.rusuapp.view.VoiceMessageView
import java.util.*


class VoiceMessageActivity : AppCompatActivity() {

    var mediaRecorder: MediaRecorder? = null
    var mediaPlayer : MediaPlayer? = null

    var isPlaying : Boolean = false
    var isRecording : Boolean = false
    var vm : VoiceMessage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_message)

        updateList()

        findViewById<Button>(R.id.btn_record).setOnClickListener {
            var text = ""
            if (isRecording) {
                stopRecord()
                text = "録音"
            } else {
                resetVoiceMessage()
                startRecord()
                text = "録音終了"
            }
            findViewById<Button>(R.id.btn_record).setText(text)
        }
        findViewById<Button>(R.id.btn_play).setOnClickListener {
            var text = ""
            if (isPlaying) {
                stopPlay()
            } else {
                startPlay()
            }
            playing()
            findViewById<Button>(R.id.btn_play).setText(text)
        }

        findViewById<Button>(R.id.btn_send_voice).setOnClickListener {
            if (!isRecording && !isPlaying && vm != null) {
                val dbHandler = DBOpenHelper(this, null)
                dbHandler.addMessage(vm!!)
                updateList()
            }
        }

        findViewById<Button>(R.id.btn_delte_voice_message).setOnClickListener {
            if (vm != null) {
                AlertDialog.Builder(this).apply {
                    setTitle("この音声を消しますか？")
                    setMessage(vm!!.filename)
                    setPositiveButton("消す", { _, _ ->
                        val dbHandler = DBOpenHelper(applicationContext, null)
                        dbHandler.deleteMessage(vm!!)
                        resetVoiceMessage()
                    })
                    setNegativeButton("キャンセル", null)
                    show()
                }

            }
        }
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
            updateVoiceMessage(vm!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun startPlay() {
        if (vm != null) {
            Log.d("play", vm!!.filename)
            try {
                mediaPlayer = MediaPlayer()
                mediaPlayer!!.setDataSource(vm!!.filename)
                mediaPlayer!!.prepare()
                mediaPlayer!!.setOnCompletionListener{
                    this.isRecording = false
                    this.isPlaying = false
                    playing()
                }
                mediaPlayer!!.start()
                this.isRecording = false
                this.isPlaying = true
            } catch (e: IOException) {
                Log.d("play", e.toString())

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
        val listAdapter = VoiceMessageAdapter(applicationContext)

        val dbHandler = DBOpenHelper(this, null)
        val cursor = dbHandler.getAllVoiceMessage()

        val voiceMessages: MutableList<VoiceMessage> = ArrayList()
        if (cursor!!.moveToFirst()) {
            do {
                val filename = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_FILENAME))
                val date = Date(cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_CREATED_AT)))
                val vm = VoiceMessage(filename, date)
                vm.id = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_ID))
                voiceMessages.add(vm)
            } while (cursor.moveToNext())
        }

        listAdapter.voiceMessages = voiceMessages.toList()
        val listView: ListView = findViewById(R.id.listview_voice_message) as ListView

        listView.adapter = listAdapter
        listView.setOnItemClickListener { adapterView, view, position, id ->
            Log.d("click", "clicked")
            val vm = listAdapter.voiceMessages[position]
            updateVoiceMessage(vm)
        }
    }

    fun updateVoiceMessage(vm: VoiceMessage) {
        this.vm = vm
        findViewById<EditText>(R.id.edit_voice_message).setText(vm.filename)
        updateList()
    }

    fun resetVoiceMessage() {
        this.vm = null
        findViewById<EditText>(R.id.edit_voice_message).setText("")
        updateList()
    }

    fun playing() {
        findViewById<Button>(R.id.btn_play).setText(if (isPlaying) "停止" else "再生")
    }
}