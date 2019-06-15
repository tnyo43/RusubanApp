package com.example.ktomoya.rusuapp.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.ktomoya.rusuapp.model.GarallyImage
import java.io.File
import java.util.*

class GarallyDB (context: Context, factory: SQLiteDatabase.CursorFactory?) :
        SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = (
                "CREATE TABLE "
                        + TABLE_NAME + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY,"
                        + COLUMN_FILENAME + " TEXT NOT NULL,"
                        + COLUMN_CREATED_AT + " LONG NOT NULL,"
                        + COLUMN_FILETYPE + " TEXT NOT NULL"
                        + ")")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val query = "DROP TABLE IF EXISTS " + TABLE_NAME
        db.execSQL(query)
        onCreate(db)
    }

    fun getAllGarallyItems(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM ${TABLE_NAME} ORDER BY ${COLUMN_CREATED_AT} DESC", null)
    }

    fun addMessage(gi: GarallyImage) {
        val values = ContentValues()
        values.put(COLUMN_FILENAME, gi.filename)
        values.put(COLUMN_CREATED_AT, (gi.createdAt?: Date()).time)
        values.put(COLUMN_FILETYPE, TYPE_IMAGE)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun deleteMessage(gi: GarallyImage) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", arrayOf(gi.id.toString()))
        File(gi.filename).delete()
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "rusuapp.db"
        val TABLE_NAME = "garally_tbl"
        val COLUMN_ID = "id"
        val COLUMN_FILENAME = "filename"
        val COLUMN_CREATED_AT = "updated_at"
        val COLUMN_FILETYPE = "type"

        val TYPE_IMAGE = "image"
        val TYPE_VOICE = "voice"
    }
}