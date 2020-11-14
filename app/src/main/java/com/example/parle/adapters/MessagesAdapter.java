package com.example.parle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parle.R;
import com.example.parle.models.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter {

    Context mContext;
    ArrayList<Message> mMessages;
    String mUserId;
    RecyclerView mRecyclerView;

    public MessagesAdapter(Context context, ArrayList<Message> messages,RecyclerView recyclerView) {
        mContext = context;
        mMessages = messages;
        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mRecyclerView= recyclerView;
    }

    @Override
    public int getItemViewType(int position) {
        //return ((int) (Math.random()*10))%2;
        if(mMessages.get(position).getSenderId().equals(mUserId))
            return 0;
        return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType)
        {
            case 0:
                view = LayoutInflater.from(mContext).inflate(R.layout.message_sent_item,parent,false);
                return new SentMessageViewHolder(view);
            case 1:
                view = LayoutInflater.from(mContext).inflate(R.layout.message_received_item,parent,false);
                return new ReceivedMessageViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getClass()==SentMessageViewHolder.class)
        {
            ((SentMessageViewHolder) holder).textMessage.setText(mMessages.get(position).getText());
        }

        else
        {
            ((ReceivedMessageViewHolder) holder).textMessage.setText(mMessages.get(position).getText());
        }

    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public void updateList(ArrayList<Message> messages)
    {
        mMessages.clear();
        mMessages.addAll(messages);
        this.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(getItemCount()-1);
    }

    class SentMessageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textMessage;
        public TextView timeDetails;

        public SentMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.textMessageMain);
            timeDetails = itemView.findViewById(R.id.time_details);
        }
    }

    class ReceivedMessageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textMessage;
        public TextView timeDetails;
        public ReceivedMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.textMessageMain);
            timeDetails = itemView.findViewById(R.id.time_details);
        }
    }
}
