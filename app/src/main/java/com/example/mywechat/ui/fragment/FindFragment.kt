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
import kotlinx.android.synthetic.main.msg_page.*

open class FindFragment : Fragment() {
    private val sessionList = ArrayList<Session>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.msg_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("aaaa","实打实的")
        initSessions()
        val layoutManager = LinearLayoutManager(activity as Context)
        recycleList_session.layoutManager = layoutManager
        val adapter = SessionAdapter(sessionList)
        recycleList_session.adapter = adapter
    }

    private fun initSessions() {
//        Log.d("aaaa","shda")
        sessionList.add(Session(R.drawable.jfm, "喵星人", "在吗", "11:10"))
        sessionList.add(Session(R.drawable.kj, "小柯基", "在吗", "11:10"))
        sessionList.add(Session(R.drawable.jm, "哈哈哈", "在吗", "11:10"))
        sessionList.add(Session(R.drawable.hashiqi, "哈士奇", "在吗", "11:10"))
        sessionList.add(Session(R.drawable.bianmu, "边牧犬", "在吗", "11:10"))
        sessionList.add(Session(R.drawable.taidi, "小泰迪", "在吗", "11:10"))
        sessionList.add(Session(R.drawable.samoye, "萨摩耶", "在吗", "11:10"))
        sessionList.add(Session(R.drawable.jsx, "小仓鼠", "在吗", "11:10"))
        sessionList.add(Session(R.drawable.lggcs, "嘿嘿嘿", "在吗", "11:10"))
        sessionList.add(Session(R.drawable.yd, "小祖宗", "在吗", "11:10"))
    }
}