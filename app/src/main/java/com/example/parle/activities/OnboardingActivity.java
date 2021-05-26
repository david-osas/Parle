package com.example.parle.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.parle.R;
import com.example.parle.databinding.ActivityOnboardingBinding;
import com.example.parle.sharedPreferences.LoginSP;
import com.example.parle.adapters.OnboardingScreenAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class OnboardingActivity extends AppCompatActivity {

    //Activity after the SplashScreen which shows only the first time you open the app.
    ActivityOnboardingBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityOnboardingBinding.inflate(getLayoutInflater());

        setContentView(mBinding.getRoot());

        //makes the screen full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        int[] images = {R.drawable.first_page,R.drawable.second_page,R.drawable.third_page};
        String[] strings = getResources().getStringArray(R.array.advice);



        mBinding.slider.setAdapter(new OnboardingScreenAdapter(images,strings));



        mBinding.getStarted.setVisibility(View.GONE);

        ViewPager2.PageTransformer pageTransformer = (page, position) -> {
            mBinding.getStarted.setVisibility(View.GONE);

            if(mBinding.slider.getCurrentItem()==2)
            {
                mBinding.getStarted.setVisibility(View.VISIBLE);
            }


        };

        mBinding.slider.setPageTransformer(pageTransformer);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, mBinding.slider, true, (tab, position) -> {

        });
        tabLayoutMediator.attach();


        mBinding.next.setOnClickListener(view -> {

            int index = mBinding.slider.getCurrentItem();
            if (index<2)
            {
                mBinding.slider.setCurrentItem(index+1,true);
            }
            else
            {
                LoginSP.hasBeenOpened(OnboardingActivity.this);
                startActivity(new Intent(OnboardingActivity.this, SelectionActivity.class));
                finish();
            }
        });

        mBinding.skip.setOnClickListener(view -> {
            LoginSP.hasBeenOpened(OnboardingActivity.this);
            startActivity(new Intent(OnboardingActivity.this, SelectionActivity.class));
            finish();
        });
    }


}