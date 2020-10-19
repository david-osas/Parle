package com.example.parle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.parle.Models.Student;
import com.example.parle.databinding.ActivitySignupBinding;

import java.util.Arrays;
import java.util.List;

public class SignupActivity extends AppCompatActivity {
    private SignupViewModel viewModel;
    private ActivitySignupBinding binding;
    private String mFullName;
    private String mEmail;
    private String mUsername;
    private String mPassword;
    private String mConfirmPassword;
    private String mPIN;
    private String mGenderText;
    private boolean mIsAnonymous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
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

        binding.signUpToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               signup();
//                FirebaseFirestore db = FirebaseFirestore.getInstance();
//                db.collection("test").add(new Article("Money","Osarumense OBO")).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentReference> task) {
//                        Toast.makeText(SignupActivity.this, "Uploaded",Toast.LENGTH_LONG).show();
//                    }
//                });
            }
        });


        AutoCompleteTextView maleOrFemaleorOther = binding.gender;
        List<String> genders =  Arrays.asList(new String[]{"Male","Female","Other"});
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,genders);
        maleOrFemaleorOther.setAdapter(adapter);
        maleOrFemaleorOther.setCursorVisible(false);
        setListener(maleOrFemaleorOther);
    }

    public void signup(){
        mFullName = binding.fullname.getText().toString();
        mEmail = binding.email.getText().toString();
        mUsername = binding.username.getText().toString();
        mPassword = binding.password.getText().toString();
        mConfirmPassword = binding.confirmPassword.getText().toString();
        mPIN = binding.PIN.getText().toString();
        mGenderText = binding.gender.getText().toString();
        mIsAnonymous = binding.isAnonymous.isChecked();

        String concentrate = "11111111111111111111";

        Student newStudent = new Student();

        newStudent.setFullName(mFullName);
        newStudent.setEmail(mEmail);
        newStudent.setUsername(mUsername);
        newStudent.setPin(mPIN);
        newStudent.setGender(mGenderText);
        newStudent.setAnonymous(mIsAnonymous);
        newStudent.setConcentrate(concentrate);


        if(mFullName.isEmpty() || mEmail.isEmpty() || mUsername.isEmpty() || mPassword.isEmpty() || mConfirmPassword.isEmpty()
        || mPIN.isEmpty() || mGenderText.isEmpty()){
            Toast.makeText(this, getString(R.string.enterDetails), Toast.LENGTH_SHORT).show();
        }else{
            if(!mConfirmPassword.equals(mPassword)){
                Toast.makeText(this, getString(R.string.passwordMismatch), Toast.LENGTH_SHORT).show();
            }else{
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.signupBtn.setVisibility(View.INVISIBLE);
                viewModel.signup(mFullName, mEmail, mUsername, mPassword,SignupActivity.this, newStudent,mPIN);
            }
        }
    }

    public void toggle(){
        Intent intent = new Intent(this, LoginActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in_transtion, R.anim.fade_out_transition);
        finish();
    }

    public void setListener(AutoCompleteTextView acTV)
    {
        final AutoCompleteTextView acTV1 = acTV;
//        acTV1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                acTV1.showDropDown();
//                //selection = (String) parent.getItemAtPosition(position);
//
//            }
//        });

        acTV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                acTV1.showDropDown();
            }
        });
    }
}