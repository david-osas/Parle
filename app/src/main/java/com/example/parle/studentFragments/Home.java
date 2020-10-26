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
import com.example.parle.databinding.FragmentHomeBinding;
import com.example.parle.models.Counsellor;
import com.example.parle.models.Student;

public class Home extends Fragment {

    RecyclerView mRecyclerViewChats;
    RecyclerView mRecyclerViewArticles;
    View mView;
    FragmentHomeBinding mBinding;
    StudentHomePageViewModel mViewModel;
    TextView suggestedStuff;


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
        mRecyclerViewChats.setAdapter(new ChatHeadAdapter(mView.getContext()));
        mRecyclerViewChats.setLayoutManager(new LinearLayoutManager(mView.getContext(),RecyclerView.HORIZONTAL,false));

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
    }

    public void setUpForCounsellor()
    {
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
    }
}

