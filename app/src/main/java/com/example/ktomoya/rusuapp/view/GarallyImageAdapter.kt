package com.example.ktomoya.rusuapp.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.ktomoya.rusuapp.model.GarallyImage

class GarallyImageAdapter(private val context: Context): BaseAdapter() {

    var garallyImages: List<GarallyImage> = emptyList()

    override fun getCount(): Int = garallyImages.size

    override fun getItem(position: Int): Any? = garallyImages[position]

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View =
            ((convertView as? GarallyImageView) ?: GarallyImageView(context)).apply {
                setImage(garallyImages[position])
            }
}