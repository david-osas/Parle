package com.example.parle.ChatTabFragments;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parle.models.ChatsModel;
import com.example.parle.models.Counsellor;
import com.example.parle.models.Student;
import com.example.parle.sharedPreferences.LoginSP;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SessionChatViewModel extends ViewModel {
    private FirebaseUser mFirebaseUser;//current user could be a student or counsellor
    private FirebaseFirestore mDb;//reference to firebaes firestore
    public Context mContext;//context of the calling activity
    public String user;//student or counsellor gotten from LoginSP
    public ArrayList<ChatsModel> chats;
    public MutableLiveData<ArrayList<ChatsModel>> allChats;
    public MutableLiveData<ArrayList<Counsellor>> mCounsellorsList;
    public MutableLiveData<ArrayList<Student>> mStudentList;

    public void intializeValues(Context context) {
        //initialize values that are likely to be used in other functions.
        user = LoginSP.getUser(context);
        mContext = context;
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDb = FirebaseFirestore.getInstance();
    }

    public void getAllChats(boolean isStudent)
    {
        mCounsellorsList = new MutableLiveData<>();
        mStudentList = new MutableLiveData<>();
        chats = new ArrayList<>();
        allChats = new MutableLiveData<>();
        if(isStudent)
        {
            //gets all chats for a student.
//            mDb.collection("chats").whereEqualTo("studentId",mFirebaseUser.getUid())
//                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    if(task.isSuccessful())
//                    {
//                        for(QueryDocumentSnapshot snapshot:task.getResult())
//                        {
//                            chats.add(snapshot.toObject(ChatsModel.class));
//
//                        }
//                        allChats.postValue(chats);
//                        getUsersFromChats();
//                    }
//                }
//            });
            mDb.collection("chats").whereEqualTo("studentId",mFirebaseUser.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    for(DocumentChange dc:value.getDocumentChanges())
                    {
                        switch(dc.getType())
                        {
                            case ADDED:
                                chats.add(dc.getDocument().toObject(ChatsModel.class));
                        }
                    }
                    allChats.postValue(chats);
                    getUsersFromChats();
                }
            });

        }
        else
        {
            //gets all chats for a counsellor
            Log.i("student","3 Im a counsellor so I go here");
//            mDb.collection("chats").whereEqualTo("counsellorId",mFirebaseUser.getUid())
//                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    if(task.isSuccessful())
//                    {
//                        for(QueryDocumentSnapshot snapshot:task.getResult())
//                        {
//                            chats.add(snapshot.toObject(ChatsModel.class));
//                            Log.i("student","4 Yo Im in the loop");
//                        }
//                        Log.i("student","5 I came out of the loop adn decided to go to another functon");
//                        allChats.postValue(chats);
//                        getUsersFromChats();
//                    }
//                }
//            });

            mDb.collection("chats").whereEqualTo("counsellorId",mFirebaseUser.getUid())
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            for(DocumentChange dc:value.getDocumentChanges())
                            {
                                switch(dc.getType())
                                {
                                    case ADDED:
                                        chats.add(dc.getDocument().toObject(ChatsModel.class));
                                        getUsersFromChats();
                                }
                            }
                            Log.i("student","5 I came out of the loop adn decided to go to another functon");
                            allChats.postValue(chats);

                        }
                    });
        }

    }


    public void getUsersFromChats()
    {
        Log.i("student","6 I initiALIZED them here");
        final ArrayList<Student> students = new ArrayList<>();
        final ArrayList<Counsellor> counsellors  = new ArrayList<>();
        for(ChatsModel chat: chats)
        {
            mDb.collection("students").document(chat.getStudentId())
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        students.add(task.getResult().toObject(Student.class));
                        mStudentList.postValue(students);
                        Log.i("student","7 I posted values here for students");
                    }
                }
            });

            mDb.collection("counsellors").document(chat.getCounsellorId())
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        counsellors.add(task.getResult().toObject(Counsellor.class));
                        mCounsellorsList.postValue(counsellors);
                        Log.i("student","8 I posted values here for counsellor");
                    }
                }
            });
        }
    }

    public MutableLiveData<ArrayList<Student>> getStudentList()
    {
        Log.i("student","1 I got to this function");
        if(mStudentList==null)
        {
            Log.i("student","2 Your list is actuall null so I load it");
            getAllChats(false);
        }
        return mStudentList;

    }

    public MutableLiveData<ArrayList<Counsellor>> getCounsellorsList()
    {

        if(mCounsellorsList==null)
        {
            getAllChats(true);
        }
        return mCounsellorsList;
    }

}
