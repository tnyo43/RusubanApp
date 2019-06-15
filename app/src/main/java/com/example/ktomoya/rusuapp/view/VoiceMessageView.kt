package com.example.ktomoya.rusuapp.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Layout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.example.ktomoya.rusuapp.R
import com.example.ktomoya.rusuapp.helper.DBOpenHelper
import com.example.ktomoya.rusuapp.model.VoiceMessage
import java.text.SimpleDateFormat

class VoiceMessageView : FrameLayout {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    val titleTextView: TextView by bindView(R.id.title_voice_message)
    val dateTextView: TextView by bindView(R.id.date_voice_message)
    val isSentTextView: TextView by bindView(R.id.is_sent_voice_message)
    val isPlayedTextView: TextView by bindView(R.id.is_played_voice_message)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_voice_message, this)
    }

    fun setVoiceMessage(vm: VoiceMessage) {
        titleTextView.text = vm.filename
        dateTextView.text = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(vm.createdAt)
        isPlayedTextView.text = if (vm.isPlayed) "済" else "未"
        isSentTextView.text = if (vm.isSent) "済" else "未"
    }
}