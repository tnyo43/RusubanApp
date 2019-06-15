package com.example.ktomoya.rusuapp.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.ktomoya.rusuapp.model.GarallyImage
import com.example.ktomoya.rusuapp.model.GarallyItem
import com.example.ktomoya.rusuapp.model.GarallyVoice
import com.example.ktomoya.rusuapp.model.VoiceMessage
import java.io.File
import java.util.*

class DatabaseHandler (context: Context, factory: SQLiteDatabase.CursorFactory?) :
                    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    class VoiceMessageDB {
        companion object {
            val TABLE_NAME = "voice_tbl"
            val COLUMN_ID = "id"
            val COLUMN_FILENAME = "filename"
            val COLUMN_MEMO = "memo"
            val COLUMN_CREATED_AT = "updated_at"
            val COLUMN_SENT_AT = "send_at"
            val COLUMN_IS_SENT = "is_sent"
            val COLUMN_IS_PLAYED = "is_played"
        }
    }

    class GarallyDB {
        companion object {
            val TABLE_NAME = "garally_tbl"
            val COLUMN_ID = "id"
            val COLUMN_FILENAME = "filename"
            val COLUMN_CREATED_AT = "updated_at"
            val COLUMN_FILETYPE = "type"

            val TYPE_IMAGE = "image"
            val TYPE_VOICE = "voice"
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query1 = (
                "CREATE TABLE "
                        + VoiceMessageDB.TABLE_NAME + "("
                        + VoiceMessageDB.COLUMN_ID + " INTEGER PRIMARY KEY,"
                        + VoiceMessageDB.COLUMN_FILENAME + " TEXT NOT NULL,"
                        + VoiceMessageDB.COLUMN_MEMO + " TEXT NOT NULL,"
                        + VoiceMessageDB.COLUMN_CREATED_AT + " LONG NOT NULL,"
                        + VoiceMessageDB.COLUMN_SENT_AT + " LONG,"
                        + VoiceMessageDB.COLUMN_IS_SENT + " INT,"
                        + VoiceMessageDB.COLUMN_IS_PLAYED + " INT"
                        + ")")
        db.execSQL(query1)

        val query2 = (
                "CREATE TABLE "
                        + GarallyDB.TABLE_NAME + "("
                        + GarallyDB.COLUMN_ID + " INTEGER PRIMARY KEY,"
                        + GarallyDB.COLUMN_FILENAME + " TEXT NOT NULL,"
                        + GarallyDB.COLUMN_CREATED_AT + " LONG NOT NULL,"
                        + GarallyDB.COLUMN_FILETYPE + " TEXT NOT NULL"
                        + ")")
        db.execSQL(query2)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val query1 = "DROP TABLE IF EXISTS " + VoiceMessageDB.TABLE_NAME
        db.execSQL(query1)
        onCreate(db)
        val query2 = "DROP TABLE IF EXISTS " + GarallyDB.TABLE_NAME
        db.execSQL(query2)
        onCreate(db)
    }

    fun getAllVoiceMessage(): List<VoiceMessage> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${VoiceMessageDB.TABLE_NAME} ORDER BY ${VoiceMessageDB.COLUMN_CREATED_AT} DESC", null)

        val voiceMessages: MutableList<VoiceMessage> = ArrayList()
        if (cursor!!.moveToFirst()) {
            do {
                val filename = cursor.getString(cursor.getColumnIndex(VoiceMessageDB.COLUMN_FILENAME))
                val date = Date(cursor.getLong(cursor.getColumnIndex(VoiceMessageDB.COLUMN_CREATED_AT)))
                val vm = VoiceMessage(filename, date)
                vm.id = cursor.getInt(cursor.getColumnIndex(VoiceMessageDB.COLUMN_ID))
                vm.memo = cursor.getString(cursor.getColumnIndex(VoiceMessageDB.COLUMN_MEMO))
                voiceMessages.add(vm)
            } while (cursor.moveToNext())
        }
        return voiceMessages.toList()
    }

    fun addMessage(vm: VoiceMessage) {
        val values = ContentValues()
        values.put(VoiceMessageDB.COLUMN_FILENAME, vm.filename)
        values.put(VoiceMessageDB.COLUMN_MEMO, vm.memo)
        values.put(VoiceMessageDB.COLUMN_CREATED_AT, (vm.createdAt?: Date()).time)
        if (vm.sentAt != null)
            values.put(VoiceMessageDB.COLUMN_SENT_AT, vm.sentAt!!.time)
        values.put(VoiceMessageDB.COLUMN_IS_SENT, vm.isSent)
        values.put(VoiceMessageDB.COLUMN_IS_PLAYED, vm.isPlayed)
        val db = this.writableDatabase
        db.insert(VoiceMessageDB.TABLE_NAME, null, values)
        db.close()
    }

    fun updateMemo(vm: VoiceMessage) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(VoiceMessageDB.COLUMN_MEMO, vm.memo)
        db.update(VoiceMessageDB.TABLE_NAME, cv, "${VoiceMessageDB.COLUMN_ID} = ?", arrayOf(vm.id.toString()))
    }

    fun deleteMessage(vm: VoiceMessage) {
        val db = this.writableDatabase
        db.delete(VoiceMessageDB.TABLE_NAME, "${VoiceMessageDB.COLUMN_ID} = ?", arrayOf(vm.id.toString()))
        File(vm.filename).delete()
    }

    fun getAllGarallyItems(): List<GarallyItem> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${GarallyDB.TABLE_NAME} ORDER BY ${GarallyDB.COLUMN_CREATED_AT} DESC", null)

        val garallyItems: MutableList<GarallyItem> = ArrayList()
        if (cursor!!.moveToFirst()) {
            do {
                val filename = cursor.getString(cursor.getColumnIndex(GarallyDB.COLUMN_FILENAME))
                val date = Date(cursor.getLong(cursor.getColumnIndex(GarallyDB.COLUMN_CREATED_AT)))
                val type = cursor.getString(cursor.getColumnIndex(GarallyDB.COLUMN_FILETYPE))
                val gi = if (type == GarallyDB.TYPE_IMAGE) GarallyImage(filename, date) else GarallyVoice(filename, date)
                gi.id = cursor.getInt(cursor.getColumnIndex(GarallyDB.COLUMN_ID))
                garallyItems.add(gi)
            } while (cursor.moveToNext())
        }
        return garallyItems.toList()
    }

    fun addGarallyItem(gi: GarallyItem) {
        val values = ContentValues()
        values.put(GarallyDB.COLUMN_FILENAME, gi.filename)
        values.put(GarallyDB.COLUMN_CREATED_AT, (gi.createdAt?: Date()).time)
        values.put(GarallyDB.COLUMN_FILETYPE, gi.fileType)
        val db = this.writableDatabase
        db.insert(GarallyDB.TABLE_NAME, null, values)
        db.close()
    }

    fun deleteGarallyItem(gi: GarallyItem) {
        val db = this.writableDatabase
        db.delete(GarallyDB.TABLE_NAME, GarallyDB.COLUMN_ID + " = ?", arrayOf(gi.id.toString()))
        File(gi.filename).delete()
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "rusuapp.db"
    }
}