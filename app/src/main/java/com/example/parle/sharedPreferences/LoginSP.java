package com.example.parle.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.parle.Constants;

public class LoginSP implements Constants
{
    //singleton sharedprefernces class
    private static  SharedPreferences mSharedPreferences;
    public static final String NAME= "LoginPreferences";
    private final static String FIRST_TIME_OPENED = "FIRST_TIME_OPENED";
    private static final String PIN = "PIN";
    private static final String USER = "user";

    private LoginSP(){}

    public static SharedPreferences getInstance(Context context)
    {
        if(mSharedPreferences == null)
        {
            mSharedPreferences = context.getSharedPreferences(NAME,context.MODE_PRIVATE);
            if(mSharedPreferences.getAll().isEmpty())
            {
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putBoolean(FIRST_TIME_OPENED,true);
                editor.putString(PIN,"0000");
                editor.putString(USER,"none");
                editor.apply();
            }

        }
        return  mSharedPreferences;
    }

    public static void setUser(Context context, String user){
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putString(USER, user);
        editor.apply();
    }
    public static void setPin(Context context,String pin){
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putString(PIN,pin);
        editor.apply();
    }

    public static String getUser(Context context){
        return getInstance(context).getString(USER,NONE);
    }
    public static String getPin(Context context){
        return getInstance(context).getString(PIN,"0000");
    }


    public  static void hasBeenOpened(Context context)
    {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putBoolean(FIRST_TIME_OPENED,false);
        editor.apply();
    }

    public static  boolean firstTimeOpened(Context context)
    {
        return getInstance(context).getBoolean(FIRST_TIME_OPENED, true);
    }

}
