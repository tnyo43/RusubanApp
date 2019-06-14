package com.example.ktomoya.rusuapp.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.ktomoya.rusuapp.model.VoiceMessage

class VoiceMessageAdapter(private val context: Context): BaseAdapter() {

    var voiceMessages: List<VoiceMessage> = emptyList()

    override fun getCount(): Int = voiceMessages.size

    override fun getItem(position: Int): Any? = voiceMessages[position]

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View =
            ((convertView as? VoiceMessageView) ?: VoiceMessageView(context)).apply {
                setVoiceMessage(voiceMessages[position])
            }
}