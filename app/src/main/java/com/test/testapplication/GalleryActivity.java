package com.test.testapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileFilter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.test.testapplication.CustomCameraActivity.IMAGE_DIRECTORY_NAME;

public class GalleryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    String[] supportedTypes = new String[]{"JPG", "JPEG", "PNG"};
    ArrayList<FileItem> fileItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        loadPermissions();
    }

    private void loadPermissions() {
        if (isStoragePermission(GalleryActivity.this)) {
            openGallery();
        } else {
            requestStoragePermission(100);
        }
    }

    private void openGallery() {
        recyclerView = findViewById(R.id.recyclerView);
        TextView tv_no_captures = findViewById(R.id.tv_no_capture);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        fileItems = new ArrayList<>();
        getFiles(new File(Environment.getExternalStorageDirectory(), IMAGE_DIRECTORY_NAME).getAbsolutePath());
        if (fileItems.size() == 0) {
            tv_no_captures.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setLayoutManager(gridLayoutManager);
            customAdapter = new CustomAdapter(fileItems);
            recyclerView.setAdapter(customAdapter);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GalleryActivity.this, CustomCameraActivity.class));
            }
        });
    }

    boolean isStoragePermission(Context context) {
        int readPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writePermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int camera = ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        return (readPermission == PackageManager.PERMISSION_GRANTED && writePermission == PackageManager.PERMISSION_GRANTED && camera == PackageManager.PERMISSION_GRANTED);
    }


    private void requestStoragePermission(int permissionType) {
        ActivityCompat.requestPermissions(GalleryActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, permissionType);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //   super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isGranted = true;
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
                break;
            }
        }
        if (isGranted) {
            openGallery();
        } else {
            new AlertDialog.Builder(GalleryActivity.this).setMessage("Permissions Required").setNeutralButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    loadPermissions();
                }
            }).show();
//            requestStoragePermission(requestCode);
        }
    }

    class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        ArrayList<FileItem> fileItems;

        public CustomAdapter(ArrayList<FileItem> fileItems) {
            this.fileItems = fileItems;
        }


        class MyViewHolder extends RecyclerView.ViewHolder {

            private ImageView iv_thumbnail;

            public MyViewHolder(View view) {
                super(view);
                iv_thumbnail = (ImageView) view.findViewById(R.id.iv_item);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_gallery, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            final MyViewHolder holder1 = (MyViewHolder) holder;

//            holder1.iv_thumbnail.setImageURI(Uri.fromFile(fileItems.get(position).getFile()));

            Glide.with(GalleryActivity.this).
                    load(Uri.fromFile(fileItems.get(position).getFile())).asBitmap()
                    .placeholder(R.drawable.nodocument)
                    .centerCrop()
                    .into(new BitmapImageViewTarget(holder1.iv_thumbnail) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            holder1.iv_thumbnail.setImageBitmap(resource);
//                            mIvProfile.setImageBitmap(resource);
                        }
                    });
        }

        @Override
        public int getItemCount() {
            return fileItems.size();
        }
    }

    private void getFiles(String pathname) {
        File sd;
        File[] files, directories;
        if (pathname == null)
            sd = Environment.getExternalStorageDirectory();
        else
            sd = new File(pathname);

        files = sd.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
//                return file.isDirectory() || Arrays.asList(supportedTypes).contains(FilenameUtils.getExtension(file.getName()).toUpperCase()) || Arrays.asList(videoFormats).contains(FilenameUtils.getExtension(file.getName()).toUpperCase()) || Arrays.asList(imageFormats).contains(FilenameUtils.getExtension(file.getName()).toUpperCase());
                return Arrays.asList(supportedTypes).contains(FilenameUtils.getExtension(file.getName()).toUpperCase());
            }
        });
        if (files != null)
            for (int i = 0; i < files.length; i++) {
                fileItems.add(new FileItem(files[i]));
            }
        directories = sd.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });
        if (directories != null)
            for (int i = 0; i < directories.length; i++) {
                getFiles(directories[i].getAbsolutePath());
            }
    }

}
