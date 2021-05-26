package com.example.parle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.parle.R;
import com.example.parle.databinding.OnboardingPageItemBinding;

import java.util.ArrayList;

public class OnboardingScreenAdapter extends RecyclerView.Adapter<OnboardingScreenAdapter.OnboardingPageViewHolder> {

    String[] strings;
    int[] images;


    public  OnboardingScreenAdapter(@DrawableRes int[] images, String[] strings)
    {
        this.images = images;
        this.strings = strings;
    }



    @NonNull
    @Override
    public OnboardingPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        OnboardingPageItemBinding binding = OnboardingPageItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new OnboardingPageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingPageViewHolder holder, int position) {
        holder.mBinding.adviceText.setText(strings[position]);
        holder.mBinding.personImage.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class OnboardingPageViewHolder extends RecyclerView.ViewHolder{

        OnboardingPageItemBinding mBinding;
        public OnboardingPageViewHolder(OnboardingPageItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}
