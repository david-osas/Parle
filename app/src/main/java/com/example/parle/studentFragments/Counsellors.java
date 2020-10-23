package com.example.parle.studentFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parle.adapters.CounselorsAdapter;
import com.example.parle.R;


public class Counsellors extends Fragment {

    View mView;
    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayoutManager;
    CounselorsAdapter mCounselorsAdapter;

    public Counsellors() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_counsellors, container, false);
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.homeBackground).setBackgroundColor(getActivity().getColor(android.R.color.white));
        mRecyclerView = mView.findViewById(R.id.counselors_list);
        mCounselorsAdapter = new CounselorsAdapter(mView.getContext());
        mGridLayoutManager = new GridLayoutManager(mView.getContext(),3);
        mRecyclerView.setAdapter(mCounselorsAdapter);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        return mView;
    }
}