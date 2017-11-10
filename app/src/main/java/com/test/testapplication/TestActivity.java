package com.test.testapplication;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.test.testapplication.testdb.LifeCycleActivtiy;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    ImageView imageView;
    Button button;
    LinearLayout spaceLayout, buttonLayout;
    int maxX, maxY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        final LinearLayout mainLayout = (LinearLayout) this.getLayoutInflater().inflate(R.layout.activity_test, null);
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        // set a global layout listener which will be called when the layout pass is completed and the view is drawn
        mainLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        //Remove the listener before proceeding
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            mainLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            mainLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }
//                        DisplayMetrics displayMetrics = new DisplayMetrics();
//                        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//                        int height = displayMetrics.heightPixels;
//                        int width = displayMetrics.widthPixels;
                        Log.e("Outside", " space XB= " + spaceLayout.getX() + " space YB= " + spaceLayout.getY() + " End space Y" + spaceLayout.getHeight());
                        Log.e("Outside", "buttonLayoutB= " + button.getX() + " YB= " + buttonLayout.getY());
                        maxX = spaceLayout.getWidth();
                        maxY = spaceLayout.getHeight();
                        Log.e("maxX " + maxX, "maxY " + maxY);
                        done(maxX, maxY);
                    }
                }
        );
        setContentView(mainLayout);
        button = findViewById(R.id.button);
        spaceLayout = findViewById(R.id.spaceLayout);
        buttonLayout = findViewById(R.id.buttonLayout);

    }


    private void done(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
    }

    @Nullable
    public void animate(View view) {

    }

    public void stopAnimation(View view) {
    }
}
