package com.test.testapplication.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//import com.larvalabs.svgandroid.SVG;
//import com.larvalabs.svgandroid.SVGParser;
import com.test.testapplication.CreateInvoiceActivity;
import com.test.testapplication.DashboardFragment;
import com.test.testapplication.GalleryActivity;
import com.test.testapplication.R;
import com.test.testapplication.SearchFragment;
import com.test.testapplication.Utils.DbHelper;
import com.test.testapplication.model.Status;
//import com.wnafee.vector.compat.VectorDrawable;

import java.util.Map;

import static com.test.testapplication.GalleryActivity.TYPE_GRID;
import static com.test.testapplication.GalleryActivity.TYPE_LIST;
import static com.test.testapplication.Utils.CommonUtil.displayString;

public class Main2Activity extends AppCompatActivity {
    private TabLayout tabLayout, actionTablayout;
    FragmentTransaction fragmentTransaction;
    BottomNavigationView navigation;
    int[] drawables = new int[]{R.drawable.ic_dashboard_select, R.drawable.ic_grid, R.drawable.ic_list, R.drawable.ic_search};
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_grid:
                    tabLayout.setVisibility(View.VISIBLE);
                    loadFragment(GalleryActivity.newInstance(TYPE_GRID, Status.ALL.toString()), "Grid");
                    return true;
                case R.id.navigation_list:
                    tabLayout.setVisibility(View.VISIBLE);
                    loadFragment(GalleryActivity.newInstance(TYPE_LIST, Status.ALL.toString()), "List");
                    return true;
                case R.id.navigation_dashboard:
                    tabLayout.setVisibility(View.GONE);
                    loadFragment(DashboardFragment.newInstance(), "Dashboard");
                    return true;
                case R.id.navigation_search:
                    tabLayout.setVisibility(View.GONE);
                    loadFragment(SearchFragment.newInstance(), "Search");
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
        actionTablayout = findViewById(R.id.actionTablayout);
        tabLayout = findViewById(R.id.tabLayout);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadActionTabs();
        loadTabs();
        loadFragment(DashboardFragment.newInstance(), "Dashboard");
    }

    private void loadActionTabs() {

        for (int i = 0; i < drawables.length; i++) {
            actionTablayout.addTab(actionTablayout.newTab().setIcon(drawables[i]));
        }
        actionTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selectedActionPosition = actionTablayout.getSelectedTabPosition();
                switch (selectedActionPosition) {
                    case 0:
                        tabLayout.setVisibility(View.GONE);
                        tab.setIcon(R.drawable.ic_dashboard_select);
                        loadFragment(DashboardFragment.newInstance(), "Dashboard");
                        break;
                    case 1:
                        tabLayout.setVisibility(View.VISIBLE);
                        tab.setIcon(R.drawable.ic_grid_select);
                        loadFragment(GalleryActivity.newInstance(TYPE_GRID, Status.ALL.toString()), "Grid");
                        break;
                    case 2:
                        tabLayout.setVisibility(View.VISIBLE);
                        tab.setIcon(R.drawable.ic_list_select);
                        loadFragment(GalleryActivity.newInstance(TYPE_LIST, Status.ALL.toString()), "List");
                        break;
                    case 3:
                        tabLayout.setVisibility(View.GONE);
                        tab.setIcon(R.drawable.ic_search_select);
                        loadFragment(SearchFragment.newInstance(), "Search");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int unselectedPosition = tab.getPosition();
                switch (unselectedPosition) {
                    case 0:
                        tab.setIcon(R.drawable.ic_dashboard);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.ic_grid);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.ic_list);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.ic_search);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void loadTabs() {
        Status[] statuses = Status.values();
        DbHelper dbHelper = new DbHelper(Main2Activity.this);
        Map<String, Integer> invoicesBasedStatusesCount = dbHelper.getInvoicesBasedStatusesCount();
        int allStautsesCount = 0;
        for (int i = 0; i < statuses.length; i++) {
//            int statusCount = invoicesBasedStatusesCount.containsKey(statuses[i].toString()) ? invoicesBasedStatusesCount.get(statuses[i].toString()) : 0;
//            allStautsesCount += statusCount;
            tabLayout.addTab(tabLayout.newTab().setCustomView(getTabView(statuses[i].toString())));
        }
//        tabLayout.addTab(tabLayout.newTab().setText(statuses[0].toString() + "(" + allStautsesCount + ")"), 0);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String selectedStatus = Status.values()[tabLayout.getSelectedTabPosition()].toString();
                if (navigation.getSelectedItemId() == R.id.navigation_grid) {
                    loadFragment(GalleryActivity.newInstance(TYPE_GRID, Status.ALL.toString()), "Grid");
                } else {
                    loadFragment(GalleryActivity.newInstance(TYPE_LIST, selectedStatus), "List");
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

    private void loadFragment(Fragment fragment, String tagName) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment, fragment, tagName);
        fragmentTransaction.commitAllowingStateLoss();
    }


    private View getTabView(String status) {
        View v = LayoutInflater.from(Main2Activity.this).inflate(R.layout.custom_tab_item, null);
        TextView tv = v.findViewById(R.id.textView);
        ImageView imageView = v.findViewById(R.id.icon);
        tv.setText(displayString(status));
        switch (status.toUpperCase()) {
            case "ALL":
                imageView.setImageResource(R.drawable.ic_circle_violet);
                break;
            case "REGISTERED":
                tv.setText(displayString("In Process"));
                imageView.setImageResource(R.drawable.ic_circle_orange);
                break;
            case "REJECTED":
                imageView.setImageResource(R.drawable.ic_circle_red);
                break;
            case "PROCESSED":
                imageView.setImageResource(R.drawable.ic_circle_green);
                break;
        }
        return v;
    }

}
