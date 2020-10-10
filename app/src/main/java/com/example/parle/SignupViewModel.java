package com.example.parle;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupViewModel extends ViewModel {
    private FirebaseAuth auth;
    private MutableLiveData<Integer> state = new MutableLiveData<>();

    public void setInitialState(){
        state.setValue(0);
    }
    public LiveData<Integer> getState(){
        return state;
    }

    public void signup(String fullName, String email,String username, String password, Context context){
        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            state.postValue(1);
                        }else{
                            state.postValue(2);
                        }
                    }
                });
    }
}