package com.test.testapplication.model;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;

public interface OnImageResult {
    void getImageInfo(Uri imageUri, Bitmap captureImageBitmap, File captureImageFile);

    void deleteProfile();
}