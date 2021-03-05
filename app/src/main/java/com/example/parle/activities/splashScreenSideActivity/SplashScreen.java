package com.example.parle.activities.splashScreenSideActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.parle.activities.MainActivity;
import com.example.parle.R;
import com.example.parle.activities.SelectionActivity;
import com.example.parle.sharedPreferences.LoginSP;
import com.example.parle.activities.StudentHomePage;

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
        },500);
    }

    public void checkIfStudent()
    {
        //close the splashscreen activity.
        if(mViewModel.userIsNull())//if there is no logged in user go to the log in selection screen
        {
            LoginSP.setUser(this,"none");
            startActivity(new Intent(SplashScreen.this, SelectionActivity.class));
        }


        else//if there is a logged in user
        {
            startActivity(new Intent(SplashScreen.this, StudentHomePage.class));

        }
        finish();//close the splashscreen activity.
    }
}