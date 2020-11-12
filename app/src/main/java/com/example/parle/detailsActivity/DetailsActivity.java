package com.example.parle.detailsActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.parle.concentrateActivity.ConcentrateActivity;
import com.example.parle.models.Student;
import com.example.parle.R;
import com.example.parle.databinding.ActivityDetailsBinding;
import com.example.parle.sharedPreferences.LoginSP;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailsBinding binding;
    private EditText datePicker;
    Calendar mCalendar;
    DatePickerDialog mPicker;
   private DetailsActivityViewModel mViewModel;


    private Student mStudent;
    private String user;
    private TimePickerDialog mTimePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = LoginSP.getUser(this);// getIntent().getStringExtra("user");

        if(user.equals("counsellor"))
        {
            setUpForCounsellor();
        }
        else
        {
           setUpforStudent();
        }

        mViewModel = new ViewModelProvider(this).get(DetailsActivityViewModel.class);

        mViewModel.initializeValues(this);

        mStudent = new Student();






        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.mCountry = binding.country.getText().toString();
                mViewModel.mState  = binding.State.getText().toString();
                mViewModel.mPhone_number = binding.phone.getText().toString();
                mViewModel.mReligionText = binding.religion.getText().toString();

                if(user.equals("student"))
                {
                    mViewModel.mDate_of_birth = binding.datePickerActions.getText().toString();

                    mViewModel.mPreferredCounsellor = (binding.yesOrNo.getText().toString() == getResources().getStringArray(R.array.yes_or_no_list)[0]);
                    mViewModel.mPreferredSession = (binding.religiousCounsellorPrefer.getText().toString() == getResources().getStringArray(R.array.counselor_preference_list)[0]);

                    //ENSURE THAT NO EDITTEXT IS EMPTY
                    if(mViewModel.mCountry.isEmpty() || mViewModel.mState.isEmpty() || mViewModel.mPhone_number.isEmpty() || mViewModel.mDate_of_birth.isEmpty()
                            || mViewModel.mReligionText.isEmpty() || binding.religiousCounsellorPrefer.getText().toString().isEmpty()
                            || binding.yesOrNo.getText().toString().isEmpty())
                    {
                        Toast.makeText(DetailsActivity.this, R.string.please_enter_all_details ,Toast.LENGTH_LONG).show();
                    }

                    else//IF ALL EDITTEXR ARE FILLED
                    {
                        mViewModel.updateDetailsForStudent();//UPDATE THE DETAILS
                        checkUpdateSuccess();
                    }
                }

                else
                {
                    mViewModel.canCounselBasedOnFaith = (binding.yesOrNo.getText().toString() == getResources().getStringArray(R.array.yes_or_no_list)[0]);
                    mViewModel.yearsOfExperience = binding.experience.getText().toString();
                    mViewModel.availableHours = binding.availableHours.getText().toString();
                    mViewModel.startTime = binding.timePicker.getText().toString();

                    if(mViewModel.mCountry.isEmpty() || mViewModel.mState.isEmpty() || mViewModel.mPhone_number.isEmpty()
                            || mViewModel.mReligionText.isEmpty()
                            || binding.yesOrNo.getText().toString().isEmpty())
                    {
                        Toast.makeText(DetailsActivity.this, R.string.please_enter_all_details ,Toast.LENGTH_LONG).show();
                    }

                    else//IF ALL EDITTEXR ARE FILLED
                    {
                        mViewModel.updateDetailsForCounsellor();//UPDATE THE DETAILS
                        checkUpdateSuccess();
                    }
                }


            }
        });

        datePicker = binding.datePickerActions;


        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                mPicker = new DatePickerDialog(DetailsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                datePicker.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        },year,month,day);
                mPicker.show();
            }
        });



        final EditText timePickerEdit = binding.timePicker;

        timePickerEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                mTimePickerDialog = new TimePickerDialog(DetailsActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                int i=0;
                                timePickerEdit.setText(get12HourTime(hour));
                            }
                        },hour,minute,false);
                mTimePickerDialog.show();

            }
        });





        AutoCompleteTextView religion = binding.religion,
                counselor_religion = binding.religiousCounsellorPrefer,
                yes_or_no = binding.yesOrNo,
                experience = binding.experience,
                availableHours = binding.availableHours;
        List<String> religions,
                yesNo,
                counselor_preference;

        religions = Arrays.asList(getResources().getStringArray(R.array.religions_list));
        ArrayAdapter<String> ReligionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,religions);
        religion.setAdapter(ReligionAdapter);
        religion.setCursorVisible(false);
        setListener(religion);

        yesNo= Arrays.asList(getResources().getStringArray(R.array.yes_or_no_list));
        ArrayAdapter<String> YesNoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,yesNo);
        yes_or_no.setAdapter(YesNoAdapter);
        yes_or_no.setCursorVisible(false);
        setListener(yes_or_no);

        counselor_preference= Arrays.asList(getResources().getStringArray(R.array.counselor_preference_list));;
        ArrayAdapter<String> CounselorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,counselor_preference);
        counselor_religion.setAdapter(CounselorAdapter);
        counselor_religion.setCursorVisible(false);
        setListener(counselor_religion);

        experience.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.yearsOfExperience)));
        setListener(experience);


        availableHours.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.availableHours)));
        setListener(availableHours);


    }





    public  static String get12HourTime(int hour) {
        String ans="";
        if(hour==0)
            return "12:00 A.M";
        if(hour>0 && hour <12)
        {
            return hour+":00 A.M";
        }
        if(hour==12)
            return "12:00 P.M";

        return hour%12+":00 P.M";
    }






    public void setListener(AutoCompleteTextView acTV)
    {
        final AutoCompleteTextView acTV1 = acTV;
        acTV1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                acTV1.showDropDown();

            }
        });

        acTV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                acTV1.showDropDown();
            }
        });
    }









    public void checkUpdateSuccess()
    {
        mViewModel.updated.observe(DetailsActivity.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==1)//UPDATE WAS SUCCESSFUL
                {
                    Toast.makeText(DetailsActivity.this, getString(R.string.details_update_succesful), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(DetailsActivity.this, ConcentrateActivity.class);
                    //intent.putExtra("user",user);
                    startActivity(intent);
                }
                else if(integer==2)//UPDATE WAS UNSUCCESSFUL
                {
                    Toast.makeText(DetailsActivity.this, getString(R.string.unable_to_update_details), Toast.LENGTH_LONG).show();
                }

            }
        });
    }










    public void setUpforStudent()
    {
        //showing dateofBirth
        binding.dob.setVisibility(View.VISIBLE);
        binding.datePickerActions.setVisibility(View.VISIBLE);

        //Change religion question and ADD the extra religion question

        binding.religionTitle2.setText(getString(R.string.similarCounsellor));
        binding.religionTitle3.setVisibility(View.VISIBLE);
        binding.religiousCounsellorPrefer.setVisibility(View.VISIBLE);

        //HIDE everything under duration

        binding.durationHeading.setVisibility(View.GONE);
        binding.experienceHeading.setVisibility(View.GONE);
        binding.experience.setVisibility(View.GONE);
        binding.counsellorHours.setVisibility(View.GONE);


    }







    public void setUpForCounsellor()
    {
        //hiding dateofBirth
        binding.dob.setVisibility(View.GONE);
        binding.datePickerActions.setVisibility(View.GONE);

        //Change religion question and remove the extra religion question

        binding.religionTitle2.setText(getString(R.string.counsellorFaith));
        binding.religionTitle3.setVisibility(View.GONE);
        binding.religiousCounsellorPrefer.setVisibility(View.GONE);

        //show everything under duration

        binding.durationHeading.setVisibility(View.VISIBLE);
        binding.experienceHeading.setVisibility(View.VISIBLE);
        binding.experience.setVisibility(View.VISIBLE);
        binding.counsellorHours.setVisibility(View.VISIBLE);
    }

}