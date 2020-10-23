package com.example.parle.SplashScreenSide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.parle.MainActivity;
import com.example.parle.R;
import com.example.parle.SelectionActivity;
import com.example.parle.SharedPreferences.LoginSP;
import com.example.parle.StudentHomePage;

public class SplashScreen extends AppCompatActivity {

    SplashScreenViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        mViewModel = new ViewModelProvider(this).get(SplashScreenViewModel.class);
        mViewModel.initializeValues(this);

        final boolean firstTimeOpened = LoginSP.firstTimeOpened(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //if the app is opened for the first time.
                if(firstTimeOpened)
                {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    Toast.makeText(SplashScreen.this,getString(R.string.splashToast),Toast.LENGTH_LONG).show();
                    finish();//close the splashscreen activity.
                }


                //if it has been opened before
                else
                {
                    checkIfStudent();
                }




            }
        },1200);
    }

    public void checkIfStudent()
    {
        if(mViewModel.userIsNull())//if there is no logged in user go to the log in selection screeen
        {
            startActivity(new Intent(SplashScreen.this, SelectionActivity.class));
            Toast.makeText(this, "no user is logged in", Toast.LENGTH_LONG).show();
            finish();//close the splashscreen activity.
        }


        else//if there is a logged in user
        {
            mViewModel.updateStudent();

            mViewModel.getIsStudent().observe(SplashScreen.this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if(integer==1)//if a student is logged in
                    {
                        startActivity(new Intent(SplashScreen.this, StudentHomePage.class));
                        Toast.makeText(SplashScreen.this,"Student is logged in",Toast.LENGTH_LONG).show();
                        finish();//close the splashscreen activity.
                    }

                    else if(integer ==2)
                    //a counselor is logged in
                    {
                        startActivity(new Intent(SplashScreen.this, SelectionActivity.class));
                        Toast.makeText(SplashScreen.this,"Counselor is logged in",Toast.LENGTH_LONG).show();
                        mViewModel.setIsStudent(0);
                        finish();//close the splashscreen activity.
                    }
                }
            });
        }
    }
}