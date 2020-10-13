package com.example.parle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ConcentrateActivity extends AppCompatActivity {
    private ArrayList<String> choices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concentrate);
    }

    public void moveToNext(View view){
        Intent intent = new Intent(this, PinActivity.class);
        startActivity(intent);
    }

    public void manageValue(View view){
        Button button = (Button) view;
        if(view.getBackgroundTintList() == getColorStateList(R.color.btnLight)){
            button.setBackgroundTintList(null);
            button.setTextColor(getColor(R.color.btnDark));
            choices.add(button.getText().toString());
        }else{
            button.setBackgroundTintList(getColorStateList(R.color.btnLight));
            button.setTextColor(getColor(R.color.white));
            choices.remove(button.getText().toString());
        }
    }
}