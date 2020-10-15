package com.example.parle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ConcentrateActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;

    String[] mList = {"Depression",
            "Stress and Anxiety",
            "Coping with Addictions",
            "Anxiety",
            "Family Issues",
            "Trauma and abuse",
            "Relationship issues",
            "Sexuality issues",
            "Coping with grief and loss",
            "Eating disorder",
            "Sleeping disorder",
            "Motivation, self esteem and confidence",
            "Fatigue",
            "Anger Management",
            "Career choices",
            "Bipolar diosrder",
            "Concentration, memory and focus (ADHD)",
            "Executive and Professional Coaching",
            "Life Changes",
            "Parenting Issues"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concentrate);

        mRecyclerView = findViewById(R.id.specialties);
        mRecyclerView.setAdapter(new SpecialtyAdapter(this,mList));

        mGridLayoutManager = new GridLayoutManager(this,10);
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
        mRecyclerView.setLayoutManager(mGridLayoutManager);

    }

    public void moveToNext(View view){
        Intent intent = new Intent(this, PinActivity.class);
        startActivity(intent);
    }



}

class
SpecialtyAdapter extends RecyclerView.Adapter<SpecialtyAdapter.ViewHolder>{
    Context mContext;
    String[] mList;
    ArrayList<String> selected;

    int[] colors = {R.color.dark_wine,R.color.darker_orange,R.color.dark_blue_back};
    public SpecialtyAdapter(Context context,String[] list) {
        mContext = context;
        mList = list;
        selected = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.concentrate_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.button.setText(mList[position]);
        final Typeface bold = ResourcesCompat.getFont(mContext,R.font.montserrat_bold);
        final Typeface normal = ResourcesCompat.getFont(mContext,R.font.montserrat);
        final Button button = holder.button;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = new Random().nextInt(3);
                if(selected.contains(button.getText().toString()))
                {
                    selected.remove(button.getText().toString());
                    button.setBackgroundTintList(null);
                    button.setTextColor(mContext.getColor(android.R.color.black));
                    button.setTypeface(normal);
                }
                else
                {
                    selected.add(button.getText().toString());
                    button.setBackgroundTintList(ColorStateList.valueOf(mContext.getColor(colors[n])));
                    button.setTextColor(mContext.getColor(android.R.color.white));
                    button.setTypeface(bold);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.specialty);


        }



    }

}