package com.example.parle.fragments.studentFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parle.fragments.ChatTabFragments.ChatFragment;
import com.example.parle.fragments.ChatTabFragments.EditChallenge;
import com.example.parle.fragments.ChatTabFragments.SuggestedCounsellors;
import com.example.parle.R;
import com.example.parle.adapters.ChatFragmentAdapter;
import com.example.parle.databinding.FragmentChatBinding;
import com.example.parle.databinding.FragmentChatsBinding;
import com.example.parle.sharedPreferences.LoginSP;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class Chats extends Fragment {

    private TabLayout mTabLayout;
    private TabLayoutMediator mTabLayoutMediator;
    private FragmentChatsBinding mBinding;
    private ViewPager2 mViewPager2;
    private View mView;
    private ArrayList<Fragment> mFragments;
    public Chats() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabLayout = mBinding.chatTabs;
        mViewPager2 = mBinding.chatSlider;
        mFragments = new ArrayList<>();
        mFragments.add(new EditChallenge());
        mFragments.add(new ChatFragment());
        mFragments.add(new SuggestedCounsellors());
        final int[] names = {R.string.edit_challenge,R.string.chats,R.string.suggestedCounselorsCaps};

        if(LoginSP.getUser(requireContext()).equals("counsellor"))
        {
            names[0] = R.string.edit_aoe;
            names[2] = R.string.requested_sessions;
        }


        mViewPager2.setAdapter(new ChatFragmentAdapter(requireActivity().getSupportFragmentManager(),requireActivity().getLifecycle(),mFragments));


        mTabLayoutMediator = new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(getResources().getString(names[position]));
            }
        });


        mTabLayoutMediator.attach();
        mViewPager2.setCurrentItem(1,false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.homeBackground).setBackgroundColor(getActivity().getColor(R.color.light_orange));
        mBinding = FragmentChatsBinding.inflate(inflater,container,false);
        mView = mBinding.getRoot();

        return mView;


    }
}