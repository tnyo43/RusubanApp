package com.example.ktomoya.rusuapp.model

import android.os.Environment
import java.util.*

class GarallyImage {
    var id: Int = -1
    var filename: String? = null
    var createdAt: Date? = null

    constructor(filename:String, date: Date?) {
        this.id = -1
        this.filename = DIR + "/img.png"
        this.createdAt = date?: Date()
    }

    companion object {
        private val FILE_FORMAT = "/img_%d.png"
        private val DIR = Environment.getExternalStorageDirectory().absolutePath

        fun createGarallyImage(): GarallyImage {
            val time = Date().time.toString()
            val filename = DIR + FILE_FORMAT.format(System.currentTimeMillis() / 1000)
            return GarallyImage(filename, null)
        }
    }
}