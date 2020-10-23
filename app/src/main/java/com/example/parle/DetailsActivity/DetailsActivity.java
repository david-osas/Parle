package com.example.parle.DetailsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parle.ConcentrateActivity;
import com.example.parle.Models.Student;
import com.example.parle.R;
import com.example.parle.databinding.ActivityDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailsBinding binding;
    private EditText datePicker;
    Calendar mCalendar;
    DatePickerDialog mPicker;
    private String mCountry;
    private String mState;
    private String mPhone_number;
    private String mDate_of_birth;
    private String mReligionText;
    private boolean mPreferredCounsellor;
    private boolean mPreferredSession;

    private FirebaseFirestore db;
    private FirebaseUser mFirebaseUser;
    private Student mStudent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mStudent = new Student();
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCountry = binding.country.getText().toString();
                mState  = binding.State.getText().toString();
                mPhone_number = binding.phone.getText().toString();
                mDate_of_birth = binding.datePickerActions.getText().toString();
                mReligionText = binding.religion.getText().toString();
                mPreferredCounsellor = (binding.yesOrNo.getText().toString() == getResources().getStringArray(R.array.yes_or_no_list)[0]);
                mPreferredSession = (binding.religiousCounsellorPrefer.getText().toString() == getResources().getStringArray(R.array.counselor_preference_list)[0]);

                if(mCountry.isEmpty() || mState.isEmpty() || mPhone_number.isEmpty() || mDate_of_birth.isEmpty()
                || mReligionText.isEmpty() || binding.religiousCounsellorPrefer.getText().toString().isEmpty()
                || binding.yesOrNo.getText().toString().isEmpty())
                {
                    Toast.makeText(DetailsActivity.this, R.string.please_enter_all_details ,Toast.LENGTH_LONG).show();
                }
                else
                {
                    db = FirebaseFirestore.getInstance();
                    mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    db.collection("students").document(mFirebaseUser.getUid())
                            .update("country",mCountry,
                                    "state",mState,
                                    "phoneNumber",mPhone_number,
                                    "dateOfBirth",mDate_of_birth,
                                    "religion",mReligionText,
                                    "similarReligionCounselor",mPreferredCounsellor,
                                    "spiritualCounselling",mPreferredSession)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(DetailsActivity.this, getString(R.string.details_update_succesful), Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(DetailsActivity.this, ConcentrateActivity.class));
                                    }
                                    else
                                    {
                                        Toast.makeText(DetailsActivity.this, getString(R.string.unable_to_update_details), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                }

            }
        });

        datePicker = binding.datePickerActions;
        mCalendar = Calendar.getInstance();


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
                        }, year, month, day);
                mPicker.show();
            }
        });

        AutoCompleteTextView religion = binding.religion,
                counselor_religion = binding.religiousCounsellorPrefer,
                yes_or_no = binding.yesOrNo;
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

}