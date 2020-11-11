package com.example.parle.profileViewFragment;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parle.StudentHomePageViewModel;
import com.example.parle.models.ChatsModel;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileViewViewModel extends ViewModel
{
    public MutableLiveData<Counsellor> mCounsellor;
    public MutableLiveData<Student> mStudent;
    public FirebaseFirestore mDb;
    public FirebaseUser mFirebaseUser;
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


    public void sendRequest(final String studentId, final String counsellorId) {


        mDb.collection("requests").whereEqualTo("counsellorId",counsellorId)
                .whereEqualTo("studentId",studentId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    QuerySnapshot snapshot = task.getResult();
                    if(snapshot.isEmpty())//the request hasnt been made before
                        confirmSend(studentId,counsellorId);
                    else
                        Toast.makeText(mContext,"Duplicate request. You have already sent a request to this counsellor. Wait for apporval", Toast.LENGTH_LONG).show();

                }
            }
        });


    }

    private void confirmSend(final String studentId, final String counsellorId) {
        Request request = new Request();
        request.setCounsellorId(counsellorId);
        request.setStudentId(studentId);

        DocumentReference documentReference = mDb.collection("requests").document();
        final String requestId = documentReference.getId();
        request.setRequestId(requestId);


        documentReference.set(request).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Map<String,String> map = new HashMap<>();

                    Toast.makeText(mContext,"Request sent", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void postStudentValue(Student student )
    {
        mStudent = new MutableLiveData<>();
        mStudent.postValue(student);
    }

    public void acceptRequest(final String studentId, final String counsellorId)
    {
        mDb.collection("requests")
                .whereEqualTo("counsellorId",counsellorId)
                .whereEqualTo("studentId",studentId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().isEmpty())//the request has already been accpeted
                    {
                        Toast.makeText(mContext,"Duplicate request. You have already accepted the request", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Request request = new Request();
                        for(QueryDocumentSnapshot snapshot: task.getResult())
                        {
                            request = snapshot.toObject(Request.class);
                        }
                        confirmAccept(request);
                    }
                }
            }
        });
    }

    private void confirmAccept(final Request request) {
        ChatsModel chatsModel = new ChatsModel();
        chatsModel.setChatId(request.getRequestId());
        chatsModel.setStudentId(request.getStudentId());
        chatsModel.setCounsellorId(request.getCounsellorId());


        mDb.collection("chats").document(request.getRequestId()).set(chatsModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            deleteRequest(request);
                        }
                    }
                });
    }

    private void deleteRequest(Request request) {
        mDb.collection("requests").document(request.getRequestId())
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(mContext, "Request accepted check chats for message",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
