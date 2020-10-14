package com.example.parle.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.example.parle.R;

import java.util.ArrayList;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    ArrayList<String> mList;
    View mView;
    Context mContext;
    ExpandableListView mExpandableListView;

    public ExpandableListAdapter(Context context, ExpandableListView expandableListView){
        mContext = context;
        LayoutInflater layoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mExpandableListView = expandableListView;
        mView = layoutInflater.inflate(R.layout.personal_details, null);
    }
    @Override
    public int getGroupCount() {
        return 3;
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return "Personal Details";
    }

    @Override
    public Object getChild(int i, int i1) {
        return R.layout.personal_details;
    }

    @Override
    public long getGroupId(int groupId) {
        return groupId;
    }

    @Override
    public long getChildId(int groupPosition, int itemPosition) {
        return itemPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.list_group_header, null);
        }


        TextView txtListChild = (TextView) view
                .findViewById(R.id.group_header);

        txtListChild.setText("Personal Details");

        Typeface bold = ResourcesCompat.getFont(mContext,R.font.montserrat_bold);
        Typeface normal = ResourcesCompat.getFont(mContext,R.font.montserrat);



        ImageView imageView = view.findViewById(R.id.arrow);
        LinearLayout linearLayout = view.findViewById(R.id.container);
        if (b)
        {
            view.setPadding(0, 0, 0, 0);
            linearLayout.setBackgroundTintList(mContext.getColorStateList( R.color.darker_orange));
            txtListChild.setTextColor(mContext.getColor(android.R.color.white));
            txtListChild.setTypeface(bold);
            imageView.setImageResource(R.drawable.arrow_up);
        }

        else
        {
            view.setPadding(0, 0, 0, 32);
            linearLayout.setBackgroundTintList(mContext.getColorStateList( R.color.light_orange));
            txtListChild.setTextColor(mContext.getColor(android.R.color.black));
            txtListChild.setTypeface(normal);
            imageView.setImageResource(R.drawable.arrow_down);
        }


        if(i==2)
            view.setPadding(0, 0, 0, 0);
        //mExpandableListView.setDividerHeight(100);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.personal_details, null);
        }

       // mExpandableListView.setDividerHeight(0);

        view.setPadding(0,0,0,32);
        return view;
    }



    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
