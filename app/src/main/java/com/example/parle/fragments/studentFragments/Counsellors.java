package com.example.parle.fragments.studentFragments;

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
import android.widget.TextView;

import com.example.parle.StudentHomePageViewModel;
import com.example.parle.adapters.CounselorsAdapter;
import com.example.parle.R;
import com.example.parle.adapters.StudentRequestsAdapter;
import com.example.parle.models.Counsellor;
import com.example.parle.models.Student;
import com.example.parle.fragments.profileViewFragment.ProfileViewViewModel;

import java.util.ArrayList;
import java.util.List;


public class Counsellors extends Fragment {

    View mView;
    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayoutManager;
    CounselorsAdapter mCounselorsAdapter;
    StudentRequestsAdapter mStudentRequestsAdapter;
    StudentHomePageViewModel mViewModel;
    ArrayList<Counsellor> mAllCounsellors;
    ArrayList<Student> mAllStudentRequestedSessions;

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


        mViewModel = new ViewModelProvider(requireActivity()).get(StudentHomePageViewModel.class);
        mAllCounsellors = new ArrayList<>();


        mRecyclerView = mView.findViewById(R.id.counselors_list);


        if(mViewModel.user.equals("student"))
        //if user is a student get the list of all counsellors.
        {
            mAllCounsellors = new ArrayList<>();
            mCounselorsAdapter = new CounselorsAdapter(mView.getContext(),mAllCounsellors,1,
                    new ViewModelProvider(requireActivity()).get(ProfileViewViewModel.class));
            mGridLayoutManager = new GridLayoutManager(mView.getContext(),3);
            mRecyclerView.setAdapter(mCounselorsAdapter);
            mRecyclerView.setLayoutManager(mGridLayoutManager);

            mViewModel.getAllCounsellor().observe(requireActivity(), new Observer<List<Counsellor>>() {
                @Override
                public void onChanged(List<Counsellor> counsellors) {
                    //mAllCounsellors = (ArrayList<Counsellor>) counsellors;
                    //Toast.makeText(requireContext(),"data has changed",Toast.LENGTH_LONG).show();Toast.makeText(requireContext(),"data has changed",Toast.LENGTH_LONG).show();
                    mCounselorsAdapter.updateList((ArrayList) counsellors);
                }
            });
        }
        else
            //else then get all students that requested for sessions
        {
            ((TextView) mView.findViewById(R.id.titleCounsellors)).setText(requireContext().getString(R.string.requested_sessions));
            mAllStudentRequestedSessions = new ArrayList<>();
            mStudentRequestsAdapter = new StudentRequestsAdapter(mView.getContext(),mAllStudentRequestedSessions,1,
                    new ViewModelProvider(requireActivity()).get(ProfileViewViewModel.class));

            mGridLayoutManager = new GridLayoutManager(mView.getContext(),3);
            mRecyclerView.setAdapter(mStudentRequestsAdapter);
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            mViewModel.loadAllRequestedSessions();

            mViewModel.getRequestedSessions().observe(requireActivity(), new Observer<List<Student>>() {
                @Override
                public void onChanged(List<Student> students) {
                   // Toast.makeText(requireContext(),"data has changed",Toast.LENGTH_LONG).show();
                    mStudentRequestsAdapter.updateList((ArrayList) students);
                }
            });
        }



        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }
}