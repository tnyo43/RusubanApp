package com.example.ktomoya.rusuapp.model

import android.os.Environment
import java.util.*

open class GarallyItem {
    var id: Int = -1
    var filename: String? = null
    var createdAt: Date? = null
    var fileType: String? = null

    constructor(filename:String, date: Date?, type: String) {
        this.id = -1
        this.filename = filename
        this.createdAt = date?: Date()
        this.fileType = type
    }

    companion object {
        val DIR = Environment.getExternalStorageDirectory().absolutePath
        fun createFilename (format: String): String = DIR + format.format(System.currentTimeMillis() / 1000)
    }
}