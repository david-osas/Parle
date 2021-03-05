package com.example.parle.fragments.ChatTabFragments;

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

import com.example.parle.R;
import com.example.parle.StudentHomePageViewModel;
import com.example.parle.adapters.CounselorsAdapter;
import com.example.parle.adapters.StudentRequestsAdapter;
import com.example.parle.databinding.FragmentSuggestedCounsellorsBinding;
import com.example.parle.models.Counsellor;
import com.example.parle.models.Student;
import com.example.parle.fragments.profileViewFragment.ProfileViewViewModel;
import com.example.parle.sharedPreferences.LoginSP;

import java.util.ArrayList;
import java.util.List;

public class SuggestedCounsellors extends Fragment {

    View mView;
    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayoutManager;
    CounselorsAdapter mCounselorsAdapter;
    StudentHomePageViewModel mViewModel;
    ArrayList<Counsellor> mAllCounsellors;
    ArrayList<Student> mAllStudentsThatRequested;
    StudentRequestsAdapter mStudentRequestsAdapter;
    FragmentSuggestedCounsellorsBinding mBinding;

    public SuggestedCounsellors() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentSuggestedCounsellorsBinding.inflate(inflater,container,false);
        mView = mBinding.getRoot();





        mViewModel = new ViewModelProvider(requireActivity()).get(StudentHomePageViewModel.class);
        mAllCounsellors = new ArrayList<>();
        mRecyclerView = mView.findViewById(R.id.counselors_list);


        if(LoginSP.getUser(requireContext()).equals("student"))
        {

            mAllCounsellors = new ArrayList<>();
            mGridLayoutManager = new GridLayoutManager(mView.getContext(),3);
            mCounselorsAdapter = new CounselorsAdapter(mView.getContext(),mAllCounsellors,2,
                    new ViewModelProvider(requireActivity()).get(ProfileViewViewModel.class));
            mRecyclerView.setAdapter(mCounselorsAdapter);
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            mViewModel.getAllCounsellor().observe(requireActivity(), new Observer<List<Counsellor>>() {
                @Override
                public void onChanged(List<Counsellor> counsellors) {
                   mCounselorsAdapter.updateList((ArrayList) counsellors);
                }
            });
        }
        else
        {
            mAllStudentsThatRequested = new ArrayList<>();
            mStudentRequestsAdapter = new StudentRequestsAdapter(mView.getContext(),mAllStudentsThatRequested,2,
                    new ViewModelProvider(requireActivity()).get(ProfileViewViewModel.class));

            mGridLayoutManager = new GridLayoutManager(mView.getContext(),3);
            mRecyclerView.setAdapter(mStudentRequestsAdapter);
            mRecyclerView.setLayoutManager(mGridLayoutManager);


            mViewModel.getRequestedSessions().observe(requireActivity(), new Observer<List<Student>>() {
                @Override
                public void onChanged(List<Student> students) {
                   // Toast.makeText(requireContext(),"data has changed",Toast.LENGTH_LONG).show();
                    mStudentRequestsAdapter.updateList((ArrayList) students);

                }
            });
        }




        return mView ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);






    }
}