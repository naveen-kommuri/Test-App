package com.test.testapplication.testdb

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.test.testapplication.R
import com.test.testapplication.application.MyApplication
import com.test.testapplication.database.Poems

import kotlinx.android.synthetic.main.activity_test_db_activtiy.*
import java.util.*
import java.util.concurrent.Executor

class TestDbActivtiy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_db_activtiy)
        setSupportActionBar(toolbar)
        var appl = application as MyApplication
        fab.setOnClickListener { view ->
            //            Thread() {
//                run {
//                    val r = Random()
//                    val nextInt = r.nextInt(150)
//                    val poems = Poems()
//                    poems.poem = "--- $nextInt"
//                    poems.meaning = "--$nextInt---"
//                    appl.db.poemsDao().insertAll(poems)
//                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show()
//                }
//            }.start()

            startActivity(Intent(this@TestDbActivtiy, LifeCycleActivtiy::class.java).putExtra("val", "1"))
        }
    }

}
