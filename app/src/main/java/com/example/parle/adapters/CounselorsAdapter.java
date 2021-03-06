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
import com.example.parle.fragments.profileViewFragment.ProfileViewViewModel;

import java.util.ArrayList;

public class CounselorsAdapter extends RecyclerView.Adapter<CounselorsAdapter.ViewHolder>{
    /*
    * Adapter for counselors list shown in counsellor and suggested counsellor fragments*/

    private Context mContext;//context of calling activity
    private ArrayList<Counsellor> mList;//list of counsellors
    private int page;//the adapter is used in both the cousellors fragment and the chats fragment.
    // It helps us to identify whoch one it is in
    private ProfileViewViewModel mViewModel;//helps send data between the recyclerview and the profile view fragment

    public CounselorsAdapter (Context context, ArrayList<Counsellor> counsellors, int page, ProfileViewViewModel viewModel)
    {
        this.page = page;
        mContext = context;
        mList = counsellors;
        mViewModel=viewModel;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_list_itemx80,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bind(position);

        holder.counsellorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(page==1)//from counsellors page
                {
                    mViewModel.postCounsellorValue(mList.get(position));
                    //use the correct navcontroller
                    Navigation.findNavController(view).navigate(R.id.action_action_counselors_to_profileViewFragment);
                }
                else if(page==2)//from suggested counsellors under chats
                {
                    mViewModel.postCounsellorValue(mList.get(position));
                    //use the correct navcontroller
                    Navigation.findNavController(view).navigate(R.id.action_action_messages_to_profileViewFragment);
                }

            }
        });

    }

    public void updateList(ArrayList<Counsellor> list)
    {
        //adapter is usally attached before data actually arrives.
        //this function takes the required data and adds it to the list wihout detaching the adapter
        mList.clear();
        mList.addAll(list);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView fullName;
        ImageView counsellorImage;
        TextView availableTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.counsellorFullName);
            counsellorImage = itemView.findViewById(R.id.counsellorImage);
            availableTime = itemView.findViewById(R.id.time_available);
        }

        public void  bind(int position)
        {
            //This is all we are changing for now.
            fullName.setText(mList.get(position).getFullName());
        }
    }
}
