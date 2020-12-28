package com.example.secondexperiment.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.secondexperiment.R
import com.example.secondexperiment.adapter.FriendAdapter
import com.example.secondexperiment.model.Friend
import com.example.secondexperiment.model.MSGDataBaseHelper
import com.example.secondexperiment.utils.startActivity
import kotlinx.android.synthetic.main.activity_friend_list.*
import java.io.*

class FriendList : BaseActivity() , View.OnClickListener{
    private lateinit var manager: NotificationManager

    private val friendList = ArrayList<Friend>()
    private lateinit var chatHelper : MSGDataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_list)

//        创建好友、聊天信息数据库
        val msgHelper = MSGDataBaseHelper(this, "chatDatabase", 2)
        msgHelper.readableDatabase
        chatHelper = MSGDataBaseHelper(this, "chatDatabase", 2)
//        设置ToolBar
        setSupportActionBar(toolbar_friend)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }

//        Toast.makeText(this, isLogined.toString(), Toast.LENGTH_SHORT).show()

        initFriends()
        val layoutManager = LinearLayoutManager(this)
        recycleList_friendList.layoutManager = layoutManager
        val adapter = FriendAdapter(friendList)
        recycleList_friendList.adapter = adapter

//        发送通知
        manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("message", "消息", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }
//        点击事件
//        btn_sendNotice.setOnClickListener(this)
//        btn_functions.setOnClickListener(this)
//        btn_friend_forceOffline.setOnClickListener(this)
//
////        强制下线
//        btn_friend_forceOffline.setOnClickListener {
//            val intent = Intent("com.example.secondexperiment.FORCE_OFFLINE")
//            intent.setPackage(packageName)
//            sendBroadcast(intent, null)
//        }


        var ret = 0;
        try {
            val input = openFileInput("loginStatus")
            val reader = BufferedReader(InputStreamReader(input))
            reader.use {
                ret = reader.read()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
//        Toast.makeText(this, isLogined.toString(), Toast.LENGTH_SHORT).show()
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call()
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
//
    fun call() {
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:19825054022")
            startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }


    private fun initFriends() {
//        repeat(3) {
//            friendList.add(Friend(R.drawable.jfm, "喵星人"))
//            friendList.add(Friend(R.drawable.kj, "小柯基"))
//            friendList.add(Friend(R.drawable.jm, "哈哈哈"))
//            friendList.add(Friend(R.drawable.hashiqi, "哈士奇"))
//            friendList.add(Friend(R.drawable.bianmu, "边牧犬"))
//            friendList.add(Friend(R.drawable.taidi, "小泰迪"))
//            friendList.add(Friend(R.drawable.samoye, "萨摩耶"))
//            friendList.add(Friend(R.drawable.jsx, "小仓鼠"))
//            friendList.add(Friend(R.drawable.lggcs, "嘿嘿嘿"))
//            friendList.add(Friend(R.drawable.yd, "小祖宗"))
//        }
        val db = chatHelper.readableDatabase
        val cursor = db.rawQuery("select * from friends", null)
        if (cursor.moveToFirst()) {
            do {
//                        val account = cursor.getString(cursor.getColumnIndex("content"))
//                        val password = cursor.getString(cursor.getColumnIndex("sender"))

                val friend = cursor.getString(cursor.getColumnIndex("friend_id"))
                friendList.add(Friend(R.drawable.yd, friend))
            } while (cursor.moveToNext())
        }
        cursor.close()
    }

    override fun onClick(v: View?) {
        when (v) {
//            btn_functions -> {
////                val intent = Intent(this, FunctionActivity::class.java)
////                startActivity(intent)
//                startActivity<FunctionActivity>(this)
//            }
//            btn_sendNotice ->  {
//                val intent = Intent(this, ChatActivity::class.java)
//                val pi = PendingIntent.getActivity(this, 0, intent, 0)
//                val notification = NotificationCompat.Builder(this, "message")
//                    .setContentTitle("好友来消息了")
//                    .setContentText("在吗？")
////                    .setStyle(NotificationCompat.BigTextStyle().bigText("Learn how to build notifications, send and sync data, and use voice actions. Get the official Android IDE and developer tools to build apps for Android."))
////                    .setStyle(NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(resources, R.drawable.big_image)))
//                    .setSmallIcon(R.drawable.small_icon)
//                    .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.large_icon))
//                    .setContentIntent(pi)
//                    .setAutoCancel(true)
//                    .build()
//                manager.notify(1, notification)
//            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.friend_list_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Toast.makeText(this, "功能待实现", Toast.LENGTH_SHORT).show()

                val chatHelper = MSGDataBaseHelper(this, "chatDatabase", 2)
                val db =  chatHelper.readableDatabase
                val cursor = db.rawQuery("select * from friends", null)
                if (cursor.moveToFirst()) {
                    do {
//                        val account = cursor.getString(cursor.getColumnIndex("content"))
//                        val password = cursor.getString(cursor.getColumnIndex("sender"))

                        val account = cursor.getString(cursor.getColumnIndex("mine_id"))
                        val password = cursor.getString(cursor.getColumnIndex("friend_id"))
                        Log.d("LoginActivity", "account is $account")
                        Log.d("LoginActivity", "password is $password")
                    } while (cursor.moveToNext())
                }
                cursor.close()
            }

            R.id.toolbar_come_message -> {
                val intent = Intent(this, ChatActivity::class.java)
                val pi = PendingIntent.getActivity(this, 0, intent, 0)
                val notification = NotificationCompat.Builder(this, "message")
                    .setContentTitle("好友来消息了")
                    .setContentText("在吗？")
//                    .setStyle(NotificationCompat.BigTextStyle().bigText("Learn how to build notifications, send and sync data, and use voice actions. Get the official Android IDE and developer tools to build apps for Android."))
//                    .setStyle(NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(resources, R.drawable.big_image)))
                    .setSmallIcon(R.drawable.small_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.large_icon))
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build()
                manager.notify(1, notification)
            }

            R.id.toolbar_function -> {
                startActivity<FunctionActivity>(this)
            }
            R.id.toolbar_offline -> {
                val intent = Intent("com.example.secondexperiment.FORCE_OFFLINE")
                intent.setPackage(packageName)
                sendBroadcast(intent, null)
            }

            R.id.item_drawerLayout -> {
//                val intent = Intent(this, DrawerLayout::class.java)
                startActivity<DrawerLayout>(this)
            }
        }
        return true
    }
}