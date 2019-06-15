package com.example.ktomoya.rusuapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import com.example.ktomoya.rusuapp.R
import com.example.ktomoya.rusuapp.helper.GarallyDB
import com.example.ktomoya.rusuapp.model.GarallyImage
import com.example.ktomoya.rusuapp.view.GarallyImageAdapter
import java.util.*

class GarallyActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_garally)
        updateList()
    }


    fun updateList() {
        val listAdapter = GarallyImageAdapter(applicationContext)

        val dbHandler = GarallyDB(this, null)
        val cursor = dbHandler.getAllGarallyItems()

        val garallyImages: MutableList<GarallyImage> = ArrayList()
        if (cursor!!.moveToFirst()) {
            do {
                val filename = cursor.getString(cursor.getColumnIndex(GarallyDB.COLUMN_FILENAME))
                val date = Date(cursor.getLong(cursor.getColumnIndex(GarallyDB.COLUMN_CREATED_AT)))
                val gi = GarallyImage(filename, date)
                gi.id = cursor.getInt(cursor.getColumnIndex(GarallyDB.COLUMN_ID))
                garallyImages.add(gi)
            } while (cursor.moveToNext())
        }

        listAdapter.garallyImages = garallyImages.toList()
        val listView: ListView = findViewById(R.id.listview_garally) as ListView

        listView.adapter = listAdapter
        listView.setOnItemClickListener { adapterView, view, position, id ->
            val gi = listAdapter.garallyImages[position]
        }
    }
}