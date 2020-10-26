package com.example.parle;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parle.models.Counsellor;
import com.example.parle.models.Student;
import com.example.parle.sharedPreferences.LoginSP;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentHomePageViewModel extends ViewModel {

    //should be used with the student home page.
    //it should get user details from the firestore and update them in the app view.

    private FirebaseUser mFirebaseUser;
    private FirebaseFirestore mDb;
    public Context mContext;
    public String user;

    public MutableLiveData<Student> mStudent;
    public MutableLiveData<Counsellor> mCounsellor;

    public void intializeValues(Context context) {
        user = LoginSP.getUser(context);
        mContext = context;
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDb = FirebaseFirestore.getInstance();
    }

    public void logout()
    {
        FirebaseAuth.getInstance().signOut();
        LoginSP.setPin(mContext,"0000");
        LoginSP.setUser(mContext,"none");
    }

    public void getStudent()
    {
        mStudent = new MutableLiveData<>();
        mDb.collection("students").document(mFirebaseUser.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            Student student = task.getResult().toObject(Student.class);
                            mStudent.postValue(student);
                        }
                        else
                        {
                            Student student = null;
                            mStudent.postValue(student);
                        }
                    }
                });
    }

    public void getCounsellor()
    {
        mCounsellor = new MutableLiveData<>();
        mDb.collection("counsellors").document(mFirebaseUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            Counsellor counsellor = task.getResult().toObject(Counsellor.class);
                            mCounsellor.postValue(counsellor);
                        }
                        else
                        {
                            Counsellor counsellor = null;
                            mCounsellor.postValue(counsellor);
                        }
                    }
                });
    }

}