package com.example.parle.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.parle.R;
import com.example.parle.StudentHomePageViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentHomePage extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private StudentHomePageViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        mViewModel = new ViewModelProvider(this).get(StudentHomePageViewModel.class);
        mViewModel.intializeValues(this);//initialize the values in the viewmodel that will be useful to all fragments attached to this activity

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//makes it easier to type on the page
        mBottomNavigationView = findViewById(R.id.nav_bar);
        setUpNavigation();

    }

    public void setUpNavigation(){
        //set up the bottomNavigationView with a navcontroller and nav host fragment
        NavHostFragment navHostFragment =  (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(mBottomNavigationView, navHostFragment.getNavController());
    }
}