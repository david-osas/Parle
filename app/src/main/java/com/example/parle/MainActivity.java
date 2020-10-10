package com.example.parle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;

import android.graphics.Typeface;
import android.os.Build;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.parle.StartScreenFragments.PageOneFragment;
import com.example.parle.StartScreenFragments.PageThreeFragment;
import com.example.parle.StartScreenFragments.PageTwoFragment;
import com.example.parle.StartScreenFragments.StartScreenAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Fragment> mFragments;
    private TextView mTextView;
    private ViewPager2 mViewPager2;
    private Animation fade_out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mTextView = findViewById(R.id.get_started);
        mFragments = new ArrayList<>();
        mFragments.add(new PageOneFragment());
        mFragments.add(new PageTwoFragment());
        mFragments.add(new PageThreeFragment());

        fade_out = AnimationUtils.loadAnimation(this,R.anim.fade_out);
        mViewPager2 = findViewById(R.id.slider);
        mViewPager2.setAdapter(new StartScreenAdapter(getSupportFragmentManager(),getLifecycle(),mFragments));



        mTextView.setVisibility(View.GONE);
        ViewPager2.PageTransformer pageTransformer = new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                mTextView.setVisibility(View.GONE);

                if(mViewPager2.getCurrentItem()==2)
                {
                    mTextView.setVisibility(View.VISIBLE);
                }


            }
        };

        mViewPager2.setPageTransformer(pageTransformer);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, mViewPager2, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

            }
        });
        tabLayoutMediator.attach();


        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = mViewPager2.getCurrentItem();
                if (index<2)
                {
                    mViewPager2.setCurrentItem(index+1,true);
                }
                else
                {
                    startActivity(new Intent(MainActivity.this, SelectionActivity.class));
                    finish();
                }
            }
        });

        findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SelectionActivity.class));
                finish();
            }
        });
    }


}