package com.example.secondexperiment.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mywechat.ui.MainActivity
import com.example.secondexperiment.R
import com.example.secondexperiment.model.MSGDataBaseHelper
import com.example.secondexperiment.model.RemAccontDataBaseHelper
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() , View.OnClickListener{
//    val pref: SharedPreferences = getPreferences(Context.MODE_PRIVATE)
//    val editor: SharedPreferences.Editor = pref.edit()

    companion object {
        fun actionStart(context: Context, userName: String, userPassword: String) {
            val intent = Intent(context, LoginActivity::class.java).apply {
                putExtra("userName", userName)
                putExtra("userPassword", userPassword)
            }
            context.startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        Log.d("bbb", isLogined.toString())
//        saveLoginStatus(loginStatus)
        val editor = getSharedPreferences("loginStatus", MODE_PRIVATE).edit()
        editor.putBoolean("loginStatus", loginStatus)
        editor.apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_login_register.setOnClickListener(this)
        btn_login_login.setOnClickListener(this)
        btn_login_forgetPwd.setOnClickListener(this)

//        数据库
        val dbHelper = RemAccontDataBaseHelper(this, "accountList", 1)
        dbHelper.writableDatabase

//        Toast.makeText(this, getLoginStatus().toString(), Toast.LENGTH_SHORT).show()

//        获取登录状态
//        if (getLoginStatus().toString() == "1") {
//            val intent = Intent(this, FriendList::class.java)
//            startActivity(intent)
//            finish()
//        }

//        if (getLoginStatus() == true) {
//            val intent = Intent(this, FriendList::class.java)
//            startActivity(intent)
//            finish()
//        }

        //        boolean status = ;
        //        判断登陆状态
        val pref_loginStatus = getSharedPreferences("loginStatus", MODE_PRIVATE)
        if (pref_loginStatus.getBoolean("loginStatus", false) == true) {
            val intent = Intent()
            intent.setClass(applicationContext, FriendList::class.java)
            startActivity(intent)
            finish()
        }

        val userName = intent.getStringExtra("userName")
        edit_login_userName.setText(userName)
        edit_login_passWord.setText(intent.getStringExtra("userPassword"))

        val pref = getPreferences(Context.MODE_PRIVATE)
        val isRemember = pref.getBoolean("remember_password", false)
//
        if (isRemember) {
            val account = pref.getString("account", "")
            val password = pref.getString("password", "")

            edit_login_userName.setText(account)
            edit_login_passWord.setText(password)
            checkBox_isRemember.isChecked = true
        }



    }

    override fun onClick(v: View?) {
        val dbHelper = RemAccontDataBaseHelper(this, "accountList", 1)
        val db = dbHelper.readableDatabase
        when (v?.id) {
            R.id.btn_login_register -> {
//                Toast.makeText(this, "222", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_login_login -> {
                val pref = getPreferences(Context.MODE_PRIVATE)
                val editor = pref.edit()
                val account = edit_login_userName.text.toString()
                val password = edit_login_passWord.text.toString()
                if (checkBox_isRemember.isChecked) {

                    editor.putString("account", account)
                    editor.putString("password", password)
                    editor.putBoolean("remember_password", true)
                } else {
                    editor.clear()
                }
                editor.apply()

                loginStatus = true
//                isLogined = 1
//                Log.d("aaa", isLogined.toString())

//                数据持久化 登陆过的保存

                db.execSQL(
                    "insert into account (account, password) values(?, ?)",
                    arrayOf(account, password)
                )
                val cursor = db.rawQuery("select * from account", null)
                while (cursor.moveToNext()) {
                    Log.d("aaa", cursor.getString(cursor.getColumnIndex("account")))
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.btn_login_forgetPwd -> {
//                Toast.makeText(this, "功能有待完善", Toast.LENGTH_SHORT).show()
//                Toast.makeText(this, isLogined.toString(), Toast.LENGTH_SHORT).show()
                val chatHelper = MSGDataBaseHelper(this, "chatDatabase", 2)
                val db =  chatHelper.readableDatabase
                val cursor = db.rawQuery("select * from message", null)
                if (cursor.moveToFirst()) {
                    do {
                        val account = cursor.getString(cursor.getColumnIndex("content"))
                        val password = cursor.getString(cursor.getColumnIndex("sender"))
                        Log.d("LoginActivity", "account is $account")
                        Log.d("LoginActivity", "password is $password")
                    } while (cursor.moveToNext())
                }
                cursor.close()
            }
        }
    }

//    private fun getLoginStatus(): Int {
//        var ret = 0;
//        try {
//            val input = openFileInput("loginStatus")
//            val reader = BufferedReader(InputStreamReader(input))
//            reader.use {
//                ret = reader.read()
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//
//        return ret;
//    }


//    private fun saveLoginStatus(loginStatus: Int) {
//        try {
//            val output = openFileOutput("loginStatus", Context.MODE_PRIVATE)
//            val writer = BufferedWriter(OutputStreamWriter(output))
//
//            writer.use {
//                it.write(loginStatus)
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }





}