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
import com.test.testapplication.Utils.MyGridView;

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

public class GalleryActivity extends Fragment {
    //public class GalleryActivity extends FragmentActivity {
    RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    String[] supportedTypes = new String[]{"JPG", "JPEG", "PNG"};
    ArrayList<FileItem> fileItems;
    Map<String, ArrayList<FileItem>> fileItemsMap;
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
        fileItems = new ArrayList<>();
        getFiles(new File(Environment.getExternalStorageDirectory(), IMAGE_DIRECTORY_NAME).getAbsolutePath());
        if (fileItems.size() == 0) {
            tv_no_captures.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            loadIntoMap(fileItems);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//            recyclerView.setLayoutManager(gridLayoutManager);
            if (listType == TYPE_GRID)
                recyclerView.setAdapter(new CustomAdapter(fileItemsMap, TYPE_GRID));
            else {
                customAdapter = new CustomAdapter(fileItems, TYPE_LIST);
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
                recyclerView.setAdapter(new CustomAdapter(fileItemsMap, TYPE_GRID));
                return true;
            }
        });
    }

    private void loadIntoMap(ArrayList<FileItem> fileItems) {
        fileItemsMap = new LinkedHashMap<>();
        for (FileItem item : fileItems) {
            String key = getDate(item.getFile().lastModified());
            ArrayList<FileItem> fileItems1;
            if (fileItemsMap.containsKey(key)) {
                fileItems1 = fileItemsMap.get(key);
            } else {
                fileItems1 = new ArrayList<>();
            }
            fileItems1.add(item);
            fileItemsMap.put(key, fileItems1);
        }
    }

    private String getDate(long modified) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM yy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(modified);
        String format = simpleDateFormat.format(calendar.getTime());
        Log.e("Converted Date", format);
        return format;
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

    class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
        ArrayList<FileItem> fileItems;
        Map<String, ArrayList<FileItem>> fileItemsList;
        int listType;

        public CustomAdapter(ArrayList<FileItem> fileItems, int listType) {
            this.fileItems = fileItems;
            this.listType = listType;
        }

        public CustomAdapter(Map<String, ArrayList<FileItem>> fileItemsList, int listType) {
            this.fileItemsList = fileItemsList;
            this.listType = listType;
        }

        @Override
        public Filter getFilter() {
            return new SearchFilter();
        }

        public class SearchFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    // No filter implemented we return all the list
                    filterResults.values = fileItems;
                    filterResults.count = fileItems.size();
                } else {
                    List<FileItem> filteredList = new ArrayList<>();
                    for (int i = 0; i < fileItems.size(); i++) {
                        FileItem fileItem = fileItems.get(i);
                        if (fileItem.getFile().getName().toUpperCase().contains(constraint.toString().toUpperCase())) {
                            filteredList.add(fileItem);
                        }
                    }
                    filterResults.values = filteredList;
                    filterResults.count = filteredList.size();
                }
                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                fileItems = (ArrayList<FileItem>) results.values;
                notifyDataSetChanged();
            }
        }


        class ListViewHolder extends RecyclerView.ViewHolder {

            private ImageView iv_thumbnail;
            TextView tv_date, tv_name, tv_size;

            public ListViewHolder(View view) {
                super(view);
                iv_thumbnail = view.findViewById(R.id.imageView);
                tv_name = view.findViewById(R.id.tv_name);
                tv_date = view.findViewById(R.id.tv_date);
                tv_size = view.findViewById(R.id.tv_size);
            }
        }

        class GridViewHolder extends RecyclerView.ViewHolder {
            TextView titleView;
            MyGridView gridView;

            public GridViewHolder(View itemView) {
                super(itemView);
                titleView = itemView.findViewById(R.id.tv_title);
                gridView = itemView.findViewById(R.id.grid_items);
            }
        }

        @Override
        public int getItemViewType(int position) {
            return this.listType;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_GRID) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_gallery, parent, false);
                return new GridViewHolder(itemView);
            } else {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_gallery, parent, false);
                return new ListViewHolder(itemView);
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof GridViewHolder) {
                final GridViewHolder holder1 = (GridViewHolder) holder;
                String key = fileItemsList.keySet().toArray()[position] + "";
                holder1.titleView.setText(key);
                ArrayList<FileItem> _fileItems = fileItemsList.get(key);
                holder1.gridView.setAdapter(new CustomGridAdapter(_fileItems, getActivity()));
            } else if (holder instanceof ListViewHolder) {
                final ListViewHolder listViewHolder = (ListViewHolder) holder;
                listViewHolder.tv_name.setText(fileItems.get(position).getFile().getName());
                listViewHolder.tv_date.setText(getDate(fileItems.get(position).getFile().lastModified()));
                listViewHolder.tv_size.setText(fileItems.get(position).getFile().length() + " Bytes");
                Glide.with(getActivity()).
                        load(Uri.fromFile(fileItems.get(position).getFile())).asBitmap()
                        .placeholder(R.drawable.nodocument)
                        .centerCrop()
                        .into(new BitmapImageViewTarget(listViewHolder.iv_thumbnail) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                listViewHolder.iv_thumbnail.setImageBitmap(resource);
                            }
                        });
            }
        }

        @Override
        public int getItemCount() {
            return this.listType == TYPE_GRID ? fileItemsMap.size() : fileItems.size();
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
