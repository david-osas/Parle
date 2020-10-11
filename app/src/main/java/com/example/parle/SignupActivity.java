package com.example.parle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.parle.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {
    private SignupViewModel viewModel;
    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        viewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        viewModel.setInitialState();

        viewModel.getState().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 1){
                    Intent intent = new Intent(SignupActivity.this, DetailsActivity.class);
                    startActivity(intent);
                    finish();
                }else if(integer == 2){
                    Toast.makeText(SignupActivity.this, getString(R.string.signupError), Toast.LENGTH_SHORT).show();
                    binding.signupBtn.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void signup(View view){
        String fullName = binding.fullname.getText().toString();
        String email = binding.email.getText().toString();
        String username = binding.username.getText().toString();
        String password = binding.password.getText().toString();
        String confirmPassword = binding.confirmPassword.getText().toString();

        if(fullName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            Toast.makeText(this, getString(R.string.enterDetails), Toast.LENGTH_SHORT).show();
        }else{
            if(!confirmPassword.equals(password)){
                Toast.makeText(this, getString(R.string.passwordMismatch), Toast.LENGTH_SHORT).show();
            }else{
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.signupBtn.setVisibility(View.INVISIBLE);
                viewModel.signup(fullName,email,username,password,SignupActivity.this);
            }
        }
    }

    public void toggle(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}