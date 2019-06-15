package com.example.ktomoya.rusuapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.example.ktomoya.rusuapp.R
import com.example.ktomoya.rusuapp.model.GarallyVoice
import java.text.SimpleDateFormat

class GarallyVoiceView  : FrameLayout {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val btn: Button by bindView(R.id.btn_garally_voice)
    val dateTextView: TextView by bindView(R.id.date_garally_voice)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_garally_voice, this)
    }

    fun setVoice(gv: GarallyVoice) {
        btn.setOnClickListener {

        }
        dateTextView.text = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(gv.createdAt)
    }
}