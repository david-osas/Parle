package com.example.parle.StudentFragments;

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

import com.example.parle.Adapters.ArticleAdapter;
import com.example.parle.Adapters.ChatHeadAdapter;
import com.example.parle.R;
import com.example.parle.databinding.FragmentHomeBinding;

public class Home extends Fragment {

    RecyclerView mRecyclerViewChats;
    RecyclerView mRecyclerViewArticles;
    View mView;
    FragmentHomeBinding mBinding;


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
        mBinding = FragmentHomeBinding.inflate(getLayoutInflater());
        mView = mBinding.getRoot();

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


    }
}

