package com.test.testapplication;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    TabItem ti_all, ti_in_process, ti_rejected;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ti_all = findViewById(R.id.ti_all);
        ti_in_process = findViewById(R.id.ti_in_process);
        ti_rejected = findViewById(R.id.ti_rejected);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Toast.makeText(TestActivity.this, "___" + tab.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
