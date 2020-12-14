package com.example.parle;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.parle.models.Counsellor;
import com.example.parle.models.Request;
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

public class StudentHomePageViewModel extends ViewModel {

    //should be used with the student home page.
    //it should get user details from the firestore and update them in the app view.

    private FirebaseUser mFirebaseUser;//current user could be a student or counsellor
    private FirebaseFirestore mDb;//reference to firebaes firestore
    public Context mContext;//context of the calling activity
    public String user;//student or counsellor gotten from LoginSP

    public MutableLiveData<Student> mStudent;//Object of the currently logged in student
    public MutableLiveData<Counsellor> mCounsellor;//object of currently logged in counsellor
    public MutableLiveData<List<Counsellor>> mAllCounsellors;//get list of all counsellors when student is logged in
    public MutableLiveData<List<Student>> mAllRequestedSessions;//get list of all requestes sessions when counsellor is logged in
    public MutableLiveData<Integer> updated;//shows whether concentrate details where updated or not
    public ArrayList<Request> mRequests;//ArrayList to hold all requests

    public void intializeValues(Context context) {
        //initialize values that are likely to be used in other functions.
        user = LoginSP.getUser(context);
        mContext = context;
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDb = FirebaseFirestore.getInstance();
    }

    public void logout()
    {
        //log out from the app
        FirebaseAuth.getInstance().signOut();
        LoginSP.setPin(mContext,"0000");
        LoginSP.setUser(mContext,"none");
    }

    public void getStudent()
    {
            //get the student object of currently logged in student
            mStudent = new MutableLiveData<>();
            mDb.collection("students").document(mFirebaseUser.getUid()).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful())
                            {
                                Student student = task.getResult().toObject(Student.class);
                                mStudent.postValue(student);
                            }
                            else
                            {
                                Student student = null;
                                mStudent.postValue(student);
                            }
                        }
                    });

    }

    public void getCounsellor()
    {
        //gets the counsellor object for the currently logged in counsellor
            mCounsellor = new MutableLiveData<>();
            mDb.collection("counsellors").document(mFirebaseUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful())
                            {
                                Counsellor counsellor = task.getResult().toObject(Counsellor.class);
                                mCounsellor.postValue(counsellor);
                            }
                            else
                            {
                                Counsellor counsellor = null;
                                mCounsellor.postValue(counsellor);
                            }
                        }
                    });

    }

    public void loadAllCounsellors()
    {
        //gets all counsellor that a student can connect with. It is only called whrn a student is logged in
        mAllCounsellors = new MutableLiveData<List<Counsellor>>();
        mDb.collection("counsellors").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            ArrayList<Counsellor> list = new ArrayList<>();
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult())
                            {
                                Counsellor counsellor = documentSnapshot.toObject(Counsellor.class);
                                list.add(counsellor);
                                //Toast.makeText(mContext,counsellor.getFullName(),Toast.LENGTH_LONG).show();
                            }
                            mAllCounsellors.postValue(list);
                        }

                        else
                        {
                            Toast.makeText(mContext,mContext.getString(R.string.unableToGetCounsellors),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public MutableLiveData<List<Counsellor>> getAllCounsellor()
    {
        //works hand in hand with the previous function loadAllCounsellors
        if(mAllCounsellors==null)
        {
           loadAllCounsellors();
        }
        return mAllCounsellors;
    }


    public void updateDetailsForStudent(String concentrate)
    {
        //updates the concentrate part for the students
        updated = new MutableLiveData<>();
        mDb.collection("students").document(mFirebaseUser.getUid()).update("concentrate",concentrate)
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
        //updates the concentrate part for a counsellor
        updated = new MutableLiveData<>();
        mDb.collection("counsellors").document(mFirebaseUser.getUid()).update("concentrate",concentrate)
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

    public void loadAllRequestedSessions()
    {
        //loads all sessions requested by students for the particular counsellor that is logged in
        mAllRequestedSessions = new MutableLiveData<>();

        mRequests = new ArrayList<>();


        mDb.collection("requests").whereEqualTo("counsellorId",mFirebaseUser.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)
                    Log.i("error", error.toString());
                else
                {
                    for(DocumentChange dc: value.getDocumentChanges())
                    {
                        Request request;
                        switch (dc.getType())
                        {
                            case ADDED:
                                request = dc.getDocument().toObject(Request.class);
                                mRequests.add(request);
                                break;
                            case REMOVED:
                                request = dc.getDocument().toObject(Request.class);
                                mRequests.remove(request);
                                break;
                        }
                    }
                //    Toast.makeText(mContext,"All requests gotten",Toast.LENGTH_LONG).show();
                    Log.i("students","finished getting all reuqets");
                    getAllStudents(mRequests);
                }
            }
        });


    }


    public void getAllStudents(ArrayList<Request> requests)
    {
        //loads all students that sent the requests in the list argument
        Log.i("students","i got into this function after");
       // Toast.makeText(mContext,"Start getting students from requests",Toast.LENGTH_LONG).show();
        final ArrayList<Student> studentsThatRequestedSessions = new ArrayList<>();
        for(Request request: requests)
        {
            mDb.collection("students").document(request.getStudentId())
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        Student student = task.getResult().toObject(Student.class);
                        Log.i("students","gotten student "+student.getUsername());
                        studentsThatRequestedSessions.add(student);
                        Log.i("students","initial size of list "+ studentsThatRequestedSessions.size()+"");
                        mAllRequestedSessions.postValue(studentsThatRequestedSessions);
                    }
                }
            });
        }

    }

    public MutableLiveData<List<Student>> getRequestedSessions()
    {
        //works hand in hand with the previous two methods
        if(mAllRequestedSessions==null)
            loadAllRequestedSessions();
        return mAllRequestedSessions;
    }



}