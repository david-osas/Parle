package com.example.parle.detailsActivity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailsActivityViewModel extends ViewModel {

    public MutableLiveData<Integer> updated;
    public Context mContext;

    public String mCountry;
    public String mState;
    public String mPhone_number;
    public String mDate_of_birth;
    public String mReligionText;
    public boolean mPreferredCounsellor;
    public boolean mPreferredSession;

    public FirebaseFirestore db;
    public FirebaseUser mFirebaseUser;

    public void initializeValues(Context context)
    {
        mContext = context;
        updated = new MutableLiveData<>();
        updated.setValue(0);
    }

    public void updateDetails()
    {
        db = FirebaseFirestore.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("students").document(mFirebaseUser.getUid())
                .update("country",mCountry,
                        "state",mState,
                        "phoneNumber",mPhone_number,
                        "dateOfBirth",mDate_of_birth,
                        "religion",mReligionText,
                        "similarReligionCounselor",mPreferredCounsellor,
                        "spiritualCounselling",mPreferredSession)
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
