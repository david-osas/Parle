package com.example.parle.ChatTabFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.parle.R;
import com.example.parle.adapters.SessionChatListAdapter;
import com.example.parle.databinding.FragmentChatBinding;
import com.example.parle.models.ChatsModel;
import com.example.parle.models.Counsellor;
import com.example.parle.models.Student;

import java.util.ArrayList;


public class ChatFragment extends Fragment {

    public View mView;
    private FragmentChatBinding mBinding;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private SessionChatListAdapter mChatListAdapter;
    SessionChatViewModel mViewModel;
    private ArrayList<Student> mStudents;
    private ArrayList<Counsellor> mCounsellors;

    public ChatFragment() {
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
        mBinding = FragmentChatBinding.inflate(inflater,container,false);
        mView = mBinding.getRoot();
        mViewModel = new ViewModelProvider(requireActivity()).get(SessionChatViewModel.class);
        mViewModel.intializeValues(requireContext());
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCounsellors = new ArrayList<>();
        mStudents = new ArrayList<>();



        if(mViewModel.user.equals("student"))
        {
            mRecyclerView = mBinding.myChats;
            mChatListAdapter = new SessionChatListAdapter(requireContext(),mStudents,mCounsellors,null,0);
            mLinearLayoutManager = new LinearLayoutManager(requireContext());
            mRecyclerView.setAdapter(mChatListAdapter);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);

            mViewModel.getCounsellorsList()
                    .observe(requireActivity(), new Observer<ArrayList<Counsellor>>() {
                @Override
                public void onChanged(ArrayList<Counsellor> counsellors) {
                    mChatListAdapter.updateList(null,counsellors);
                }
            });
        }

        else
        {
            mRecyclerView = mBinding.myChats;
            mChatListAdapter = new SessionChatListAdapter(requireContext(),mStudents,mCounsellors,null,1);
            mLinearLayoutManager = new LinearLayoutManager(requireContext());
            mRecyclerView.setAdapter(mChatListAdapter);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);

            mViewModel.getStudentList()
                    .observe(requireActivity(), new Observer<ArrayList<Student>>() {
                @Override
                public void onChanged(ArrayList<Student> students) {
                    mChatListAdapter.updateList(students,null);
                }
            });


        }

        mViewModel.allChats.observe(requireActivity(), new Observer<ArrayList<ChatsModel>>() {
            @Override
            public void onChanged(ArrayList<ChatsModel> chatsModels) {

                mChatListAdapter.mChatsModels = chatsModels;
            }
        });



    }
}
