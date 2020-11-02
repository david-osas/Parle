package com.example.parle.studentFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parle.StudentHomePageViewModel;
import com.example.parle.adapters.CounselorsAdapter;
import com.example.parle.R;
import com.example.parle.models.Counsellor;

import java.util.ArrayList;
import java.util.List;


public class Counsellors extends Fragment {

    View mView;
    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayoutManager;
    CounselorsAdapter mCounselorsAdapter;
    StudentHomePageViewModel mViewModel;
    ArrayList<Counsellor> mAllCounsellors;

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

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(StudentHomePageViewModel.class);
        mAllCounsellors = new ArrayList<>();


        mRecyclerView = mView.findViewById(R.id.counselors_list);
        mViewModel.getAllCounsellor().observe(requireActivity(), new Observer<List<Counsellor>>() {
            @Override
            public void onChanged(List<Counsellor> counsellors) {
                mAllCounsellors = (ArrayList<Counsellor>) counsellors;
                mCounselorsAdapter = new CounselorsAdapter(mView.getContext(),mAllCounsellors,1);
                mGridLayoutManager = new GridLayoutManager(mView.getContext(),3);
                mRecyclerView.setAdapter(mCounselorsAdapter);
                mRecyclerView.setLayoutManager(mGridLayoutManager);
            }
        });

    }
}