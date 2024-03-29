package com.example.ktomoya.rusuapp.model

import android.os.Environment
import java.util.*

class VoiceMessage {
    var id: Int = -1
    var filename: String? = null
    var memo: String? = null
    var createdAt: Date? = null
    var sentAt: Date? = null
    var isSent = false
    var isPlayed = false

    constructor(filename:String, date:Date?) {
        this.id = -1
        this.filename = filename
        this.memo = "無題"
        this.createdAt = date?: Date()
        this.sentAt = null
        this.isSent = false
        this.isPlayed
    }

    companion object {
        private val FILE_FORMAT = "/voice_%d.mp3"
        private val DIR = Environment.getExternalStorageDirectory().absolutePath

        fun createNewMessage(): VoiceMessage {
            val filename = DIR + FILE_FORMAT.format(System.currentTimeMillis() / 1000)
            return VoiceMessage(filename, null)
        }
    }
}