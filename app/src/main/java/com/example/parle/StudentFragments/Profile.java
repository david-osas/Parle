package com.example.parle.StudentFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.parle.Adapters.ExpandableListAdapter;
import com.example.parle.R;


public class Profile extends Fragment {

    private View mView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().findViewById(R.id.homeBackground).setBackgroundColor(getActivity().getColor(android.R.color.white));

        mView = inflater.inflate(R.layout.fragment_profile, container, false);
        ExpandableListView expandableListView = mView.findViewById(R.id.profile_details_list);
        expandableListView.setAdapter(new ExpandableListAdapter(mView.getContext(),expandableListView));
        // Inflate the layout for this fragment
        return mView;
    }
}