package com.example.parle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.parle.StudentFragments.Articles;
import com.example.parle.StudentFragments.Chats;
import com.example.parle.StudentFragments.Counsellors;
import com.example.parle.StudentFragments.Home;
import com.example.parle.StudentFragments.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentHomePage extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        mBottomNavigationView = findViewById(R.id.nav_bar);


        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                Fragment fragment;
                boolean ans = true;
                switch (item.getItemId())
                {
                    case R.id.action_student_home:
                        fragment = new Home();
                        ans = true;
                        item.setChecked(true);
                        loadFragment(fragment);
                        break;

                    case R.id.action_articles:
                        fragment = new Articles();
                        ans = true;
                        item.setChecked(true);
                        loadFragment(fragment);
                        break;

                    case R.id.action_counselors:
                        fragment = new Counsellors();
                        ans = true;
                        item.setChecked(true);
                        loadFragment(fragment);
                        break;

                    case R.id.action_messages:
                        fragment = new Chats();
                        ans = true;
                        item.setChecked(true);
                        loadFragment(fragment);
                        break;

                    case R.id.action_profile:
                        fragment = new Profile();
                        ans = true;
                        item.setChecked(true);
                        loadFragment(fragment);
                        break;
                };
               return ans;
            }
        });

        mBottomNavigationView.setSelectedItemId(R.id.action_profile);

    }



    private void loadFragment(Fragment fragment)
    {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.to_be_replaced,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        if (fragment.getClass() == Chats.class)
            findViewById(R.id.homeBackground).setBackgroundColor(getColor(R.color.light_grey));
        else
            findViewById(R.id.homeBackground).setBackgroundColor(getColor(android.R.color.white));
    }
}