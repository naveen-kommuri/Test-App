package com.test.testapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.testapplication.Utils.DbHelper;
import com.test.testapplication.adapter.CustomAdapter;
import com.test.testapplication.model.Invoice;
import com.test.testapplication.model.Status;

import java.util.ArrayList;

import static com.test.testapplication.GalleryActivity.TYPE_LIST;

/**
 * Created by NKommuri on 8/30/2017.
 */

public class SearchFragment extends Fragment {
    EditText et_search;
    private View view;
    RecyclerView recyclerView;
    TextView tv_no_capture;
    private ArrayList<Invoice> invoiceItems;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;
    ProgressBar progressBar;

    public SearchFragment() {

    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_gallery, container, false);
        loadViews();
        loadListeners();
        return view;
    }

    private void loadListeners() {
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchStr = editable.toString();
                if (searchStr.trim().length() > 2) {
                    displayProgress(true);
                    searchInvoices(searchStr);
                }
            }
        });
    }

    private void searchInvoices(String searchStr) {
        invoiceItems.clear();
        DbHelper dbHelper = new DbHelper(getActivity());
        invoiceItems.addAll(dbHelper.getInvoices(searchStr, Status.ALL.toString()));
        displayProgress(false);
        if (invoiceItems.size() == 0) {
            tv_no_capture.setText("No Records found");
            tv_no_capture.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tv_no_capture.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void loadViews() {
        et_search = view.findViewById(R.id.et_search);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        tv_no_capture = view.findViewById(R.id.tv_no_capture);
        tv_no_capture.setText("Search Records here");
        et_search.setVisibility(View.VISIBLE);
        et_search.requestFocus();
        invoiceItems = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter = new CustomAdapter(invoiceItems, TYPE_LIST, getActivity()));
    }

    private void displayProgress(boolean isProgress) {
        recyclerView.setVisibility(isProgress ? View.GONE : View.VISIBLE);
        tv_no_capture.setVisibility(isProgress ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(isProgress ? View.VISIBLE : View.GONE);
    }
}
