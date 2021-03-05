package com.example.parle.activities.concentrateActivity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ConcentrateViewModel extends ViewModel {

    public MutableLiveData<Integer> updated;
    public Context mContext;
    private FirebaseUser mUser;
    private FirebaseFirestore mDb;

    public void initializeValues(Context context)
    {
        mContext = context;
        updated = new MutableLiveData<>();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDb = FirebaseFirestore.getInstance();
    }

    public void updateDetailsForStudent(String concentrate)
    {

        mDb.collection("students").document(mUser.getUid()).update("concentrate",concentrate)
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

        mDb.collection("counsellors").document(mUser.getUid()).update("concentrate",concentrate)
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
