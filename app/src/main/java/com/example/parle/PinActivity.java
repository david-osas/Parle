package com.example.parle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parle.sharedPreferences.LoginSP;
import com.example.parle.databinding.ActivityPinBinding;

import java.util.ArrayList;

public class PinActivity extends AppCompatActivity {
    private ArrayList<String> pin = new ArrayList<>();
    private int index = 0;
    private ActivityPinBinding binding;
    private TextView[] textViews;
    private  String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        action = intent.getStringExtra("action");

        textViews = new TextView[]{binding.first, binding.second, binding.third, binding.fourth};
    }

    public void addValue(View view){
        if(index < 4){
            index++;
            Button button = (Button) view;
            String value = button.getText().toString();
            pin.add(value);

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
            textViews[index].setText(getString(R.string.blank));
        }

    }

    public void moveToNext(View view){
        if(pin.size() < 4){
            Toast.makeText(this, getString(R.string.enterDetails), Toast.LENGTH_SHORT).show();
        }else{
            if(action.equals("validate")){
                boolean check = validatePin();
                if(check){
                    Toast.makeText(this, getString(R.string.validatePin), Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(this, getString(R.string.invalidPin), Toast.LENGTH_SHORT).show();
                }
            }else{
                LoginSP.setPin(this, pin.toString());
                Toast.makeText(this, getString(R.string.createPin), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, StudentHomePage.class);
                startActivity(intent);
            }
        }
    }
    public boolean validatePin(){
        String validPin = LoginSP.getPin(this);
        return pin.toString().equals(validPin);

    }
}