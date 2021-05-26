package com.example.parle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.parle.Constants;
import com.example.parle.R;
import com.example.parle.activities.loginActivity.LoginActivity;
import com.example.parle.databinding.ActivitySelectionBinding;
import com.example.parle.sharedPreferences.LoginSP;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SelectionActivity extends AppCompatActivity implements Constants {

    //Looking in hindsight now, this didnt need to be an activity.
    //Anyways its the activity that shows 2 chatboxes for user to login as counsellor or student
    private boolean student_selected;
    private ImageView studentBackground;
    private ImageView counsellorBackground;
    private ImageView Student;
    private ImageView Counsellor;
    ActivitySelectionBinding mBinding;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivitySelectionBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        //setView to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);



        studentBackground = mBinding.asStudentBackground;
        counsellorBackground = mBinding.asCounselorBackground;

        Student = mBinding.asStudent;
        Counsellor = mBinding.asCounsellor;

        adjustSelection(true);

        Student.setOnClickListener(view -> adjustSelection(true));

        Counsellor.setOnClickListener(view -> adjustSelection(false));

        findViewById(R.id.next).setOnClickListener(view -> {
            //startActivity(new Intent(SelectionActivity.this, StudentHomePage.class));
            toLogin();
        });

    }



    public void toLogin(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent;
        if(user == null){
            intent = new Intent(this, LoginActivity.class);
        }
        else{
            intent = new Intent(this, LoginActivity.class);
        }
        if(student_selected) {
            intent.putExtra(USER,STUDENT);

        }
        else{
            intent.putExtra(USER,COUNSELLOR);
        }
        startActivity(intent);
    }


    private  void adjustSelection(boolean student_is_selected)
    {
        //changes some variables depending on what is selected
        student_selected = student_is_selected;
        if(student_is_selected)
        {
            studentBackground.setVisibility(View.VISIBLE);
            counsellorBackground.setVisibility(View.INVISIBLE);
        }
        else
        {
            counsellorBackground.setVisibility(View.VISIBLE);
            studentBackground.setVisibility(View.INVISIBLE);
        }

    }
}