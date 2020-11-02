package com.example.parle.profileViewFragment;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parle.models.Counsellor;
import com.example.parle.models.Request;
import com.example.parle.models.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileViewViewModel extends ViewModel
{
    public MutableLiveData<Counsellor> mCounsellor;
    public MutableLiveData<Student> mStudent;
    public FirebaseFirestore mDb;
    public FirebaseUser mFirebaseUser;
    public MutableLiveData<String> requestId;
    private Context mContext;
    public MutableLiveData<Boolean> requestSent;

    public void initializeValues(Context context)
    {
        mContext = context;
        mDb = FirebaseFirestore.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void postCounsellorValue(Counsellor counsellor )
    {
        mCounsellor = new MutableLiveData<>();
        mCounsellor.postValue(counsellor);
    }

    public void sendRequest(String studentId, String CounsellorId) {
        Request request = new Request();
        request.setCounsellorId(CounsellorId);
        request.setStudentId(studentId);

        DocumentReference documentReference = mDb.collection("requests").document();
        request.setRequestId(documentReference.getId());


        documentReference.set(request);
//        mDb.collection("requests").document("ose4g.com.ose4g").set(request);
//        Toast.makeText(mContext,"I reaally dont understans again",Toast.LENGTH_SHORT).show();
    }

}
