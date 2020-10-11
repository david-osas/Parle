package com.example.parle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parle.databinding.ActivityPinBinding;

import java.util.ArrayList;

public class PinActivity extends AppCompatActivity {
    private ArrayList<Integer> pin = new ArrayList<>();
    private int index = 0;
    private ActivityPinBinding binding;
    private TextView[] textViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        textViews = new TextView[]{binding.first, binding.second, binding.third, binding.fourth};
    }

    public void addValue(View view){
        if(index < 4){
            index++;
            Button button = (Button) view;
            String value = button.getText().toString();
            pin.add(Integer.parseInt(value));

            textViews[index-1].setText(value);
            if(index != 1){
                textViews[index-2].setText("*");
            }
        }

    }

    public void removeValue(View view){
        if(index > 0){
            index--;
            pin.remove(index);
            textViews[index].setText("");
        }

    }

    public void moveToNext(View view){
        if(pin.size() < 4){
            Toast.makeText(this, getString(R.string.enterDetails), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, getString(R.string.savedPin), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}