package com.example.parle.profileViewFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.parle.R;
import com.example.parle.databinding.FragmentProfileViewBinding;
import com.example.parle.models.Counsellor;
import com.example.parle.models.Student;
import com.example.parle.sharedPreferences.LoginSP;

public class ProfileViewFragment extends Fragment {

    ProfileViewViewModel mViewModel;
    FragmentProfileViewBinding mBinding;
    Counsellor mCounsellor;
    Student mStudent;
    boolean isStudent;

    public ProfileViewFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.homeBackground).setBackgroundColor(getActivity().getColor(android.R.color.white));
        mBinding = FragmentProfileViewBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewViewModel.class);
        mViewModel.initializeValues(requireContext());
         isStudent = LoginSP.getUser(requireActivity()).equals("student");
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(isStudent)
        {
            mViewModel.mCounsellor.observe(requireActivity(), new Observer<Counsellor>() {
                @Override
                public void onChanged(Counsellor counsellor) {
                    mCounsellor = counsellor;
                    mBinding.counsellorName.setText(counsellor.getFullName());
                    mBinding.buttonRequest.setText("Book as Counsellor");
                }
            });
        }


        else
        {
            mViewModel.mStudent.observe(requireActivity(), new Observer<Student>() {
                @Override
                public void onChanged(Student student) {
                    mStudent = student;
                    mBinding.counsellorName.setText(mStudent.getUsername());
                    mBinding.buttonRequest.setText("ACCEPT REQUEST");
                }
            });
        }



        mBinding.buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isStudent)
                {
                    //add new request to request tabs
                    //Toast.makeText(requireContext(),"I actually got to the button",Toast.LENGTH_SHORT).show();
                    mViewModel.sendRequest(mViewModel.mFirebaseUser.getUid(),mCounsellor.getUserId());
                    //add the request id to the counsellor and student
                }

                else
                {
                    mViewModel.acceptRequest(mStudent.getUserId(),mViewModel.mFirebaseUser.getUid());
                }

            }
        });
    }
}