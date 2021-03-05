package com.example.parle.fragments.ChatTabFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parle.R;
import com.example.parle.StudentHomePageViewModel;
import com.example.parle.adapters.SpecialtyAdapter;
import com.example.parle.databinding.FragmentEditChallengeBinding;
import com.example.parle.models.Counsellor;
import com.example.parle.models.Student;
import com.example.parle.sharedPreferences.LoginSP;

import java.util.Arrays;


public class EditChallenge extends Fragment {

    private FragmentEditChallengeBinding mBinding;
    private RecyclerView mRecyclerView;
    public static TextView noSelected;
    private GridLayoutManager mGridLayoutManager;
    String[] mList;
    private SpecialtyAdapter mAdapter;
    StudentHomePageViewModel mViewModel;


    public EditChallenge() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentEditChallengeBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(StudentHomePageViewModel.class);

        mList =getResources().getStringArray(R.array.concentrate_points_list);
        mRecyclerView = mBinding.specialties;

        noSelected = mBinding.noSelected;

        mAdapter = new SpecialtyAdapter(requireContext(),mList,noSelected);

        mGridLayoutManager = new GridLayoutManager(requireContext(),10);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Integer pos = new Integer(position);
                Integer[] span4 = {0,3,4,7,12,18};//span 4
                Integer[] span6 = {1,2,5,6,13,19};//span 6
                Integer[] span12 = {8,11,16,17}; //span10

                if(Arrays.asList(span4).contains(pos))
                {
                    return 4;
                }
                else if(Arrays.asList(span6).contains(pos))
                {
                    return 6;
                }
                else if(Arrays.asList(span12).contains(pos))
                    return 10;

                return 5;
            }

        });

        attachAdapter();

        mBinding.toPinActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToNext();
            }
        });

    }

    public void moveToNext(){
        if(LoginSP.getUser(requireContext()).equals("student"))//update is for student
        {
            mViewModel.updateDetailsForStudent(mAdapter.getChosenOnes());
            mViewModel.updated.observe(requireActivity(), new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if(integer==1)//UPDATE WAS SUCCESSFUL
                    {
                        Toast.makeText(requireContext(), R.string.details_update_succesful, Toast.LENGTH_LONG).show();
                    }
                    else if(integer==2)//UPDATE WAS UNSUCCESSFUL
                    {
                        Toast.makeText(requireContext(), R.string.unable_to_update_details, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        else//update is for counsellor
        {
            mViewModel.updateDetailsForCounsellor(mAdapter.getChosenOnes());
            mViewModel.updated.observe(requireActivity(), new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if(integer==1)//UPDATE WAS SUCCESSFUL
                    {
                        Toast.makeText(requireContext(), R.string.details_update_succesful, Toast.LENGTH_LONG).show();
                    }
                    else if(integer==2)//UPDATE WAS UNSUCCESSFUL
                    {
                        Toast.makeText(requireContext(), R.string.unable_to_update_details, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    public void attachAdapter()
    {
        if(LoginSP.getUser(requireContext()).equals("student"))
        {
            mViewModel.getStudent();
            mViewModel.mStudent.observe(requireActivity(), new Observer<Student>() {
                @Override
                public void onChanged(Student student) {
                    if(student!=null)
                    {
                        String concentrate = student.getConcentrate();
                        mAdapter.chosenOnes = stringToList(concentrate);
                        Log.i("concan","list_length "+ mAdapter.chosenOnes.length);
                        Log.i("concan","first_value "+ mAdapter.chosenOnes[0]);
                        Log.i("concan",concentrate);
                        Log.i("concan","from recycler view "+mAdapter.getChosenOnes());
                        for(String i: mAdapter.chosenOnes)

                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.setLayoutManager(mGridLayoutManager);
                    }
                    else
                        Toast.makeText(requireContext(),getActivity().getString(R.string.unable_to_update_details)+" stident",Toast.LENGTH_LONG).show();
                }
            });
        }

        else
        {
            mViewModel.getCounsellor();
            mViewModel.mCounsellor.observe(requireActivity(), new Observer<Counsellor>() {
                @Override
                public void onChanged(Counsellor counsellor) {
                    if(counsellor!=null)
                    {
                        String concentrate = counsellor.getConcentrate();
                        mAdapter.chosenOnes = stringToList(concentrate);
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.setLayoutManager(mGridLayoutManager);
                    }

                    else
                        Toast.makeText(requireContext(),getActivity().getString(R.string.unable_to_update_details)+" counsellor",Toast.LENGTH_LONG).show();


                }
            });
        }
    }

    public String[] stringToList(String string)
    {
        String[] list = string.split("");
        String[] ans = new String[20];
        for(int i=1;i<list.length;i++)
            ans[i-1]=list[i];
        return ans;
    }
}