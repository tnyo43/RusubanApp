package com.example.ktomoya.rusuapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ktomoya.rusuapp.R
import android.media.MediaRecorder
import java.io.File
import java.io.IOException
import android.media.MediaPlayer
import android.os.AsyncTask
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.example.ktomoya.rusuapp.helper.DatabaseHandler
import com.example.ktomoya.rusuapp.model.VoiceMessage
import com.example.ktomoya.rusuapp.view.VoiceMessageAdapter
import okhttp3.*


class VoiceMessageActivity : AppCompatActivity() {

    var mediaRecorder: MediaRecorder? = null
    var mediaPlayer: MediaPlayer? = null

    var isPlaying: Boolean = false
    var isRecording: Boolean = false
    var vm: VoiceMessage? = null

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
            if (isPlaying) {
                stopPlay()
            } else {
                startPlay()
            }
            playing()
        }

        findViewById<Button>(R.id.btn_send_voice).setOnClickListener {
            VoicePostTask().execute(this.vm!!.filename!!)
        }

        findViewById<Button>(R.id.btn_delte_voice_message).setOnClickListener {
            if (vm != null) {
                AlertDialog.Builder(this).apply {
                    setTitle("この音声を消しますか？")
                    setMessage(vm!!.filename)
                    setPositiveButton("消す", { _, _ ->
                        val dbHandler = DatabaseHandler(applicationContext, null)
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
            mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
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

            vm!!.memo = findViewById<EditText>(R.id.edit_voice_message).text.toString()
            val dbHandler = DatabaseHandler(this, null)
            dbHandler.addMessage(vm!!)
            updateList()
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
                mediaPlayer!!.setOnCompletionListener {
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
        if (vm != null) {
            vm!!.memo = findViewById<EditText>(R.id.edit_voice_message).text.toString()
            val dbHandler = DatabaseHandler(this, null)
            dbHandler.updateMemo(vm!!)
        }

        val listAdapter = VoiceMessageAdapter(applicationContext)

        val dbHandler = DatabaseHandler(this, null)
        listAdapter.voiceMessages = dbHandler.getAllVoiceMessage()
        val listView: ListView = findViewById(R.id.listview_voice_message) as ListView

        listView.adapter = listAdapter
        listView.setOnItemClickListener { adapterView, view, position, id ->
            val vm = listAdapter.voiceMessages[position]
            updateVoiceMessage(vm)
        }
    }

    fun updateVoiceMessage(vm: VoiceMessage) {
        this.vm = vm
        findViewById<EditText>(R.id.edit_voice_message).setText(vm.memo)
        updateList()
    }

    fun resetVoiceMessage() {
        this.vm = null
        findViewById<EditText>(R.id.edit_voice_message).setText("")
        updateList()
    }

    fun playing() {
        findViewById<Button>(R.id.btn_play).setText(if (isPlaying) "停止" else "再生")
        updateList()
    }

    class VoicePostTask: AsyncTask<String, Integer, String> {
        constructor()

        override fun doInBackground(vararg filename: String?): String {
            val url = "http://192.168.11.5:5000/upload"
            val file = File(filename[0])
            val requestBody: RequestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addPart(
                            Headers.of("Content-Disposition", "form-data; name=\"filename\""),
                            RequestBody.create(MediaType.parse("text/plain; charset=utf-8"), "hoge.mp3")
                    )
                    .addFormDataPart("audio", file.getName(), RequestBody.create(MediaType.parse("audio/mpeg"), file))
                    .build()
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build()
            var result = ""
            try {
                val response = client.newCall(request).execute()
                result = response.body().toString();
            } catch (e: Exception) {
                Log.e("VoiceMessage", e.toString())
            }

            Log.d("VoiceMessage", result)
            return result
        }
    }

}
