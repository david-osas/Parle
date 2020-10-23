package com.example.parle.studentFragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parle.adapters.ExpandableListAdapter;
import com.example.parle.R;
import com.example.parle.SelectionActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;


public class Profile extends Fragment {

    private View mView;
    private EditText datePicker;
    Calendar mCalendar;
    DatePickerDialog mPicker;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().findViewById(R.id.homeBackground).setBackgroundColor(getActivity().getColor(android.R.color.white));

        mView = inflater.inflate(R.layout.fragment_profile, container, false);
        ExpandableListView expandableListView = mView.findViewById(R.id.profile_details_list);
        expandableListView.setAdapter(new ExpandableListAdapter(mView.getContext(),expandableListView));
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

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getActivity(), getString(R.string.logout), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), SelectionActivity.class);
        startActivity(intent);
    }
}