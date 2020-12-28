package com.example.viewpagertest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.secondexperiment.R
import com.example.secondexperiment.adapter.FriendAdapter
import com.example.secondexperiment.model.Friend
import com.example.secondexperiment.model.MSGDataBaseHelper
import kotlinx.android.synthetic.main.activity_friend_list.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class FriendFragment : Fragment() {
    private lateinit var manager: NotificationManager
    private val friendList = ArrayList<Friend>()
    private lateinit var chatHelper : MSGDataBaseHelper
    private val friendHeadPortraitList = listOf<Int>(R.drawable.jfm, R.drawable.bianmu, R.drawable.hashiqi,
            R.drawable.jfm, R.drawable.taidi, R.drawable.samoye, R.drawable.kj)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("adsdsa", "i.toString()")
//        val msgHelper = MSGDataBaseHelper(this, "chatDatabase", 2)
//        msgHelper.readableDatabase
        chatHelper = MSGDataBaseHelper(activity as Context, "chatDatabase", 2)


        return inflater.inflate(R.layout.friend_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        for (i in friendList) {
//            Log.d("adsdsa", i.toString())
//        }


        initFriends()
        val layoutManager = LinearLayoutManager(activity as Context)
        recycleList_friendList.layoutManager = layoutManager
        val adapter = FriendAdapter(friendList)
        recycleList_friendList.adapter = adapter

    }

    private fun initFriends() {

        val db = chatHelper.readableDatabase
        val cursor = db.rawQuery("select * from friends", null)
        if (cursor.moveToFirst()) {
            do {
                val friend = cursor.getString(cursor.getColumnIndex("friend_id"))
                friendList.add(Friend(friendHeadPortraitList[(0..6).random()], friend))
            } while (cursor.moveToNext())
        }
        cursor.close()
    }
}