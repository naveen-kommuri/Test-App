package com.test.testapplication.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.test.testapplication.R;

/**
 * Created by NKommuri on 10/17/2017.
 */

public class CustomImage extends View {
    Bitmap bitmap;
    int valX, valY;
    int bitmapX, bitmapY;
    int appendX, appendY;
    int rotate = 0;
    boolean isPlay = false;
    /*Left=0
    Top=1
    Right=2
    Bottom=3*/
    int direction = -1;

    public CustomImage(Context context) {
        super(context);
        valY = 450;
        valX = 450;
        appendX = 1;
        appendY = 1;
    }

    public CustomImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_circle);
//
        Matrix matrix = new Matrix();

        if (isPlay) {
            if (valX >= canvas.getWidth() - bitmap.getWidth() || direction == 0)
                appendX = -1;
            if (valX <= 0 || direction == 2)
                appendX = 1;
            if (valY >= canvas.getHeight() - bitmap.getHeight() || direction == 1)
                appendY = -1;
            if (valY <= 0 || direction == 3)
                appendY = 1;
            direction = -1;
            valX += appendX;
            valY += appendY;

            rotate = rotate + 1;
        }
        matrix.postRotate(rotate, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        matrix.postTranslate(valX, valY);
        canvas.drawBitmap(bitmap, matrix, null);
        invalidate();
    }

    public void pause() {
        isPlay = false;
        this.invalidate();
    }

    public void play() {
        isPlay = true;
        this.invalidate();
    }

    public void setDirection(int direction) {
        this.direction = direction;
        invalidate();
    }
}
