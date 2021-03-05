package com.example.parle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.parle.R;
import com.example.parle.activities.loginActivity.LoginActivity;
import com.example.parle.sharedPreferences.LoginSP;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SelectionActivity extends AppCompatActivity {

    //Looking in hindsight now, this didnt need to be an activity.
    //Anyways its the activity that shows 2 chatboxes for user to login as counsellor or student
    private boolean student_selected;
    private ImageView studentBackground;
    private ImageView counsellorBackground;
    private ImageView Student;
    private ImageView Counsellor;

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("user","Selectoin activity "+LoginSP.getUser(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);



        studentBackground = findViewById(R.id.as_student_background);
        counsellorBackground = findViewById(R.id.as_counselor_background);

        Student = findViewById(R.id.as_student);
        Counsellor = findViewById(R.id.as_counsellor);

        adjustSelection(true);

        Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adjustSelection(true);
            }
        });

        Counsellor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adjustSelection(false);
            }
        });

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(SelectionActivity.this, StudentHomePage.class));
                toLogin();
            }
        });

    }



    public void toLogin(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent;
        if(user == null){
            intent = new Intent(this, LoginActivity.class);
        }
        else{
            intent = new Intent(this, StudentHomePage.class);
        }
        if(student_selected) {
            intent.putExtra("user","student");

        }
        else{
            intent.putExtra("user","counsellor");
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