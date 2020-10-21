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
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.parle.StudentFragments.Articles;
import com.example.parle.StudentFragments.Chats;
import com.example.parle.StudentFragments.Counsellors;
import com.example.parle.StudentFragments.Home;
import com.example.parle.StudentFragments.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentHomePage extends BaseActivity {

    private BottomNavigationView mBottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mBottomNavigationView = findViewById(R.id.nav_bar);
        setUpNavigation();




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