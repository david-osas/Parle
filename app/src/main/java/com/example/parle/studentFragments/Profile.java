package com.example.parle.studentFragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parle.StudentHomePageViewModel;
import com.example.parle.adapters.ExpandableListAdapter;
import com.example.parle.R;
import com.example.parle.SelectionActivity;
import com.example.parle.sharedPreferences.LoginSP;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;


public class Profile extends Fragment {

    private View mView;
    private EditText datePicker;
    Calendar mCalendar;
    DatePickerDialog mPicker;
    private Context mContext;
    private StudentHomePageViewModel mViewModel;
    private ExpandableListView mExpandableListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().findViewById(R.id.homeBackground).setBackgroundColor(getActivity().getColor(android.R.color.white));

        mView = inflater.inflate(R.layout.fragment_profile, container, false);
        mExpandableListView = mView.findViewById(R.id.profile_details_list);

        // Inflate the layout for this fragment
        TextView textView = mView.findViewById(R.id.logout);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel =  new ViewModelProvider(requireActivity()).get(StudentHomePageViewModel.class);

        if(mViewModel.user.equals("counsellor"))
            setUpForCounsellor();
        else
            setUpForStudent();

        mExpandableListView.setGroupIndicator(null);
        mExpandableListView.setChildIndicator(null);
        mExpandableListView.setChildDivider(requireActivity().getDrawable(R.color.white));
        mExpandableListView.setDivider(requireActivity().getDrawable(R.color.white));
        mExpandableListView.setDividerHeight(2);

    }

    private void setUpForStudent() {
        mExpandableListView.setAdapter(new ExpandableListAdapter(mView.getContext(),mExpandableListView,"student"));
    }

    private void setUpForCounsellor() {
        mExpandableListView.setAdapter(new ExpandableListAdapter(mView.getContext(),mExpandableListView,"counsellor"));
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getActivity(), getString(R.string.logout), Toast.LENGTH_SHORT).show();
        LoginSP.setUser(getContext(),"none");
        LoginSP.setPin(getContext(),"0000");
        Intent intent = new Intent(getActivity(), SelectionActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}