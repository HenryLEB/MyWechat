package com.example.secondexperiment.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.secondexperiment.R
import com.example.secondexperiment.adapter.MsgAdapter
import com.example.secondexperiment.model.MSGDataBaseHelper
import com.example.secondexperiment.model.Msg
import kotlinx.android.synthetic.main.activity_chat.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : BaseActivity(), View.OnClickListener {
    private val msgList = ArrayList<Msg>()

    private lateinit var adapter: MsgAdapter
    private lateinit var friendName: String
    private lateinit var chatHelper: MSGDataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //    获取聊天对象用户
        friendName = intent.getStringExtra("friendName").toString()

        chatHelper = MSGDataBaseHelper(this, "chatDatabase", 2)

        setContentView(R.layout.activity_chat)
        initMsg()
        val layoutManager = LinearLayoutManager(this)
        recycleView_chat.layoutManager = layoutManager
        if (!::adapter.isInitialized) {
            adapter = MsgAdapter(msgList)
        }
        recycleView_chat.adapter = adapter
        recycleView_chat.scrollToPosition(msgList.size - 1)
        btn_chat_send.setOnClickListener(this)



    }






    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        when (v) {
            btn_chat_send -> {
                val content = edit_chat_input.text.toString()
                if (content.isNotEmpty()) {
                    val msg = Msg(content, Msg.TYPE_SENT)
                    msgList.add(msg)
                    val db = chatHelper.readableDatabase
//                    获取本地时间
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    val date = Date(System.currentTimeMillis())
//                    Log.d("sadhioas", dateFormat.format(date).toString())
//                    Log.d("sadhioas", "101010")
                    val currentTime = dateFormat.format(date).toString()
//
                    db.execSQL(
                        "insert into message (sender, recipient, content, send_time) values(?, ?, ?, ?)",
//                    db.execSQL("insert into message (sender, recipient, content, send_time) values('哈士奇', '$friendName', '$content', '2020-12-10')")
                        arrayOf("哈士奇", friendName, content, currentTime)
//                                arrayOf("哈士奇", friendName, content, "2020-12-5")
                    )
                    adapter.notifyItemInserted(msgList.size - 1) // 当有新消息时，刷新RecyclerView中的显示
                    recycleView_chat.scrollToPosition(msgList.size - 1)  // 将 RecyclerView定位到最后一行
                    edit_chat_input.setText("") // 清空输入框中的内容
                }
            }
        }
    }

    private fun initMsg() {
        val db = chatHelper.readableDatabase
        val cursor = db.rawQuery(
            "select * from message where (sender='哈士奇' and recipient='$friendName') " +
                    "or (sender='$friendName' and recipient='哈士奇')", null
        )
        while (cursor.moveToNext()) {
            val myName = cursor.getString(cursor.getColumnIndex("sender")).toString()
                val friendName = cursor.getString(cursor.getColumnIndex("recipient")).toString()
                val content = cursor.getString(cursor.getColumnIndex("content")).toString()
                if (myName == "哈士奇") {
                    msgList.add(Msg(content, Msg.TYPE_SENT))
                    continue
                }
                Log.d("33333", "222222222")
                msgList.add(Msg(content, Msg.TYPE_RECEIVED))
        }
        cursor.close()
    }
}