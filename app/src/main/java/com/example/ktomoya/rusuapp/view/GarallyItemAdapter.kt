package com.example.ktomoya.rusuapp.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.ktomoya.rusuapp.model.GarallyImage
import com.example.ktomoya.rusuapp.model.GarallyItem
import com.example.ktomoya.rusuapp.model.GarallyVoice

class GarallyItemAdapter(private val context: Context): BaseAdapter() {

    var garallyItem: List<GarallyItem> = emptyList()

    override fun getCount(): Int = garallyItem.size

    override fun getItem(position: Int): Any? = garallyItem[position]

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item = garallyItem[position]
        if (item is GarallyImage) {
            return ((convertView as? GarallyImageView) ?: GarallyImageView(context)).apply {
                setImage(item)
            }
        } else if (item is GarallyVoice){
            return ((convertView as? GarallyVoiceView) ?: GarallyVoiceView(context)).apply {
                setVoice(item)
            }
        }
        return View(null)
    }
}