package com.example.parle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.parle.studentFragments.Chats;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentHomePage extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private StudentHomePageViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        mViewModel = new ViewModelProvider(this).get(StudentHomePageViewModel.class);
        mViewModel.intializeValues(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mBottomNavigationView = findViewById(R.id.nav_bar);
        setUpNavigation();

    }

    public void setUpNavigation(){
        NavHostFragment navHostFragment =  (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(mBottomNavigationView, navHostFragment.getNavController());
    }
}