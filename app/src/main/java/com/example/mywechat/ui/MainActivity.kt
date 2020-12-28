package com.example.mywechat.ui

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.mywechat.sunnyweather.MainWeatherActivity
import com.example.secondexperiment.R
import com.example.secondexperiment.model.MSGDataBaseHelper
import com.example.secondexperiment.ui.BaseActivity
import com.example.secondexperiment.ui.ChatActivity
import com.example.secondexperiment.ui.DrawerLayout
import com.example.secondexperiment.ui.FunctionActivity
import com.example.viewpagertest.*
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_friend_list.*

class MainActivity : BaseActivity() {
//        private var mViewPager2: ViewPager2? = null
//    private var mTabLayout: TabLayout? = null
    private lateinit var manager: NotificationManager
    private lateinit var mViewPager2: ViewPager2
    private lateinit var mTabLayout: TabLayout
    private lateinit var mAdapter: ViewPagerAdapter
    //    private var listFragment: ArrayList<Fragment>? = null
    private val list_icons = listOf<Int>(
        R.mipmap.icon_msg,
        R.mipmap.icon_friend,
        R.mipmap.icon_find,
        R.mipmap.icon_me
    )

    private val list_icons_selected = listOf<Int>(
        R.mipmap.icon_msg_selected,
        R.mipmap.icon_friend_selected,
        R.mipmap.icon_find_selected,
        R.mipmap.icon_me_selected
    )

    private val listIconName = listOf<String>(
        "微信", "好友", "发现", "我"
    )

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //        设置ToolBar
        setSupportActionBar(toolbar_friend)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        //        发送通知
        manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("message", "消息", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }

//        initToolBar()
//        initFragmentList()
//        初始化 Fragment 数组
        val listFragment = ArrayList<Fragment>()
        listFragment?.add(MsgFragment())
        listFragment?.add(FriendFragment())
        listFragment?.add(FindFragment())
        listFragment?.add(MeFragment())

        mViewPager2 = findViewById<ViewPager2>(R.id.viewpager2)
        mAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle, listFragment)
        mViewPager2.setAdapter(mAdapter)

//        var mTabLayout: TabLayout? = null
        mTabLayout = findViewById<TabLayout>(R.id.tabLayout)

        for (i in list_icons.indices) {

            val tab = mTabLayout.newTab()
            val view = layoutInflater.inflate(R.layout.tab_custom, null)
            val tv_tab: TextView = view.findViewById<View>(R.id.tv_tab) as TextView
            tv_tab.text = listIconName[i]
            val img_tab: ImageView = view.findViewById(R.id.img_tab) as ImageView
            img_tab.setImageResource(list_icons[i])
            tab.customView = view
            mTabLayout.addTab(tab)
        }



        mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            @RequiresApi(Build.VERSION_CODES.M)
            @SuppressLint("ResourceAsColor")
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mViewPager2.currentItem = tab!!.position
                val view = layoutInflater.inflate(R.layout.tab_custom, null)
//                val tv_tab: TextView = view.findViewById<View>(R.id.tv_tab) as TextView
//                tv_tab.text = listIconName[tab!!.position]
//                val img_tab: ImageView = view.findViewById(R.id.img_tab) as ImageView
//                img_tab.setImageResource(list_icons_selected[tab!!.position])
//                tab.customView = view
//                tab.setCustomView()
                //                imgTab.setImageResource(R.drawable.mojie);
//                tab.setCustomView(view);
//                tab.setText(R.string.app_name)
                toolbar_friend.title = listIconName[tab!!.position]
//                Log.d("asasasas", tab.toString())
//                tab.text = "222"
                runOnUiThread {
//                    tab.CustomView().findViewById(R.id.img_tab).setFocusable(true);
                    tab.customView?.findViewById<TextView>(R.id.tv_tab)?.setTextColor(resources.getColor(R.color.green, null))
                    tab.customView?.findViewById<ImageView>(R.id.img_tab)?.setImageResource(list_icons_selected[tab!!.position])
                }
            }

            @RequiresApi(Build.VERSION_CODES.M)
            @SuppressLint("ResourceAsColor")
            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.customView?.findViewById<TextView>(R.id.tv_tab)?.setTextColor(resources.getColor(R.color.black, null))
                tab.customView?.findViewById<ImageView>(R.id.img_tab)?.setImageResource(list_icons[tab!!.position])
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
        mViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
//                Log.d("bbb", position.toString())
                mTabLayout.setScrollPosition(position, 0f, false)
                mTabLayout.getTabAt(position)!!.select()
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })

//        tab.customView?.findViewById<TextView>(R.id.tv_tab)?.setTextColor(resources.getColor(R.color.green, null))
        mTabLayout.getTabAt(0)?.customView?.findViewById<TextView>(R.id.tv_tab)?.setTextColor(resources.getColor(R.color.green, null))
        mTabLayout.getTabAt(0)?.customView?.findViewById<ImageView>(R.id.img_tab)?.setImageResource(list_icons_selected[0])
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.friend_list_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
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
                com.example.secondexperiment.utils.startActivity<FunctionActivity>(this)
            }
            R.id.toolbar_offline -> {
                val intent = Intent("com.example.mywechat.FORCE_OFFLINE")
                intent.setPackage(packageName)
                sendBroadcast(intent, null)
            }

            R.id.item_drawerLayout -> {
//                val intent = Intent(this, DrawerLayout::class.java)
                com.example.secondexperiment.utils.startActivity<DrawerLayout>(this)
            }

            R.id.toolbar_func_weather -> {
                com.example.secondexperiment.utils.startActivity<MainWeatherActivity>(this)
            }
        }
//        return true
        return super.onOptionsItemSelected(item)
    }
//    private fun initToolBar() {
//        when (mT)
//    }


//    private fun initFragmentList() {
//        listFragment.add(FindFragment())
//        listFragment.add(MeFragment())
//        listFragment.add(MsgFragment())
//        listFragment.add(FriendFragment())
//    }
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
}