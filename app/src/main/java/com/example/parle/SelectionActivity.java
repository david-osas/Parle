package com.example.parle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.parle.SharedPreferences.LoginSP;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SelectionActivity extends AppCompatActivity {

    private boolean student_selected;
    private ImageView studentBackground;
    private ImageView counsellorBackground;
    private ImageView Student;
    private ImageView Counsellor;

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
        if(student_selected)
        {
            Intent intent;
            if(user == null){
                intent = new Intent(this, LoginActivity.class);
            }else{
                intent = new Intent(this,StudentHomePage.class);
            }
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Yet to come anticipate", Toast.LENGTH_LONG).show();
        }
    }


    private  void adjustSelection(boolean student_is_selected)
    {
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