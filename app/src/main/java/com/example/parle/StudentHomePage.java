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

        loadFragment(new Home());
        mBottomNavigationView = findViewById(R.id.nav_bar);

        mBottomNavigationView.set

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                boolean ans = false;
                switch (item.getItemId())
                {
                    case R.id.action_student_home:
                        fragment = new Home();
                        ans = true;
                        loadFragment(fragment);
                        break;

                    case R.id.action_articles:
                        fragment = new Articles();
                        ans = true;
                        loadFragment(fragment);
                        break;

                    case R.id.action_counselors:
                        fragment = new Counsellors();
                        ans = true;
                        loadFragment(fragment);
                        break;

                    case R.id.action_messages:
                        fragment = new Chats();
                        ans = true;
                        loadFragment(fragment);
                        break;

                    case R.id.action_profile:
                        fragment = new Profile();
                        ans = true;
                        loadFragment(fragment);
                        break;
                };
               return ans;
            }
        });
    }

    private void loadFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.to_be_replaced,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}