package com.example.parle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parle.ChatTabFragments.SessionChatViewModel;
import com.example.parle.adapters.MessagesAdapter;
import com.example.parle.databinding.ActivityMainChatBinding;
import com.example.parle.models.ChatsModel;
import com.example.parle.models.Counsellor;
import com.example.parle.models.Message;
import com.example.parle.models.Student;
import com.example.parle.sharedPreferences.LoginSP;

import java.util.ArrayList;

public class MainChatActivity extends AppCompatActivity {


    private ActivityMainChatBinding mBinding;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private MessagesAdapter mMessagesAdapter;
    private MainChatViewModel mViewModel;
    private Counsellor mCounsellor;
    private Student mStudent;
    private ChatsModel currentChat;
    private EditText mMyText;
    private ArrayList<Message> mMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainChatBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mViewModel = new ViewModelProvider(this).get(MainChatViewModel.class);
        mViewModel.intializeValues(this);

        mCounsellor = SessionHelper.getInstance().currentCounsellor;
        mStudent = SessionHelper.getInstance().currentStudent;
        currentChat = SessionHelper.getInstance().currentChat;

        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);//changing the toolbar of the activity

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//adding the back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);//to the toolbar
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);//and changing the drawbale used


        mMessages = new ArrayList<>();//all messages for this chat
        mRecyclerView = mBinding.chatMessages;//recyclerview involved
        mLinearLayoutManager = new LinearLayoutManager(this);//Layout manager for this recycler view
        mLinearLayoutManager.setStackFromEnd(true);//make the views to start at the bottom like whatsapp chats
        mMessagesAdapter = new MessagesAdapter(this,mMessages,mRecyclerView);//Adapter for all the messages in  the chat
        mRecyclerView.setAdapter(mMessagesAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        setUpToolbar();//set the name of the correct CHATEE in the toolbar.
        //CHATEE is the person you're chatting with



        mMyText = mBinding.myTextMessage;

        mBinding.actionSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = mBinding.myTextMessage.getText().toString();
                if(!(text.isEmpty()))
                {
                    mViewModel.sendTextMessage(text, currentChat.getChatId());
                    mMyText.setText("");
                }
                else//if box is empty prompt them to enter message
                    Toast.makeText(MainChatActivity.this,"Please enter your message",Toast.LENGTH_LONG).show();
            }
        });

        //when the messages have been gotten from the firestore, update the recyclerView adapter
        mViewModel.getAllMessages(currentChat.getChatId()).observe(this, new Observer<ArrayList<Message>>() {
            @Override
            public void onChanged(ArrayList<Message> messages) {
                mMessagesAdapter.updateList(messages);
            }
        });
    }

    public void setUpToolbar()
    {
        //sets the name of the correct student/counsellor in the toolbar
        if(mViewModel.user.equals("student"))
        {
            mBinding.nameOfChatter.setText(mCounsellor.getFullName());
        }
        else
        {
            mBinding.nameOfChatter.setText(mStudent.getUsername());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:{onBackPressed(); break;}
        }
        return super.onOptionsItemSelected(item);
    }
}