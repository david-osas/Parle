package com.example.parle;

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

import com.example.parle.databinding.ActivityDetailsBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailsBinding binding;
    private EditText datePicker;
    Calendar mCalendar;
    DatePickerDialog mPicker;
    private String selection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsActivity.this, ConcentrateActivity.class));
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
        ArrayList<String> religions = new ArrayList<>(),
                yesNo = new ArrayList<>(),
                counselor_preference = new ArrayList<>();

        religions.add("Christianity");religions.add("Islam");religions.add("Others");
        ArrayAdapter<String> ReligionAdapter = new ArrayAdapter<>(DetailsActivity.this, android.R.layout.simple_list_item_1,religions);
        religion.setAdapter(ReligionAdapter);
        religion.setCursorVisible(false);
        setListener(religion);

        yesNo.add("Yes");yesNo.add("No");
        ArrayAdapter<String> YesNoAdapter = new ArrayAdapter<>(DetailsActivity.this, android.R.layout.simple_list_item_1,yesNo);
        yes_or_no.setAdapter(YesNoAdapter);
        yes_or_no.setCursorVisible(false);
        setListener(yes_or_no);

        counselor_preference.add("Yes I would like that"); counselor_preference.add("No, I would prefer to keep our sessions clinical");
        ArrayAdapter<String> CounselorAdapter = new ArrayAdapter<>(DetailsActivity.this, android.R.layout.simple_list_item_1,counselor_preference);
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
                selection = (String) parent.getItemAtPosition(position);

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