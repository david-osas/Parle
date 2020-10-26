package com.example.parle;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parle.sharedPreferences.LoginSP;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentHomePageViewModel extends ViewModel {

    //should be used with the student home page.
    //it should get user details from the firestore and update them in the app view.

    private FirebaseUser mFirebaseUser;
    private FirebaseFirestore mDb;
    public Context mContext;
    public String user;

    public MutableLiveData<String> userName;

    public void intializeValues(Context context)
    {
        user = LoginSP.getUser(context);
        mContext = context;
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDb = FirebaseFirestore.getInstance();
    }

}