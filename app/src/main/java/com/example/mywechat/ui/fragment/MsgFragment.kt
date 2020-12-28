package com.example.viewpagertest

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mywechat.adapter.SessionAdapter
import com.example.mywechat.model.Session
import com.example.secondexperiment.R
import com.example.secondexperiment.model.MSGDataBaseHelper
import kotlinx.android.synthetic.main.item_session.*
import kotlinx.android.synthetic.main.msg_page.*
import kotlin.random.Random

class MsgFragment : Fragment() {
    private val sessionList = ArrayList<Session>()
    private lateinit var msgHelper: MSGDataBaseHelper
    private val sessionHeadPortraitList = listOf<Int>(R.drawable.jfm, R.drawable.bianmu, R.drawable.hashiqi,
        R.drawable.jfm, R.drawable.taidi, R.drawable.samoye, R.drawable.kj)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        msgHelper = MSGDataBaseHelper(activity as Context, "chatDatabase", 2)
        return inflater.inflate(R.layout.msg_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initSessions()
        Log.d("aaaa",sessionList.size.toString())
        val layoutManager = LinearLayoutManager(activity as Context)
        recycleList_session.layoutManager = layoutManager
        val adapter = SessionAdapter(sessionList)
        recycleList_session.adapter = adapter
        Log.d("bbbbb",layoutManager.toString())

    }

    private fun initSessions() {

//        Log.d("aaaa","shda")
        val db = msgHelper.readableDatabase
        val cursor = db.rawQuery("select * from message where sender='哈士奇' group by recipient " +
                "union all " +
                "select * from message where recipient='哈士奇' group by sender", null)
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                val latestMsg = cursor.getString(cursor.getColumnIndex("content"))
                val sessionName = cursor.getString(cursor.getColumnIndex("recipient"))
                val sendTime = cursor.getString(cursor.getColumnIndex("send_time"))
                sessionList.add(Session(sessionHeadPortraitList[(0..6).random()], sessionName, latestMsg, sendTime))
            }
        }


//        sessionList.add(Session(R.drawable.jfm, "喵星人", "在吗", "11:10"))
//        sessionList.add(Session(R.drawable.kj, "小柯基", "在吗", "11:10"))
//        sessionList.add(Session(R.drawable.jm, "哈哈哈", "在吗", "11:10"))
//        sessionList.add(Session(R.drawable.hashiqi, "哈士奇", "在吗", "11:10"))
//        sessionList.add(Session(R.drawable.bianmu, "边牧犬", "在吗", "11:10"))
//        sessionList.add(Session(R.drawable.taidi, "小泰迪", "在吗", "11:10"))
//        sessionList.add(Session(R.drawable.samoye, "萨摩耶", "在吗", "11:10"))
//        sessionList.add(Session(R.drawable.jsx, "小仓鼠", "在吗", "11:10"))
//        sessionList.add(Session(R.drawable.lggcs, "嘿嘿嘿", "在吗", "11:10"))
//        sessionList.add(Session(R.drawable.yd, "小祖宗", "在吗", "11:10"))
    }
}