package com.example.ktomoya.rusuapp.model

import com.example.ktomoya.rusuapp.helper.DatabaseHandler
import java.util.*

class GarallyImage: GarallyItem {

    constructor(filename:String, date: Date?): super(filename, date, DatabaseHandler.GarallyDB.TYPE_IMAGE)

    companion object {
        private val FILE_FORMAT = "/img_%d.png"

        fun createGarallyImage(): GarallyImage {
            val filename = GarallyItem.createFilename(FILE_FORMAT)
            return GarallyImage(filename, null)
        }
    }
}