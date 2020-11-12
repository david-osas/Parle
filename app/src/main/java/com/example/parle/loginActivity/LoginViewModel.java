package com.example.parle.loginActivity;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.parle.models.Student;
import com.example.parle.sharedPreferences.LoginSP;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginViewModel extends ViewModel {
    private FirebaseAuth auth;
    private MutableLiveData<Integer> state = new MutableLiveData<>();
    public FirebaseUser mFirebaseUser;
    public FirebaseFirestore mFirestore;
    private Context mContext;
    private MutableLiveData<Integer> isStudent;
    public MutableLiveData<String> pin;

    public void setInitialState(Context context){
        mContext = context;
        isStudent = new MutableLiveData<>();

    }

    public LiveData<Integer> getState(){
        return state;
    }

    public void login(String email, String password, Context context){
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            state.postValue(1);
                            mFirebaseUser = auth.getCurrentUser();
                            mFirestore = FirebaseFirestore.getInstance();
                        }
                        else{
                            state.postValue(2);
                        }
                    }
                });
    }

    public void setIsStudent()
    {
        final DocumentReference documentReference = mFirestore.collection("students").document(mFirebaseUser.getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    pin = new MutableLiveData<>();
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists())
                    {
                        Student student = task.getResult().toObject(Student.class);
                        Toast.makeText(mContext, student.getUserId()+student.getUsername(),Toast.LENGTH_LONG).show();
                        isStudent.postValue(1);
                    }

                    else {
                        isStudent.postValue(2);
                    }
                }
            }
        });
    }

    public MutableLiveData<Integer> getIsStudent()
    {
        return isStudent;
    }


    public void logout()
    {
        FirebaseAuth.getInstance().signOut();
        LoginSP.setUser(mContext,"none");
    }

    public void getPin()
    {
        mFirestore.collection("students").document(mFirebaseUser.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            String p = task.getResult().toObject(Student.class).getPin();
                            //Toast.makeText(mContext,"pin = "+p,Toast.LENGTH_LONG).show();
                            pin.postValue(p);
                        }
                    }
                });
    }

}
