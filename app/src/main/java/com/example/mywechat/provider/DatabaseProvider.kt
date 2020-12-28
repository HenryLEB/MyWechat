package com.example.secondexperiment.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.secondexperiment.model.RemAccontDataBaseHelper

class DatabaseProvider : ContentProvider() {
    private val userDir = 0
    private val userItem = 1

    private val authority = "com.example.secondexperiment.provider"
    private var dbHelper: RemAccontDataBaseHelper? = null

    private val uriMatcher by lazy {
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
        matcher.addURI(authority, "account", userDir)
        matcher.addURI(authority, "account/#", userItem)
        matcher
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) = dbHelper?.let {
        // 删除数据
        val db = it.writableDatabase
        val deletedRows = when (uriMatcher.match(uri)) {
            userDir -> db.delete("Account", selection, selectionArgs)
            userItem -> {
                val userId = uri.pathSegments[1]
                db.delete("Account", "id = ?", arrayOf(userId))
            }
            else -> 0
        }
        deletedRows
    } ?: 0

    override fun getType(uri: Uri) = when (uriMatcher.match(uri)) {
        userDir -> "vnd.android.cursor.dir/vnd.com.example.secondexperiment.provider.account"
        userItem -> "vnd.android.cursor.item/vnd.com.example.secondexperiment.provider.account"
        else -> null
    }

    override fun insert(uri: Uri, values: ContentValues?) = dbHelper?.let {
        val db = it.writableDatabase
        val uriReturn = when (uriMatcher.match(uri)) {
            userDir, userItem -> {
                val newUserId = db.insert("Account", null, values)
                Uri.parse("content://$authority/account/$newUserId")
            }
            else -> null
        }
        uriReturn
    }

    override fun onCreate() = context?.let {
        dbHelper = RemAccontDataBaseHelper(it, "accountList", 1)
        true
    } ?: false

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?) = dbHelper?.let {
        val db = it.readableDatabase
        val cursor = when (uriMatcher.match(uri)) {
            userDir -> {
//                db.query("account", projection, selection, selectionArgs, null, null, sortOrder)
                db.rawQuery("select * from account",null)
            }
            userItem -> {
                val userId = uri.pathSegments[1]
                db.query("Account", projection, "id = ?", arrayOf(userId), null, null, sortOrder)
            }
            else -> null
        }

        cursor
    }


    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?) =
            dbHelper?.let {
                // 更新数据
                val db = it.writableDatabase
                val updatedRows = when (uriMatcher.match(uri)) {
                    userDir -> db.update("Account", values, selection, selectionArgs)
                    userItem -> {
                        val userId = uri.pathSegments[1]
                        db.update("Account", values, "id = ?", arrayOf(userId))
                    }
                    else -> 0
                }
                updatedRows
            } ?: 0

}