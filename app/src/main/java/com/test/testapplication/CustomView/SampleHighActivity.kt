package com.test.testapplication.CustomView

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import com.test.testapplication.R
import kotlinx.android.synthetic.main.custom_dialog.*
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.ImageView
import com.test.testapplication.CustomHighLightView.CustomHighLightView
import kotlinx.android.synthetic.main.sample_custom_high_light_view.*


/**
 * Created by NKommuri on 10/23/2017.
 */
class SampleHighActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = LayoutInflater.from(this@SampleHighActivity)
        val view = inflater.inflate(R.layout.sample_custom_high_light_view, null)
        setContentView(view)
        val image = findViewById<ImageView>(R.id.image)
        image.viewTreeObserver.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        val offset = IntArray(2)
                        image.getLocationOnScreen(offset)
                        Log.e("x== " + offset[0], " Y===" + offset[1])
                        Log.e("x---- " + image.x, " Y---=" + image.y)
                        // Layout has happened here.
                        val custom = CustomHighLightView(this@SampleHighActivity)
                        custom.centerValX = image.x
                        custom.centerValY = image.y
                        custom.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                        custom.alpha = 0.5f
//
                        if (view != null) {
                            val parent = view.parent as ViewGroup
                            parent?.removeView(view)
                        }
                        val layout = FrameLayout(this@SampleHighActivity)
                        layout.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                        layout.addView(view)
                        layout.addView(custom)
                        setContentView(layout)
                        // Don't forget to remove your listener when you are done with it.
                        image.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                });
    }
}