package com.example.parle.concentrateActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parle.PinActivity;
import com.example.parle.R;
import com.example.parle.StudentHomePage;
import com.example.parle.adapters.SpecialtyAdapter;
import com.example.parle.sharedPreferences.LoginSP;
import com.example.parle.databinding.ActivityConcentrateBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ConcentrateActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    public static TextView noSelected;
    private ConcentrateViewModel mViewModel;
    private ActivityConcentrateBinding binding;
    private String user;

    String[] mList;
    private SpecialtyAdapter mAdapter;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConcentrateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = LoginSP.getUser(this);//getIntent().getStringExtra("user");
        if(user.equals("counsellor"))
        {
            binding.heading.setText(getString(R.string.concentrateHeading2));
        }
        else
        {
            binding.heading.setText(getString(R.string.concentrateHeading1));
        }

        mViewModel = new ViewModelProvider(this).get(ConcentrateViewModel.class);
        mViewModel.initializeValues(this);

        mList =getResources().getStringArray(R.array.concentrate_points_list);
        mRecyclerView = binding.specialties;


        noSelected = binding.noSelected;
        mAdapter = new SpecialtyAdapter(this,mList,noSelected);


        mGridLayoutManager = new GridLayoutManager(this,10);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Integer pos = new Integer(position);
                Integer[] span4 = {0,3,4,7,12,18};//span 4
                Integer[] span6 = {1,2,5,6,13,19};//span 6
                Integer[] span12 = {8,11,16,17}; //span10

                if(Arrays.asList(span4).contains(pos))
                {
                    return 4;
                }
                else if(Arrays.asList(span6).contains(pos))
                    {
                        return 6;
                    }
                    else if(Arrays.asList(span12).contains(pos))
                            return 10;

                return 5;
            }

        });
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void moveToNext(View view){

        if(user.equals("student"))
        {
            mViewModel.updateDetailsForStudent(mAdapter.getChosenOnes());
            mViewModel.updated.observe(ConcentrateActivity.this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if(integer==1)//UPDATE WAS SUCCESSFUL
                    {
                        Toast.makeText(ConcentrateActivity.this, R.string.details_update_succesful, Toast.LENGTH_LONG).show();
                        if(user.equals("student"))
                            mIntent = new Intent(ConcentrateActivity.this, StudentHomePage.class);
                        else
                            mIntent = new Intent(ConcentrateActivity.this, StudentHomePage.class);

                        startActivity(mIntent);
                    }
                    else if(integer==2)//UPDATE WAS UNSUCCESSFUL
                    {
                        Toast.makeText(ConcentrateActivity.this, R.string.unable_to_update_details, Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
        else
        {
            mViewModel.updateDetailsForCounsellor(mAdapter.getChosenOnes());
            mViewModel.updated.observe(ConcentrateActivity.this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if(integer==1)//UPDATE WAS SUCCESSFUL
                    {
                        Toast.makeText(ConcentrateActivity.this, R.string.details_update_succesful, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ConcentrateActivity.this, StudentHomePage.class);
//                        intent.putExtra("action","create");
//                        intent.putExtra("user",user);
                        startActivity(intent);
                    }
                    else if(integer==2)//UPDATE WAS UNSUCCESSFUL
                    {
                        Toast.makeText(ConcentrateActivity.this, R.string.unable_to_update_details, Toast.LENGTH_LONG).show();
                    }

                }
            });
        }

    }



}
