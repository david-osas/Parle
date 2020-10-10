package com.example.parle.StartScreenFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.parle.R;

import java.util.ArrayList;

public class StartScreenAdapter extends FragmentStateAdapter {

    private ArrayList<Fragment> mFragments;
    ;
    public StartScreenAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ArrayList<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        mFragments = new ArrayList<>();
        mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {


        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments.size();
    }
}
