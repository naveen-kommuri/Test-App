package com.test.testapplication.home;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

//import com.larvalabs.svgandroid.SVG;
//import com.larvalabs.svgandroid.SVGParser;
import com.test.testapplication.CustomCameraActivity;
import com.test.testapplication.GalleryActivity;
import com.test.testapplication.GalleryFragment;
import com.test.testapplication.R;
//import com.wnafee.vector.compat.VectorDrawable;

import static com.test.testapplication.GalleryActivity.TYPE_GRID;
import static com.test.testapplication.GalleryActivity.TYPE_LIST;

public class Main2Activity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.fragment, GalleryActivity.newInstance(TYPE_GRID), "Grid");
                    fragmentTransaction.commitAllowingStateLoss();
                    return true;
                case R.id.navigation_dashboard:
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction1.replace(R.id.fragment, GalleryActivity.newInstance(TYPE_LIST), "List");
                    fragmentTransaction1.commitAllowingStateLoss();
                    return true;
                case R.id.navigation_notifications:
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
                startActivity(new Intent(Main2Activity.this, CustomCameraActivity.class));
                return true;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadDefault();
    }

    private void loadDefault() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment, GalleryActivity.newInstance(TYPE_GRID), "Grid");
        fragmentTransaction.commitAllowingStateLoss();
    }

}
