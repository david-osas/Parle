package com.example.parle;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parle.Models.Student;
import com.example.parle.SharedPreferences.LoginSP;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupViewModel extends ViewModel {
    private FirebaseAuth auth;
    private MutableLiveData<Integer> state = new MutableLiveData<>();
    private FirebaseFirestore db;

    public void setInitialState(){
        state.setValue(0);
    }
    public LiveData<Integer> getState(){
        return state;
    }

    public void signup(String fullName, final String email, String username, final String password, final Context context, final Student student,final String pin){
        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            state.postValue(1);
                            LoginSP.setLoginDetails(context,password,email,pin);
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
}
