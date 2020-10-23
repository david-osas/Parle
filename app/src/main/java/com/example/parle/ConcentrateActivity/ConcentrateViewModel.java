package com.example.parle.ConcentrateActivity;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parle.PinActivity;
import com.example.parle.R;
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
        updated.setValue(0);
    }

    public void updateDetails(String concentrate)
    {
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDb = FirebaseFirestore.getInstance();
        mDb.collection("students").document(mUser.getUid()).update("concentrate",concentrate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            updated.setValue(1);

                        }
                        else
                        {
                            updated.setValue(2);
                        }
                    }
                });
    }
}
