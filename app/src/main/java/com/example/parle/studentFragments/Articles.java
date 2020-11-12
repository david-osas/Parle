package com.example.parle.studentFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parle.adapters.ArticleAdapter;
import com.example.parle.R;
import com.example.parle.databinding.FragmentArticlesBinding;


public class Articles extends Fragment {

    View mView;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    ArticleAdapter mArticleAdapter;
    FragmentArticlesBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentArticlesBinding.inflate(inflater,container,false);
        mView = mBinding.getRoot();

        getActivity().findViewById(R.id.homeBackground).setBackgroundColor(getActivity().getColor(android.R.color.white));

        mRecyclerView = mBinding.articlesLists;//mView.findViewById(R.id.articles_lists);
        mArticleAdapter = new ArticleAdapter(mView.getContext());
        mLinearLayoutManager = new LinearLayoutManager(mView.getContext());
        mRecyclerView.setAdapter(mArticleAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        return mView;
    }
}