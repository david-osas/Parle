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


public class Articles extends Fragment {

    View mView;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    ArticleAdapter mArticleAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_articles, container, false);
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.homeBackground).setBackgroundColor(getActivity().getColor(android.R.color.white));
        mRecyclerView = mView.findViewById(R.id.articles_lists);
        mArticleAdapter = new ArticleAdapter(mView.getContext());
        mLinearLayoutManager = new LinearLayoutManager(mView.getContext());
        mRecyclerView.setAdapter(mArticleAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        return mView;
    }
}