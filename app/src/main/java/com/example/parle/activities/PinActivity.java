package com.example.parle.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parle.R;
import com.example.parle.sharedPreferences.LoginSP;
import com.example.parle.databinding.ActivityPinBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PinActivity extends AppCompatActivity {
    private ArrayList<String> pin = new ArrayList<>();
    private int index = 0;
    private ActivityPinBinding binding;
    private TextView[] textViews;
    private  String action;
    private String user;
    private PinViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        action = intent.getStringExtra("action");

        textViews = new TextView[]{binding.first, binding.second, binding.third, binding.fourth};
        mViewModel = new ViewModelProvider(this).get(PinViewModel.class);
        mViewModel.initializeValues();

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
                LoginSP.setPin(this, getPin(pin));
                mViewModel.updatePin(getPin(pin));

                Toast.makeText(this, getString(R.string.createPin), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, StudentHomePage.class);
                startActivity(intent);
            }
        }
    }
    public boolean validatePin(){
        String validPin = LoginSP.getPin(this);
        return getPin(pin).equals(validPin);

    }

    public String getPin(ArrayList<String> pino)
    {
        String ans = "";
        for(String i:pino)
            ans+=i;
        return ans;
    }
}

class PinViewModel extends ViewModel
{
    public MutableLiveData<Integer> updated;
    private FirebaseUser mUser;
    private FirebaseFirestore mDb;

    public void initializeValues()
    {
        updated = new MutableLiveData<>();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDb = FirebaseFirestore.getInstance();
    }

    public void updatePin(String PIN)
    {
        mDb.collection("students").document(mUser.getUid()).update("pin",PIN)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            updated.postValue(1);

                        }
                        else
                        {
                            updated.postValue(2);
                        }
                    }
                });

    }
}