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
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.test.testapplication.Utils.DbHelper;
import com.test.testapplication.Utils.MyGridView;
import com.test.testapplication.adapter.CustomAdapter;
import com.test.testapplication.model.Invoice;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileFilter;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.test.testapplication.CustomCameraActivity.IMAGE_DIRECTORY_NAME;
import static com.test.testapplication.Utils.CommonUtil.getDate;

public class GalleryActivity extends Fragment {
    //public class GalleryActivity extends FragmentActivity {
    RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    String[] supportedTypes = new String[]{"JPG", "JPEG", "PNG"};
    ArrayList<Invoice> invoiceItems;
    Map<String, ArrayList<Invoice>> fileItemsMap;
    public static int TYPE_GRID = 1;
    public static int TYPE_LIST = 2;
    EditText et_search;
    int listType;
    View view;

    public GalleryActivity() {

    }

    public static GalleryActivity newInstance(int listType) {
        GalleryActivity fragment = new GalleryActivity();
        Bundle args = new Bundle();
        args.putInt("listType", listType);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_gallery);
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_gallery, container, false);
        if (getArguments() != null) {
            listType = getArguments().getInt("listType");
        }
        et_search = view.findViewById(R.id.et_search);
//        et_search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String searchStr = editable.toString();
//                Toast.makeText(getActivity(), "...." + searchStr, Toast.LENGTH_SHORT).show();
//                if (customAdapter != null) {
//                    customAdapter.getFilter().filter(searchStr);
//                }
//            }
//        });
        loadPermissions();
        return view;
    }

    private void loadPermissions() {
        if (isStoragePermission(getActivity())) {
            openGallery();
        } else {
            requestStoragePermission(100);
        }
    }

    private void openGallery() {
        recyclerView = view.findViewById(R.id.recyclerView);
        TextView tv_no_captures = view.findViewById(R.id.tv_no_capture);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        invoiceItems = new ArrayList<>();
//        getFiles(new File(Environment.getExternalStorageDirectory(), IMAGE_DIRECTORY_NAME).getAbsolutePath());
        getFiles();
        if (invoiceItems.size() == 0) {
            tv_no_captures.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            loadIntoMap(invoiceItems);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//            recyclerView.setLayoutManager(gridLayoutManager);
            if (listType == TYPE_GRID)
                recyclerView.setAdapter(new CustomAdapter(fileItemsMap, TYPE_GRID, getActivity()));
            else {
                customAdapter = new CustomAdapter(invoiceItems, TYPE_LIST, getActivity());
                recyclerView.setAdapter(customAdapter);
            }
        }
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                customAdapter = new CustomAdapter(fileItems, TYPE_LIST);
//                recyclerView.setAdapter(customAdapter);
                startActivity(new Intent(getActivity(), CustomCameraActivity.class));
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(getActivity(), "Long Click", Toast.LENGTH_SHORT).show();
                recyclerView.setAdapter(new CustomAdapter(fileItemsMap, TYPE_GRID, getActivity()));
                return true;
            }
        });
    }

    private void loadIntoMap(ArrayList<Invoice> invoiceItems) {
        fileItemsMap = new LinkedHashMap<>();
        for (Invoice item : invoiceItems) {
            String key = getDate(item.getInvoiceDate());
            ArrayList<Invoice> fileItems1;
            if (fileItemsMap.containsKey(key)) {
                fileItems1 = fileItemsMap.get(key);
            } else {
                fileItems1 = new ArrayList<>();
            }
            fileItems1.add(item);
            fileItemsMap.put(key, fileItems1);
        }
    }


    boolean isStoragePermission(Context context) {
        int readPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writePermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int camera = ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        return (readPermission == PackageManager.PERMISSION_GRANTED && writePermission == PackageManager.PERMISSION_GRANTED && camera == PackageManager.PERMISSION_GRANTED);
    }


    private void requestStoragePermission(int permissionType) {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, permissionType);
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
            new AlertDialog.Builder(getActivity()).setMessage("Permissions Required").setNeutralButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    loadPermissions();
                }
            }).show();
//            requestStoragePermission(requestCode);
        }
    }

    //    class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private void getFiles() {
        DbHelper dbHelper = new DbHelper(getActivity());
        invoiceItems.addAll(dbHelper.getInvoices(null));
    }

//    private void getFiles(String pathname) {
//        File sd;
//        File[] files, directories;
//        if (pathname == null)
//            sd = Environment.getExternalStorageDirectory();
//        else
//            sd = new File(pathname);
//
//        files = sd.listFiles(new FileFilter() {
//            @Override
//            public boolean accept(File file) {
////                return file.isDirectory() || Arrays.asList(supportedTypes).contains(FilenameUtils.getExtension(file.getName()).toUpperCase()) || Arrays.asList(videoFormats).contains(FilenameUtils.getExtension(file.getName()).toUpperCase()) || Arrays.asList(imageFormats).contains(FilenameUtils.getExtension(file.getName()).toUpperCase());
//                return Arrays.asList(supportedTypes).contains(FilenameUtils.getExtension(file.getName()).toUpperCase());
//            }
//        });
//        if (files != null)
//            for (int i = 0; i < files.length; i++) {
//                fileItems.add(new FileItem(files[i]));
//            }
//        directories = sd.listFiles(new FileFilter() {
//            @Override
//            public boolean accept(File file) {
//                return file.isDirectory();
//            }
//        });
//        if (directories != null)
//            for (int i = 0; i < directories.length; i++) {
//                getFiles(directories[i].getAbsolutePath());
//            }
//    }

}
