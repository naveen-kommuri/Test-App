package com.test.testapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

/**
 * Created by NKommuri on 8/17/2017.
 */

public class CustomCameraActivity extends Activity {
    private static final String TAG = "CustomCameraActivity";
    private Camera camera;
    private CameraPreview mPreview;
    public static final String IMAGE_DIRECTORY_NAME = "TaxNGo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (checkCameraHardware(CustomCameraActivity.this)) {
            camera = getCameraInstance();
            camera.enableShutterSound(true);
            camera.setDisplayOrientation(90);
            Camera.Parameters parameters = camera.getParameters();
//            parameters.setJpegQuality(100);
//            parameters.setPictureSize(720, 1280);
            parameters.setRotation(90);
            camera.setParameters(parameters);
            mPreview = new CameraPreview(this, camera);
            FrameLayout preview = findViewById(R.id.camera_preview);
            preview.addView(mPreview);
        }
        ImageButton captureButton = findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        camera.takePicture(new Camera.ShutterCallback() {
                            public void onShutter() {
                                AudioManager mgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                                mgr.playSoundEffect(AudioManager.FLAG_PLAY_SOUND);
                            }
                        }, null, mPicture);
                    }
                }
        );

    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null) {
                Log.d(TAG, "Error creating media file, check storage permissions: ");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
            camera.release();
            startActivity(new Intent(CustomCameraActivity.this, PreviewActivity.class).putExtra("imageUrl", Uri.fromFile(pictureFile).toString()));
        }
    };

    private static File getOutputMediaFile(int type) {

        // External sdcard location
//        File mediaStorageDir = new File(
//                Environment
//                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
//                IMAGE_DIRECTORY_NAME);
        File mediaStorageDir = new File(
                Environment
                        .getExternalStorageDirectory(),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {

                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "image_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (camera != null)
            camera.release();
    }

    private boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c; // returns null if camera is unavailable
    }
}
