package com.example.parle.StudentFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parle.Adapters.ArticleAdapter;
import com.example.parle.Adapters.ChatHeadAdapter;
import com.example.parle.R;

public class Home extends Fragment {

    RecyclerView mRecyclerViewChats;
    RecyclerView mRecyclerViewArticles;
    View mView;

    public Home() {
        // Required empty public constructor
    }


    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerViewChats = mView.findViewById(R.id.chat_heads);
        mRecyclerViewChats.setAdapter(new ChatHeadAdapter(mView.getContext()));
        mRecyclerViewChats.setLayoutManager(new LinearLayoutManager(mView.getContext(),RecyclerView.HORIZONTAL,false));
        mRecyclerViewArticles = mView.findViewById(R.id.articles_list_grid);
        mRecyclerViewArticles.setAdapter(new ArticleAdapter(mView.getContext()));
        mRecyclerViewArticles.setLayoutManager(new GridLayoutManager(mView.getContext(),2));
        return mView;
    }
}