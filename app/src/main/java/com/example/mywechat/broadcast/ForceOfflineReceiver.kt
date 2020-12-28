package com.example.secondexperiment.broadcast

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.secondexperiment.ui.ActivityCollector
import com.example.secondexperiment.ui.LoginActivity
import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStreamWriter

class ForceOfflineReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        AlertDialog.Builder(context).apply {
            setTitle("Warning")
            setMessage("You are forced to be offline. Please try to login again.")
            setCancelable(false)
            setPositiveButton("OK") { _, _ ->
                ActivityCollector.finishAll() // 销毁所有Activity
                val i = Intent(context, LoginActivity::class.java)
                context?.startActivity(i) // 重新启动LoginActivity
//                try {
//                    val output = context?.openFileOutput("loginStatus", Context.MODE_PRIVATE)
//                    val writer = BufferedWriter(OutputStreamWriter(output))
//
//                    writer.use {
//                        it.write(0)
//                    }
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
                val editor = context?.getSharedPreferences("loginStatus", AppCompatActivity.MODE_PRIVATE)?.edit()
                editor?.putBoolean("loginStatus", false)
                editor?.apply()
            }
            show()
        }
    }
}