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

public class ChatHeadAdapter extends RecyclerView.Adapter<ChatHeadAdapter.ChatHeadViewHolder>
{
    /*
    * Adapter used for horizontal scrolling chatheads used in the home page.
    * Right now there are no click events working with it.
    * click events only happen front eh couunsellor and chat tabs*/

    private Context mContext;
    private ArrayList<Counsellor> mCounsellors;
    private ArrayList<Student> mStudents;
    private int mType;//0==student then list will be counsellor
    //1 = counsellor then list will be students

    public ChatHeadAdapter(Context context,int type,ArrayList<Counsellor> counsellors,ArrayList<Student> students) {
        mContext = context;
        mType = type;
        if(type==0)
            mCounsellors = counsellors;
        else
            mStudents = students;
    }

    @NonNull
    @Override
    public ChatHeadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_list_item,parent,false);
        return new ChatHeadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHeadViewHolder holder, int position) {
        if(mType==0)
        {
            holder.text.setText(mCounsellors.get(position).getFullName());
        }
        else
            holder.text.setText(mStudents.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        if(mType==0)
            return mCounsellors.size();
        else
            return mStudents.size();

    }

    public void updateStudents(ArrayList<Student> students)
    {
        mStudents.addAll(students);
        notifyDataSetChanged();
    }

    public void upddateCounsellors(ArrayList<Counsellor> counsellors)
    {
        mCounsellors.addAll(counsellors);
        notifyDataSetChanged();
    }

    class ChatHeadViewHolder extends RecyclerView.ViewHolder
    {
        TextView text;

        public ChatHeadViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }
}
