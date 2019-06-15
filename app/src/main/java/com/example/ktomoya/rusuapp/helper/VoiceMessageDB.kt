package com.example.ktomoya.rusuapp.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.ktomoya.rusuapp.model.VoiceMessage
import java.io.File
import java.util.*

class VoiceMessageDB (context: Context, factory: SQLiteDatabase.CursorFactory?) :
                    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = (
                "CREATE TABLE "
                        + TABLE_NAME + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY,"
                        + COLUMN_FILENAME + " TEXT NOT NULL,"
                        + COLUMN_MEMO + " TEXT NOT NULL,"
                        + COLUMN_CREATED_AT + " LONG NOT NULL,"
                        + COLUMN_SENT_AT + " LONG,"
                        + COLUMN_IS_SENT + " INT,"
                        + COLUMN_IS_PLAYED + " INT"
                        + ")")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val query = "DROP TABLE IF EXISTS " + TABLE_NAME
        db.execSQL(query)
        onCreate(db)
    }

    fun getAllVoiceMessage(): Cursor? {

        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_CREATED_AT DESC", null) //arrayOf(String.format("%s DESC", COLUMN_CREATED_AT)))
    }

    fun addMessage(vm: VoiceMessage) {
        val values = ContentValues()
        values.put(COLUMN_FILENAME, vm.filename)
        values.put(COLUMN_MEMO, vm.memo)
        values.put(COLUMN_CREATED_AT, (vm.createdAt?: Date()).time)
        if (vm.sentAt != null)
            values.put(COLUMN_SENT_AT, vm.sentAt!!.time)
        values.put(COLUMN_IS_SENT, vm.isSent)
        values.put(COLUMN_IS_PLAYED, vm.isPlayed)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun updateMemo(vm: VoiceMessage) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_MEMO, vm.memo)
        db.update(TABLE_NAME, cv, COLUMN_ID + " = ?", arrayOf(vm.id.toString()))
    }

    fun deleteMessage(vm: VoiceMessage) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", arrayOf(vm.id.toString()))
        File(vm.filename).delete()
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "rusuapp.db"
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