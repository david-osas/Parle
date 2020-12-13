package com.example.parle.studentFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parle.StudentHomePageViewModel;
import com.example.parle.adapters.ArticleAdapter;
import com.example.parle.adapters.ChatHeadAdapter;
import com.example.parle.R;
import com.example.parle.adapters.CounselorsAdapter;
import com.example.parle.databinding.FragmentHomeBinding;
import com.example.parle.models.Counsellor;
import com.example.parle.models.Student;
import com.example.parle.profileViewFragment.ProfileViewViewModel;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    RecyclerView mRecyclerViewChats;
    RecyclerView mRecyclerViewArticles;
    View mView;
    FragmentHomeBinding mBinding;
    StudentHomePageViewModel mViewModel;
    TextView suggestedStuff;
    private ArrayList<Counsellor> mAllCounsellors;
    ChatHeadAdapter mChatHeadAdapter;
    ArrayList<Student> mAllStudentRequestedSessions;


    public Home() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //I changed from using findViewBYId to using bindimg in this fragment.
        //might do the same to other fragments later

        // Inflate the layout for this fragment
        mBinding = FragmentHomeBinding.inflate(inflater,container,false);
        mView = mBinding.getRoot();

        suggestedStuff = mBinding.signUpToggle;
        getActivity().findViewById(R.id.homeBackground).setBackgroundColor(getActivity().getColor(android.R.color.white));

        //for chats recycler view
        mRecyclerViewChats =mBinding.chatHeads;// mView.findViewById(R.id.chat_heads);


        //for articles recycler view
        mRecyclerViewArticles = mBinding.articlesListGrid;//mView.findViewById(R.id.articles_list_grid);
        mRecyclerViewArticles.setAdapter(new ArticleAdapter(mView.getContext()));
        mRecyclerViewArticles.setLayoutManager(new GridLayoutManager(mView.getContext(),2));

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(StudentHomePageViewModel.class);
        if(mViewModel.user.equals("counsellor"))
            setUpForCounsellor();
        else
            setUpForStudent();

        ;

    }

    public void setUpForStudent()
    {
        //show the userame of the student
        mViewModel.getStudent();
        suggestedStuff.setText(getActivity().getString(R.string.suggestedCounsellors));
        mViewModel.mStudent.observe(requireActivity(), new Observer<Student>() {
            @Override
            public void onChanged(Student student) {
                if(student!=null)
                    mBinding.hiTextView.setText(getActivity().getString(R.string.hi)+" "+student.getUsername());
                else
                    Toast.makeText(requireContext(),requireActivity().getString(R.string.unable_to_update_details),Toast.LENGTH_LONG).show();
            }
        });

        mAllCounsellors = new ArrayList<>();
        mChatHeadAdapter = new ChatHeadAdapter(requireContext(),0,new ArrayList<Counsellor>(),null);
        mRecyclerViewChats.setAdapter(mChatHeadAdapter);
        mRecyclerViewChats.setLayoutManager(new LinearLayoutManager(mView.getContext(),RecyclerView.HORIZONTAL,false));

        mViewModel.getAllCounsellor().observe(requireActivity(), new Observer<List<Counsellor>>() {
            @Override
            public void onChanged(List<Counsellor> counsellors) {
                //mAllCounsellors = (ArrayList<Counsellor>) counsellors;
                //Toast.makeText(requireContext(),"data has changed",Toast.LENGTH_LONG).show();Toast.makeText(requireContext(),"data has changed",Toast.LENGTH_LONG).show();
                mChatHeadAdapter.upddateCounsellors((ArrayList) counsellors);
            }
        });
    }

    public void setUpForCounsellor()
    {
        //show the fullname of the counsellor
        mViewModel.getCounsellor();
        suggestedStuff.setText(getActivity().getString(R.string.requested_sessions));
        mViewModel.mCounsellor.observe(requireActivity(), new Observer<Counsellor>() {
            @Override
            public void onChanged(Counsellor counsellor) {
                if(counsellor!=null)
                    mBinding.hiTextView.setText(getActivity().getString(R.string.hi)+" "+counsellor.getFullName());
                else
                    Toast.makeText(requireContext(),requireActivity().getString(R.string.unable_to_update_details),Toast.LENGTH_LONG).show();
            }
        });

        mAllStudentRequestedSessions = new ArrayList<>();
        mChatHeadAdapter = new ChatHeadAdapter(requireContext(),1,null,new ArrayList<Student>());
        mRecyclerViewChats.setAdapter(mChatHeadAdapter);
        mRecyclerViewChats.setLayoutManager(new LinearLayoutManager(mView.getContext(),RecyclerView.HORIZONTAL,false));



        mViewModel.getRequestedSessions().observe(requireActivity(), new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
               // Toast.makeText(requireContext(),"data has changed",Toast.LENGTH_LONG).show();
                mChatHeadAdapter.updateStudents((ArrayList) students);
            }
        });
    }
}

