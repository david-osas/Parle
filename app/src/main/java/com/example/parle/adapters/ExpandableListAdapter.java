package com.example.parle.adapters;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.core.app.NotificationCompatSideChannelService;
import androidx.core.content.res.ResourcesCompat;

import com.example.parle.Constants;
import com.example.parle.R;
import com.example.parle.databinding.ChildItemBinding;
import com.example.parle.activities.detailsActivity.DetailsActivity;
import com.example.parle.models.Counsellor;
import com.example.parle.models.Student;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter implements Constants {
    /*
    * For the profile page in the app*/

    View mView; //
    Context mContext;
    ExpandableListView mExpandableListView;
    String[] headers;
    int[] layouts = {R.layout.child_item,R.layout.contact_and_location,R.layout.faith_and_religion,R.layout.child_item,R.layout.child_item};
    int[] children = {
            R.id.personal_details,
            R.id.contact_and_location,
            R.id.faith_and_religion,
            R.id.duration,
            R.id.documents
    };

    private EditText datePicker;
    Calendar mCalendar;
    DatePickerDialog mPicker;
    String selection;
    String mUser;
    private TimePickerDialog mTimePickerDialog;

    Student mStudent;
    Counsellor mCounsellor;

    HashMap<String,String> allValues;

    public ExpandableListAdapter(Context context, ExpandableListView expandableListView ,String user,Object object){
        mContext = context;//context of calling activity
        LayoutInflater layoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mExpandableListView = expandableListView;
        mView = layoutInflater.inflate(R.layout.child_item, null);//the view that contains all necessary views for the list
        mUser = user;//student or counsellor
        allValues = new HashMap<>();
        if(user.equals("student"))//set the group titles based on whether the user is a student or counsellor
        {
            headers = mContext.getResources().getStringArray(R.array.expandableListStudents);
            mStudent = (Student) object;
            allValues.put(FULL_NAME,mStudent.getFullName());
            allValues.put(EMAIL,mStudent.getEmail());
            allValues.put(USERNAME,mStudent.getUsername());
            allValues.put(COUNTRY,mStudent.getCountry());
            allValues.put(STATE,mStudent.getState());
            allValues.put(PHONE_NUMBER,mStudent.getPhoneNumber());
            allValues.put(DATE_OF_BIRTH,mStudent.getDateOfBirth());
            allValues.put(RELIGION,mStudent.getReligion());

        }
        else
        {
            headers = mContext.getResources().getStringArray(R.array.expandableListCounsellors);
            mCounsellor = (Counsellor) object;
            allValues.put(FULL_NAME,mCounsellor.getFullName());
            allValues.put(PROFESSION,mCounsellor.getProfession());
            allValues.put(EMAIL,mCounsellor.getEmail());
            allValues.put(COUNTRY,mCounsellor.getCountry());
            allValues.put(STATE,mCounsellor.getState());
            allValues.put(PHONE_NUMBER,mCounsellor.getPhoneNumber());
            allValues.put(RELIGION,mCounsellor.getReligion());
            allValues.put(YEARS_OF_EXPERIENCE,mCounsellor.getYearOfExperience());
            allValues.put(AVAILABLE_HOURS,mCounsellor.getAvailableHours());
            allValues.put(START_TIME,mCounsellor.getStartTime());
        }


    }

    @Override
    public int getGroupCount() {
        return headers.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return headers[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        return layouts[i];
    }

    @Override
    public long getGroupId(int groupId) {
        return groupId;
    }

    @Override
    public long getChildId(int groupPosition, int itemPosition) {
        return itemPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean expanded, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //inflate the required header
            view = infalInflater.inflate(R.layout.list_group_header, null);
        }


        TextView txtListChild = (TextView) view
                .findViewById(R.id.group_header);

        txtListChild.setText(headers[i]);//set the text in the header to the right value

        Typeface bold = ResourcesCompat.getFont(mContext,R.font.montserrat_bold);
        Typeface normal = ResourcesCompat.getFont(mContext,R.font.montserrat);



        ImageView imageView = view.findViewById(R.id.arrow);
        LinearLayout linearLayout = view.findViewById(R.id.container);
        if (expanded)
        {
            //if the view is expanded then addd some padding between the bottom of the group and the content
            view.setPadding(0, 0, 0, 24);
            linearLayout.setBackgroundTintList(mContext.getColorStateList( R.color.darker_orange));
            txtListChild.setTextColor(mContext.getColor(android.R.color.white));
            txtListChild.setTypeface(bold);
            imageView.setImageResource(R.drawable.arrow_up);
        }

        else
        {
            //The padding is bigger when it is closed
            view.setPadding(0, 0, 0, 32);
            linearLayout.setBackgroundTintList(mContext.getColorStateList( R.color.light_orange));
            txtListChild.setTextColor(mContext.getColor(android.R.color.black));
            txtListChild.setTypeface(normal);
            imageView.setImageResource(R.drawable.arrow_down);
            if(i==headers.length-1)
                view.setPadding(0, 0, 0, 0);
        }



        view.setBackgroundColor(mContext.getColor(R.color.white));
        return view;
    }

    @Override
    public View getChildView(int groupIndex, int i1, boolean b, View view, ViewGroup viewGroup) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.child_item,null);

       // mExpandableListView.setDividerHeight(0);



        datePicker = view.findViewById(R.id.date_picker_actions);
        mCalendar = Calendar.getInstance();

        //for datepicker in the list
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                mPicker = new DatePickerDialog(mView.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                datePicker.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                mPicker.show();
            }
        });


        //for timepicker in the counsellors list
        final EditText timePickerEdit = view.findViewById(R.id.time_picker);

        timePickerEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                mTimePickerDialog = new TimePickerDialog(mContext,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                int i=0;
                                timePickerEdit.setText(DetailsActivity.get12HourTime(hour));
                            }
                        },hour,minute,false);
                mTimePickerDialog.show();

            }
        });



        //for all the autocomplete textviews I used
        AutoCompleteTextView religion = view.findViewById(R.id.religion),
                counselor_religion = view.findViewById(R.id.religious_counsellor_prefer),
                yes_or_no = view.findViewById(R.id.yes_or_no),
                experience = view.findViewById(R.id.experience),
                availableHours = view.findViewById(R.id.availableHours);
        List<String> religions,
                yesNo,
                counselor_preference;

        religions = Arrays.asList(mContext.getResources().getStringArray(R.array.religions_list));
        ArrayAdapter<String> ReligionAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1,religions);
        religion.setAdapter(ReligionAdapter);
        religion.setCursorVisible(false);
        setListener(religion);

        yesNo= Arrays.asList(mContext.getResources().getStringArray(R.array.yes_or_no_list));
        ArrayAdapter<String> YesNoAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1,yesNo);
        yes_or_no.setAdapter(YesNoAdapter);
        yes_or_no.setCursorVisible(false);
        setListener(yes_or_no);

        counselor_preference= Arrays.asList(mContext.getResources().getStringArray(R.array.counselor_preference_list));;
        ArrayAdapter<String> CounselorAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1,counselor_preference);
        counselor_religion.setAdapter(CounselorAdapter);
        counselor_religion.setCursorVisible(false);
        setListener(counselor_religion);

        experience.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,mContext.getResources().getStringArray(R.array.yearsOfExperience)));
        setListener(experience);


        availableHours.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,mContext.getResources().getStringArray(R.array.availableHours)));
        setListener(availableHours);




        prepareChild(view,groupIndex);
        if(mUser.equals("student"))
        {
            setUpStudent(view);
        }
        else
        {
            setUpCounsellor(view);
        }
        view.setPadding(0,0,0,32);
        return view;
    }



    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public void setListener(AutoCompleteTextView acTV)
    {
        //sets onclicklistener for autocompletetextview
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

    public void prepareChild(View view, int position)
    {
        if(mUser.equals("student"))
        {
            view.findViewById(R.id.profession).setVisibility(View.GONE);
            view.findViewById(R.id.professionQuestion).setVisibility(View.GONE);

            view.findViewById(R.id.dob).setVisibility(View.VISIBLE);
            view.findViewById(R.id.date_picker_actions).setVisibility(View.VISIBLE);

            ((TextView)view.findViewById(R.id.religionTitle2)).setText(mContext.getString(R.string.similarCounsellor));
            view.findViewById(R.id.religionTitle3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.religious_counsellor_prefer).setVisibility(View.VISIBLE);

        }

        else
        {
            view.findViewById(R.id.username).setVisibility(View.GONE);
            view.findViewById(R.id.usernameQuestion).setVisibility(View.GONE);

            view.findViewById(R.id.dob).setVisibility(View.GONE);
            view.findViewById(R.id.date_picker_actions).setVisibility(View.GONE);

            ((TextView)view.findViewById(R.id.religionTitle2)).setText(mContext.getString(R.string.counsellorFaith));
            view.findViewById(R.id.religionTitle3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.religious_counsellor_prefer).setVisibility(View.VISIBLE);

        }
        for(int i=0;i<children.length;i++)
        {
            setVisibility(view,children[i],position==i);
        }
    }

    public void setVisibility(View view, int id, boolean visible)
    {
        if(visible)
            view.findViewById(id).setVisibility(View.VISIBLE);
        else
            view.findViewById(id).setVisibility(View.GONE);
    }

    public void setUpStudent(View view)
    {
        EditText fullname = view.findViewById(R.id.fullname);
        EditText email = view.findViewById(R.id.email);
        EditText username = view.findViewById(R.id.username);

        EditText country = view.findViewById(R.id.country);
        EditText state = view.findViewById(R.id.State);
        EditText phone = view.findViewById(R.id.phone);
        EditText dob = view.findViewById(R.id.date_picker_actions);

        AutoCompleteTextView religion = view.findViewById(R.id.religion);

        fullname.setText(allValues.get(FULL_NAME));
        email.setText(allValues.get(EMAIL));
        username.setText(allValues.get(USERNAME));

        country.setText(allValues.get(COUNTRY));
        state.setText(allValues.get(STATE));
        phone.setText(allValues.get(PHONE_NUMBER));
        dob.setText(allValues.get(DATE_OF_BIRTH));
        religion.setText(allValues.get(RELIGION));
    }

    public void setUpCounsellor(View view)
    {
        EditText fullname = view.findViewById(R.id.fullname);
        EditText email = view.findViewById(R.id.email);
        EditText profession = view.findViewById(R.id.profession);

        EditText country = view.findViewById(R.id.country);
        EditText state = view.findViewById(R.id.State);
        EditText phone = view.findViewById(R.id.phone);

        AutoCompleteTextView experience = view.findViewById(R.id.experience);
        EditText timePicker = view.findViewById(R.id.time_picker);
        AutoCompleteTextView available = view.findViewById(R.id.availableHours);


        AutoCompleteTextView religion = view.findViewById(R.id.religion);

        fullname.setText(allValues.get(FULL_NAME));
        email.setText(allValues.get(EMAIL));
        profession.setText(allValues.get(PROFESSION));

        country.setText(allValues.get(COUNTRY));
        state.setText(allValues.get(STATE));
        phone.setText(allValues.get(PHONE_NUMBER));
        religion.setText(allValues.get(RELIGION));

        timePicker.setText(allValues.get(START_TIME));
        experience.setText(allValues.get(YEARS_OF_EXPERIENCE));
        available.setText(allValues.get(AVAILABLE_HOURS));
    }
}
