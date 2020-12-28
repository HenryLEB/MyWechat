package com.example.secondexperiment.ui

import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.secondexperiment.broadcast.ForceOfflineReceiver

open class BaseActivity : AppCompatActivity() {
    lateinit var receiver: ForceOfflineReceiver
//    val pref_loginStatus = getPreferences(Context.MODE_PRIVATE)
    companion object {
//        var isLogined = 0
        var loginStatus = false
//        val pref_loginStatus =
    }
//    companion object {
//        var isLogined = 0
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
//        isLogined = getLoginStatus()

    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.example.mywechat.FORCE_OFFLINE")
        receiver = ForceOfflineReceiver()
        registerReceiver(receiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
//        Toast.makeText(this, isLogined.toString(), Toast.LENGTH_SHORT).show()
//        saveLoginStatus(isLogined)
    }



//    fun getLoginStatus(): Boolean {
////        var ret = 0;
////        try {
////            val input = openFileInput("loginStatus")
////            val reader = BufferedReader(InputStreamReader(input))
////            reader.use {
////                ret = reader.read()
////            }
////        } catch (e: IOException) {
////            e.printStackTrace()
////        }
////
////        return ret;
//        return pref_loginStatus.getBoolean("loginStatus", false)
//    }
//
//     fun saveLoginStatus(loginStatus: Boolean) {
////        try {
////            val output = openFileOutput("loginStatus", Context.MODE_PRIVATE)
////            val writer = BufferedWriter(OutputStreamWriter(output))
////
////            writer.use {
////                it.write(loginStatus)
////            }
//////            writer.close()
////        } catch (e: IOException) {
////            e.printStackTrace()
////        }
//         val editor = pref_loginStatus.edit()
//         editor.putBoolean("loginStatus", loginStatus)
//         editor.apply()
//    }

    open fun saveloginStatus(status: Boolean) {
        val editor = getSharedPreferences("loginStatus", MODE_PRIVATE).edit()
        editor.putBoolean("loginStatus", status)
        editor.apply()
    }
}