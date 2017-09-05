package com.test.testapplication.home;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.larvalabs.svgandroid.SVG;
//import com.larvalabs.svgandroid.SVGParser;
import com.test.testapplication.CreateInvoiceActivity;
import com.test.testapplication.CustomCameraActivity;
import com.test.testapplication.DashboardFragment;
import com.test.testapplication.GalleryActivity;
import com.test.testapplication.GalleryFragment;
import com.test.testapplication.R;
import com.test.testapplication.SearchFragment;
import com.test.testapplication.Utils.DbHelper;
import com.test.testapplication.model.Status;
//import com.wnafee.vector.compat.VectorDrawable;

import java.util.Map;

import static com.test.testapplication.GalleryActivity.TYPE_GRID;
import static com.test.testapplication.GalleryActivity.TYPE_LIST;

public class Main2Activity extends AppCompatActivity {
    private TabLayout tabLayout;
    FragmentTransaction fragmentTransaction;
    BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_grid:
                    tabLayout.setVisibility(View.VISIBLE);
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.fragment, GalleryActivity.newInstance(TYPE_GRID, Status.ALL.toString()), "Grid");
                    fragmentTransaction.commitAllowingStateLoss();
                    return true;
                case R.id.navigation_list:
                    tabLayout.setVisibility(View.VISIBLE);
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.fragment, GalleryActivity.newInstance(TYPE_LIST, Status.ALL.toString()), "List");
                    fragmentTransaction.commitAllowingStateLoss();
                    return true;
                case R.id.navigation_dashboard:
                    tabLayout.setVisibility(View.GONE);
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.fragment, DashboardFragment.newInstance(), "Dashboard");
                    fragmentTransaction.commitAllowingStateLoss();
                    return true;
                case R.id.navigation_search:
                    tabLayout.setVisibility(View.GONE);
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.fragment, SearchFragment.newInstance(), "Search");
                    fragmentTransaction.commitAllowingStateLoss();
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                this.finish();
                startActivity(new Intent(Main2Activity.this, CreateInvoiceActivity.class));
                return true;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tabLayout = findViewById(R.id.tabLayout);
        navigation = findViewById(R.id.navigation);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadTabs();
        loadDefault();
    }

    private void loadTabs() {
        Status[] statuses = Status.values();
        DbHelper dbHelper = new DbHelper(Main2Activity.this);
        Map<String, Integer> invoicesBasedStatusesCount = dbHelper.getInvoicesBasedStatusesCount();
        int allStautsesCount = 0;
        for (int i = 0; i < statuses.length; i++) {
//            int statusCount = invoicesBasedStatusesCount.containsKey(statuses[i].toString()) ? invoicesBasedStatusesCount.get(statuses[i].toString()) : 0;
//            allStautsesCount += statusCount;
            tabLayout.addTab(tabLayout.newTab().setText(statuses[i].toString()));
        }
//        tabLayout.addTab(tabLayout.newTab().setText(statuses[0].toString() + "(" + allStautsesCount + ")"), 0);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String selectedStatus = Status.values()[tabLayout.getSelectedTabPosition()].toString();
                if (navigation.getSelectedItemId() == R.id.navigation_grid) {
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.fragment, GalleryActivity.newInstance(TYPE_GRID, selectedStatus), "Grid");
                    fragmentTransaction.commitAllowingStateLoss();
                } else {
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.fragment, GalleryActivity.newInstance(TYPE_LIST, selectedStatus), "List");
                    fragmentTransaction.commitAllowingStateLoss();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void loadDefault() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment, DashboardFragment.newInstance(), "Dashboard");
        fragmentTransaction.commitAllowingStateLoss();
    }

}
