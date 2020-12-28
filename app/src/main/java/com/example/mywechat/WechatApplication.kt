package com.example.mywechat

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class WechatApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        const val TOKEN = "ab6E0r6TaHqSJe8d"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}