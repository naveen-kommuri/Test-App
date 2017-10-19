package com.test.testapplication.CustomView

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.test.testapplication.R

/**
 * Created by NKommuri on 10/17/2017.
 */
class CustomTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val customImage = CustomImage(this)
        customImage.setOnClickListener { view -> Toast.makeText(this, "Print", Toast.LENGTH_SHORT).show() }
        setContentView(customImage)
    }
}