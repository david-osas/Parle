package com.example.parle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parle.R;
import com.example.parle.models.Counsellor;
import com.example.parle.models.Student;
import com.example.parle.profileViewFragment.ProfileViewViewModel;

import java.util.ArrayList;

public class StudentRequestsAdapter  extends RecyclerView.Adapter<StudentRequestsAdapter.Viewholder>{
    //ADapter for list of student requests. Similar to counsellors adapter but this time for students.
    private Context mContext;
    private ArrayList<Student> mList;
    private int page;//the adapter is used in both the cousellors fragment and the chats fragment.
    // It helps us to identify whoch one it is in
    private ProfileViewViewModel mViewModel;//helps send data between the recyclerview and the profile view fragment

    public StudentRequestsAdapter(Context context, ArrayList<Student> students, int page, ProfileViewViewModel viewModel)
    {
        this.page = page;
        mContext = context;
        mList = students;
        mViewModel=viewModel;//needed so we can pass information from this recycelrview to the fragment.
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_list_itemx80,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {

        holder.bind(position);
        holder.counsellorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(page==1)//from counsellors page
                {
                    mViewModel.postStudentValue(mList.get(position));
                    Navigation.findNavController(view).navigate(R.id.action_action_counselors_to_profileViewFragment);
                }
                else if(page==2)//from suggested counsellors under chats
                {
                    mViewModel.postStudentValue(mList.get(position));
                    Navigation.findNavController(view).navigate(R.id.action_action_messages_to_profileViewFragment);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateList(ArrayList<Student> list)
    {
        mList.addAll(list);
        this.notifyDataSetChanged();
    }

    class Viewholder extends RecyclerView.ViewHolder{
        TextView fullName;
        ImageView counsellorImage;
        TextView availableTime;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.counsellorFullName);
            counsellorImage = itemView.findViewById(R.id.counsellorImage);
            availableTime = itemView.findViewById(R.id.time_available);
        }

        public void  bind(int position)
        {
            fullName.setText(mList.get(position).getUsername());
        }
    }
}
