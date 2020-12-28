package com.example.secondexperiment.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.secondexperiment.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() , View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        btn_register_register.setOnClickListener(this)
        btn_register_clear.setOnClickListener(this)

        val data_city = listOf<String>("广州","深圳","佛山","珠海")
        val adapter_city = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data_city)
        Spinner_register_city.adapter = adapter_city

        val data_college = listOf<String>("广东东软学院","广东中医药大学","广东工业大学","华南理工大学",
            "华南师范大学","华南农业大学")
        val adapter_college = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data_college)
        autoText_register_college.setAdapter(adapter_college)



    }

    override fun onClick(v: View?) {
        when (v) {
            btn_register_register -> {
//                获取用户名密码性别出生年月邮箱所在城市、学校
                val userName = edit_register_userName.text.toString()
                val userPassWord = edit_register_userPassword.text.toString()
                val confirmPwd = edit_register_confirmPassword.text.toString()
                val sex = findViewById<RadioButton>(RG_register_sex.checkedRadioButtonId).text
//                val birthday
                val email = edit_register_email.text.toString()
                val city = Spinner_register_city.selectedItem.toString()
                val college = autoText_register_college.text.toString()
                val email_RegexStr = Regex("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")
                if (userName.length < 3 || userName.length > 10) {
                    edit_register_userName.setError("用户帐号长度不符合要求，请输入4-10个字符！")
                }
                else if (userName.contains(' ')) {
                    edit_register_userName.setError("用户名不能包含空格")
                }
                else if (userPassWord != confirmPwd) {
                    edit_register_confirmPassword.setError("两次密码不一致！")
                }
                else if (email_RegexStr.matches(email) == false) {
                    edit_register_email.setError("邮箱格式不正确！")
                }
                else if (city==null) {
                    Toast.makeText(this, "请选择城市!", Toast.LENGTH_SHORT).show()
                }
                else if (college==null) {
                    autoText_register_college.setError("学校不能为空")
                }
                else {
                    val intent = Intent(this, RegisterInfoActivity::class.java).apply {
                        putExtra("userName", userName)
                        putExtra("userPassword", userPassWord)
                        putExtra("sex", sex)
                    }
                    startActivity(intent)
                }

//                Toast.makeText(this, sex, Toast.LENGTH_SHORT).show()
            }
            btn_register_clear -> {
                edit_register_userName.setText("")
                edit_register_userPassword.setText("")
                edit_register_confirmPassword.setText("")
                edit_register_email.setText("")
                Spinner_register_city.setSelection(0)
                autoText_register_college.setText("")
            }

        }
    }
}