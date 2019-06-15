package com.example.ktomoya.rusuapp.view

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.ktomoya.rusuapp.R
import com.example.ktomoya.rusuapp.model.GarallyImage
import java.io.File
import java.text.SimpleDateFormat

class GarallyImageView  : FrameLayout {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val imageView: ImageView by bindView(R.id.img_garally_image)
    val dateTextView: TextView by bindView(R.id.date_garally_image)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_garally_image, this)
    }

    fun setImage(gi: GarallyImage) {
        imageView.setImageURI(Uri.fromFile(File(gi.filename)))
        dateTextView.text = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(gi.createdAt)
    }
}