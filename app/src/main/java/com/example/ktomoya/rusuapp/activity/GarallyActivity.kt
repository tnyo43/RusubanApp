package com.example.ktomoya.rusuapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import com.example.ktomoya.rusuapp.R
import com.example.ktomoya.rusuapp.helper.DatabaseHandler
import com.example.ktomoya.rusuapp.model.GarallyImage
import com.example.ktomoya.rusuapp.model.GarallyVoice
import com.example.ktomoya.rusuapp.view.GarallyItemAdapter

class GarallyActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_garally)

        val dbHandler = DatabaseHandler(this, null)
        updateList()
    }


    fun updateList() {
        val listAdapter = GarallyItemAdapter(applicationContext)

        val dbHandler = DatabaseHandler(this, null)
        listAdapter.garallyItem = dbHandler.getAllGarallyItems()
        val listView: ListView = findViewById(R.id.listview_garally) as ListView

        listView.adapter = listAdapter
        listView.setOnItemClickListener { adapterView, view, position, id ->
            val gi = listAdapter.garallyItem[position]
        }
    }
}