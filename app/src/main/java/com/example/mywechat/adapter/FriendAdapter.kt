package com.example.secondexperiment.adapter

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mywechat.ui.MainActivity
import com.example.secondexperiment.R
import com.example.secondexperiment.model.Friend
import com.example.secondexperiment.ui.ChatActivity
import com.example.secondexperiment.ui.FriendList
import com.example.viewpagertest.FriendFragment

class FriendAdapter(val friendList: List<Friend>): RecyclerView.Adapter<FriendAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friend_list_item, parent, false)
        val viewHolder = ViewHolder(view)

//        给好友注册点击事件
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val friend = friendList[position]
            Toast.makeText(parent.context, "you clicked ${friend.name}", Toast.LENGTH_SHORT).show()

            val intent = Intent(parent.context, ChatActivity::class.java).apply {
                putExtra("friendName", friend.name)
            }

            parent.context.startActivity(intent)
        }

        viewHolder.chatWith.setOnClickListener {
            val position = viewHolder.adapterPosition
            val friend = friendList[position]
            Toast.makeText(parent.context, "you clicked ${friend.name}", Toast.LENGTH_SHORT).show()

            val intent = Intent(parent.context, ChatActivity::class.java).apply {
                putExtra("friendName", friend.name)
            }

            parent.context.startActivity(intent)
        }

        viewHolder.callFriend.setOnClickListener {
            if (ContextCompat.checkSelfPermission(parent.context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(parent.context as Activity, arrayOf(android.Manifest.permission.CALL_PHONE), 1)
            } else {
                Log.d("oooo", "aa")
                val activity = parent.context as MainActivity
                activity.call()
//                val fragment = FriendFragment()
//                fragment.call()
            }

        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = friendList[position]
        holder.friendImage.setImageResource(friend.resourceID)
        holder.friendName.text = friend.name
    }

    override fun getItemCount() = friendList.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val friendImage: ImageView = view.findViewById(R.id.image_friend)
        val friendName: TextView = view.findViewById(R.id.text_friend)
        val chatWith: Button = view.findViewById(R.id.btn_chatWithFriend)
        val callFriend: Button = view.findViewById(R.id.btn_callFriend)
    }

}