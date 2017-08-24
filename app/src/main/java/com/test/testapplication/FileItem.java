package com.test.testapplication;

import java.io.File;

/**
 * Created by NKommuri on 8/22/2017.
 */

class FileItem {
    String imageUrl;
    File file;

    public FileItem(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
