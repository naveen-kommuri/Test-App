package com.test.testapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.test.testapplication.Utils.DbHelper;
import com.test.testapplication.home.Main2Activity;
import com.test.testapplication.model.User;

public class LoginActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }

    public static class SignInFragment extends Fragment {

        public SignInFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static SignInFragment newInstance() {
            return new SignInFragment();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_signin, container, false);
            final EditText et_email = (EditText) rootView.findViewById(R.id.et_email);
            final EditText et_password = (EditText) rootView.findViewById(R.id.et_password);
            Button bt_signin = (Button) rootView.findViewById(R.id.bt_signin);

            bt_signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DbHelper dbHelper = new DbHelper(getActivity());
                    if (dbHelper.isValidUser(new User(et_email.getText().toString(), et_password.getText().toString()))) {
                        Toast.makeText(getActivity(), "Valid Login", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                        startActivity(new Intent(getActivity(), Main2Activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    } else
                        Toast.makeText(getActivity(), "Please Enter Valid Credentials", Toast.LENGTH_SHORT).show();
                }
            });
            return rootView;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class SignUpFragment extends Fragment {

        public SignUpFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_signup, container, false);
            final EditText et_email = (EditText) rootView.findViewById(R.id.et_email);
            final EditText et_fullname = (EditText) rootView.findViewById(R.id.et_name);
            final EditText et_password = (EditText) rootView.findViewById(R.id.et_password);
            final EditText et_confirm_password = (EditText) rootView.findViewById(R.id.et_confirm_password);
            final EditText et_contact_no = (EditText) rootView.findViewById(R.id.et_contact_no);
            final EditText et_pan = (EditText) rootView.findViewById(R.id.et_pan);
            final EditText et_gstin = (EditText) rootView.findViewById(R.id.et_gstin);
            final EditText et_gstin_reg_date = (EditText) rootView.findViewById(R.id.et_gstin_reg_date);
            final EditText et_reg_type = (EditText) rootView.findViewById(R.id.et_reg_type);
            final EditText et_pin_code = (EditText) rootView.findViewById(R.id.et_pin_code);
            Button bt_signup = (Button) rootView.findViewById(R.id.bt_signup);
            bt_signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User user = new User(et_fullname.getText().toString(), et_email.getText().toString(), et_contact_no.getText().toString()
                            , et_password.getText().toString(), et_pan.getText().toString(), et_gstin.getText().toString(), et_gstin_reg_date.getText().toString()
                            , et_reg_type.getText().toString(), et_pin_code.getText().toString());
                    DbHelper dbHelper = new DbHelper(getActivity());
                    if (validUser(user, et_confirm_password.getText().toString()) && dbHelper.insertUsers(user)) {
                        getActivity().finish();
                        startActivity(new Intent(getActivity(), Main2Activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getActivity(), "Something Went wrong", Toast.LENGTH_SHORT).show();
                }
            });

            return rootView;
        }

        private boolean validUser(User user, String confirmPassword) {
            return user.getPassword().equals(confirmPassword) && user.getEmailId().toString().length() != 0 && user.getPassword().length() != 0
                    && user.getName().length() != 0 && user.getContactNo().length() != 0;
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static SignUpFragment newInstance() {
            return new SignUpFragment();
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a SignUpFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return SignInFragment.newInstance();
                case 1:
                    return SignUpFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Sign In";
                case 1:
                    return "Sign Up";
            }
            return null;
        }
    }
}
