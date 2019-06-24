package com.example.ktomoya.rusuapp.values

open class Urls {
    companion object {
        val HTTP = "http://"
        val IP = "192.168.11.5"
        val PORT = "5000"

        val UPLOAD_AUDIO = "/upload"
        val STREAMING = "/~pi/stream.html"
        val KEY_OPEN = "/~pi/key.php"

        fun getAddress() = HTTP + IP
        fun getAddressWithPort() = HTTP + IP + ":" + PORT
        fun getUploadAudioAdress() = getAddressWithPort() + UPLOAD_AUDIO
        fun getStreamingAddress() =  getAddress() + STREAMING
        fun getKeyOpen(isOpen: Boolean) = getAddress() + KEY_OPEN + "?stat=" + (if (isOpen) "1" else "0")
    }
}