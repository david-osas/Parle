package com.example.parle.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parle.MainChatActivity;
import com.example.parle.R;
import com.example.parle.SessionHelper;
import com.example.parle.models.ChatsModel;
import com.example.parle.models.Counsellor;
import com.example.parle.models.Student;

import java.util.ArrayList;

public class SessionChatListAdapter extends RecyclerView.Adapter<SessionChatListAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<Student> mStudents;
    private ArrayList<Counsellor> mCounsellors;
    private int type;//tells us whether this list conatins stdents or counsllors. 0 == counsellors list 1== student list
    private String userId;
    public ArrayList<ChatsModel> mChatsModels;

    public SessionChatListAdapter(Context context, ArrayList<Student> students, ArrayList<Counsellor> counsellors, ArrayList<ChatsModel> chats, int type) {
        mContext = context;
        mStudents = students;
        mCounsellors = counsellors;
        this.type = type;
        this.mChatsModels = chats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sessions_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        if(type==0)//the list is a list of counsellors
        {
            holder.name.setText(mCounsellors.get(position).getFullName());
        }
        else//list of students
        {
            holder.name.setText(mStudents.get(position).getUsername());
        }
        holder.mConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MainChatActivity.class);

                if(type==0)
                {
                    SessionHelper.getInstance().currentCounsellor = mCounsellors.get(position);
                    Log.i("id user", mCounsellors.get(position).getUserId());
                }
                else
                {
                    SessionHelper.getInstance().currentStudent = mStudents.get(position);
                    Log.i("id-user", mStudents.get(position).getUserId());
                }
                SessionHelper.getInstance().currentChat = mChatsModels.get(position);
                Log.i("id-student", mChatsModels.get(position).getStudentId());
                Log.i("id-counsellor", mChatsModels.get(position).getCounsellorId());

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(type==0)
            return mCounsellors.size();
        else
            return mStudents.size();
    }

    public void updateList(ArrayList<Student> students,ArrayList<Counsellor> counsellors)
    {
        if(counsellors==null)
        {
            mStudents.clear();
            mStudents.addAll(students);
        }
        else
        {
            mCounsellors.clear();
            mCounsellors.addAll(counsellors);
        }
        this.notifyDataSetChanged();

    }


    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private ConstraintLayout mConstraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.chatHeadName);
            mConstraintLayout = itemView.findViewById(R.id.body);
        }
    }
}
