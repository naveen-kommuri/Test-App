package com.test.testapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.test.testapplication.CustomGridAdapter;
import com.test.testapplication.PreviewActivity;
import com.test.testapplication.R;
import com.test.testapplication.Utils.MyGridView;
import com.test.testapplication.model.Invoice;

import java.util.ArrayList;
import java.util.Map;

import static com.test.testapplication.GalleryActivity.TYPE_GRID;
import static com.test.testapplication.Utils.CommonUtil.getDate;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Invoice> fileItems;
    Map<String, ArrayList<Invoice>> fileItemsList;
    int listType;
    Context context;

    public CustomAdapter(ArrayList<Invoice> fileItems, int listType, Context context) {
        this.fileItems = fileItems;
        this.listType = listType;
        this.context = context;
    }

    public CustomAdapter(Map<String, ArrayList<Invoice>> fileItemsList, int listType, Context context) {
        this.fileItemsList = fileItemsList;
        this.listType = listType;
        this.context = context;
    }

//        @Override
//        public Filter getFilter() {
//            return new SearchFilter();
//        }

//        public class SearchFilter extends Filter {
//
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                FilterResults filterResults = new FilterResults();
//
//                if (constraint == null || constraint.length() == 0) {
//                    // No filter implemented we return all the list
//                    filterResults.values = fileItems;
//                    filterResults.count = fileItems.size();
//                } else {
//                    List<FileItem> filteredList = new ArrayList<>();
//                    for (int i = 0; i < fileItems.size(); i++) {
//                        FileItem fileItem = fileItems.get(i);
//                        if (fileItem.getFile().getName().toUpperCase().contains(constraint.toString().toUpperCase())) {
//                            filteredList.add(fileItem);
//                        }
//                    }
//                    filterResults.values = filteredList;
//                    filterResults.count = filteredList.size();
//                }
//                return filterResults;
//            }
//
//            @SuppressWarnings("unchecked")
//            @Override
//            protected void publishResults(CharSequence constraint,
//                                          FilterResults results) {
//                fileItems = (ArrayList<FileItem>) results.values;
//                notifyDataSetChanged();
//            }
//        }


    class ListViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_thumbnail;
        TextView tv_date, tv_name, tv_invoice_no, tv_amount;
        ConstraintLayout linearLayout;

        public ListViewHolder(View view) {
            super(view);
            iv_thumbnail = view.findViewById(R.id.imageView);
            linearLayout = view.findViewById(R.id.linearLayout);
            tv_name = view.findViewById(R.id.tv_name);
            tv_date = view.findViewById(R.id.tv_date);
            tv_amount = view.findViewById(R.id.tv_amount);
            tv_invoice_no = view.findViewById(R.id.tv_invoice_no);
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
            ArrayList<Invoice> _fileItems = fileItemsList.get(key);
            holder1.gridView.setAdapter(new CustomGridAdapter(_fileItems, context));
        } else if (holder instanceof ListViewHolder) {
            final ListViewHolder listViewHolder = (ListViewHolder) holder;
            listViewHolder.tv_name.setText(fileItems.get(position).getMerchantName());
            listViewHolder.tv_date.setText(getDate(fileItems.get(position).getInvoiceDate()));
            listViewHolder.tv_amount.setText(fileItems.get(position).getInvoiceAmount());
            listViewHolder.tv_invoice_no.setText(fileItems.get(position).getInvoiceNo());
            listViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, PreviewActivity.class).putExtra("invoiceId", fileItems.get(position).getInvoiceId()).putExtra("imageUrl", fileItems.get(position).getInvoiceFileLoc()));
                }
            });
            Glide.with(context).load(fileItems.get(position).getInvoiceFileLoc())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(listViewHolder.iv_thumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return this.listType == TYPE_GRID ? fileItemsList.size() : fileItems.size();
    }
}
