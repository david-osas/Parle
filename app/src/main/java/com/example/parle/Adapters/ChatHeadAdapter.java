package com.example.parle.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parle.R;

public class ChatHeadAdapter extends RecyclerView.Adapter<ChatHeadAdapter.ChatHeadViewHolder>
{
    private Context mContext;
    public ChatHeadAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ChatHeadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_list_item,parent,false);
        return new ChatHeadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHeadViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 9;
    }

    class ChatHeadViewHolder extends RecyclerView.ViewHolder
    {

        public ChatHeadViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
