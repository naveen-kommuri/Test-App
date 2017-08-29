package com.test.testapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.test.testapplication.home.Main2Activity;

import java.io.IOException;

public class PreviewActivity extends AppCompatActivity {
    ImageView iv_capture_img_preview, iv_proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        iv_capture_img_preview = findViewById(R.id.iv_capture_img_preview);
        iv_proceed = findViewById(R.id.iv_proceed);
        if (getIntent().getExtras() != null && getIntent().getStringExtra("imageUrl").length() != 0) {
            Uri imageUrl = Uri.parse(getIntent().getStringExtra("imageUrl"));
            previewImage(imageUrl);
        }
        iv_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PreviewActivity.this, Main2Activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    private void previewImage(Uri imageUrl) {
        try {
            Glide.with(PreviewActivity.this).load(imageUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv_capture_img_preview);
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUrl);
//            Matrix matrix = new Matrix();
//            matrix.postRotate(90);
//            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//            iv_capture_img_preview.setImageBitmap(rotatedBitmap);
//        } catch (IOException e) {
//            e.printStackTrace();
        } catch (Exception e) {
            previewImage(imageUrl);
        }
    }
}
