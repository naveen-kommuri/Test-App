package com.test.testapplication;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.test.testapplication.Utils.CommonUtil;
import com.test.testapplication.Utils.DbHelper;
import com.test.testapplication.home.Main2Activity;
import com.test.testapplication.model.Invoice;
import com.test.testapplication.model.OnImageResult;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static android.app.Activity.RESULT_OK;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static com.test.testapplication.CustomCameraActivity.IMAGE_DIRECTORY_NAME;


public class CreateInvoiceActivity extends AppCompatActivity implements OnImageResult {
    View view;
    String TAG = "CreateInvoice";
    DatePickerDialog datePickerDialog;
    private SimpleDateFormat displayDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Calendar newCalendar;
    EditText et_invoice_date, et_invoice_gst_in, et_invoice_no, et_merchant_name, et_amount;
    Button bt_save;
    ImageButton iv_capture;
    private static final int STORAGE_PERMISSION_GALLERY_CONSTANT = 1001;
    private static final int STORAGE_PERMISSION_CAMERA_CONSTANT = 1002;
    private final int PICK_FROM_CAMERA = 1003;
    private final int PICK_FROM_GALLERY = 1004;
    private final int CROP_IMAGE = 1005;
    boolean isChoosen = false;
    private OnImageResult onImageResult;
    private File captureImageFile;
    private Uri captureImageUri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invoice);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.activity_create_invoice, container, false);
        loadViews();
        loadDatePickers();
        loadListeners();
//        return view;
    }


    private void loadViews() {
        et_invoice_date = findViewById(R.id.et_invoice_date);
        et_invoice_gst_in = findViewById(R.id.et_invoice_gst_in);
        et_invoice_no = findViewById(R.id.et_invoice_no);
        et_merchant_name = findViewById(R.id.et_merchant_name);
        et_amount = findViewById(R.id.et_amount);
        bt_save = findViewById(R.id.bt_save);
        iv_capture = findViewById(R.id.iv_capture);
        et_invoice_date.setInputType(InputType.TYPE_NULL);
    }

    private void loadListeners() {
        et_invoice_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    CommonUtil.hideKeyboard(CreateInvoiceActivity.this);
                    datePickerDialog.show();
                }
            }
        });
        iv_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage(CreateInvoiceActivity.this);
            }
        });
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isChoosen && captureImageUri != null) {
                    if (et_amount.getText().toString().trim().length() != 0 && et_invoice_date.getText().toString().trim().length() != 0 && et_invoice_no.getText().toString().trim().length() != 0 && et_invoice_gst_in.getText().toString().trim().length() != 0 && et_merchant_name.getText().toString().trim().length() != 0) {
                        DbHelper dbHelper = new DbHelper(CreateInvoiceActivity.this);
                        if (dbHelper.insertInvoice(new Invoice(et_amount.getText().toString().trim(), et_invoice_no.getText().toString().trim(), et_invoice_gst_in.getText().toString().trim(), et_invoice_date.getText().toString().trim(), et_merchant_name.getText().toString().trim(), captureImageUri.toString()))) {
                            Toast.makeText(CreateInvoiceActivity.this, "Invoice Created Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(CreateInvoiceActivity.this, Main2Activity.class));
                        } else
                            Toast.makeText(CreateInvoiceActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(CreateInvoiceActivity.this, "All filelds are required.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CreateInvoiceActivity.this, "Please Capture Invoice", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadDatePickers() {
        newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                et_invoice_date.setText(displayDateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(newCalendar.getTimeInMillis());
    }

    void chooseImage(final Context _mContext) {
        View choosePickerView = LayoutInflater.from(_mContext).inflate(R.layout.bottom_choose_picker_dialog, null);
        final BottomSheetDialog choosePickerDialog = new BottomSheetDialog(_mContext);
        choosePickerDialog.setContentView(choosePickerView);
        choosePickerView.findViewById(R.id.tv_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePickerDialog.dismiss();
                captureImage(_mContext);
            }
        });
        choosePickerView.findViewById(R.id.tv_preview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePickerDialog.dismiss();
                if (isChoosen && captureImageUri != null)
                    startActivity(new Intent(CreateInvoiceActivity.this, PreviewActivity.class).putExtra("imageUrl", captureImageUri.toString()));

            }
        });
        choosePickerView.findViewById(R.id.tv_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePickerDialog.dismiss();
                pickImageFromGallery(_mContext);
            }
        });
        choosePickerDialog.show();
    }

    private void captureImage(Context context) {
        onImageResult = (OnImageResult) context;
        if (Build.VERSION.SDK_INT >= 23) {
            takePermissionForMedia(STORAGE_PERMISSION_CAMERA_CONSTANT);
        } else {
            imageCapture();
        }
    }

    private void pickImageFromGallery(Context context) {
        this.onImageResult = (OnImageResult) context;
        if (Build.VERSION.SDK_INT >= 23) {
            takePermissionForMedia(STORAGE_PERMISSION_GALLERY_CONSTANT);
        } else {
            imagePick();
        }
    }

    private void imageCapture() {
        if (this.onImageResult != null) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
                // Create the File where the photo should go
                captureImageFile = createImageFile();
                if (captureImageFile != null) {
                    if (Build.VERSION.SDK_INT >= LOLLIPOP) {
                        captureImageUri = FileProvider.getUriForFile(this,
                                BuildConfig.APPLICATION_ID + ".provider",
                                captureImageFile);
                    } else
                        captureImageUri = Uri.fromFile(captureImageFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, captureImageUri);
                    startActivityForResult(takePictureIntent, PICK_FROM_CAMERA);
                }
            }
        }
    }

    private void imagePick() {
        if (this.onImageResult != null) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Choose Picture"), PICK_FROM_GALLERY);
        }
    }

    private void cropImage(Uri imageUriFromBitmap) {
        try {
            if (imageUriFromBitmap != null) {
                Intent cropIntent = new Intent("com.android.camera.action.CROP");
                cropIntent.setDataAndType(imageUriFromBitmap, "image/*");
                cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                cropIntent.putExtra("crop", "true");
                cropIntent.putExtra("aspectX", 1);
                cropIntent.putExtra("aspectY", 1);
                cropIntent.putExtra("outputX", 256);
                cropIntent.putExtra("outputY", 256);
                cropIntent.putExtra("return-data", true);
//                cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, captureImageUri);
                startActivityForResult(cropIntent, CROP_IMAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            cropImage(this, imageUriFromBitmap);
        }
    }

    boolean isStoragePermission(Context context) {
        int readPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writePermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int camera = ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        return (readPermission == PackageManager.PERMISSION_GRANTED && writePermission == PackageManager.PERMISSION_GRANTED && camera == PackageManager.PERMISSION_GRANTED);
    }

    private void takePermissionForMedia(int type) {
        if (!isStoragePermission(this)) {
            requestStoragePermission(type);
        } else {
            switch (type) {
                case STORAGE_PERMISSION_CAMERA_CONSTANT:
                    imageCapture();
                    break;
                case STORAGE_PERMISSION_GALLERY_CONSTANT:
                    imagePick();
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_CAMERA && resultCode == RESULT_OK) {
            isChoosen = true;
            displayPic();
//            cropImage(this, captureImageUri);
        } else if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            captureImageUri = data.getData();
            isChoosen = true;
            displayPic();
//            cropImage(this, captureImageUri);
        } else if (requestCode == CROP_IMAGE) {
            try {
                Bitmap captureImageBitmap;
                if (data != null && data.getExtras() != null) {
                    captureImageBitmap = data.getExtras().getParcelable("data");
                    captureImageUri = getImageUri(captureImageBitmap);
                } else {
                    captureImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), captureImageUri);
                }
                circularBitmap(captureImageBitmap);
            } catch (Exception e) {
                e.printStackTrace();
                cropImage(captureImageUri);
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                captureImageUri = result.getUri();
                circularBitmap(null);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("Eoor", error.getLocalizedMessage());
            }
        }

    }

    private void displayPic() {
        if (isChoosen)
            Glide.with(CreateInvoiceActivity.this).load(captureImageUri)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv_capture);

//        iv_capture.setImageURI(captureImageUri);
    }

    private Uri getImageUri(Bitmap inImage) {
        Uri imageUri = null;
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
            imageUri = Uri.parse(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageUri;
    }

    private void cropImage(Activity thisActivity, Uri data) {
        CropImage.activity(data).setRequestedSize(256, 256, CropImageView.RequestSizeOptions.RESIZE_EXACT).setAspectRatio(1, 1).start(thisActivity);
    }

    private void circularBitmap(Bitmap captureImageBitmap) {
        try {
            if (captureImageBitmap == null) {
                try {
                    captureImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), captureImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (onImageResult == null)
                this.onImageResult = (OnImageResult) this;
            captureImageFile = persistImage(captureImageBitmap);
            this.onImageResult.getImageInfo(captureImageUri, captureImageBitmap, captureImageFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    File persistImage(Bitmap bitmap) {
        File imageFile = new File(this.getFilesDir(), "profile_image_cropped.jpg");
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap");
            e.printStackTrace();
        }
        return imageFile;
    }

    private void requestStoragePermission(int permissionType) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, permissionType);
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
            switch (requestCode) {
                case STORAGE_PERMISSION_CAMERA_CONSTANT:
                    imageCapture();
                    break;
                case STORAGE_PERMISSION_GALLERY_CONSTANT:
                    imagePick();
                    break;
            }
        } else {
            Toast.makeText(this, "Permissions Required", Toast.LENGTH_SHORT).show();
            ;
//            requestStoragePermission(requestCode);
        }
    }

    private File createImageFile() {
        File mediaFile = null;
        try {
            File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), IMAGE_DIRECTORY_NAME);
            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {

                    return null;
                }
            }
            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());

            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "image_" + timeStamp + ".jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediaFile;
    }


    @Override
    public void getImageInfo(Uri imageUri, Bitmap captureImageBitmap, File captureImageFile) {

        Glide.with(CreateInvoiceActivity.this).load(imageUri)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv_capture);
    }

    @Override
    public void deleteProfile() {

    }
}
