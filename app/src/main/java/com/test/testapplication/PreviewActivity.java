package com.test.testapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.test.testapplication.Utils.DbHelper;
import com.test.testapplication.home.Main2Activity;
import com.test.testapplication.model.Invoice;

import java.io.IOException;

import static com.test.testapplication.Utils.CommonUtil.getDate;

public class PreviewActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    ImageView iv_capture_img_preview;
    TextView tv_details;
    private GestureDetector gestureScanner;
    boolean isDialogDisplay = false;
    int invoiceId;
    Invoice invoiceDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        iv_capture_img_preview = findViewById(R.id.iv_capture_img_preview);
        tv_details = findViewById(R.id.tv_details);
        gestureScanner = new GestureDetector(this);

        if (getIntent().getExtras() != null && getIntent().getStringExtra("imageUrl").length() != 0) {
            Uri imageUrl = Uri.parse(getIntent().getStringExtra("imageUrl"));
            invoiceId = getIntent().getIntExtra("invoiceId", -1);
            previewImage(imageUrl);
        }
        if (invoiceId == -1) {
            tv_details.setVisibility(View.GONE);
        } else {
            invoiceDetails = new DbHelper(PreviewActivity.this).getInvoiceBasedId(invoiceId);
        }
        tv_details.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureScanner.onTouchEvent(motionEvent);
            }
        });
        tv_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayBottomSheetDialog();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureScanner.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private void displayBottomSheetDialog() {
        if (!isDialogDisplay) {
            View choosePickerView = LayoutInflater.from(PreviewActivity.this).inflate(R.layout.bottom_details_dialog, null);
            final BottomSheetDialog choosePickerDialog = new BottomSheetDialog(PreviewActivity.this);
            choosePickerDialog.setContentView(choosePickerView);
            ((TextView) choosePickerView.findViewById(R.id.tv_merchant_name)).setText(invoiceDetails.getMerchantName());
            ((TextView) choosePickerView.findViewById(R.id.tv_amount)).setText(invoiceDetails.getInvoiceAmount());
            ((TextView) choosePickerView.findViewById(R.id.tv_invoice_no)).setText(invoiceDetails.getInvoiceNo());
            ((TextView) choosePickerView.findViewById(R.id.tv_invoice_date)).setText(getDate(invoiceDetails.getInvoiceDate()));
            ((TextView) choosePickerView.findViewById(R.id.tv_invoice_gstin)).setText(invoiceDetails.getInvoiceGSTIn());

            choosePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    isDialogDisplay = false;
                }
            });
            isDialogDisplay = true;
            choosePickerDialog.show();
        }
    }

    private void previewImage(Uri imageUrl) {
        Glide.with(PreviewActivity.this).load(imageUrl)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv_capture_img_preview);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float distanceX, float distanceY) {
        if (distanceX == 0 && Math.abs(distanceY) > 1) {
            Toast.makeText(this, "Scroll Up", Toast.LENGTH_SHORT).show();
            displayBottomSheetDialog();
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
