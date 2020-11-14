package com.example.parle;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parle.adapters.ArticleAdapter;
import com.example.parle.models.ChatsModel;
import com.example.parle.models.Counsellor;
import com.example.parle.models.Message;
import com.example.parle.models.Student;
import com.example.parle.sharedPreferences.LoginSP;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainChatViewModel extends ViewModel {
    private FirebaseUser mFirebaseUser;//current user could be a student or counsellor
    private FirebaseFirestore mDb;//reference to firebaes firestore
    public Context mContext;//context of the calling activity
    public String user;//student or counsellor gotten from LoginSP
    public MutableLiveData<ArrayList<Message>> allMessages;
    public ArrayList<Message> mMessages;




    public void intializeValues(Context context) {
        //initialize values that are likely to be used in other functions.
        user = LoginSP.getUser(context);
        mContext = context;
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDb = FirebaseFirestore.getInstance();
    }

    public void loadAllMessages(String chatId)
    {
        allMessages = new MutableLiveData<>();
        mMessages = new ArrayList<>();
        mDb.collection("chats").document(chatId).collection("messages").orderBy("timestamp", Query.Direction.ASCENDING)
               .addSnapshotListener(new EventListener<QuerySnapshot>() {
                   @Override
                   public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                       if(error!=null)
                       {
                           Log.i("error",error.toString());
                       }
                       else
                       {
                           for(DocumentChange change: value.getDocumentChanges())
                           {
                               switch (change.getType())
                               {
                                   case ADDED:
                                       mMessages.add(change.getDocument().toObject(Message.class));
                                       allMessages.postValue(mMessages);
                                       break;
                               }
                           }
                       }
                   }
               });

    }

    public MutableLiveData<ArrayList<Message>> getAllMessages(String chatId)
    {
        if(allMessages==null)
            loadAllMessages(chatId);
        return allMessages;
    }

    public void sendTextMessage(String text, String chatId)
    {
        Message message = new Message();
        message.setChatId(chatId);
        message.setSenderId(mFirebaseUser.getUid());
        message.setText(text);
        message.setType(0);
        message.setTimestamp(System.currentTimeMillis());

        DocumentReference reference = mDb.collection("chats").document(chatId).collection("messages").document();
        message.setMessageId(reference.getId());

        reference.set(message);
    }


}
