package com.test.testapplication.pract;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.test.testapplication.R;

import org.jetbrains.annotations.Contract;

public class PractActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pract);
        TextView textView = findViewById(R.id.tecx);
        textView.setText((getVal() == null?"1":"2"));
    }

    @Nullable
    private String getVal() {
        if (1 == 2)
            return "1";
        return null;
    }
}
