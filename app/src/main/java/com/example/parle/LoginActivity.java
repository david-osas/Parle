package com.example.parle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.parle.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.setInitialState();

        viewModel.getState().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 1){
                    Intent intent = new Intent(LoginActivity.this, StudentHomePage.class);
                    startActivity(intent);
                    finish();
                }else if(integer == 2){
                    Toast.makeText(LoginActivity.this, getString(R.string.loginError), Toast.LENGTH_SHORT).show();
                    binding.loginBtn.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    viewModel.setInitialState();
                }
            }
        });


        binding.signUpToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

    }

    public void login(View view){
        String email = binding.email.getText().toString();
        String password = binding.password.getText().toString();
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, getString(R.string.enterDetails), Toast.LENGTH_SHORT).show();
        }else{
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.loginBtn.setVisibility(View.INVISIBLE);
            viewModel.login(email,password,LoginActivity.this);
        }
    }

    public void toggle(){
        Intent intent = new Intent(this, SignupActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in_transtion, R.anim.fade_out_transition);
        finish();
    }
}