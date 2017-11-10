package com.test.testapplication.CustomView

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.test.testapplication.R
import android.R.attr.y
import android.R.attr.x
import android.view.WindowManager


class ImageDialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dialog_custom_img)

        val params = window.attributes
        params.height = 250
        params.width = 250
        this.window.attributes = params
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFinishAfterTransition()
    }
}
