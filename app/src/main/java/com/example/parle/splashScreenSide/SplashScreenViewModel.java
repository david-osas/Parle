package com.example.parle.splashScreenSide;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashScreenViewModel extends ViewModel {
    public FirebaseUser mFirebaseUser;
    private Context mContext;
    public FirebaseFirestore mFirestore;
    private MutableLiveData<Integer> isStudent;

    public void  initializeValues(Context context)
    {
        isStudent = new MutableLiveData<>();
        mContext = context;
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();
        isStudent.setValue(0);
    }

    public void setIsStudent(Integer integer)
    {
        isStudent.setValue(integer);
    }

    public boolean userIsNull()
    {
        return (mFirebaseUser == null);
    }

    public void updateStudent()
    {
        final DocumentReference documentReference = mFirestore.collection("students").document(mFirebaseUser.getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists())
                    {
                        isStudent.setValue(1);


                    }

                    else {
                        isStudent.setValue(2);
                    }
                }
            }
        });
    }

    public MutableLiveData<Integer> getIsStudent()
    {
        if(isStudent.getValue().equals(0))
        {
            updateStudent();
        }
        return isStudent;
    }

}
