package com.example.secondexperiment.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.secondexperiment.ui.BaseActivity

class RemAccontDataBaseHelper(val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {

    private val createDateBase = "create table Account (" +
            " id integer primary key autoincrement," +
            "account text," +
            "password text)"

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(createDateBase)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}