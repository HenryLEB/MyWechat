package com.example.secondexperiment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.secondexperiment.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_drawer_layout.*

class DrawerLayout : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        设置Toolbar
        setContentView(R.layout.activity_drawer_layout)
        setSupportActionBar(toolbar_drawerLayout)
        fab_drawerLayout.setOnClickListener { view ->
            Snackbar.make(view, "Data deleted", Snackbar.LENGTH_SHORT)
                .setAction("Undo") {
                    Toast.makeText(this, "DATA RESTORED", Toast.LENGTH_SHORT).show()
                }
                .show()
        }


//        侧滑菜单
        navView.setCheckedItem(R.id.navCall)
        navView.setNavigationItemSelectedListener {
            drawerLauout.closeDrawers()
            true
        }
    }

    override fun onClick(v: View?) {
        when (v) {
        }
    }
}