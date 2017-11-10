package com.test.testapplication.CustomView

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.graphics.Path
import android.icu.text.RelativeDateTimeFormatter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MotionEventCompat
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import android.widget.TextView
import android.widget.Toast
import com.test.testapplication.R
import kotlinx.android.synthetic.main.custom_test.*
import kotlinx.android.synthetic.main.fragment_signin.*
import org.jetbrains.anko.toast
import android.view.GestureDetector
import android.gesture.Gesture
import android.R.attr.action


class CustomAnotherTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_test)
        button.setOnClickListener {
            if (button.text == "Pause") {
                image.pause()
                button.text = "Play"
            } else {
                image.play()
                button.text = "Pause"
            }
        }
        image.setOnClickListener() { view ->
            animateImg()
            Log.e("Clocl", "Js[[r")
        }
//        image.setOnTouchListener(object : OnSwipeTouchListener(this@CustomAnotherTest) {
//            override fun swipeState(direction: Int) {
//                super.swipeState(direction)
//                image.direction = direction
//            }
//
//        })

    }

    //Animate mic image
    private fun animateImg() {
        var anim = true;
        mic.animate().rotation(180f).translationY(-100f).setDuration(1500).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                if (anim) {
                    trash.animate().alpha(1f).setDuration(1500).start()
                    mic.animate().setDuration(1500).rotation(0f).translationY(0f).alpha(0f).setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            mic.alpha = 1f
                            trash.alpha = 0f
                        }
                    }).start()
                    anim = false
                }
            }
        }).start()
    }
}
