package com.example.parle.activities.loginActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.parle.R;
import com.example.parle.sharedPreferences.LoginSP;
import com.example.parle.activities.signupActivity.SignupActivity;
import com.example.parle.activities.StudentHomePage;
import com.example.parle.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;
    private String user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//makes it easier to type in the page

        user = getIntent().getStringExtra("user");
        Log.i("user","login "+user);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.setInitialState(this);

        viewModel.getState().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 1)
                {
                    viewModel.setIsStudent();
                    checkIsValidLogin();

                }
                else if(integer == 2){
                    Toast.makeText(LoginActivity.this, getString(R.string.loginError), Toast.LENGTH_SHORT).show();
                    binding.loginBtn.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
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
        intent.putExtra("user",user);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in_transtion, R.anim.fade_out_transition);
        finish();
    }

    public void checkIsValidLogin()
    {
        viewModel.getIsStudent().observe(LoginActivity.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(user.equals("student"))//if the user chose login as student
                {
                    if(integer==1)//the user is actually a student
                    {

                        LoginSP.setUser(LoginActivity.this,"student");

                        viewModel.getPin();
                        updatePin();


                    }
                    else if(integer==0)
                    {
                        Toast.makeText(LoginActivity.this,"So sad, can't log in",Toast.LENGTH_LONG).show();
                        viewModel.logout();
                        finish();
                        binding.loginBtn.setVisibility(View.VISIBLE);
                        binding.progressBar.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,"You signed up as a counsellor go back and log in as counsellor",Toast.LENGTH_LONG).show();
                        viewModel.logout();
                        finish();
                        binding.loginBtn.setVisibility(View.VISIBLE);
                        binding.progressBar.setVisibility(View.INVISIBLE);
                    }
                }

                else if(user.equals("counsellor"))//if the user selected login as counsellor
                {
                    if(integer==2)//the user is actually a counsellor
                    {
                        Intent intent = new Intent(LoginActivity.this, StudentHomePage.class);
                        LoginSP.setUser(LoginActivity.this,"counsellor");
                        startActivity(intent);
                        finish();
                    }
                    else if(integer==0)
                    {
                        Toast.makeText(LoginActivity.this,"So sad, can't log in",Toast.LENGTH_LONG).show();
                        viewModel.logout();
                        finish();
                        binding.loginBtn.setVisibility(View.VISIBLE);
                        binding.progressBar.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,"You signed up as a student go back and log in as student",Toast.LENGTH_LONG).show();
                        viewModel.logout();
                        finish();
                        binding.loginBtn.setVisibility(View.VISIBLE);
                        binding.progressBar.setVisibility(View.INVISIBLE);

                    }
                }

                viewModel.setInitialState(LoginActivity.this);
            }
        });
    }

    public void updatePin()
    {
        viewModel.pin.observe(LoginActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Intent intent = new Intent(LoginActivity.this, StudentHomePage.class);
                LoginSP.setPin(LoginActivity.this,s);
                startActivity(intent);
                finish();
            }
        });
    }
}