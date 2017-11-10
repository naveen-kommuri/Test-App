package com.test.testapplication.pract

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.test.testapplication.R
import kotlinx.android.synthetic.main.activity_pract.*
import org.jetbrains.anko.toast

/**
 * Created by NKommuri on 10/23/2017.
 */
class PractActKT : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pract)
        tecx.text = getVal()
        tecx.setOnClickListener { toast("Hello World") }
    }

    private fun getVal(): String? {
        return ("hello").toString()
    }
}

