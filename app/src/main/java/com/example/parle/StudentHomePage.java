package com.example.parle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.parle.StudentFragments.Articles;
import com.example.parle.StudentFragments.Chats;
import com.example.parle.StudentFragments.Counsellors;
import com.example.parle.StudentFragments.Home;
import com.example.parle.StudentFragments.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentHomePage extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mBottomNavigationView = findViewById(R.id.nav_bar);
        setUpNavigation();

//        mListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                int id = item.getItemId();
//
//                Fragment fragment;
//                boolean ans = true;
//                switch (item.getItemId())
//                {
//                    case R.id.action_student_home:
//                        fragment = new Home();
//                        ans = true;
//                        item.setChecked(true);
//                        loadFragment(fragment);
//                        break;
//
//                    case R.id.action_articles:
//                        fragment = new Articles();
//                        ans = true;
//                        item.setChecked(true);
//                        loadFragment(fragment);
//                        break;
//
//                    case R.id.action_counselors:
//                        fragment = new Counsellors();
//                        ans = true;
//                        item.setChecked(true);
//                        loadFragment(fragment);
//                        break;
//
//                    case R.id.action_messages:
//                        fragment = new Chats();
//                        ans = true;
//                        item.setChecked(true);
//                        loadFragment(fragment);
//                        break;
//
//                    case R.id.action_profile:
//                        fragment = new Profile();
//                        ans = true;
//                        item.setChecked(true);
//                        loadFragment(fragment);
//                        break;
//                };
//               return ans;
//            }
//        };
//
//        mBottomNavigationView.setOnNavigationItemSelectedListener(mListener);
//        mBottomNavigationView.setSelectedItemId(R.id.action_student_home);;


    }

    public void setUpNavigation(){
        NavHostFragment navHostFragment =  (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(mBottomNavigationView, navHostFragment.getNavController());
    }


    private void loadFragment(Fragment fragment)
    {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.to_be_replaced,fragment);
        transaction.addToBackStack(null);
        if (fragment.getClass() == Chats.class)
            findViewById(R.id.homeBackground).setBackgroundColor(getColor(R.color.light_grey));
        else
            findViewById(R.id.homeBackground).setBackgroundColor(getColor(android.R.color.white));
        transaction.commit();

    }
}