package com.example.parle;

import android.content.Context;
import android.widget.Toast;

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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StudentHomePageViewModel extends ViewModel {

    //should be used with the student home page.
    //it should get user details from the firestore and update them in the app view.

    private FirebaseUser mFirebaseUser;
    private FirebaseFirestore mDb;
    public Context mContext;
    public String user;

    public MutableLiveData<Student> mStudent;
    public MutableLiveData<Counsellor> mCounsellor;
    public MutableLiveData<List<Counsellor>> mAllCounsellors;
    public MutableLiveData<Integer> updated;

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

    public void loadAllCounsellors()
    {
        mAllCounsellors = new MutableLiveData<List<Counsellor>>();
        mDb.collection("counsellors").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            ArrayList<Counsellor> list = new ArrayList<>();
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult())
                            {
                                Counsellor counsellor = documentSnapshot.toObject(Counsellor.class);
                                list.add(counsellor);
                                Toast.makeText(mContext,counsellor.getFullName(),Toast.LENGTH_LONG).show();
                            }
                            mAllCounsellors.postValue(list);
                        }

                        else
                        {
                            Toast.makeText(mContext,mContext.getString(R.string.unableToGetCounsellors),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public MutableLiveData<List<Counsellor>> getAllCounsellor()
    {
        if(mAllCounsellors==null)
        {
           loadAllCounsellors();
        }
        return mAllCounsellors;
    }


    public void updateDetailsForStudent(String concentrate)
    {
        updated = new MutableLiveData<>();
        mDb.collection("students").document(mFirebaseUser.getUid()).update("concentrate",concentrate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            updated.postValue(1);

                        }
                        else
                        {
                            updated.postValue(2);
                        }
                    }
                });
    }

    public void updateDetailsForCounsellor(String concentrate)
    {
        updated = new MutableLiveData<>();
        mDb.collection("counsellors").document(mFirebaseUser.getUid()).update("concentrate",concentrate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            updated.postValue(1);

                        }
                        else
                        {
                            updated.postValue(2);
                        }
                    }
                });
    }

}