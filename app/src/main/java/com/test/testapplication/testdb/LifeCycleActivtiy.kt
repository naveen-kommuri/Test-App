package com.test.testapplication.testdb

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.test.testapplication.R
import kotlinx.android.synthetic.main.activity_life_cycle_activtiy.*
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.doIfSdk

class LifeCycleActivtiy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("Calls", "OnCreate")
        setContentView(R.layout.activity_life_cycle_activtiy)
    }

    override fun onStart() {
        super.onStart()
        Log.e("Calls", "OnStart")
    }

    override fun onStop() {
        super.onStop()
        Log.e("Calls", "OnStop")
    }

    override fun onResume() {
        super.onResume()
        Log.e("Calls", "OnResume")
        recyclerView.text = intent.extras.getString("val")
        recyclerView.setOnClickListener { startActivity(Intent(this@LifeCycleActivtiy, TestCActivity::class.java)) }

    }

    override fun onRestart() {
        super.onRestart()
        Log.e("Calls", "OnRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.e("Calls", "OnPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("Calls", "OnDestroy")
    }
}
