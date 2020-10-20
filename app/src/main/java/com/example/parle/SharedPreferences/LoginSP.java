package com.example.parle.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginSP
{
    private static  SharedPreferences mSharedPreferences;
    public static final String NAME= "LoginPreferences";
    private final static String FIRST_TIME_OPENED = "FIRST_TIME_OPENED";
    private static final String PIN = "PIN";

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
                editor.apply();
            }

        }
        return  mSharedPreferences;
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
