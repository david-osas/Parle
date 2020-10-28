package com.example.parle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parle.R;
import com.example.parle.models.Counsellor;

import java.util.ArrayList;

public class CounselorsAdapter extends RecyclerView.Adapter<CounselorsAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<Counsellor> mList;

    public CounselorsAdapter (Context context, ArrayList<Counsellor> counsellors)
    {
        mContext = context;
        mList = counsellors;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_list_itemx80,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
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
            fullName.setText(mList.get(position).getFullName());
        }
    }
}
