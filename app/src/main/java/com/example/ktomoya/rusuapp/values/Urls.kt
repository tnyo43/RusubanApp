package com.example.ktomoya.rusuapp.values

open class Urls {
    companion object {
        val HTTP = "http://"
        val IP = "192.168.11.5"
        val PORT = "5000"

        val UPLOAD_AUDIO = "/upload"
        val STREAMING = "/streaming"

        fun getAddress() = HTTP + IP + ":" + PORT
        fun getUploadAudioAdress() = getAddress() + UPLOAD_AUDIO
        fun getStreamingAddress() = getAddress() + STREAMING
    }
}