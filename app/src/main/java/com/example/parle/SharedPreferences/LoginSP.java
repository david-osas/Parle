package com.example.parle.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginSP
{
    private static  SharedPreferences mSharedPreferences;
    public static final String NAME= "LoginPreferences";
    private final static String FIRST_TIME_OPENED = "FIRST_TIME_OPENED";
    private final static String EMAIL = "EMAIL";
    private static final String PASSWORD = "PASSWORD";

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
                editor.putString(PASSWORD,null);
                editor.putString(EMAIL,null);
                editor.apply();
            }

        }
        return  mSharedPreferences;
    }

    public static void setLoginDetails(Context context, String password, String email)
    {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putString(EMAIL,email);
        editor.putString(PASSWORD,password);
        editor.apply();
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

    public static  String getEmail(Context context)
    {
        return getInstance(context).getString(EMAIL,"");
    }

    public static String getPassword(Context context)
    {
        return getInstance(context).getString(PASSWORD,"");
    }
}
