package com.test.testapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.test.testapplication.model.Invoice;

import java.util.ArrayList;

/**
 * Created by NKommuri on 8/28/2017.
 */

public class CustomGridAdapter extends BaseAdapter {
    ArrayList<Invoice> fileItems;
    Context context;

    public CustomGridAdapter(ArrayList<Invoice> fileItems, Context context) {
        this.context = context;
        this.fileItems = fileItems;
    }


    @Override
    public int getCount() {
        return fileItems.size();
    }

    @Override
    public Object getItem(int i) {
        return fileItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class Holder {
        TextView titleView;
        ImageView imageView;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_gallery, null);
        final Holder holder2 = new Holder();
        holder2.titleView = view.findViewById(R.id.tv_title);
        holder2.imageView = view.findViewById(R.id.iv_item);
//        holder2.titleView.setText(fileItems.get(i).getFile().getName());
        holder2.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, PreviewActivity.class).putExtra("imageUrl", fileItems.get(i).getInvoiceFileLoc()));

            }
        });
        Glide.with(context).
                load(fileItems.get(i).getInvoiceFileLoc()).asBitmap()
                .placeholder(R.drawable.nodocument)
                .centerCrop()
                .into(new BitmapImageViewTarget(holder2.imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        holder2.imageView.setImageBitmap(resource);
                    }
                });
        return view;
    }
}
