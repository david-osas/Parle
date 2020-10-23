package com.example.parle;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentHomePageViewModel extends ViewModel {

    //should be used with the student home page.
    //it should get user details from the firestore and update them in the app view.

    public FirebaseUser mFirebaseUser;
    public FirebaseFirestore mDb;
    public Context mContext;


}