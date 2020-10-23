package com.example.parle;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parle.Models.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentHomePageViewModel extends ViewModel {

    //should be used with the student home page.
    //it should get user details from the firestore and update them in the app view.

    public FirebaseUser mFirebaseUser;
    public FirebaseFirestore mDb;
    public Context mContext;


}