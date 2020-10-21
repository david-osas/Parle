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
import android.widget.TextView;
import android.widget.Toast;

import com.example.parle.databinding.ActivityConcentrateBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ConcentrateActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    public static TextView noSelected;

    String[] mList = getResources().getStringArray(R.array.concentrate_points_list);
    private SpecialtyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concentrate);

        mRecyclerView = findViewById(R.id.specialties);
        mAdapter = new SpecialtyAdapter(this,mList);
        mRecyclerView.setAdapter(mAdapter);
        noSelected = findViewById(R.id.no_selected);

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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("students").document(user.getUid()).update("concentrate",mAdapter.getChosenOnes())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ConcentrateActivity.this, R.string.details_update_succesful, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ConcentrateActivity.this, PinActivity.class);
                            intent.putExtra("action","create");
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(ConcentrateActivity.this, R.string.unable_to_update_details, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }



}

class
SpecialtyAdapter extends RecyclerView.Adapter<SpecialtyAdapter.ViewHolder>{
    Context mContext;
    String[] mList;
    ArrayList<String> selected;
    String[] chosenOnes = {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};

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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
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
                    chosenOnes[position] = "0";

                }
                else
                {
                    selected.add(button.getText().toString());
                    button.setBackgroundTintList(ColorStateList.valueOf(mContext.getColor(colors[n])));
                    button.setTextColor(mContext.getColor(android.R.color.white));
                    button.setTypeface(bold);
                    chosenOnes[position] = "1";
                }

                ConcentrateActivity.noSelected.setText(selected.size()+" "+ mContext.getString(R.string.selected));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.length;
    }

    public String getChosenOnes()
    {
        String ans = "";
        for(String i : chosenOnes)
            ans = ans+i;
        return ans;
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