package com.example.ktomoya.rusuapp.model

import com.example.ktomoya.rusuapp.helper.DatabaseHandler
import java.util.*

class GarallyVoice: GarallyItem {

    constructor(filename:String, date: Date?): super(filename, date, DatabaseHandler.GarallyDB.TYPE_VOICE)

    companion object {
        private val FILE_FORMAT = "/voice_%d.png"

        fun createGarallyVoice(): GarallyVoice {
            val filename = GarallyItem.createFilename(FILE_FORMAT)
            return GarallyVoice(filename, null)
        }
    }
}