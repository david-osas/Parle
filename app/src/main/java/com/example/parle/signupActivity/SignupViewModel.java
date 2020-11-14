package com.example.parle.signupActivity;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parle.models.Counsellor;
import com.example.parle.models.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupViewModel extends ViewModel {
    private FirebaseAuth auth;
    private MutableLiveData<Integer> state ;
    private FirebaseFirestore db;

    public void setInitialState(){
        state = new MutableLiveData<>();
        auth = FirebaseAuth.getInstance();
    }
    public LiveData<Integer> getState(){
        return state;
    }


    //signup the counsellor usong email and password
    public void signupForStudent( final String email, final String password, final Context context, final Student student){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            state.postValue(1);
                            FirebaseUser user = auth.getCurrentUser();
                            student.setUserId(user.getUid());
                            db = FirebaseFirestore.getInstance();
                            db.collection("students").document(user.getUid()).set(student);

                        }else{
                            state.postValue(2);
                        }
                    }
                });
    }

    //signup counsellor using email and password
    public void signUpForCounsellor(final String email, final String password, final Context context, final Counsellor counsellor)
    {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            state.postValue(1);
                            FirebaseUser user = auth.getCurrentUser();
                            counsellor.setUserId(user.getUid());
                            db = FirebaseFirestore.getInstance();
                            db.collection("counsellors").document(user.getUid()).set(counsellor);

                        }else{
                            state.postValue(2);
                        }
                    }
                });
    }


}
