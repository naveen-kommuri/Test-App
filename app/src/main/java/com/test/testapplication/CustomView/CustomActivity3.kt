package com.test.testapplication.CustomView

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.test.testapplication.R

import kotlinx.android.synthetic.main.activity_custom3.*
import kotlinx.android.synthetic.main.content_custom3.*

class CustomActivity3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom3)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            Toast.makeText(this@CustomActivity3, "Clicked", Toast.LENGTH_SHORT).show()
        }
        text22.setOnClickListener { Toast.makeText(this@CustomActivity3, "Clicked", Toast.LENGTH_SHORT).show() }
    }

}
