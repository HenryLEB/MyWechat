package com.example.mywechat.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mywechat.model.Session
import com.example.secondexperiment.R
import com.example.secondexperiment.ui.ChatActivity
import com.example.secondexperiment.utils.startActivity
import kotlinx.android.synthetic.main.item_session.view.*

class SessionAdapter(private val list_Sessions: List<Session>): RecyclerView.Adapter<SessionAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_session, parent, false)
        val viewHolder = ViewHolder(view)

//        会话点击事件
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val session = list_Sessions[position]
            Toast.makeText(parent.context, "you clicked ${session.sessionName}", Toast.LENGTH_SHORT).show()

            startActivity<ChatActivity>(parent.context) {
                putExtra("friendName", session.sessionName)
            }

        }
        Log.d("cccc","sessionList.size.toString()")

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val session = list_Sessions[position]
        holder.sessionHeadPortrait.setImageResource(session.sessionHeadPortrait)
        holder.sessionName.text = session.sessionName
        holder.latestMsg.text = session.latestMsg
        holder.time.text = session.time
    }

    override fun getItemCount() = list_Sessions.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val sessionHeadPortrait: ImageView = view.findViewById(R.id.headPortrait)
        val sessionName: TextView = view.findViewById(R.id.session_name)
        val latestMsg: TextView = view.findViewById(R.id.latestMsg)
        val time: TextView = view.findViewById(R.id.session_latestTime)
    }
}