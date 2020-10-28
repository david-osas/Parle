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

import androidx.core.content.res.ResourcesCompat;

import com.example.parle.R;
import com.example.parle.databinding.ChildItemBinding;
import com.example.parle.detailsActivity.DetailsActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    ArrayList<String> mList;
    View mView;
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

    public ExpandableListAdapter(Context context, ExpandableListView expandableListView ,String user){
        mContext = context;
        LayoutInflater layoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mExpandableListView = expandableListView;
        mView = layoutInflater.inflate(R.layout.child_item, null);
        mUser = user;
        if(user.equals("student"))
        {
            headers = mContext.getResources().getStringArray(R.array.expandableListStudents);
        }
        else
        {
            headers = mContext.getResources().getStringArray(R.array.expandableListCounsellors);
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
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.list_group_header, null);
        }


        TextView txtListChild = (TextView) view
                .findViewById(R.id.group_header);

        txtListChild.setText(headers[i]);

        Typeface bold = ResourcesCompat.getFont(mContext,R.font.montserrat_bold);
        Typeface normal = ResourcesCompat.getFont(mContext,R.font.montserrat);



        ImageView imageView = view.findViewById(R.id.arrow);
        LinearLayout linearLayout = view.findViewById(R.id.container);
        if (b)
        {
            view.setPadding(0, 0, 0, 24);
            linearLayout.setBackgroundTintList(mContext.getColorStateList( R.color.darker_orange));
            txtListChild.setTextColor(mContext.getColor(android.R.color.white));
            txtListChild.setTypeface(bold);
            imageView.setImageResource(R.drawable.arrow_up);
        }

        else
        {
            view.setPadding(0, 0, 0, 32);
            linearLayout.setBackgroundTintList(mContext.getColorStateList( R.color.light_orange));
            txtListChild.setTextColor(mContext.getColor(android.R.color.black));
            txtListChild.setTypeface(normal);
            imageView.setImageResource(R.drawable.arrow_down);
            if(i==headers.length-1)
                view.setPadding(0, 0, 0, 0);
        }



        //mExpandableListView.setDividerHeight(100);
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
        view.setPadding(0,0,0,32);
        return view;
    }



    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
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

    public void prepareChild(View view, int position)
    {
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
}
