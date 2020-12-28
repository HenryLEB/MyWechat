package com.example.secondexperiment.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.secondexperiment.R
import kotlinx.android.synthetic.main.activity_register_info.*

class RegisterInfoActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_info)
        val userName = intent.getStringExtra("userName").toString()
        val userPassword = intent.getStringExtra("userPassword").toString()
        val sex = intent.getStringExtra("sex")

        text_registerInfo_userName.text = userName
        text_registerInfo_userPwd.text = userPassword
        text_registerInfo_sex.text = sex

        btn_registerInfo_jumpToLogin.setOnClickListener {
//            val intent = Intent(this, LoginActivity::class.java)
            LoginActivity.actionStart(this, userName, userPassword)
        }
    }
}