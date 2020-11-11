package com.example.parle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parle.R;
import com.example.parle.models.Counsellor;
import com.example.parle.models.Student;

import java.util.ArrayList;

public class SessionChatListAdapter extends RecyclerView.Adapter<SessionChatListAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<Student> mStudents;
    private ArrayList<Counsellor> mCounsellors;
    private int type;//tells us whether this list conatins stdents or counsllors. 0 == counsellors list 1== student list

    public SessionChatListAdapter(Context context, ArrayList<Student> students, ArrayList<Counsellor> counsellors, int type) {
        mContext = context;
        mStudents = students;
        mCounsellors = counsellors;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sessions_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(type==0)//the list is a list of counsellors
        {
            holder.name.setText(mCounsellors.get(position).getFullName());
        }
        else//list of students
        {
            holder.name.setText(mStudents.get(position).getUsername());
        }
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
            mStudents.addAll(students);
        }
        else
        {
            mCounsellors.addAll(counsellors);
        }
        this.notifyDataSetChanged();

    }


    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.chatHeadName);
        }
    }
}
