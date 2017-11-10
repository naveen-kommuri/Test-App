package com.test.testapplication.CustomView

import android.app.Activity
import android.app.ActivityOptions
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.test.testapplication.MainActivity
import com.test.testapplication.R
import kotlinx.android.synthetic.main.custom_dialog.*
import org.jetbrains.anko.startActivity
import android.support.v4.app.ActivityOptionsCompat


/**
 * Created by NKommuri on 10/17/2017.
 */
class CustomTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog)
        image.setOnClickListener() {
            //            displayDialog()
////            displayDialogFrag()
//            val _drawable = image.drawable as BitmapDrawable
//            val bitmap = _drawable.getBitmap()
//            Toast.makeText(this, "Position---" + image.x + "-----" + image.y, Toast.LENGTH_SHORT).show()
//            val scanBundle = ActivityOptions.makeThumbnailScaleUpAnimation(image, bitmap, image.x.toInt(), image.y.toInt()).toBundle()
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, image as View, "profile")
            startActivity(Intent(this, ImageDialogActivity::class.java), options.toBundle())
        }
        image2.setOnClickListener() {
            //            displayDialog()
//            displayDialogFrag()
//            val _drawable = image2.drawable as BitmapDrawable
//            val bitmap = _drawable.getBitmap()
            val scanBundle = ActivityOptions.makeScaleUpAnimation(image2, image2.x.toInt(), image2.y.toInt(), 50, 50).toBundle()
            startActivity(Intent(this, ImageDialogActivity::class.java), scanBundle)
        }
    }

    private fun displayDialogFrag() {
        val newFragment = CustomDialogFragment()
        newFragment.show(fragmentManager, "dialog")
    }

    private fun displayDialog() {
        val inflater = LayoutInflater.from(this@CustomTestActivity)
        val view = inflater.inflate(R.layout.dialog_custom_img, null)
        val image = view.findViewById<ImageView>(R.id.img)
        image.setImageDrawable(ResourcesCompat.getDrawable(resources, R.mipmap.ic_circle, null))
//        image.layoutParams = LinearLayout.LayoutParams(500, 500);
        val alertDialog = Dialog(this@CustomTestActivity)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(view)
        alertDialog.show()
    }
}