package com.example.parle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.parle.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailsBinding binding;
    private Boolean similar, spiritual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void similar(View view){
        view.setBackgroundTintList(getColorStateList(R.color.btnDark));
        if(view.getId() == R.id.similarYes){
            binding.similarNo.setBackgroundTintList(getColorStateList(R.color.btnLight));
            similar = true;
        }else{
            binding.similarYes.setBackgroundTintList(getColorStateList(R.color.btnLight));
            similar = false;
        }
    }

    public void spiritual(View view){
        view.setBackgroundTintList(getColorStateList(R.color.btnDark));
        if(view.getId() == R.id.spiritualYes){
            binding.spiritualNo.setBackgroundTintList(getColorStateList(R.color.btnLight));
            spiritual = true;
        }else{
            binding.spiritualYes.setBackgroundTintList(getColorStateList(R.color.btnLight));
            spiritual = false;
        }
    }

    public void moveToNext(View view){
        String country = binding.country.getText().toString();
        String state = binding.state.getText().toString();
        String number = binding.number.getText().toString();
        String dob = binding.dob.getText().toString();
        String religion = binding.religion.getText().toString();
        String denomination = binding.denomination.getText().toString();

        if(country.isEmpty() || state.isEmpty() || number.isEmpty() || dob.isEmpty() || religion.isEmpty() || denomination.isEmpty() || similar == null || spiritual == null){
            Toast.makeText(this,getString(R.string.enterDetails),Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(this,ConcentrateActivity.class);
            startActivity(intent);
        }

    }
}