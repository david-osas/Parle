package com.example.parle.activities.signupActivity;

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

import com.example.parle.activities.detailsActivity.DetailsActivity;
import com.example.parle.activities.loginActivity.LoginActivity;
import com.example.parle.models.Counsellor;
import com.example.parle.models.Student;
import com.example.parle.R;
import com.example.parle.databinding.ActivitySignupBinding;
import com.example.parle.sharedPreferences.LoginSP;

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
    private String mProfession;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        user = getIntent().getStringExtra("user");
        if(user.equals("student"))
            setUpforStudent();
        else
            setUpForCounsellor();

        viewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        viewModel.setInitialState();

        viewModel.getState().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 1){
                    Intent intent = new Intent(SignupActivity.this, DetailsActivity.class);
                    LoginSP.setUser(SignupActivity.this,user);
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
            }
        });


        AutoCompleteTextView maleOrFemaleorOther = binding.gender;
        List<String> genders =  Arrays.asList(getResources().getStringArray(R.array.genders_list));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,genders);
        maleOrFemaleorOther.setAdapter(adapter);
        maleOrFemaleorOther.setCursorVisible(false);
        setListener(maleOrFemaleorOther);
    }





    public void signup(){

        mFullName = binding.fullname.getText().toString();
        mEmail = binding.email.getText().toString();
        mPassword = binding.password.getText().toString();
        mConfirmPassword = binding.confirmPassword.getText().toString();
        mGenderText = binding.gender.getText().toString();
        String concentrate = "11111111111111111111";

        if(user.equals("student")) {//if user is registering as student


            mUsername = binding.username.getText().toString();
            mPIN = binding.PIN.getText().toString();
            mIsAnonymous = binding.isAnonymous.isChecked();

            Student newStudent = new Student();

            newStudent.setFullName(mFullName);
            newStudent.setEmail(mEmail);
            newStudent.setUsername(mUsername);
            newStudent.setPin(mPIN);
            newStudent.setGender(mGenderText);
            newStudent.setAnonymous(mIsAnonymous);
            newStudent.setConcentrate(concentrate);


            if (mFullName.isEmpty() || mEmail.isEmpty() || mUsername.isEmpty() || mPassword.isEmpty() || mConfirmPassword.isEmpty()
                    || mPIN.isEmpty() || mGenderText.isEmpty()) {
                Toast.makeText(this, getString(R.string.enterDetails), Toast.LENGTH_SHORT).show();
            } else {
                if (!mConfirmPassword.equals(mPassword)) {
                    Toast.makeText(this, getString(R.string.passwordMismatch), Toast.LENGTH_SHORT).show();
                } else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.signupBtn.setVisibility(View.INVISIBLE);
                    viewModel.signupForStudent(mEmail,mPassword, SignupActivity.this, newStudent);
                }
            }

        }


        else//if user is registering as counsellor
        {
            mProfession = binding.profession.getText().toString();

            Counsellor counsellor = new Counsellor();

            counsellor.setFullName(mFullName);
            counsellor.setEmail(mEmail);
            counsellor.setProfession(mProfession);
            counsellor.setConcentrate(concentrate);
            counsellor.setGender(mGenderText);

            if (mFullName.isEmpty() || mEmail.isEmpty() || mPassword.isEmpty() || mConfirmPassword.isEmpty()
                    || mGenderText.isEmpty() || mProfession.isEmpty())
            {
                Toast.makeText(this, getString(R.string.enterDetails), Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (!mConfirmPassword.equals(mPassword))
                {
                    Toast.makeText(this, getString(R.string.passwordMismatch), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.signupBtn.setVisibility(View.INVISIBLE);
                    viewModel.signUpForCounsellor(mEmail,mPassword, SignupActivity.this, counsellor);
                }
            }
        }
    }




    public void toggle(){
        Intent intent = new Intent(this, LoginActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("user",user);
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




    public void setUpforStudent()
    {
        //showing username question and edittext
        binding.usernameQuestion.setVisibility(View.VISIBLE);
        binding.username.setVisibility(View.VISIBLE);

        //show pin question
        binding.pinLinearLayout.setVisibility(View.VISIBLE);

        //hide the profession question
        binding.profession.setVisibility(View.GONE);
        binding.professionQuestion.setVisibility(View.GONE);

        //show the checkbox
        binding.isAnonymous.setVisibility(View.VISIBLE);

    }





    public void setUpForCounsellor()
    {
        //hiding username question and edittext
        binding.usernameQuestion.setVisibility(View.GONE);
        binding.username.setVisibility(View.GONE);

        //hide pin question
        binding.pinLinearLayout.setVisibility(View.GONE);

        //SHOW the profession question
        binding.profession.setVisibility(View.VISIBLE);
        binding.professionQuestion.setVisibility(View.VISIBLE);

        //HIDE the checkbox
        binding.isAnonymous.setVisibility(View.GONE);
    }
}


